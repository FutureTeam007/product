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
	public ModelAndView registerPage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.addObject("accountMsg","请使用公司邮箱");
		mav.setViewName("/page/register/registerStep1");
		return mav;
	}
	
	/**
	 * 进入新增或编辑页面
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/doRegister")
	public ModelAndView userRegister(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		//取得注册参数
		String acountNo = request.getParameter("acountNo");
		String passwd = request.getParameter("passwd");
		String companyId = request.getParameter("companyId");
		String companyName = request.getParameter("companyName");
		//String deptId = request.getParameter("deptId");
		String chineseName = request.getParameter("chineseName");
		String givenName = request.getParameter("givenName");
		String familyName = request.getParameter("familyName");
		String gender = request.getParameter("gender");
		String mobileNo = request.getParameter("mobileNo");
		String areaCode = request.getParameter("areaCode");
		String phoneNo = request.getParameter("phoneNo");
		//检查是否已存在该账号
		boolean exist = registerService.checkLoginCodeIsExist(acountNo);
		if(!exist){
			//封装注册实体
			RegisterInfo ri = new RegisterInfo();
			ri.setOpCode(acountNo);
			ri.setCcCustId(VarTypeConvertUtils.string2Long(companyId));
			ri.setCustName(companyName);
			ri.setFirstName(givenName);
			ri.setLastName(familyName);
			ri.setOpName(chineseName);
			ri.setDefCcCustId(VarTypeConvertUtils.string2Long(companyId));
			ri.setGender(VarTypeConvertUtils.string2Short(gender));
			ri.setLoginCode(acountNo);
			ri.setLoginPasswd(passwd);
			ri.setMobileNo(mobileNo);
			ri.setOfficeTel(areaCode+"-"+phoneNo);
			ri.setState(-1L);//未激活
			//注册
			registerService.userRegister(ri);
//			//生成激活链接
//			String activeCode = Encrypt.encrypt(acountNo);
//			StringBuffer activeURL = new StringBuffer();
//			activeURL.append(request.getScheme()+"://");
//			activeURL.append(request.getServerName());
//			activeURL.append("80".equals(request.getServerPort())?"/":(":"+request.getServerPort()+"/"));
//			activeURL.append(request.getContextPath());
//			activeURL.append("/doActive?key="+activeCode);
//			//发送邮件
			//TODO
			mav.setViewName("/page/register/registerStep2");
			return mav;
		}else{
			mav.addObject("acountNo",acountNo);
			mav.addObject("chineseName",chineseName);
			mav.addObject("givenName",givenName);
			mav.addObject("familyName",familyName);
			mav.addObject("mobileNo",mobileNo);
			mav.addObject("areaCode",areaCode);
			mav.addObject("phoneNo",phoneNo);
			mav.addObject("accountMsg","<span class='form-error-alert'>邮箱被注册，请选用其他邮箱</span>");
			mav.setViewName("/page/register/registerStep1");
			return mav;
		}
		
		
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