package com.ei.itop.register.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ailk.dazzle.util.sec.Encrypt;
import com.ailk.dazzle.util.type.VarTypeConvertUtils;
import com.ei.itop.common.dbentity.CcCust;
import com.ei.itop.common.dbentity.CcUser;
import com.ei.itop.register.bean.RegisterInfo;
import com.ei.itop.register.service.RegisterService;

@Controller
@RequestMapping("")
public class RegisterPageController {
	
	@Autowired
	private RegisterService registerService;
	
	/**
	 * 进入管理页面
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/register")
	public String registerPage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return "/page/register/registerStep1";
	}
	
	/**
	 * 进入新增或编辑页面
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/doRegister")
	public String userRegister(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//取得注册参数
		String acountNo = request.getParameter("acountNo");
		String passwd = request.getParameter("passwd");
		String companyId = request.getParameter("companyId");
		String companyName = request.getParameter("companyName");
		String orgId = request.getParameter("orgId");
		String orgName = request.getParameter("orgName");
		String deptId = request.getParameter("deptId");
		String chineseName = request.getParameter("chineseName");
		String givenName = request.getParameter("givenName");
		String familyName = request.getParameter("familyName");
		String gender = request.getParameter("gender");
		String mobileNo = request.getParameter("mobileNo");
		String phoneNo = request.getParameter("phoneNo");
		//封装注册实体
		RegisterInfo ri = new RegisterInfo();
		ri.setCcCustId(VarTypeConvertUtils.string2Long(companyId));
		ri.setCustName(companyName);
		ri.setFirstName(givenName);
		ri.setLastName(familyName);
		ri.setOpName(chineseName);
		ri.setDefCcCustId(VarTypeConvertUtils.string2Long(deptId));
		ri.setGender(VarTypeConvertUtils.string2Short(gender));
		ri.setLoginCode(acountNo);
		ri.setLoginPasswd(passwd);
		ri.setMobileNo(mobileNo);
		ri.setOfficeTel(phoneNo);
		ri.setScOrgId(VarTypeConvertUtils.string2Long(orgId));
		ri.setScOrgName(orgName);
		ri.setState(-1L);//未激活
		//注册
		registerService.userRegister(ri);
		//生成激活链接
		String activeCode = Encrypt.encrypt(acountNo);
		StringBuffer activeURL = new StringBuffer();
		activeURL.append(request.getScheme()+"://");
		activeURL.append(request.getServerName());
		activeURL.append("80".equals(request.getServerPort())?"/":(":"+request.getServerPort()+"/"));
		activeURL.append(request.getContextPath());
		activeURL.append("/doActive?key="+activeCode);
		//发送邮件
		
		
		return "/page/register/registerStep2";
	}
	
	/**
	 * 进入新增或编辑页面
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/doActive")
	public ModelAndView userAccountActive(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		String activeCode = request.getParameter("key");
		String loginCode = Encrypt.decrypt(activeCode);
		//账号是否存在
		CcUser user = registerService.getCcUserByLoginCode(loginCode);
		//用户不存在
		if(user==null){
			mav.addObject("msg", "账号不存在，请您先注册");
		}
		//用户存在
		else{
			registerService.activeAccount(user.getCcUserId());
			mav.addObject("msg", "账号已成功激活！");
		}
		mav.setViewName("/page/register/registerStep3");
		return mav;
	}
	
}
