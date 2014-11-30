var i18n={};
i18n.loading={};
i18n.dialog={};
i18n.upload={};
i18n.login={};
i18n.register={};
i18n.usercenter={};
i18n.opcenter={};
i18n.incident={};
i18n.transaction={};
i18n.custmgnt={};
i18n.scmgnt={};
//loading
i18n.loading.GridLoading="数据加载中，请稍后……";
i18n.loading.GirdDataEmpty="暂无数据记录";
//dialog
i18n.dialog.AlertTitle="提示";
//upload
i18n.upload.UploadFailure="上传失败了，可能是网络原因或系统故障，请稍后再试";
//login
i18n.login.AccountNoBlank="请填写登录账号";
i18n.login.PasswordBlank="请填写登录密码";
i18n.login.VerifyCodeBlank="请填写验证码";
i18n.login.SessionTimeout="出错了，可能是登录超时，请重新登录再试";
//register
i18n.register.AccountFormatError="请输入邮箱格式的登录账号";
i18n.register.PasswordFormatError="请输入至少4位的登录密码";
i18n.register.PasswordConfirmError="登录密码与确认密码不一致";
i18n.register.CompanyEmpty="请选择您所在的公司";
i18n.register.RoleEmpty="请选择工作角色";
i18n.register.ChineseNameEmpty="请填写中文姓名";
i18n.register.EnglishNameEmpty="请填写英文姓名";
i18n.register.GenderEmpty="请选择性别";
i18n.register.MobileNoFormatError="请输入11位的手机号码";
i18n.register.AreaCodeFormatError="请输入正确的区号";
i18n.register.OfficeTelFormatError="请输入正确的办公电话";

//usercenter
i18n.usercenter.profile={};
i18n.usercenter.profile.QueryInfoError="查询信息错误,可能无法修改！";
i18n.usercenter.pwdchange={};
i18n.usercenter.pwdchange.OldPasswordEmpty="请输入旧密码";
i18n.usercenter.pwdchange.NewPasswordEmpty="请输入新密码";
i18n.usercenter.pwdchange.NewPasswordFormatError="密码位数不能小于4位";
i18n.usercenter.pwdchange.ConfirmPasswordEmpty="请输入确认密码";
i18n.usercenter.pwdchange.PasswordNotSame="新密码和确认密码不一致";
i18n.usercenter.pwdchange.ChangeSuccess="修改成功,新密码下次登录生效";

//opcenter
i18n.opcenter.profile={};
i18n.opcenter.profile.QueryInfoError="查询信息错误,可能无法修改！";
i18n.opcenter.profile.OpCodeFormatError="工号必须是不小于4位的字母或数字";

