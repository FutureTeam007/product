package com.ei.itop.custmgnt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ailk.dazzle.exception.BizException;
import com.ailk.dazzle.util.json.JSONUtils;
import com.ailk.dazzle.util.sec.Encrypt;
import com.ailk.dazzle.util.type.VarTypeConvertUtils;
import com.ei.itop.common.dbentity.CcUser;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.custmgnt.service.UserService;
import com.ei.itop.register.bean.RegisterInfo;

@Controller
@RequestMapping("/custmgnt/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/get")
	public void queryUserInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		long userId = VarTypeConvertUtils.string2Long(request.getParameter("userId"));
		CcUser user = userService.queryUser(userId);
		String jsonData = JSONUtils.toJSONString(user);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}
	
	@RequestMapping("/changepwd")
	public void changePasswd(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//取得用户ID
		long opId = SessionUtil.getOpInfo().getOpId();
		//旧密码
		String oldPassword = request.getParameter("oldPassword");
		//新密码
		String newPassword = request.getParameter("newPassword");
		//查出旧密码
		String oldPasswordStored = userService.queryUser(opId).getLoginPasswd();
		if(!oldPasswordStored.equals(oldPassword)){
			throw new BizException("旧密码不正确，请重新输入");
		}
		userService.changeLoginPasswd(opId, newPassword);
	}
	
	@RequestMapping("/changebaseinfo")
	public ModelAndView changeBaseInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		//取得注册参数
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
		//封装CcUser
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
		user.setOfficeTel(areaCode+"-"+phoneNo);
		try {
			userService.modifyUserInfo(user);
			mav.addObject("msg","修改成功");
		} catch (Exception e) {
			mav.addObject("msg","修改失败,请联系系统管理员");
		}
		mav.setViewName("/page/usercenter/changeBaseInfo");
		return mav;
	}
}
