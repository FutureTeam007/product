<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>事件查看</title>
	<%@ include file="../common/commonHead.jsp"%>
	<%@ include file="../common/easyuiHead.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/page/incidentmgnt/js/incidentView.js"></script>
</head>
<body style="background:#fff;padding-left:5px;overflow-x:hidden;">
  <div>
		<button type='button' class='btn btn-link' onclick='parent.hideSubPage()'>&lt;&lt;关闭</button>
  </div>
  <div class="inci-dtl-title">
		<h5 style="font-weight:bold">[系列号:CNH14090012]提出时间：2014-09-26 12:00，处理人：Sally，状态：一线顾问处理中</h5>
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
			       <a href="#">附件1</a> <a href="#">附件2</a> <a href="#">附件3</a> <a href="#">附件4</a> <a href="#">附件5</a>
			       <button type="button" class="btn btn-primary btn-outline btn-sm">选择文件</button>
			    </div>
  			</div>
  			<div class="form-group clearfix">
	  			<div class="col-sm-12 form-btns">
			      <button type="submit" class="btn btn-warning">提交</button>
			      <button type="submit" class="btn btn-warning">转顾问</button>
			      <button type="submit" class="btn btn-warning">转客户</button>
			      <button type="submit" class="btn btn-warning">挂起</button>
			      <button type="submit" class="btn btn-warning">完成</button>
			      <button type="submit" class="btn btn-warning">关闭</button>
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
	      <div class="panel-body">
	        	<div class="trans-item">
	        		<div class="col-sm-1">事务4</div>
	        		<div class="col-sm-11">
		        		<div class="trans-item-header clearfix">
		        			<div><label>处理人：</label><span>李四</span></div>
		        			<div><label>处理时间：</label><span>2014年9月25日 15:30:28</span></div>
		        			<div><label>类型：</label><span>流程事务</span></div>
		        		</div>
		        		<div>
		        			<div><label>事务说明：</label><br/><span class="trans-content">事务说明事务说明事务说明事务说明事务说明事务说明事务说明事务说明</span></div>
		        		</div>
		        		<div>
		        			<div><label>附件：</label><br/><span><a href="#">附件1</a> <a href="#">附件2</a> <a href="#">附件3</a></span></div>
		        		</div>
	        		</div>
	        	</div>
	        	<div class="trans-item">
	        		<div class="col-sm-1">事务3</div>
	        		<div class="col-sm-11">
		        		<div class="trans-item-header clearfix">
		        			<div><label>处理人：</label><span>李四</span></div>
		        			<div><label>处理时间：</label><span>2014年9月25日 15:30:28</span></div>
		        			<div><label>类型：</label><span>流程事务</span></div>
		        		</div>
		        		<div>
		        			<div><label>事务说明：</label><br/><span class="trans-content">事务说明事务说明事务说明事务说明事务说明事务说明事务说明事务说明</span></div>
		        		</div>
		        		<div>
		        			<div><label>附件：</label><br/><span><a href="#">附件1</a> <a href="#">附件2</a> <a href="#">附件3</a></span></div>
		        		</div>
	        		</div>
	        	</div>
	        	<div class="trans-item">
	        		<div class="col-sm-1">事务2</div>
	        		<div class="col-sm-11">
		        		<div class="trans-item-header clearfix">
		        			<div><label>处理人：</label><span>李四</span></div>
		        			<div><label>处理时间：</label><span>2014年9月25日 15:30:28</span></div>
		        			<div><label>类型：</label><span>流程事务</span></div>
		        		</div>
		        		<div>
		        			<div><label>事务说明：</label><br/><span class="trans-content">事务说明事务说明事务说明事务说明事务说明事务说明事务说明事务说明</span></div>
		        		</div>
		        		<div>
		        			<div><label>附件：</label><br/><span><a href="#">附件1</a> <a href="#">附件2</a> <a href="#">附件3</a></span></div>
		        		</div>
	        		</div>
	        	</div>
	        	<div class="trans-item">
	        		<div class="col-sm-1">事务1</div>
	        		<div class="col-sm-11">
		        		<div class="trans-item-header clearfix">
		        			<div><label>处理人：</label><span>李四</span></div>
		        			<div><label>处理时间：</label><span>2014年9月25日 15:30:28</span></div>
		        			<div><label>类型：</label><span>流程事务</span></div>
		        		</div>
		        		<div>
		        			<div><label>事务说明：</label><br/><span class="trans-content">事务说明事务说明事务说明事务说明事务说明事务说明事务说明事务说明</span></div>
		        		</div>
		        		<div>
		        			<div><label>附件：</label><br/><span><a href="#">附件1</a> <a href="#">附件2</a> <a href="#">附件3</a></span></div>
		        		</div>
	        		</div>
	        	</div>
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
      		<div class="col-sm-4">CNH上海</div>
      		<div class="col-sm-2"><label>类别</label></div>
      		<div class="col-sm-4">业务咨询</div>
      	</div>
        <div class="clearfix">
      		<div class="col-sm-2"><label>产品线</label></div>
      		<div class="col-sm-4">CNH-BAAN系统咨询规划</div>
      		<div class="col-sm-2"><label>服务目录</label></div>
      		<div class="col-sm-4">账务管理</div>
      	</div>
      	<div class="clearfix">
      		<div class="col-sm-2"><label>影响度</label></div>
      		<div class="col-sm-4">一般</div>
      		<div class="col-sm-2"><label>复杂度</label></div>
      		<div class="col-sm-4">一般</div>
      	</div>
      	<div class="clearfix">
      		<div class="col-sm-2"><label>发生时间</label></div>
      		<div class="col-sm-4">2014-09-21 15:33</div>
      		<div class="col-sm-2"><label>事件状态</label></div>
      		<div class="col-sm-4">待一线顾问响应</div>
      	</div>
      	<div class="clearfix">
      		<div class="col-sm-2"><label>响应时限</label></div>
      		<div class="col-sm-4">2小时</div>
      		<div class="col-sm-2"><label>处理时限</label></div>
      		<div class="col-sm-4">72小时</div>
      	</div>
      	<div class="clearfix">
      		<div class="col-sm-2"><label>响应截止时间</label></div>
      		<div class="col-sm-4">2014-09-26 14:00
				<button class="btn btn-success btn-circle" type="button"></button>
				<button class="btn btn-warning btn-circle" type="button"></button>
				<button class="btn btn-danger btn-circle" type="button"></button>
      		</div>
      		<div class="col-sm-2"><label>处理截止时间</label></div>
      		<div class="col-sm-4">2014-09-29 12:00
      			<button class="btn btn-success btn-circle" type="button"></button>
				<button class="btn btn-warning btn-circle" type="button"></button>
				<button class="btn btn-danger btn-circle" type="button"></button>
      		</div>
      	</div>
      	<div class="clearfix">
      		<div class="col-sm-2"><label>当前处理人</label></div>
      		<div class="col-sm-10">Sally</div>
      	</div>
      	<div class="clearfix">
      		<div class="col-sm-2"><label>事件简述</label></div>
      		<div class="col-sm-10">请求简述请求简述请求简述请求简述请求简述请求简述请求简述请求简述请求简述请求简述请求简述请求简述请求简述</div>
      	</div>
      	<div class="clearfix">
      		<div class="col-sm-2"><label>事件详细说明</label></div>
      		<div class="col-sm-10">事件详细说明事件详细说明事件详细说明事件详细说明事件详细说明事件详细说明事件详细说明事件详细说明事件详细说明事件详细说明事件详细说明事件详细说明事件详细说明事件详细说明事件详细说明事件详细说明事件详细说明事件详细说明</div>
      	</div>
      	<div class="clearfix">
      		<div class="col-sm-2"><label>附件</label></div>
      		<div class="col-sm-10"><a href="#">附件1</a> <a href="#">附件2</a> <a href="#">附件3</a></div>
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
      		<div class="col-sm-4">Jessica</div>
      		<div class="col-sm-2"><label>电子邮件</label></div>
      		<div class="col-sm-4">Jessica@xx.com</div>
      	</div>
      	<div class="clearfix">
      		<div class="col-sm-2"><label>手机</label></div>
      		<div class="col-sm-4">13410231022</div>
      		<div class="col-sm-2"><label>固定电话</label></div>
      		<div class="col-sm-4">021-88880909转1234</div>
      	</div>
      </div>
    </div>
  </div>
</div>
</body>
</html>