//incident
i18n.incident.query={};
i18n.incident.query.QryConditionCustEmpty="请先选择客户！";
i18n.incident.query.DataTitleIncidentCode="事件系列号";
i18n.incident.query.DataTitleBrief="事件简述";
i18n.incident.query.DataTitleCustName="公司";
i18n.incident.query.DataTitleProdName="产品线";
i18n.incident.query.DataTitleClassValOp="类别";
i18n.incident.query.DataTitlePriorityVal="优先级";
i18n.incident.query.DataTitleComplexVal="复杂度";
i18n.incident.query.DataTitleAffectValOp="影响度";
i18n.incident.query.DataTitleItStateVal="状态";
i18n.incident.query.DataTitlePlObjectName="登记人";
i18n.incident.query.DataTitleRegisteTime="登记时间";
i18n.incident.query.DataTitlePlanFinishTime="计划完成时间";
i18n.incident.query.DataTitleScLoginName="责任顾问";
i18n.incident.query.DataTitleModifyDate="最近更新时间";
i18n.incident.query.DataTitleFinishTime="完成时间";
i18n.incident.query.DataTitleFeedbackVal="满意度";
i18n.incident.mgnt={};
i18n.incident.mgnt.CommitFailure="操作失败";
i18n.incident.mgnt.CommitIncidentSuccess="提交事件成功！";
i18n.incident.mgnt.RemoveIncidentConfirm="您确认要删除该事件？";
i18n.incident.mgnt.RemoveIncidentSuccess="删除事件成功！";
i18n.incident.mgnt.CloseIncidentSuccess="关闭事件成功！";
i18n.incident.mgnt.FeedbackIncidentSuccess="评价成功！";
i18n.incident.mgnt.FeedbackOptionEmpty="请选择一个评价值！";
i18n.incident.mgnt.MarkIncidentSuccess="归档成功！";
i18n.incident.mgnt.MarkOptionEmpty="请至少为事件打一个标记！";
i18n.incident.mgnt.CommitBtn="提交";
i18n.incident.mgnt.EditBtn="编辑";
i18n.incident.mgnt.DeleteBtn="删除";
i18n.incident.mgnt.CloseBtn="关闭";
i18n.incident.mgnt.ViewBtn="查看";
i18n.incident.mgnt.MarkBtn="归档";
i18n.incident.mgnt.EvaluateBtn="评价";
i18n.incident.mgnt.ReOpenBtn="恢复";
i18n.incident.mgnt.ExportCustEmpty="请选择导出的客户";
i18n.incident.mgnt.ExportDateEmpty="请输入导出的日期范围";
i18n.incident.edit={};
i18n.incident.edit.QueryTicketInfoError="查询事件信息失败！";
i18n.incident.edit.CompanyEmpty="请选择公司！";
i18n.incident.edit.ProductEmpty="请选择产品线！";
i18n.incident.edit.ModuleEmpty="请选择服务目录！";
i18n.incident.edit.AffectEmpty="请选择影响度！";
i18n.incident.edit.ClassEmpty="请选择事件分类！";
i18n.incident.edit.BriefEmpty="请填写事件简述！";
i18n.incident.edit.HappenTimeEmpty="请填写事件发生时间！";
i18n.incident.edit.DetailEmpty="请填写事件详细说明！";
i18n.incident.edit.CcFormatError="请填写正确的邮箱地址，多个用逗号分隔！";
i18n.incident.view={};
i18n.incident.view.LendHandBtn="搭把手";
i18n.incident.view.TransTplStartMan="发起人";
i18n.incident.view.TransTplDealtime="处理时间";
i18n.incident.view.TransTplType="类型";
i18n.incident.view.TransTplContent="事务说明";
i18n.incident.view.TransOrderPrefix="事务";
i18n.incident.view.QryTransListError="查询事务信息错误！";
i18n.incident.view.QryContactInfoError="查询联系人信息错误！";
i18n.incident.view.TransContentEmpty="请填写事务内容！";
i18n.incident.view.TransCommitSuccess="提交成功";
i18n.incident.view.TransferNameTitle="姓名";
i18n.incident.view.TransferAccountTitle="账号";
i18n.incident.view.TransferMobileTitle="手机";
i18n.incident.view.TransferPhoneTitle="固定电话";
i18n.incident.view.TransferSelectEmpty="请选择一个转派的顾问！";
i18n.incident.view.TransferSuccess="转派成功";
i18n.incident.view.FinishCodeEmpty="请选择一个事件完成结果！";
i18n.incident.view.TransCompleteProductEmpty="请选择产品线";
i18n.incident.view.TransCompleteModuleEmpty="请选择服务目录";
i18n.incident.view.TransCompletePriorityEmpty="请选择优先级";
i18n.incident.view.TransCompleteComplexEmpty="请选择复杂度";
//custmgnt
i18n.custmgnt.main={};
i18n.custmgnt.main.CustTreeRoot="全部客户";
i18n.custmgnt.custinfo={};
i18n.custmgnt.custinfo.CustTreeRoot="顶层客户";
i18n.custmgnt.custinfo.QryCustInfoError="查询客户信息出错了！";
i18n.custmgnt.custinfo.CustCodeEmptyError="请填写客户编码";
i18n.custmgnt.custinfo.CustNameEmptyError="请填写客户名称";
i18n.custmgnt.custinfo.ShortNameEmptyError="请填写客户简称";
i18n.custmgnt.custinfo.EnNameEmptyError="请填写客户英文名称";
i18n.custmgnt.custinfo.SupCustEmptyError="请选择上级客户";
i18n.custmgnt.custinfo.DomainNameEmptyError="请填写域名";
i18n.custmgnt.custinfo.DeleteSuccess="删除客户成功！";
i18n.custmgnt.userinfo={};
i18n.custmgnt.userinfo.TableTitleUserName="姓名";
i18n.custmgnt.userinfo.TableTitleFirstName="英文名";
i18n.custmgnt.userinfo.TableTitleLastName="英文姓";
i18n.custmgnt.userinfo.TableTitleCustName="客户名称";
i18n.custmgnt.userinfo.TableTitleUserCode="编码";
i18n.custmgnt.userinfo.TableTitleUserPasswd="密码";
i18n.custmgnt.userinfo.TableTitleGender="性别";
i18n.custmgnt.userinfo.TableTitleUserType="类别";
i18n.custmgnt.userinfo.TableTitleMobileNo="手机号";
i18n.custmgnt.userinfo.TableTitleOfficeTel="办公电话";
i18n.custmgnt.userinfo.TableTitleUserState="状态";
i18n.custmgnt.userinfo.TableTitleOperations="";
i18n.custmgnt.userinfo.GenderMr="男";
i18n.custmgnt.userinfo.GenderMs="女";
i18n.custmgnt.userinfo.RoleBusiness="业务人员";
i18n.custmgnt.userinfo.RoleIT="IT人员";
i18n.custmgnt.userinfo.StateNormal="正常";
i18n.custmgnt.userinfo.StateLeave="离职";
i18n.custmgnt.userinfo.StateLock="锁定";
i18n.custmgnt.userinfo.OperCancelEditBtn="取消";
i18n.custmgnt.userinfo.OperSubmitBtn="保存";
i18n.custmgnt.userinfo.OperLeaveBtn="离职";
i18n.custmgnt.userinfo.OperLockBtn="锁定";
i18n.custmgnt.userinfo.OperUnLeaveBtn="恢复";
i18n.custmgnt.userinfo.OperUnLockBtn="解锁";
i18n.custmgnt.userinfo.OperEditBtn="编辑";
i18n.custmgnt.userinfo.StateChangeSuccess="提交成功";
i18n.custmgnt.userinfo.InfoUnknown="不详";
i18n.custmgnt.prodinfo={};
i18n.custmgnt.prodinfo.ProdTableTitleProdName="产品名称";
i18n.custmgnt.prodinfo.ProdTableTitleProdCode="产品编码";
i18n.custmgnt.prodinfo.ProdTableTitleServiceStartDate="服务开始时间";
i18n.custmgnt.prodinfo.ProdTableTitleServiceEndDate="服务结束时间";
i18n.custmgnt.prodinfo.ProdSelDup="该产品线已被添加，请选择其他产品线";
i18n.custmgnt.prodinfo.CancelBtn="取消";
i18n.custmgnt.prodinfo.EditBtn="编辑";
i18n.custmgnt.prodinfo.DeleteBtn="删除";
i18n.custmgnt.prodinfo.SubmitBtn="保存";
i18n.custmgnt.prodinfo.OnlyTopCustCanAddProd="只有顶层客户才能添加产品线，请在左侧选择顶层客户节点！";
i18n.custmgnt.prodinfo.EditConflictError="请先取消或提交正在编辑的数据行！";
i18n.custmgnt.prodinfo.OpTableTitleOpName="顾问姓名";
i18n.custmgnt.prodinfo.OpTableTitleOpCode="顾问编码";
i18n.custmgnt.prodinfo.OpTableTitleLoginCode="登录账号";
i18n.custmgnt.prodinfo.OpTableTitleJobName="岗位名称";
i18n.custmgnt.prodinfo.OpSelDup="这个顾问已经添加过了，请选择其他顾问！";
i18n.custmgnt.prodinfo.OnlyTopCustCanAddConsultant="只有选择顶层客户时才可以添加顾问，请在左侧选择顶层客户节点！";
i18n.custmgnt.slorule={};
i18n.custmgnt.slorule.SloTableTitleProdName="产品线";
i18n.custmgnt.slorule.SloTableTitlePriority="优先级";
i18n.custmgnt.slorule.SloTableTitleComplex="复杂度";
i18n.custmgnt.slorule.SloTableTitleRespTime="响应时间（分钟）";
i18n.custmgnt.slorule.SloTableTitleDealTime="处理时间（分钟）";
i18n.custmgnt.slorule.EditBtn="编辑";
i18n.custmgnt.slorule.ClearBtn="清除";
i18n.custmgnt.slorule.SubmitBtn="提交";
i18n.custmgnt.slorule.CancelEditBtn="取消";
i18n.custmgnt.slorule.MustSelectTopCust="请选择一个顶层客户！";
i18n.scmgnt.paraminfo={};
i18n.scmgnt.paraminfo.ParamTableTitleKind="参数名";
i18n.scmgnt.paraminfo.ParamTableTitleCode="参数值";
i18n.scmgnt.paraminfo.ParamTableTitleValueZh="参数值中文";
i18n.scmgnt.paraminfo.ParamTableTitleValueEn="参数值英文";
i18n.scmgnt.paraminfo.EditBtn="编辑";
i18n.scmgnt.paraminfo.RemoveBtn="删除";
i18n.scmgnt.paraminfo.SubmitBtn="提交";
i18n.scmgnt.paraminfo.CancelEditBtn="取消";
i18n.scmgnt.paraminfo.StateParamCanntModify="事件状态已绑定到事件处理流程中，此参数不允许修改或添加";
i18n.scmgnt.paraminfo.ParamValueExist="参数值已经存在";
i18n.scmgnt.paraminfo.ParamModiyAffectSlo="这个参数修改后需要重新设定SLO规则，请慎重操作！";
i18n.scmgnt.jobinfo={};
i18n.scmgnt.jobinfo.JobTableTitleCode="岗位编码";
i18n.scmgnt.jobinfo.JobTableTitleNameZh="中文名称";
i18n.scmgnt.jobinfo.JobTableTitleNameEn="英文名称";
i18n.scmgnt.jobinfo.JobTableTitleClass="岗位类型";
i18n.scmgnt.jobinfo.JobTableTitleLevel="岗位级别";
i18n.scmgnt.jobinfo.EditBtn="编辑";
i18n.scmgnt.jobinfo.RemoveBtn="删除";
i18n.scmgnt.jobinfo.SubmitBtn="提交";
i18n.scmgnt.jobinfo.CancelEditBtn="取消";
i18n.scmgnt.jobinfo.JobCodeExist="这个岗位编码已经添加了，请更换一个！";
i18n.scmgnt.opinfo={};
i18n.scmgnt.opinfo.EditBtn="编辑";
i18n.scmgnt.opinfo.RemoveBtn="删除";
i18n.scmgnt.opinfo.SubmitBtn="提交";
i18n.scmgnt.opinfo.CancelEditBtn="取消";
//opmgnt/////////////////////////
//table
i18n.scmgnt.opinfo.TableTitleOpName="中文姓名";
i18n.scmgnt.opinfo.TableTitleFirstName="英文名";
i18n.scmgnt.opinfo.TableTitleLastName="英文姓";
i18n.scmgnt.opinfo.TableTitleLoginCode="登录工号";
i18n.scmgnt.opinfo.TableTitleLoginPasswd="密码";
i18n.scmgnt.opinfo.TableTitleGender="性别";
i18n.scmgnt.opinfo.TableTitleOpKind="类别";
i18n.scmgnt.opinfo.TableTitleMobileNo="手机号";
i18n.scmgnt.opinfo.TableTitleOfficeTel="办公电话";
i18n.scmgnt.opinfo.TableTitleOpState="状态";
i18n.scmgnt.opinfo.TableTitleOperations="";
i18n.scmgnt.opinfo.GenderMr="男";
i18n.scmgnt.opinfo.GenderMs="女";
i18n.scmgnt.opinfo.InfoUnknown="不详";
i18n.scmgnt.opinfo.OpKindSuper="超级管理员";
i18n.scmgnt.opinfo.OpKindAdmin="管理员";
i18n.scmgnt.opinfo.OpKindUser="普通用户";
i18n.scmgnt.opinfo.StateNormal="正常";
i18n.scmgnt.opinfo.StateLeave="离职";
i18n.scmgnt.opinfo.StateLock="锁定";
//button
i18n.scmgnt.opinfo.OperCancelEditBtn="取消";
i18n.scmgnt.opinfo.OperSubmitBtn="保存";
i18n.scmgnt.opinfo.OperLeaveBtn="离职";
i18n.scmgnt.opinfo.OperLockBtn="锁定";
i18n.scmgnt.opinfo.OperUnLeaveBtn="恢复";
i18n.scmgnt.opinfo.OperUnLockBtn="解锁";
i18n.scmgnt.opinfo.OperEditBtn="编辑";
i18n.scmgnt.opinfo.CancelBtn="取消";
i18n.scmgnt.opinfo.SubmitBtn="保存";
i18n.scmgnt.opinfo.EditBtn="编辑";
i18n.scmgnt.opinfo.RemoveBtn="删除";
//info
i18n.scmgnt.opinfo.EditConflictError="请先取消或提交正在编辑的数据行！";
i18n.scmgnt.opinfo.StateChangeSuccess="提交成功";
//prodinfo
i18n.scmgnt.prodinfo={};
i18n.scmgnt.prodinfo.ProdTableTitleProdNameZh="中文名称";
i18n.scmgnt.prodinfo.ProdTableTitleProdNameEn="英文名称";
i18n.scmgnt.prodinfo.ProdTableTitleProdCode="编码";
i18n.scmgnt.prodinfo.ProdTableTitleState="状态";
i18n.scmgnt.prodinfo.ProdStateNormal="正常";
i18n.scmgnt.prodinfo.ProdStateDisable="失效";
i18n.scmgnt.prodinfo.ModuleSelClear="清除";
i18n.scmgnt.prodinfo.ModuleHasSubModule="当前服务目录包含下级服务目录,请先将它们删除!";
i18n.scmgnt.prodinfo.ProdCodeExist="该产品编码已存在，请更换另一个！";
i18n.scmgnt.moduleinfo={};
i18n.scmgnt.moduleinfo.QryModuleInfoError="查询服务目录信息失败!";
i18n.scmgnt.moduleinfo.ModuleCodeEmptyError="请填写编码";
i18n.scmgnt.moduleinfo.ModuleZhNameEmptyError="请填写中文名称";
i18n.scmgnt.moduleinfo.ModuleEnNameEmptyError="请填写英文名称";
i18n.scmgnt.moduleinfo.ModuleDescEmptyError="请填写描述";