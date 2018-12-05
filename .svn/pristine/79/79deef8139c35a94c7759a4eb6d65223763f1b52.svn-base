<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<% String ctx=request.getContextPath(); %>
<input type="hidden" id="id" name="id" value="${valmap.id}">
<div class="row">
	<div class="col-md-12">
		<form class="form-horizontal" id="form">
			<div class="box-body">
	            <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 50px;"><font color="red">*</font>石油名称:</label>
					<div class="col-xs-9">
	                   	<input type="text" class="form-control" id="name" name="name" placeholder="石油名称" value="${valmap.name}">
	               	</div>
	            </div>
	            <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 50px;"><font color="red">*</font>提成金额:</label>
					<div class="col-xs-9">
	                   	<input type="text" class="form-control" id="disprice" name="disprice" placeholder="提成金额" value="${valmap.disprice}">
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
		"disprice",
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
		//debug:true,//调试模式取消submit的默认提交功能   
        //errorClass:"label.error",//默认为错误的样式类为：error   
        focusInvalid:true,//当为false时，验证无效时，没有焦点响应  
        onkeyup:false,   
        rules:{
        	name:{
            	required:true,
            	remote:{
               	    url:"<%=ctx%>/oil/checkname",//后台处理程序
               	 	async:false,				//同步验证设置
               	    type:"post",               //数据发送方式
               	    dataType:"json",           //接受数据格式   
               	    data:{//要传递的数据
               	    	id:function(){
               	    		return $("#id").val();
               	    	},
               	    	name:function(){
               	        	return $("#name").val();
               	        }
               	    }
               	}
            },
            disprice:{
            	required:true,
      			disprice:true
            }
        },
        messages:{
        	name:{
                required:"请填写石油名称！",
                remote:"名称重复，请重新填写！"
            },
            disprice:{
            	required:"请填写提成金额！",
            	disprice:"请填写数字并保留两位小数！"
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
		var name=$("#name").val();
		var disprice=$("#disprice").val();
		$.ajax({
			type:'post',
			url:'<%=ctx %>/oil/save',
			data:{'id':id,'name':name,'disprice':disprice},
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