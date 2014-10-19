<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>事件查看</title>
	<%@ include file="../common/commonHead.jsp"%>
	<%@ include file="../common/easyuiHead.jsp"%>
	<script type="text/javascript">
		var incidentId = "<%=id%>";
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/res/easyui/jquery.tmpl.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/page/incidentmgnt/js/incidentView.js"></script>
</head>
<body style="background:#fff;padding-left:5px;overflow-x:hidden;">
  <div>
		<button type='button' class='btn btn-link top-close-link' onclick='parent.hideSubPage()'>&lt;&lt;关闭</button>
  </div>
  <div class="inci-dtl-title">
		<h5 style="font-weight:bold">[<span id="incidentCode"></span>]&nbsp;提出时间：<span id="registTime"></span>，处理人：<span id="scLoginName"></span>，状态：<span id="itStateCode"></span></h5>
  </div>
  <div class="inci-trans-commit-form" style="width:99%">
		<form class="form-horizonta inci-info-form" role="form">
			<div class="form-group clearfix">
			    <label for="transDesc" class="col-sm-2 control-label">事务说明</label>
			    <div class="col-sm-10">
			       <textarea class="form-control" rows="3" id="transDesc"></textarea>
			    </div>
  			</div>
  			<div class="form-group clearfix">
			    <label for="attachments" class="col-sm-2 control-label">附件</label>
			    <div class="col-sm-10">
			       <div id="commitAttach" class="list-inline pull-left ml5"><a href="#">附件1</a> <a href="#">附件2</a> <a href="#">附件3</a> <a href="#">附件4</a> <a href="#">附件5</a></div>
			       <button type="button" class="btn btn-primary btn-outline btn-xs ml10">选择文件</button>
			    </div>
  			</div>
  			<div class="form-group clearfix">
	  			<div class="col-sm-12 form-btns">
			      <input type="button" class="btn btn-warning" onclick="transCommit()" value="提交" />
			      <input type="button" class="btn btn-warning undis" id="openConsultantSelBtn" onclick="openConsultantSelWin()" value="转顾问处理" />
			      <input type="button" class="btn btn-warning undis" id="deliverCustCommitBtn" onclick="deliverCustCommit()" value="转客户补充资料" />
			      <input type="button" class="btn btn-warning undis" id="blockCommitBtn" onclick="blockCommit()" value="挂起" />
			      <input type="button" class="btn btn-warning undis" id="finishCommitBtn" onclick="finishCommit()" value="完成" />
			    </div>
  			</div>
		</form>
  </div>
  <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true" style="width:98%">
	  <div class="panel panel-default">
	    <div class="panel-heading">
	      <h4 class="panel-title">
	        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
	          	事务记录
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
	          	事件信息
	        </a>
      </h4>
    </div>
    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel">
      <div class="panel-body inci-base-info">
      	<div class="clearfix">
      		<div class="col-sm-2"><label>公司</label></div>
      		<div class="col-sm-4" id="custName"></div>
      		<div class="col-sm-2"><label>类别</label></div>
      		<div class="col-sm-4" id="classCodeOp"></div>
      	</div>
        <div class="clearfix">
      		<div class="col-sm-2"><label>产品线</label></div>
      		<div class="col-sm-4" id="prodName"></div>
      		<div class="col-sm-2"><label>服务目录</label></div>
      		<div class="col-sm-4" id="moduleName"></div>
      	</div>
      	<div class="clearfix">
      		<div class="col-sm-2"><label>影响度</label></div>
      		<div class="col-sm-4" id="affectCodeOp"></div>
      		<div class="col-sm-2"><label>复杂度</label></div>
      		<div class="col-sm-4" id="complexCode"></div>
      	</div>
      	<div class="clearfix">
      		<div class="col-sm-2"><label>发生时间</label></div>
      		<div class="col-sm-4" id="happenTime"></div>
      		<div class="col-sm-2"><label>事件状态</label></div>
      		<div class="col-sm-4" id="itPhase"></div>
      	</div>
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
      	<div class="clearfix">
      		<div class="col-sm-2"><label>当前处理人</label></div>
      		<div class="col-sm-10" id="infoScLoginName"></div>
      	</div>
      	<div class="clearfix">
      		<div class="col-sm-2"><label>事件简述</label></div>
      		<div class="col-sm-10" id="brief"></div>
      	</div>
      	<div class="clearfix">
      		<div class="col-sm-2"><label>事件详细说明</label></div>
      		<div class="col-sm-10" id="detail"></div>
      	</div>
      	<div class="clearfix">
      		<div class="col-sm-2"><label>附件</label></div>
      		<div class="col-sm-10" id="attachments"><a href="#">附件1</a> <a href="#">附件2</a> <a href="#">附件3</a></div>
      	</div>
      </div>
    </div>
  </div>
  <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
          	联系人信息
        </a>
      </h4>
    </div>
    <div id="collapseThree" class="panel-collapse collapse" role="tabpanel">
      <div class="panel-body inci-base-info">
        <div class="clearfix">
      		<div class="col-sm-2"><label>姓名</label></div>
      		<div class="col-sm-4" id="opName"></div>
      		<div class="col-sm-2"><label>电子邮件</label></div>
      		<div class="col-sm-4" id="loginCode"></div>
      	</div>
      	<div class="clearfix">
      		<div class="col-sm-2"><label>手机</label></div>
      		<div class="col-sm-4" id="mobileNo"></div>
      		<div class="col-sm-2"><label>固定电话</label></div>
      		<div class="col-sm-4" id="officeTel"></div>
      	</div>
      </div>
    </div>
  </div>
