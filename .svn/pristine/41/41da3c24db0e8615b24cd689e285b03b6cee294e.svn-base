<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<% String ctx=request.getContextPath(); %>
<input type="hidden" id="customerid" name="customerid" value="${valmap.customerid}">
<div class="row">
	<div class="col-md-12">
		<div class="box box-default">
            <div class="box-body">
              	<div class="row">
                	<label style="padding:10px 0px 0px 20px;font-size:20px">${valmap.customername}</label>
                </div>
          	</div>
  		</div>
		<form class="form-horizontal" id="form">
			<div class="box-body">
				<c:forEach items="${oillist }" var="oilmap">
					 <div class="form-group">
						 <div class="col-xs-2 control-label" style="padding-left:50px;">
								<input type="checkbox" value="${oilmap.id }" <c:if test="${fn:contains(oiltypelist, oilmap.id) }">checked="checked"</c:if> name="oilType">
		               	</div>
						<label class="col-xs-10" >&nbsp;&nbsp;${oilmap.name }</label>
	            	</div>
				</c:forEach>
	       	</div>
       	</form>
    </div>
</div>
<script>
//validate验证
var validate;
//页面初始化
$(window).load(function(){
	
});
//保存
function save(){
	var oilTypeDIV= document.getElementsByName("oilType");
	var ret="";
	var isok=0;
	if(oilTypeDIV){//验证重置
		isok=1;
	}else{
		alert("请选择油类型！")
	}
	if(isok==1){
		var oilType = "";
		for( var i = 0; i < oilTypeDIV.length; i++ )
		{
		if ( oilTypeDIV[i].checked ){
			oilType += oilTypeDIV[i].value+',';
		}
		}
		oilType = oilType.substr(0,oilType.length-1);
		var customerid='${valmap.customerid}';
		$.ajax({
			type:'post',
			url:'<%=ctx %>/customer/oilsave',
			data:{'oilids':oilType,'customerid':customerid},
			dataType:'text',
			async:false,
			success:function(data){
				ret="ok";
			}
		});
	}
	return ret;
}

</script>