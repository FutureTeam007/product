package com.ei.itop.custmgnt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ailk.dazzle.exception.BizException;
import com.ailk.dazzle.util.json.JSONUtils;
import com.ailk.dazzle.util.type.VarTypeConvertUtils;
import com.ailk.dazzle.util.web.ActionUtils;
import com.ei.itop.common.dbentity.CcCust;
import com.ei.itop.common.dbentity.CcUser;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.custmgnt.service.CustMgntService;
import com.ei.itop.custmgnt.service.UserService;

@Controller
@RequestMapping("/custmgnt/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private CustMgntService custMgntService;
	

	@RequestMapping("/get")
	public void queryUserInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long userId = VarTypeConvertUtils.string2Long(request
				.getParameter("userId"));
		CcUser user = userService.queryUser(userId);
		String jsonData = JSONUtils.toJSONString(user);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}

	@RequestMapping("/list")
	public void queryUserList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long orgId = SessionUtil.getOpInfo().getOrgId();
		long custId = VarTypeConvertUtils.string2Long(request
				.getParameter("custId"));
		List<CcUser> users = userService.queryUserList(orgId, custId);
		String jsonData = JSONUtils.toJSONString(users);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}
	
	@RequestMapping("/list/all")
	public void queryAllUserList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long orgId = SessionUtil.getOpInfo().getOrgId();
		int page = VarTypeConvertUtils.string2Integer(
				request.getParameter("page"), 0);
		// 获取分页大小
		int pageSize = VarTypeConvertUtils.string2Integer(
				request.getParameter("rows"), 0);
		// 获取分页起始位置
		long startIndex = (page - 1) * pageSize + 1;
		// 获得查询条件
		String userCode = request.getParameter("userCode");
		if(StringUtils.isEmpty(userCode)){
			userCode = null;
		}else{
			userCode = ActionUtils.transParam(userCode, "UTF-8");
		}
		String userName = request.getParameter("userName");
		if(StringUtils.isEmpty(userName)){
			userName = null;
		}else{
			userName = ActionUtils.transParam(userName, "UTF-8");
		}
		// 客户ID
		String custId = request.getParameter("custId");
		//根据客户ID取得所有子客户(含当前客户)
		List<CcCust> custs = custMgntService.getSubCusts(VarTypeConvertUtils.string2Long(custId));
		long[] custIds = new long[custs.size()];
		for(int i=0;i<custs.size();i++){
			custIds[i]=custs.get(i).getCcCustId();
		}
		//获取数据条数
		long count = userService.queryAllUserListCount(orgId, custIds, userCode, userName, startIndex, pageSize);
		//获取数据
		List<CcUser> users = userService.queryAllUserList(orgId, custIds,userCode,userName,startIndex,pageSize);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", count);
		result.put("rows", users);
		String jsonData = JSONUtils.toJSONString(result);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}

	@RequestMapping("/changepwd")
	public void changePasswd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 取得用户ID
		long opId = SessionUtil.getOpInfo().getOpId();
		// 旧密码
		String oldPassword = request.getParameter("oldPassword");
		// 新密码
		String newPassword = request.getParameter("newPassword");
		// 查出旧密码
		String oldPasswordStored = userService.queryUser(opId).getLoginPasswd();
		if (!oldPasswordStored.equals(oldPassword)) {
			throw new BizException(SessionUtil.getRequestContext().getMessage(
					"i18n.usercenter.profile.OldPasswordIncorrect"));
		}
		userService.changeLoginPasswd(opId, newPassword);
	}

	@RequestMapping("/changebaseinfo")
	public ModelAndView changeBaseInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		// 取得注册参数
		String companyId = request.getParameter("companyId");
		String companyName = request.getParameter("companyName");
		String jobRole = request.getParameter("jobRole");
		String chineseName = request.getParameter("chineseName");
		String givenName = request.getParameter("givenName");
		String familyName = request.getParameter("familyName");
		String gender = request.getParameter("gender");
		String mobileNo = request.getParameter("mobileNo");
		String areaCode = request.getParameter("areaCode");
		String phoneNo = request.getParameter("phoneNo");
		// 封装CcUser
		CcUser user = new CcUser();
		user.setCcUserId(SessionUtil.getOpInfo().getOpId());
		user.setOpName(chineseName);
		user.setCustName(companyName);
		user.setCcCustId(VarTypeConvertUtils.string2Long(companyId));
		user.setDefCcCustId(VarTypeConvertUtils.string2Long(companyId));
		user.setOpKind(VarTypeConvertUtils.string2Long(jobRole));
		user.setFirstName(givenName);
		user.setLastName(familyName);
		user.setGender(VarTypeConvertUtils.string2Short(gender));
		user.setMobileNo(mobileNo);
		user.setOfficeTel(areaCode + "-" + phoneNo);
		try {
			userService.modifyUserInfo(user);
			mav.addObject(
					"msg",
					SessionUtil.getRequestContext().getMessage(
							"i18n.usercenter.profile.ChangeSuccess"));
		} catch (Exception e) {
			mav.addObject(
					"msg",
					SessionUtil.getRequestContext().getMessage(
							"i18n.usercenter.profile.ChangeFailure"));
		}
		mav.setViewName("/page/usercenter/changeBaseInfo");
		return mav;
	}
	
	@RequestMapping("/modifyuserinfo")
	public void modifyUserInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//获取前台传入参数
		long ccCustId = VarTypeConvertUtils.string2Long(request.getParameter("ccCustId"));
		long ccUserId = VarTypeConvertUtils.string2Long(request.getParameter("ccUserId"));
		String custName = request.getParameter("custName");
		long defCcCustId = ccCustId;
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String gender = request.getParameter("gender");
		String loginCode = request.getParameter("loginCode");
		String loginPasswd = request.getParameter("loginPasswd");
		String mobileNo = request.getParameter("mobileNo");
		String officeTel = request.getParameter("officeTel");
		String opKind = request.getParameter("opKind");
		String opName = request.getParameter("opName");
		// 封装CcUser
		CcUser user = new CcUser();
		user.setCcUserId(ccUserId);
		user.setOpName(opName);
		user.setCustName(custName);
		user.setCcCustId(ccCustId);
		user.setLoginCode(loginCode);
		user.setLoginPasswd(loginPasswd);
		user.setDefCcCustId(defCcCustId);
		user.setOpKind(VarTypeConvertUtils.string2Long(opKind));
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setGender(VarTypeConvertUtils.string2Short(gender));
		user.setMobileNo(mobileNo);
		user.setOfficeTel(officeTel);
		userService.modifyUserInfo(user);
	}
	
	@RequestMapping("/modifyState")
	public void modifyState(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 取得参数
		String userId = request.getParameter("userId");
		String state = request.getParameter("state");
		// 封装CcUser
		CcUser user = new CcUser();
		user.setCcUserId(VarTypeConvertUtils.string2Long(userId));
		user.setState(VarTypeConvertUtils.string2Long(state));
		userService.modifyUserInfo(user);
	}
}
