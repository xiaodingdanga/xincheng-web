<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<% String ctx=request.getContextPath(); %>
<input type="hidden" id="id" name="id" value="${valmap.id}">
<input type="hidden" id="money" name="money" value="${valmap.money}">
<div class="row">
	<div class="col-md-12">
		<form class="form-horizontal" id="form">
			<div class="box-body">
	            <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 45px;"><font color="red">*</font>提现金额:</label>
					<div class="col-xs-9">
	                   	<input type="text" class="form-control" id="price" name="price" placeholder="提现金额" >
	               	</div>
	            </div>
	       	</div>
       	</form>
    </div>
</div>
<script>
//验证
var validate;
//页面初始化
$(window).load(function(){
	//价格校验
	jQuery.validator.addMethod(
		"price",
		function(value,element){
			var sub=true;
			var tel =/^[0-9]+([.]\d{1,2})?$/;
			if(!tel.test(value)){
				sub=false;
			}
	  		return sub;    
		},
		"价格格式不正确！"
	);
	//validate验证
	validate=$("#form").validate({
        focusInvalid:true,//当为false时，验证无效时，没有焦点响应  
        onkeyup:false,   
        rules:{
        	price:{
            	required:true,
            	price:true
            }
        },
        messages:{
        	price:{
            	required:"请填写充值金额！",
            	price:"请填写数字并保留两位小数！"
            }
        },
        errorPlacement:function(error,element){//错误信息显示位置
        	element.after(error);
       	}
    });
});

//部门保存
function save(){
	var ret="";
	if(validate){//验证重置
		validate.resetForm();
	}
	if(validate.form()){
		var id=$("#id").val();
		var price=$("#price").val();
		var money =$("#money").val();
		if(parseFloat(money)<=0||parseFloat(price)>parseFloat(money)||parseFloat(price)<=0){
			Dialog.alert("提现金额不符合条件！")
		}else{
			$.ajax({
				type:'post',
				url:'<%=ctx %>/user/tixainsave',
				data:{'id':id,'price':price,'money':money},
				dataType:'text',
				async:false,
				success:function(data){
					ret="ok";
				}
			});	
		}
	}
	return ret;
}
</script>