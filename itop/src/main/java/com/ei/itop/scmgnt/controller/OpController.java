package com.ei.itop.scmgnt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ailk.dazzle.exception.BizException;
import com.ailk.dazzle.util.json.JSONUtils;
import com.ailk.dazzle.util.type.VarTypeConvertUtils;
import com.ei.itop.common.dbentity.CcUser;
import com.ei.itop.common.dbentity.ScOp;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.scmgnt.service.OpService;

@Controller
@RequestMapping("/op")
public class OpController {

	@Autowired
	private OpService opService;
	/**
	 * 查询列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/changepwd")
	public void changePasswd(HttpServletRequest request,HttpServletResponse response) throws Exception{
		long opId = SessionUtil.getOpInfo().getOpId();
		//旧密码
		String oldPassword = request.getParameter("oldPassword");
		//新密码
		String newPassword = request.getParameter("newPassword");
		//查出旧密码
		String oldPasswordStored = opService.queryScOp(opId).getLoginPasswd();
		if(!oldPasswordStored.equals(oldPassword)){
			throw new BizException("旧密码不正确，请重新输入");
		}
		opService.changeLoginPasswd(opId, newPassword);
	}
	
	/**
	 * 查询列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public void queryAllOp(HttpServletRequest request,HttpServletResponse response) throws Exception{
		long orgId = SessionUtil.getOpInfo().getOrgId();
		List<ScOp> opList = opService.queryAllOp(orgId);
		String jsonData = JSONUtils.toJSONString(opList);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}
	
	/**
	 * 查询单个顾问信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/get")
	public void querySingleOp(HttpServletRequest request,HttpServletResponse response) throws Exception{
		long opId = VarTypeConvertUtils.string2Long(request.getParameter("opId"));
		String jsonData = JSONUtils.toJSONString(opService.queryScOp(opId));
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}
	/**
	 * 修改顾问信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/changebaseinfo")
	public ModelAndView changeBaseInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		//取得注册参数
		String opCode = request.getParameter("opCode");
		String jobRole = request.getParameter("jobRole");
		String chineseName = request.getParameter("chineseName");
		String givenName = request.getParameter("givenName");
		String familyName = request.getParameter("familyName");
		String gender = request.getParameter("gender");
		String mobileNo = request.getParameter("mobileNo");
		String areaCode = request.getParameter("areaCode");
		String phoneNo = request.getParameter("phoneNo");
		//封装Op
		ScOp op = new ScOp();
		op.setFirstName(givenName);
		op.setGender(VarTypeConvertUtils.string2Short(gender));
		op.setLastName(familyName);
		op.setMobileNo(mobileNo);
		op.setOfficeTel(areaCode+"-"+phoneNo);
		op.setOpCode(opCode);
		op.setOpKind(VarTypeConvertUtils.string2Short(jobRole));
		op.setOpName(chineseName);
		op.setScOpId(SessionUtil.getOpInfo().getOpId());
		try {
			opService.modifyScOp(op);
			mav.addObject("msg","修改成功");
		} catch (Exception e) {
			mav.addObject("msg","修改失败,请联系系统管理员");
		}
		mav.setViewName("/page/opcenter/changeBaseInfo");
		return mav;
	}
}
