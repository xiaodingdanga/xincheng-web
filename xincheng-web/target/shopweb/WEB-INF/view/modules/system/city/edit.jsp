<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<% String ctx=request.getContextPath(); %>
<input type="hidden" id="pid" name="pid" value="${valmap.pid}">
<div class="row">
	<div class="col-md-12">
		<form class="form-horizontal" id="form">
			<div class="box-body">
	            <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 50px;"><font color="red">*</font>城市名称:</label>
					<div class="col-xs-9">
	                   	<input type="text" class="form-control" id="name" name="name" placeholder="城市名称" value="${valmap.name}">
	               	</div>
	            </div>
	            <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 50px;"><font color="red">*</font>城市邮编:</label>
					<div class="col-xs-9">
	                   	<input type="text" class="form-control" id="code" name="code" placeholder="城市邮编" value="${valmap.code}">
	               	</div>
	            </div>
	            <div class="form-group has-feedback">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 50px;">父级城市:</label>
					<div class="col-xs-9" >
						<input type="hidden" id="parentId" name="parentId" value="${valmap.parentId}">
						<input type="text" class="form-control" id="pname" name="pname" readonly style="cursor:pointer;" placeholder="父级城市" value="${valmap.pname}">
						<!-- <span class="glyphicon glyphicon-search form-control-feedback"></span> -->
	               	</div>
	            </div>
	        <%--     <div class="form-group has-feedback">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 50px;">部门主管:</label>
					<div class="col-xs-9" onclick="getuser();">
						<input type="hidden" id="userids" name="userids" value="${valmap.dephead}">
						<input type="text" class="form-control" id="usernames" name="usernames" readonly style="cursor:pointer;" placeholder="部门主管" value="${valmap.depheadname}">
						<span class="glyphicon glyphicon-search form-control-feedback"></span>
	               	</div>
	            </div> --%>
	       	</div>
       	</form>
    </div>
</div>
<script>
//validate验证
var validate;
//页面初始化
$(window).load(function(){
/* 	jQuery.validator.addMethod(
		"pidequalsparentId",
		function(value,element){
			var sub=true;
			var pid=$("#pid").val();//菜单ID
			var parentId=$("#parentId").val();//父级菜单ID
			if(parentId==""){
				parentId="0";
			}
			if(pid==parentId){//菜单ID与父级菜单ID
				sub=false;
			}
	  		return sub;    
		},
		"请选择其他父级部门！"
	); */
	//验证
	validate=$("#form").validate({
		//debug:true,//调试模式取消submit的默认提交功能   
        //errorClass:"label.error",//默认为错误的样式类为：error   
        focusInvalid:true,//当为false时，验证无效时，没有焦点响应  
        onkeyup:false,   
        rules:{
    		name:{
            	required:true,
            	remote:{
               	    url:"<%=ctx%>/city/checkname",//后台处理程序
               	 	async:false,				//同步验证设置
               	    type:"post",               //数据发送方式
               	    dataType:"json",           //接受数据格式   
               	    data:{//要传递的数据
               	    	pid:function(){
               	    		return $("#pid").val();
               	    	},
        				name:function(){
               	        	return $("#name").val();
               	        },	
               	        parentId:function(){
               	        	var parentId=$("#parentId").val();
               	        
               	        	if(parentId==""){
               	        		parentId="0";
               	        	}
               	        	return parentId;
               	        }
               	    }
               	}
            },
            code:{
            	required:true,
            	remote:{
               	    url:"<%=ctx%>/city/checkname",//后台处理程序
               	 	async:false,				//同步验证设置
               	    type:"post",               //数据发送方式
               	    dataType:"json",           //接受数据格式   
               	    data:{//要传递的数据
               	    	pid:function(){
               	    		return $("#pid").val();
               	    	},
               	 		code:function(){
           	        	return $("#code").val();
           	       		 },
               	        parentId:function(){
               	        	var parentId=$("#parentId").val();
               	        
               	        	if(parentId==""){
               	        		parentId="0";
               	        	}
               	        	return parentId;
               	        }
               	    }
               	}
            },
            
          /*   pname:{
            	pidequalsparentId:true
            } */
        },
        messages:{
        	name:{
                required:"请填写城市名称！",
                remote:"城市名称重复，请重新填写！"
            },
        	code:{
                required:"请填写邮编！",
                remote:"城市邮编重复，请重新填写！"
            },
         /*    pname:{
            	pidequalsparentId:"请选择其他父级城市！"
            } */
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
		
		var pid=$("#pid").val();
		var name=$("#name").val();
		var code=$("#code").val();
		var parentId=$("#parentId").val();
		if(parentId==""){
			parentId="0";
		}
	
	/* 	var dephead=$("#userids").val(); */
		$.ajax({
			type:'post',
			url:'<%=ctx %>/city/save',
			data:{'pid':pid,'name':name,'code':code,'parentId':parentId},
			dataType:'text',
			async:false,
			success:function(data){
				ret="ok";
			}
		});
	}
	return ret;
}
//返回pid
function backpid(){
	var parentId=$("#parentId").val();
	if(parentId==""){
		parentId="0";
	}
	return parentId;
}
</script>