<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<% String ctx=request.getContextPath(); %>
<input type="hidden" id="customerid" name="customerid" value="${valmap.customerid}">
<div class="row">
	<div class="col-md-12">
		<form class="form-horizontal" id="form">
			<div class="box-body">
	            <div class="form-group">
					<label class="col-xs-2 control-label" style="padding:5px 0px 0px 50px;"><font color="red">*</font>ip:</label>
					<div class="col-xs-10">
	                   	<input type="text" class="form-control" id="ip" name="ip" placeholder="例如:192.168.1.1" value="">
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
	//validate验证
	validate=$("#form").validate({
		//debug:true,//调试模式取消submit的默认提交功能   
        //errorClass:"label.error",//默认为错误的样式类为：error   
        focusInvalid:true,//当为false时，验证无效时，没有焦点响应  
        onkeyup:false,   
        rules:{
            ip:{
            	required:true,
            }
        },
        messages:{
            password:{
            	required:"请填写ip！",
            }
        },
        errorPlacement:function(error,element){//错误信息显示位置
        	element.after(error);
       	}
    });
});
function isIP(ip){
	var reg =  /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/   
	return reg.test(ip);
}
//部门保存
function save(){
	var ret="";
	var ip=$("#ip").val();
	if(validate){//验证重置
		validate.resetForm();
	}
	if(!isIP(ip)){
		alert("ip格式错误！");
	}
	if(validate.form()&&isIP(ip)){
		var customerid=$("#customerid").val();
		$.ajax({
			type:'post',
			url:'<%=ctx %>/customer/ipsave',
			data:{'customerid':customerid,'ip':ip},
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