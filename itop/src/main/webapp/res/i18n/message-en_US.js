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
i18n.loading.GridLoading="Searching...,please wait a moment";
i18n.loading.GirdDataEmpty="No data record";
//dialog
i18n.dialog.AlertTitle="Info";
//upload
i18n.upload.UploadFailure="Upload failure,please try again later!";
//login
i18n.login.AccountNoBlank="Email Account can't be empty!";
i18n.login.PasswordBlank="Password can't be empty!";
i18n.login.VerifyCodeBlank="Verification Code can't be empty!";
i18n.login.SessionTimeout="System error,please login again!";
//register
i18n.register.AccountFormatError="Please input an email account!";
i18n.register.PasswordFormatError="Your password must be at least 4 characters!";
i18n.register.PasswordConfirmError="New and confirmed passwords are not the same!";
i18n.register.CompanyEmpty="Please choose your company!";
i18n.register.RoleEmpty="Please choose your role!";
i18n.register.OpCodeEmpty="Please input op code!";
i18n.register.ChineseNameEmpty="Please input your chinese name!";
i18n.register.EnglishNameEmpty="Please input your english name!";
i18n.register.GenderEmpty="Please choose your gender!";
i18n.register.OpKindEmpty="Please choose your type!";
i18n.register.MobileNoFormatError="Your mobile number must be 11 characters!";
i18n.register.AreaCodeFormatError="Area Code must be at least 3 or 4 numbers!";
i18n.register.OfficeTelFormatError="Office tel must be at least 7 or 8 numbers!";

//usercenter
i18n.usercenter.profile={};
i18n.usercenter.profile.QueryInfoError="Query Information Error,please try again later!";
i18n.usercenter.pwdchange={};
i18n.usercenter.pwdchange.OldPasswordEmpty="Please input old password!";
i18n.usercenter.pwdchange.NewPasswordEmpty="Please input new password!";
i18n.usercenter.pwdchange.NewPasswordFormatError="Your password must be at least 4 characters!";
i18n.usercenter.pwdchange.ConfirmPasswordEmpty="Please input confirm password!";
i18n.usercenter.pwdchange.PasswordNotSame="New and confirmed passwords are not the same!";
i18n.usercenter.pwdchange.ChangeSuccess="Your password has been updated!";

//opcenter
i18n.opcenter.profile={};
i18n.opcenter.profile.QueryInfoError="Query Information Error,please try again later!";
i18n.opcenter.profile.OpCodeFormatError="Your OpCode must be at least 4 characters,only letters or numbers are allowed!";

