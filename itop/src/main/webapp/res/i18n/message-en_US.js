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
i18n.register.ChineseNameEmpty="Please input your chinese name!";
i18n.register.EnglishNameEmpty="Please input your english name!";
i18n.register.GenderEmpty="Please choose your gender!";
i18n.register.MobileNoFormatError="Your mobile number must be 11 characters!";
i18n.register.AreaCodeFormatError="Area Code must be at least 3 or 4 numbers!";
i18n.register.OfficeTelFormatError="Area Code must be at least 7 or 8 numbers!";

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
i18n.incident.query.QryConditionCustEmpty="Please choose custumer first!";
i18n.incident.query.DataTitleIncidentCode="Ticket no";
i18n.incident.query.DataTitleBrief="Description";
i18n.incident.query.DataTitleCustName="Company";
i18n.incident.query.DataTitleProdName="Product line";
i18n.incident.query.DataTitleClassValOp="Type";
i18n.incident.query.DataTitlePriorityVal="Priority";
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
//transation