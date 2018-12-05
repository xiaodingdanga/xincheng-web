<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<% String ctx=request.getContextPath(); %>
<input type="hidden" id="id" name="id" value="${valmap.id}">
<div class="row">
	<div class="col-md-12">
		<form class="form-horizontal" id="form">
			<div class="box-body">
	            <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 15px;"><font color="red">*</font>实际收货数量:</label>
					<div class="col-xs-9">
	                   	<input type="text" class="form-control" id="realnum" name=realnum placeholder="实际收货数量" value="${valmap.realnum}">
	               	</div>
	            </div>
	            <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 15px;"><font color="red">*</font>实际订货价格:</label>
					<div class="col-xs-9">
	                   	<input type="text" class="form-control" id="realprice" name=realprice placeholder="实际订货价格" value="${valmap.realprice}">
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
	var realnum = '${valmap.realnum}';
	if(realnum==""){
		realnum='${valmap.num}';
	}
	$("#realnum").val(realnum);
	var realprice = '${valmap.realprice}';
	if(realprice==""){
		realprice='${valmap.price}';
	}
	$("#realprice").val(realprice);
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
        	realnum:{
            	required:true
            },
            realprice:{
            	required:true,
            	price:true
            }
        },
        messages:{
        	realnum:{
            	required:"请填写实际收货数量",
            },
            realprice:{
            	required:"请填写实际收货价格！",
            	price:"请填写数字并保留两位小数！"
            }
        },
        errorPlacement:function(error,element){//错误信息显示位置
        	element.after(error);
       	}
    });
});

//实际收货数量保存
function save(){
	var ret="";
	if(validate){//验证重置
		validate.resetForm();
	}
	if(validate.form()){
		var id=$("#id").val();
		var realnum=$("#realnum").val();
		var realprice=$("#realprice").val();
		$.ajax({
			type:'post',
			url:'<%=ctx %>/quotation/save',
			data:{'id':id,'realnum':realnum,'realprice':realprice},
			dataType:'json',
			async:false,
			success:function(data){
				if(data.state=="error"){
					Dialog.alert(data.code);
				}else if(data.state=="success"){
					ret="ok";
				}
			}
		});
	}
	return ret;
}
</script>