//incident
i18n.incident.query={};
i18n.incident.query.QryConditionCustEmpty="Choose company first,please!";
i18n.incident.query.DataTitleIncidentCode="Ticket no";
i18n.incident.query.DataTitleBrief="Description";
i18n.incident.query.DataTitleCustName="Company";
i18n.incident.query.DataTitleProdName="Product line";
i18n.incident.query.DataTitleClassValOp="Type";
i18n.incident.query.DataTitlePriorityVal="Priority";
i18n.incident.query.DataTitleComplexVal="Complexity";
i18n.incident.query.DataTitleAffectValOp="Infuence";
i18n.incident.query.DataTitleItStateVal="Status";
i18n.incident.query.DataTitlePlObjectName="Create by";
i18n.incident.query.DataTitleRegisteTime="Creation date";
i18n.incident.query.DataTitlePlanFinishTime="Planned finished date";
i18n.incident.query.DataTitleScLoginName="Owner";
i18n.incident.query.DataTitleModifyDate="Last updated";
i18n.incident.query.DataTitleFinishTime="Actual finished date";
i18n.incident.query.DataTitleFeedbackVal="Satisfaction";
i18n.incident.mgnt={};
i18n.incident.mgnt.CommitFailure="Operation Failure";
i18n.incident.mgnt.CommitIncidentSuccess="Ticket submit successfully!";
i18n.incident.mgnt.RemoveIncidentConfirm="Are you sure to delete this ticket?";
i18n.incident.mgnt.RemoveIncidentSuccess="Delete successfully!";
i18n.incident.mgnt.CloseIncidentSuccess="Close successfully!";
i18n.incident.mgnt.FeedbackIncidentSuccess="Evaluate successfully!";
i18n.incident.mgnt.FeedbackOptionEmpty="Please choose an evaluate option!";
i18n.incident.mgnt.MarkIncidentSuccess="IT reviewed successfully!";
i18n.incident.mgnt.MarkOptionEmpty="Choose at least one option!";
i18n.incident.mgnt.CommitBtn="Submit";
i18n.incident.mgnt.EditBtn="Edit";
i18n.incident.mgnt.DeleteBtn="Delete";
i18n.incident.mgnt.CloseBtn="Close";
i18n.incident.mgnt.ViewBtn="Detail";
i18n.incident.mgnt.MarkBtn="Review";
i18n.incident.mgnt.EvaluateBtn="Evaluate";
i18n.incident.mgnt.ReOpenBtn="Reopen";
i18n.incident.mgnt.ExportCustEmpty="Select a customer,please!";
i18n.incident.mgnt.ExportDateEmpty="Input export date range,please!";
i18n.incident.edit={};
i18n.incident.edit.QueryTicketInfoError="Query ticket information error!";
i18n.incident.edit.CompanyEmpty="Choose a company,please!";
i18n.incident.edit.ProductEmpty="Choose a product line,please!";
i18n.incident.edit.ModuleEmpty="Choose a service module,please!";
i18n.incident.edit.AffectEmpty="Choose a influnce type,please!";
i18n.incident.edit.ClassEmpty="Choose a ticket type,please!";
i18n.incident.edit.BriefEmpty="Input some brief,please!";
i18n.incident.edit.HappenTimeEmpty="Input happen time,please!";
i18n.incident.edit.DetailEmpty="Input detail information,please!";
i18n.incident.edit.CcFormatError="Input correct email address,separate multiple emails with a comma";
i18n.incident.view={};
i18n.incident.view.LendHandBtn="Do a favour";
i18n.incident.view.TransTplStartMan="Owner";
i18n.incident.view.TransTplDealtime="Repsonse time";
i18n.incident.view.TransTplType="Type";
i18n.incident.view.TransTplContent="Content";
i18n.incident.view.TransOrderPrefix="No.";
i18n.incident.view.QryTransListError="Query transactions data error!";
i18n.incident.view.QryContactInfoError="Query contact information data error!";
i18n.incident.view.TransContentEmpty="Input transaction information, please!";
i18n.incident.view.TransCommitSuccess="Submit successfully!";
i18n.incident.view.TransferNameTitle="Name";
i18n.incident.view.TransferAccountTitle="Account";
i18n.incident.view.TransferMobileTitle="Mobile";
i18n.incident.view.TransferPhoneTitle="Telephone";
i18n.incident.view.TransferSelectEmpty="Choose a consultant to handover,please!";
i18n.incident.view.TransferSuccess="handover successfully";
i18n.incident.view.FinishCodeEmpty="Choose a complete type,please!";
i18n.incident.view.TransCompleteProductEmpty="Select a product line,please!";
i18n.incident.view.TransCompleteModuleEmpty="Select a service module,please!";
i18n.incident.view.TransCompletePriorityEmpty="Select a priority type,please!";
i18n.incident.view.TransCompleteComplexEmpty="Select a complex type,please!";
//custmgnt
i18n.custmgnt.main={};
i18n.custmgnt.main.CustTreeRoot="All customers";
i18n.custmgnt.custinfo={};
i18n.custmgnt.custinfo.CustTreeRoot="Top customer";
i18n.custmgnt.custinfo.QryCustInfoError="Query customer information error！";
i18n.custmgnt.custinfo.CustCodeEmptyError="Input customer's code,please!";
i18n.custmgnt.custinfo.CustNameEmptyError="Input customer's name,please!";
i18n.custmgnt.custinfo.ShortNameEmptyError="Input customer's short name,please!";
i18n.custmgnt.custinfo.EnNameEmptyError="Input customer's english name,please!";
i18n.custmgnt.custinfo.SupCustEmptyError="Select a super customer,please!";
i18n.custmgnt.custinfo.DomainNameEmptyError="Input domain name,please!";
i18n.custmgnt.custinfo.DeleteSuccess="Delete successfully！";
i18n.custmgnt.userinfo={};
i18n.custmgnt.userinfo.TableTitleUserName="Chinese name";
i18n.custmgnt.userinfo.TableTitleFirstName="First name";
i18n.custmgnt.userinfo.TableTitleLastName="Last name";
i18n.custmgnt.userinfo.TableTitleCustName="Customer name";
i18n.custmgnt.userinfo.TableTitleUserCode="User code";
i18n.custmgnt.userinfo.TableTitleUserPasswd="Password";
i18n.custmgnt.userinfo.TableTitleGender="Gender";
i18n.custmgnt.userinfo.TableTitleUserType="Type";
i18n.custmgnt.userinfo.TableTitleMobileNo="Mobile no";
i18n.custmgnt.userinfo.TableTitleOfficeTel="Telephone";
i18n.custmgnt.userinfo.TableTitleUserState="State";
i18n.custmgnt.userinfo.TableTitleOperations="";
i18n.custmgnt.userinfo.GenderMr="Mr.";
i18n.custmgnt.userinfo.GenderMs="Ms.";
i18n.custmgnt.userinfo.RoleBusiness="Business";
i18n.custmgnt.userinfo.RoleIT="IT";
i18n.custmgnt.userinfo.StateNormal="Normal";
i18n.custmgnt.userinfo.StateLeave="Dimission";
i18n.custmgnt.userinfo.StateLock="Locked";
i18n.custmgnt.userinfo.OperCancelEditBtn="Cancel";
i18n.custmgnt.userinfo.OperSubmitBtn="Submit";
i18n.custmgnt.userinfo.OperLeaveBtn="Dismiss";
i18n.custmgnt.userinfo.OperLockBtn="Lock";
i18n.custmgnt.userinfo.OperUnLeaveBtn="Recover";
i18n.custmgnt.userinfo.OperUnLockBtn="Unlock";
i18n.custmgnt.userinfo.OperEditBtn="Edit";
i18n.custmgnt.userinfo.StateChangeSuccess="Submit successfully";
i18n.custmgnt.userinfo.InfoUnknown="Unknown";
i18n.custmgnt.prodinfo={};
i18n.custmgnt.prodinfo.ProdTableTitleProdName="Product name";
i18n.custmgnt.prodinfo.ProdTableTitleProdCode="Product code";
i18n.custmgnt.prodinfo.ProdTableTitleServiceStartDate="Service start date";
i18n.custmgnt.prodinfo.ProdTableTitleServiceEndDate="Service end date";
i18n.custmgnt.prodinfo.ProdSelDup="This Product line is already has been added! Select another one,please!";
i18n.custmgnt.prodinfo.CancelBtn="Cancel";
i18n.custmgnt.prodinfo.EditBtn="Edit";
i18n.custmgnt.prodinfo.DeleteBtn="Delete";
i18n.custmgnt.prodinfo.SubmitBtn="Submit";
i18n.custmgnt.prodinfo.OnlyTopCustCanAddProd="Only top customers can be added product lines!Select a top customer on the left tree,please!";
i18n.custmgnt.prodinfo.EditConflictError="Cancel or submit editing row first,please!";
i18n.custmgnt.prodinfo.OpTableTitleOpName="Consultant name";
i18n.custmgnt.prodinfo.OpTableTitleOpCode="Consultant code";
i18n.custmgnt.prodinfo.OpTableTitleLoginCode="Login code";
i18n.custmgnt.prodinfo.OpTableTitleJobName="Job name";
i18n.custmgnt.prodinfo.OpSelDup="This consultant is already has been added! Select another one,please!";
i18n.custmgnt.prodinfo.OnlyTopCustCanAddConsultant="Only top customers can be added consultants!Select a top customer on the left tree,please!";
i18n.custmgnt.slorule={};
i18n.custmgnt.slorule.SloTableTitleProdName="Product line";
i18n.custmgnt.slorule.SloTableTitlePriority="Priority";
i18n.custmgnt.slorule.SloTableTitleComplex="Complex";
i18n.custmgnt.slorule.SloTableTitleRespTime="Response time(Minutes)";
i18n.custmgnt.slorule.SloTableTitleDealTime="Deal time(Minutes)";
i18n.custmgnt.slorule.EditBtn="Edit";
i18n.custmgnt.slorule.ClearBtn="Clear";
i18n.custmgnt.slorule.SubmitBtn="Submit";
i18n.custmgnt.slorule.CancelEditBtn="Cancel";
i18n.custmgnt.slorule.MustSelectTopCust="Select a top customer,please!";
i18n.scmgnt.paraminfo={};
i18n.scmgnt.paraminfo.ParamTableTitleKind="Parameter name";
i18n.scmgnt.paraminfo.ParamTableTitleCode="Parameter value";
i18n.scmgnt.paraminfo.ParamTableTitleValueZh="Chinese display text";
i18n.scmgnt.paraminfo.ParamTableTitleValueEn="English display text";
i18n.scmgnt.paraminfo.EditBtn="Edit";
i18n.scmgnt.paraminfo.RemoveBtn="Remove";
i18n.scmgnt.paraminfo.SubmitBtn="Submit";
i18n.scmgnt.paraminfo.CancelEditBtn="Cancel";
i18n.scmgnt.paraminfo.StateParamCanntModify="Ticket status parameters has been binded in ticket deal process! All addition or edit operations are not allowed!";
i18n.scmgnt.paraminfo.ParamValueExist="Parameter value is already exist!";
i18n.scmgnt.paraminfo.ParamModiyAffectSlo="If you modify this parameter,the slo rules must be reconfiguered! Please be careful!";
i18n.scmgnt.jobinfo={};
i18n.scmgnt.jobinfo.JobTableTitleCode="Job code";
i18n.scmgnt.jobinfo.JobTableTitleNameZh="Job chinese name";
i18n.scmgnt.jobinfo.JobTableTitleNameEn="Job english name";
i18n.scmgnt.jobinfo.JobTableTitleClass="Job type";
i18n.scmgnt.jobinfo.JobTableTitleLevel="job level";
i18n.scmgnt.jobinfo.EditBtn="Edit";
i18n.scmgnt.jobinfo.RemoveBtn="Remove";
i18n.scmgnt.jobinfo.SubmitBtn="Submit";
i18n.scmgnt.jobinfo.CancelEditBtn="Cancel";
i18n.scmgnt.jobinfo.JobCodeExist="This job code is exist,input another one please!";
i18n.scmgnt.opinfo={};
i18n.scmgnt.opinfo.EditBtn="Edit";
i18n.scmgnt.opinfo.RemoveBtn="Remove";
i18n.scmgnt.opinfo.SubmitBtn="Submit";
i18n.scmgnt.opinfo.CancelEditBtn="Cancel";
//opmgnt/////////////////////////
//table
i18n.scmgnt.opinfo.TableTitleOpCode="Op code";
i18n.scmgnt.opinfo.TableTitleOpName="Chinese name";
i18n.scmgnt.opinfo.TableTitleFirstName="First name";
i18n.scmgnt.opinfo.TableTitleLastName="Last name";
i18n.scmgnt.opinfo.TableTitleLoginCode="Login code";
i18n.scmgnt.opinfo.TableTitleLoginPasswd="Password";
i18n.scmgnt.opinfo.TableTitleGender="Gender";
i18n.scmgnt.opinfo.TableTitleOpKind="Type";
i18n.scmgnt.opinfo.TableTitleMobileNo="Mobile no";
i18n.scmgnt.opinfo.TableTitleOfficeTel="Telephone";
i18n.scmgnt.opinfo.TableTitleOpState="State";
i18n.scmgnt.opinfo.TableTitleOperations="";
i18n.scmgnt.opinfo.GenderMr="Mr.";
i18n.scmgnt.opinfo.GenderMs="Ms.";
i18n.scmgnt.opinfo.InfoUnknown="Unknown";
i18n.scmgnt.opinfo.OpKindSuper="Super";
i18n.scmgnt.opinfo.OpKindAdmin="Administrator";
i18n.scmgnt.opinfo.OpKindUser="User";
i18n.scmgnt.opinfo.StateNormal="Normal";
i18n.scmgnt.opinfo.StateLeave="Dimission";
i18n.scmgnt.opinfo.StateLock="Locked";
//button
i18n.scmgnt.opinfo.OperCancelEditBtn="Cancel";
i18n.scmgnt.opinfo.OperSubmitBtn="Submit";
i18n.scmgnt.opinfo.OperLeaveBtn="Dismiss";
i18n.scmgnt.opinfo.OperLockBtn="Lock";
i18n.scmgnt.opinfo.OperUnLeaveBtn="Recover";
i18n.scmgnt.opinfo.OperUnLockBtn="Unlock";
i18n.scmgnt.opinfo.OperEditBtn="Edit";
i18n.scmgnt.opinfo.CancelBtn="Cancel";
i18n.scmgnt.opinfo.SubmitBtn="Submit";
i18n.scmgnt.opinfo.EditBtn="Edit";
i18n.scmgnt.opinfo.RemoveBtn="Remove";
//info
i18n.scmgnt.opinfo.EditConflictError="Cancel or submit editing row first,please!";
i18n.scmgnt.opinfo.StateChangeSuccess="Submit successfully";
//prodinfo
i18n.scmgnt.prodinfo={};
i18n.scmgnt.prodinfo.ProdTableTitleProdNameZh="Chinese name";
i18n.scmgnt.prodinfo.ProdTableTitleProdNameEn="English name";
i18n.scmgnt.prodinfo.ProdTableTitleProdCode="Code";
i18n.scmgnt.prodinfo.ProdTableTitleState="Status";
i18n.scmgnt.prodinfo.ProdStateNormal="Normal";
i18n.scmgnt.prodinfo.ProdStateDisable="Disable";
i18n.scmgnt.prodinfo.ModuleSelClear="Clear";
i18n.scmgnt.prodinfo.ModuleHasSubModule="This module has children modules!Remove them first,please!";
i18n.scmgnt.prodinfo.ProdCodeExist="This product code is already exist! Input another one,please!";
i18n.scmgnt.moduleinfo={};
i18n.scmgnt.moduleinfo.QryModuleInfoError="Query module info error";
i18n.scmgnt.moduleinfo.ModuleCodeEmptyError="Input code,please!";
i18n.scmgnt.moduleinfo.ModuleZhNameEmptyError="Input chinese name,please!";
i18n.scmgnt.moduleinfo.ModuleEnNameEmptyError="Input english name,please!";
i18n.scmgnt.moduleinfo.ModuleDescEmptyError="Input description,please!";