</div>
<div id="completeWin" title="审核事件信息" style="width:400px;height:200px;">
	<div class="form-group clearfix">
   	 	<label for="inciTypeSel" class="col-sm-2 control-label">事件类别</label>
    	<div class="col-sm-10">
		       <input class="easyui-combobox" style="width:100%"  name="inciTypeSel" id="inciTypeSel" data-options="
				url:'js/json/incidentType_data.json',
				method:'get',
				valueField:'paramCode',
				textField:'paramValue',
				panelHeight:'auto'"
			/>
		</div>
	</div>
	<div class="form-group clearfix">
	    <label for="moduleSel" class="col-sm-2 control-label">影响度</label>
	    <div class="col-sm-10">
	      	<span class="radio-inline">
			  <input type="radio" name="affectVar" id="effectLevel1" value="1"> 咨询
			</span>
			<span class="radio-inline">
			  <input type="radio" name="affectVar" id="effectLevel2" value="2"> 一般
			</span>
			<span class="radio-inline">
			  <input type="radio" name="affectVar" id="effectLevel3" value="3"> 严重
			</span>
			<span class="radio-inline">
			  <input type="radio" name="affectVar" id="effectLevel4" value="4"> 重大
			</span>
	    </div>
	</div>
	<div class="form-group clearfix">
	    <label for="moduleSel" class="col-sm-2 control-label">优先级</label>
	    <div class="col-sm-10">
	      	<span class="radio-inline">
			  <input type="radio" name="affectVar" id="effectLevel1" value="1"> 低
			</span>
			<span class="radio-inline">
			  <input type="radio" name="affectVar" id="effectLevel2" value="2" checked="checked"> 中
			</span>
			<span class="radio-inline">
			  <input type="radio" name="affectVar" id="effectLevel3" value="3"> 高
			</span>
			<span class="radio-inline">
			  <input type="radio" name="affectVar" id="effectLevel4" value="4"> 紧急
			</span>
	    </div>
	</div>
	<div class="form-group clearfix">
	    <label for="moduleSel" class="col-sm-2 control-label">复杂度</label>
	    <div class="col-sm-10">
	      	<span class="radio-inline">
			  <input type="radio" name="affectVar" id="effectLevel1" value="1" checked="checked"> 一般
			</span>
			<span class="radio-inline">
			  <input type="radio" name="affectVar" id="effectLevel2" value="2"> 复杂
			</span>
	    </div>
	</div>
	
    <div class="clearfix">
		<button id="completeBtn" class="btn btn-warning" type="button" incidentId="" onclick="completeIncident()">提交</button>
  	</div>
</div>
<div id="consultantSelWin" title="选择转派的顾问" style="width:500px;height:200px;">
	<div class="clearfix">
   	 	<div class="input-group custom-search-form" style="width:300px;float:left;">
           <input type="text" class="form-control" placeholder="输入顾问姓名查询" />
           <span class="input-group-btn">
	            <button class="btn btn-default" style="height:34px" type="button">
	                <i class="fa fa-search"></i>
	            </button>
       		</span>
         </div>
	</div>
	<div class="clearfix">
	    <table class="easyui-datagrid" style="width:99%;height:300px" id="consultantSelTable"
			data-options="singleSelect:true,collapsible:false,
				url:rootPath+'/incident/list',
				queryParams:{'stateVal':2},
				method:'get',
				loadMsg:'数据加载中，请稍后……',
				remoteSort:false,
				multiSort:false,
				pagination:true
			">
			<thead>
				<tr>
					<th data-options="field:'scOpId'," width="20%">选择</th>
					<th data-options="field:'opName'" width="20%">顾问姓名</th>
					<th data-options="field:'loginCode'" width="20%">顾问账号</th>
					<th data-options="field:'mobileNo'" width="20%">手机号码</th>
					<th data-options="field:'officeTel'" width="20%">办公电话</th>
					<th data-options="field:'firstName',hidden:true"></th>
					<th data-options="field:'lastName',hidden:true"></th>
				</tr>
			</thead>
		</table>
	</div>
    <div class="clearfix">
		<button id="consultantSelBtn" class="btn btn-warning" type="button" incidentId="" onclick="deliverConstCommit()">提交</button>
  	</div>
</div>
</body>
</html>