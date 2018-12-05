<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<% String ctx=request.getContextPath(); %>
<input type="hidden" id="customerids" name="customerids" value="${valmap.customerids}">
<div class="row">
	<div class="col-md-12">
		<div class="box box-default">
            <div class="box-body">
              	<div class="row">
                	<label style="padding:10px 0px 0px 20px;">${valmap.customernames}</label>
                </div>
          	</div>
  		</div>
		<form class="form-horizontal" id="form">
			<div class="box-body">
				<c:forEach items="${oillist }" var="oilmap">
					 <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:2px 0px 0px 47px;">&nbsp;&nbsp;${oilmap.name }:</label>
					<div class="col-xs-9">
							<input type="checkbox" value="${oilmap.id }" name="oilType">
	               	</div>
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
		alert(oilType);
		var customerids='${valmap.customerids}';
		alert(customerids+",,,,"+oilType)
		$.ajax({
			type:'post',
			url:'<%=ctx %>/customer/oilsave',
			data:{'oilids':oilType,'customerids':customerids},
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