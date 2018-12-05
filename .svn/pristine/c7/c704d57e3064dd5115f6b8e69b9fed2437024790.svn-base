<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<% String ctx=request.getContextPath(); %>
<input type="hidden" id="id" name="id" value="${valmap.id}">
<div class="row">
	<div class="col-md-12">
		<form class="form-horizontal" id="form">
			<div class="box-body">
	            <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 45px;"><font color="red">*</font>会员卡号:</label>
					<div class="col-xs-9">
	                   	<input type="text" class="form-control" id="serialnum" name="serialnum" placeholder="会员卡号" value="${valmap.serialnum}">
	               	</div>
	            </div>
	            <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 60px;"><font color="red">*</font>芯片号:</label>
					<div class="col-xs-9">
	                   	<input type="text" class="form-control" id="chipnum" name="chipnum" placeholder="芯片号" value="${valmap.chipnum}">
	               	</div>
	            </div>
	            <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 75px;">姓名:</label>
					<div class="col-xs-9">
	                   	<input type="text" class="form-control" id="name" name="name" placeholder="姓名" value="${valmap.name}">
	               	</div>
	            </div>
	            <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 60px;">电话号:</label>
					<div class="col-xs-9">
	                   	<input type="text" class="form-control" id="tel" name="tel" placeholder="电话号" value="${valmap.tel}">
	               	</div>
	            </div>
	            <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 75px;">类型:</label>
					<div class="col-xs-9">
						<select class="form-control" id="cardtype" name="cardtype" >
							<option value="0"  selected="selected" >储值卡</option>
							<option value="1" >信用卡</option>
						</select>
	               	</div>
	            </div>
	            <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 60px;">分销员:</label>
					<div class="col-xs-9">
						<select class="form-control" id="dis" name="dis" >
							<option value=""  selected="selected" ></option>
						</select>
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
	var id ='${valmap.id}' ;
	if(id==""){
		$("#chipnum").focus();
	}
	$("#dis").val('${valmap.disid}')
	var cardtype = '${valmap.cardtype}';
	if(cardtype!=""){
		$("#cardtype").val(cardtype);
	}
	loaddis();
	//价格校验
	jQuery.validator.addMethod(
		"tel",
		function(value,element){
			var sub=true;
			var tel =/^[1][0-9]{10}$/;
			if(value!=""){
				if(!tel.test(value)){
					sub=false;
				}
			}
	  		return sub;    
		},
		"电话号码格式不正确！"
	);
	//校验芯片号是否重复
	jQuery.validator.addMethod(
		"chipnum",
		function(value,element){
			var id =$("#id").val();
			var chipnum = $("#chipnum").val();
			var sub=false;
			$.ajax({
				type:'post',
				url:'<%=ctx %>/member/checkserialnum',
				data:{'id':id,'chipnum':chipnum},
				dataType:'text',
				async:false,
				success:function(data){
					sub=true;
				}
			});
	  		return sub;    
		},
		"电话号码格式不正确！"
	);
	//validate验证
	validate=$("#form").validate({
        focusInvalid:true,//当为false时，验证无效时，没有焦点响应  
        onkeyup:false,   
        rules:{
        	serialnum:{
            	required:true,
            	remote:{
               	    url:"<%=ctx%>/member/checkserialnum",//后台处理程序
               	 	async:false,				//同步验证设置
               	    type:"post",               //数据发送方式
               	    dataType:"json",           //接受数据格式   
               	    data:{//要传递的数据
               	    	id:function(){
               	    		return $("#id").val();
               	    	},
               	    	serialnum:function(){
               	        	return $("#serialnum").val();
               	        }
               	    }
               	}
            },
            chipnum:{
            	required:true,
            	chipnum:true
            	},
            tel:{
            	tel:true
            }
        },
        messages:{
        	serialnum:{
                required:"请填写填写会员卡号！",
                remote:"会员卡号重复，请重新填写！"
            },
            chipnum:{
            	required:"请填写芯片号！",
            	chipnum:"芯片号重复，请重新填写！"
            },
            tel:{
            	tel:"电话号码格式不正确！"
            }
        },
        errorPlacement:function(error,element){//错误信息显示位置
        	element.after(error);
       	}
    });
});
function loaddis(){
	$.ajax({
		url:'<%=ctx%>/member/loaddis',
		type:'post',
		success:function(data){
			var size = $("#dis option").length;
			if(size==1){
				for(var i=0;i<data.length;i++){
					$("#dis").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
				}
			}
			$("#dis").val("${valmap.disid}");
		}
	});
}
//部门保存
function save(){
	var ret="";
	if(validate){//验证重置
		validate.resetForm();
	}
	if(validate.form()){
		var id=$("#id").val();
		var name=$("#name").val();
		var tel=$("#tel").val();
		var serialnum=$("#serialnum").val();
		var chipnum=$("#chipnum").val();
		var cardtype=$("#cardtype").val();
		var disid=$("#dis").val();
		$.ajax({
			type:'post',
			url:'<%=ctx %>/member/save',
			data:{'id':id,'name':name,'tel':tel,'serialnum':serialnum,'chipnum':chipnum,'cardtype':cardtype,'disid':disid},
			dataType:'text',
			async:false,
			success:function(data){
				ret="ok";
			}
		});
	}
	return ret;
}
//下一个
function next(){
	var ret="";
	if(validate){//验证重置
		validate.resetForm();
	}
	if(validate.form()){
		var id=$("#id").val();
		var name=$("#name").val();
		var tel=$("#tel").val();
		var serialnum=$("#serialnum").val();
		var chipnum=$("#chipnum").val();
		var cardtype=$("#cardtype").val();
		var disid=$("#dis").val();
		$.ajax({
			type:'post',
			url:'<%=ctx %>/member/next',
			data:{'id':id,'name':name,'tel':tel,'serialnum':serialnum,'chipnum':chipnum,'cardtype':cardtype,'disid':disid},
			dataType:'json',
			async:false,
			success:function(data){
				var serialnum = data.serialnum;
				$("#serialnum").val(serialnum);
				$("#name").val("");
				$("#tel").val("");
				$("#chipnum").val("");
				$("#cardtype").val("0");
				$("#chipnum").focus();
				$("#dis").val("");
			}
		});
	}
	return ret;
}
</script>