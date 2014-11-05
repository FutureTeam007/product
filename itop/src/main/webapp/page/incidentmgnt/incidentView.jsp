<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="i18n" uri="../../WEB-INF/i18n.tld"%>
<%
	String id = request.getParameter("id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><i18n:message code="i18n.incident.view.PageTitle"/></title>
	<%@ include file="../common/commonHead.jsp"%>
	<%@ include file="../common/easyuiHead.jsp"%>
	<script type="text/javascript">
		var incidentId = "<%=id%>";
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/res/easyui/jquery.tmpl.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/res/easyui/jquery.ajaxfileupload.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/page/incidentmgnt/js/incidentView.js"></script>
</head>
<body style="background:#fff;padding-left:5px;overflow-x:hidden;">
  <div>
		<button type='button' class='btn btn-link top-close-link' onclick='parent.reloadData();parent.hideSubPage()'>&lt;&lt;<i18n:message code="i18n.nav.CloseWin" /></button>
  </div>
  <div class="inci-dtl-title">
		<h5 style="font-weight:bold">[<span id="incidentCode"></span>]&nbsp;<i18n:message code="i18n.incident.view.TopInfoRegisterTime" />：<span id="registTime"></span>，<i18n:message code="i18n.incident.view.TopInfoDealPerson" />：<span id="icObjectName"></span>，<i18n:message code="i18n.incident.view.TopInfoConsultant" />：<span id="scLoginName"></span>，<i18n:message code="i18n.incident.view.TopInfoStatus" />：<span id="itStateCode"></span></h5>
  </div>
  <div class="inci-trans-commit-form" style="width:99%">
		<form class="form-horizonta inci-info-form" role="form">
			<div class="form-group clearfix">
			    <label for="transDesc" class="col-sm-2 control-label"><i18n:message code="i18n.incident.view.TransactionDescLabel" /></label>
			    <div class="col-sm-10">
			       <textarea class="form-control" rows="3" id="transDesc"></textarea>
			    </div>
  			</div>
  			<div class="form-group clearfix">
			    <label for="attachments" class="col-sm-2 control-label"><i18n:message code="i18n.incident.view.AttachmentLabel" /></label>
			    <div class="col-sm-10 attachList">
			       <div id="commitAttach" class="ml5"></div>
			       <button type="button" class="btn btn-primary btn-outline btn-sm"><i18n:message code="i18n.incident.view.SelectFileLabel" /></button>
			       <input type="file" id="uploadFile1" class="upload_control" name="uploadFile" title="<i18n:message code="i18n.incident.view.SelectFileLabel" />"  onchange="attachUpload()"/>
			    </div>
  			</div>
  			<div class="form-group clearfix">
	  			<div class="col-sm-12 form-btns">
			      <input type="button" class="btn btn-warning" id="transCommitBtn" onclick="transCommit()" value="<i18n:message code="i18n.incident.view.CommitBtn" />" />
			      <input type="button" class="btn btn-warning undis" id="openConsultantSelBtn" onclick="openConsultantSelWin()" value="<i18n:message code="i18n.incident.view.TransferBtn" />" />
			      <input type="button" class="btn btn-warning undis" id="deliverCustCommitBtn" onclick="deliverCustCommit()" value="<i18n:message code="i18n.incident.view.NeedMoreBtn" />" />
			      <input type="button" class="btn btn-warning undis" id="blockCommitBtn" onclick="blockCommit()" value="<i18n:message code="i18n.incident.view.BlockBtn" />" />
			      <input type="button" class="btn btn-warning undis" id="finishCommitBtn" onclick="openFinishWin()" value="<i18n:message code="i18n.incident.view.FinishBtn" />" />
			    </div>
  			</div>
		</form>
  </div>
  <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true" style="width:98%">
	  <div class="panel panel-default">
	    <div class="panel-heading">
	      <h4 class="panel-title">
	        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
	          	<i18n:message code="i18n.incident.view.TransRecords" />
	        </a>
	      </h4>
	    </div>
	    <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel">
	      <div class="panel-body" id="transList">
	      </div>
	    </div>
	  </div>
  	<div class="panel panel-default">
    	<div class="panel-heading">
      		<h4 class="panel-title">
	        <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
	          	<i18n:message code="i18n.incident.view.TicketInfo" />
	        </a>
      </h4>
    </div>
    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel">
      <div class="panel-body inci-base-info">
      	<div class="clearfix">
      		<div class="col-sm-2"><label><i18n:message code="i18n.incident.view.CompanyLabel" /></label></div>
      		<div class="col-sm-4" id="custName"></div>
      		<div class="col-sm-2"><label><i18n:message code="i18n.incident.view.CategoryLabel" /></label></div>
      		<div class="col-sm-4" id="classCodeOp"></div>
      	</div>
        <div class="clearfix">
      		<div class="col-sm-2"><label><i18n:message code="i18n.incident.view.ProductLabel" /></label></div>
      		<div class="col-sm-4" id="prodName"></div>
      		<div class="col-sm-2"><label><i18n:message code="i18n.incident.view.ModuleLabel" /></label></div>
      		<div class="col-sm-4" id="moduleName"></div>
      	</div>
      	<div class="clearfix">
      		<div class="col-sm-2"><label><i18n:message code="i18n.incident.view.DegreeLabel" /></label></div>
      		<div class="col-sm-4" id="affectCodeOp"></div>
      		<div class="col-sm-2"><label><i18n:message code="i18n.incident.view.ComplexLabel" /></label></div>
      		<div class="col-sm-4" id="complexCode"></div>
      	</div>
      	<div class="clearfix">
      		<div class="col-sm-2"><label><i18n:message code="i18n.incident.view.PriorityLabel" /></label></div>
      		<div class="col-sm-4" id="priorityValInfo"></div>
      		<div class="col-sm-2"><label><i18n:message code="i18n.incident.view.StatusLabel" /></label></div>
      		<div class="col-sm-4" id="itPhase"></div>
      	</div>
      	<div class="clearfix">
      		<div class="col-sm-2"><label><i18n:message code="i18n.incident.view.DealPersonLabel" /></label></div>
      		<div class="col-sm-4" id="infoScLoginName"></div>
      		<div class="col-sm-2"><label><i18n:message code="i18n.incident.view.HappenTimeLabel" /></label></div>
      		<div class="col-sm-4" id="happenTime"></div>
      	</div>
      	<div class="clearfix">
      		<div class="col-sm-2"><label><i18n:message code="i18n.incident.view.RespEndTimeLabel" /></label></div>
      		<div class="col-sm-4" id="responseDur2"></div>
      		<div class="col-sm-2"><label><i18n:message code="i18n.incident.view.DealEndTimeLabel" /></label></div>
      		<div class="col-sm-4" id="dealDur2"></div>
      	</div>
      	<!-- 
      	<div class="clearfix">
      		<div class="col-sm-2"><label>响应时限</label></div>
      		<div class="col-sm-4" id="responseTime"></div>
      		<div class="col-sm-2"><label>处理时限</label></div>
      		<div class="col-sm-4" id="dealTime"></div>
      	</div>
      	<div class="clearfix">
      		<div class="col-sm-2"><label>响应截止时间</label></div>
      		<div class="col-sm-4" id="responseDur2"></div>
      		<div class="col-sm-2"><label>处理截止时间</label></div>
      		<div class="col-sm-4" id="dealDur2"></div>
      	</div>
      	 -->
      	<div class="clearfix">
      		<div class="col-sm-2"><label><i18n:message code="i18n.incident.view.BriefLabel" /></label></div>
      		<div class="col-sm-10" id="brief"></div>
      	</div>
      	<div class="clearfix">
      		<div class="col-sm-2"><label><i18n:message code="i18n.incident.view.DetailLabel" /></label></div>
      		<div class="col-sm-10" id="detail"></div>
      	</div>
      	<div class="clearfix">
      		<div class="col-sm-2"><label><i18n:message code="i18n.incident.view.AttachmentLabel" /></label></div>
      		<div class="col-sm-10" id="attachments"></div>
      	</div>
      	<div class="clearfix">
      		<div class="col-sm-2"><label><i18n:message code="i18n.incident.view.SolutionLabel" /></label></div>
      		<div class="col-sm-10" id="itSolution"></div>
      	</div>
      </div>
    </div>
  </div>
  <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
          	<i18n:message code="i18n.incident.view.ContactInfo" />
        </a>
      </h4>
    </div>
    <div id="collapseThree" class="panel-collapse collapse" role="tabpanel">
      <div class="panel-body inci-base-info">
        <div class="clearfix">
      		<div class="col-sm-2"><label><i18n:message code="i18n.incident.view.ContactNameLabel" /></label></div>
      		<div class="col-sm-4" id="opName"></div>
      		<div class="col-sm-2"><label><i18n:message code="i18n.incident.view.ContactEmailLabel" /></label></div>
      		<div class="col-sm-4" id="loginCode"></div>
      	</div>
      	<div class="clearfix">
      		<div class="col-sm-2"><label><i18n:message code="i18n.incident.view.ContactMobileLabel" /></label></div>
      		<div class="col-sm-4" id="mobileNo"></div>
      		<div class="col-sm-2"><label><i18n:message code="i18n.incident.view.ContactPhoneLabel" /></label></div>
      		<div class="col-sm-4" id="officeTel"></div>
      	</div>
      </div>
    </div>
  </div>
</div>
<div id="finishWin" title="<i18n:message code="i18n.incident.view.FinishTransWinTitle" />" style="width:540px;height:170px;">
	<div class="form-group clearfix  p10">
	    <div class="col-sm-12">
	      	<c:forEach var="finishVal" items="${finishValP}" begin="0" step="1">
		    		<span class="radio-inline"><input type="radio" name="finishVal" value="${finishVal.paramCode}" text="${finishVal.paramValue}">${finishVal.paramValue}</span>
		    </c:forEach>
	    </div>
	</div>
    <div class="clearfix p10" style="text-align:center">
		<button id="finishBtn" class="btn btn-warning" type="button" incidentId="" onclick="finishCommit()"><i18n:message code="i18n.incident.view.CommitBtn" /></button>
  	</div>
</div>
<div id="completeWin" title="<i18n:message code="i18n.incident.view.CompleteWinTitle" />" style="width:500px;height:450px">
	<div class="form-group clearfix p10">
   	 	<label for="productSel" class="col-sm-3 control-label"><i18n:message code="i18n.incident.view.CompleteProductLabel" /></label>
    	<div class="col-sm-9">
		      <input style="width:100%"  name="prodSel" id="prodSel" />
		</div>
	</div>
	<div class="form-group clearfix p10">
   	 	<label for="moduleSel" class="col-sm-3 control-label"><i18n:message code="i18n.incident.view.CompleteModuleLabel" /></label>
    	<div class="col-sm-9">
		       <input style="width:100%"  name="moduleSel" id="moduleSel" />
		</div>
	</div>
	<div class="form-group clearfix p10">
   	 	<label for="inciTypeSel" class="col-sm-3 control-label"><i18n:message code="i18n.incident.view.CompleteCategoryLabel" /></label>
    	<div class="col-sm-9">
		      <input class="easyui-combobox" style="width:100%"  name="classVar" id="inciTypeSel" data-options="
					url:rootPath+'/param/list?kindCode=IC_CLASS',
					method:'get',
					valueField:'paramCode',
					textField:'paramValue',
					panelHeight:'auto'"
				/>
		</div>
	</div>
	<div class="form-group clearfix p10">
	    <label for="moduleSel" class="col-sm-3 control-label"><i18n:message code="i18n.incident.view.CompleteDegreeLabel" /></label>
	    <div class="col-sm-9">
	      	<c:forEach var="affect" items="${affectP}" begin="0" step="1">
		    		<span class="radio-inline"><input type="radio" name="affectVar" value="${affect.paramCode}" text="${affect.paramValue}">${affect.paramValue}</span>
		    </c:forEach>
	    </div>
	</div>
	<div class="form-group clearfix  p10">
	    <label for="moduleSel" class="col-sm-3 control-label"><i18n:message code="i18n.incident.view.CompletePriorityLabel" /></label>
	    <div class="col-sm-9">
	      	<c:forEach var="priority" items="${priorityP}" begin="0" step="1">
		    		<span class="radio-inline"><input type="radio" name="priorityVar" value="${priority.paramCode}" text="${priority.paramValue}">${priority.paramValue}</span>
		    </c:forEach>
	    </div>
	</div>
	<div class="form-group clearfix p10">
	    <label for="moduleSel" class="col-sm-3 control-label"><i18n:message code="i18n.incident.view.CompleteComplexLabel" /></label>
	    <div class="col-sm-9">
	      	<c:forEach var="complex" items="${complexP}" begin="0" step="1">
		    		<span class="radio-inline"><input type="radio" name="complexVar" value="${complex.paramCode}" text="${complex.paramValue}">${complex.paramValue}</span>
		    </c:forEach>
	    </div>
	</div>
	
    <div class="clearfix p10" style="text-align:center">
		<button id="completeBtn" class="btn btn-warning" type="button" incidentId="" onclick="completeIncident()"><i18n:message code="i18n.incident.view.CommitBtn" /></button>
  	</div>
</div>
<div id="consultantSelWin" title="<i18n:message code="i18n.incident.view.TransferWinTitle" />" style="width:550px;height:400px;">
	<div class="clearfix p10">
   	 	<div class="input-group custom-search-form" style="width:300px;float:left;">
           <input type="text" class="form-control" placeholder="<i18n:message code="i18n.incident.view.TransferQryPlaceholder" />" id="consultantNameTxt"/>
           <span class="input-group-btn">
	            <button class="btn btn-default" style="height:34px" type="button" onclick="queryConsultants()">
	                <i class="fa fa-search"></i>
	            </button>
       		</span>
         </div>
	</div>
	<div class="clearfix p10">
	    <table  style="width:99%;height:240px" id="consultantSelTable"></table>
	</div>
    <div class="clearfix" style="text-align:center">
		<button id="consultantSelBtn" class="btn btn-warning" type="button" incidentId="" onclick="deliverConstCommit()"><i18n:message code="i18n.incident.view.CommitBtn" /></button>
  	</div>
</div>
<div id="uploadProgress" title="" style="width:300px;height:100px;border-top-width:1px">
		<div style="text-align:center;margin-top:10px;"><i18n:message code="i18n.upload.WaitMessage" /></div>
		<div class="progress" style="width:90%;margin-left:15px;margin-top:10px;">
		  <div class="progress-bar progress-bar-striped active"  role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
		    <span class="sr-only"><i18n:message code="i18n.upload.WaitMessage" /></span>
		  </div>
		</div>
</div>
</body>
</html>