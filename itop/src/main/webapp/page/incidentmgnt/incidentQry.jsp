<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ include file="../common/commonHead.jsp"%>
	<title>事件管理</title>
</head>
<body>
<jsp:include page="../common/pageMasterStart.jsp"></jsp:include>
<div>
	<div class="inci-search">
		<div>
			<span>
		    	<label for="inciNo">事件序列号</label>
		    	<input type="text" class="form-control" id="inciNo"/>
		  	</span>
		  	<span>
		    	<label for="inciShortDesc">事件简述</label>
		    	<input type="text" class="form-control" id="inciShortDesc"/>
		  	</span>
		  	<span>
		    	<label for="inciType">事件类别</label>
		    	<input type="text" class="form-control" id="inciType"/>
		  	</span>
		  	<span>
		    	<label for="productId">产品线</label>
		    	<input type="text" class="form-control" id="productId"/>
		  	</span>
	  	</div>
	  	<div>
			<span>
		    	<label for="inciNo">影响度</label>
		    	<input type="text" class="form-control" id="inciNo"/>
		  	</span>
		  	<span>
		    	<label for="inciNo">优先级</label>
		    	<input type="text" class="form-control" id="inciShortDesc"/>
		  	</span>
		  	<span>
		    	<label for="inciType">提出时间</label>
		    	<input type="text" class="form-control" id="inciType"/>
		  	</span>
		  	<span>
		    	<button type="button" class="btn btn-primary btn-sm">&nbsp;&nbsp;&nbsp;查询&nbsp;&nbsp;&nbsp;</button>
		  	</span>
	  	</div>
	</div>
</div>
<jsp:include page="../common/pageMasterEnd.jsp"></jsp:include>
</body>
</html>