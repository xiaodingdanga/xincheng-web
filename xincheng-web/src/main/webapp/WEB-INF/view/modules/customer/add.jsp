<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<% String ctx=request.getContextPath(); %>
<div class="row">
	<div class="col-md-12">
		<form class="form-horizontal" id="form">
		<input type="hidden" id="id" name="id" value="${valmap.id}">
			<div class="box-body">
	            <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 50px;"><font color="red">*</font>加油站名称:</label>
					<div class="col-xs-9">
	                   	<input type="text" class="form-control" id="name" name="name" placeholder="加油站名称" value="${valmap.name}">
	               	</div>
	            </div>
	            <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 95px;"><font color="red">*</font>电话:</label>
					<div class="col-xs-9">
	                   	<input type="text" class="form-control" id="tel" name="tel" placeholder="电话" value="${valmap.tel}">
	               	</div>
	            </div>
	            <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 95px;">地址:</label>
					<div class="col-xs-9">
	                   	<input type="text" class="form-control" id="address" name="address" placeholder="地址" value="${valmap.address}">
	               	</div>
	            </div>
	             <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 50px;">是否绑定ip:</label>
					<div class="col-xs-9" style="padding:5px 0px 0px 15px">
	                   	<input type="radio" <c:if test="${valmap.isIP==1}">checked="checked"</c:if>  name="isIP"  class="isIP" value="1">是
	                   	<input type="radio" <c:if test="${valmap.isIP==0}">checked="checked"</c:if> <c:if test="${valmap.id==null}">checked="checked"</c:if> name="isIP" class="isIP" value="0">否
	               	</div>
	            </div>
	             <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 95px;">备注:</label>
					<div class="col-xs-9">
	                   	<input type="text" class="form-control" id="remark" name="remark" placeholder="备注"  value="${valmap.remark}">
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
        	name:{
            	required:true,
            	remote:{
               	    url:"<%=ctx%>/customer/checkname",//后台处理程序
               	 	async:false,				//同步验证设置
               	    type:"post",               //数据发送方式
               	    dataType:"json",           //接受数据格式   
               	    data:{//要传递的数据
               	    	id:function(){
               	    		return $("#id").val();
               	    	},
               	    	account:function(){
               	        	return $("#name").val();
               	        }
               	    }
               	}
            },
            tel:{
            	required:true
            },
            address:{
            	required:true
            }
        },
        messages:{
        	name:{
                required:"请填写加油站名称！",
                remote:"加油站名称重复，请重新填写！"
            },
            tel:{
            	required:"请填写联系电话！"
            },
            address:{
            	required:"请填写详细地址",
            }
        },
        errorPlacement:function(error,element){//错误信息显示位置
        	element.after(error);
       	}
    });
});
//保存加油站信息
function save(){
	var ret="";
	if(validate){//验证重置
		validate.resetForm();
	}
	if(validate.form()){
		var id=$("#id").val();
		var name=$("#name").val();
		var tel=$("#tel").val();
		var address=$("#address").val();
		var isIP=$('input[name="isIP"]:checked').val();
		var remark=$("#remark").val();
		$.ajax({
			type:'post',
			url:'<%=ctx %>/customer/save',
			data:{'id':id,'name':name,'tel':tel,'address':address,'remark':remark,'isIP':isIP},
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