<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<% String ctx=request.getContextPath(); %>
<input type="hidden" id="id" name="id" value="${valmap.id}">
<script type="text/javascript">
//人脸录入
 $(function() {

            var pos = 0, ctx = null, saveCB, image = [];
//创建画布指定宽度和高度
            var canvas = document.createElement("canvas");
            canvas.setAttribute('width', 320);
            canvas.setAttribute('height', 240);
//如果画布成功创建
            if (canvas.toDataURL) {
//设置画布为2d，未来可能支持3d
                ctx = canvas.getContext("2d");
//截图320*240，即整个画布作为有效区(cutx?)
                image = ctx.getImageData(0, 0, 320, 240);

                saveCB = function(data) {
//把data切割为数组
                    var col = data.split(";");
                    var img = image;
//绘制图像(这里不是很理解算法)
//参数data  只是每行的数据  ，例如320*240 大小的照片，一张完整的照片下来需要240个data，每个data有320个rgb
                    for(var i = 0; i < 320; i++) {
                        //转换为十进制
                        var tmp = parseInt(col[i]);
                        img.data[pos + 0] = (tmp >> 16) & 0xff;
                        img.data[pos + 1] = (tmp >> 8) & 0xff;
                        img.data[pos + 2] = tmp & 0xff;
                        img.data[pos + 3] = 0xff;
                        pos+= 4;
                    }
//当绘制320*240像素的图片时发给后端php
                    if (pos >= 4 * 320 * 240) {
//把图像放到画布上,输出为png格式
                        ctx.putImageData(img, 0, 0);
                        $.post("upload.php", {type: "data", image: canvas.toDataURL("image/png")});
                        
                        $("#camImg").val(canvas.toDataURL("image/png")); 
                        
                        
                        $("#faceImage").val(canvas.toDataURL("image/png")); //face  
                        $("#webcamImg").attr("src",canvas.toDataURL("image/png"));
                        pos = 0;
                    }
                };

            } else {
                saveCB = function(data) {
//把数据一点点的放入image[]
                    image.push(data);
                    pos+= 4 * 320;
                    if (pos >= 4 * 320 * 240) {
                        $.post("upload.php", {type: "pixel", image: image.join('|')});
                      
                        pos = 0;
                    }
                };
            }
            $("#webcam").webcam({
                width: 320,
                height: 240,
                mode: "callback",
                swffile: "<%=ctx %>/static/js/picture/jscam_canvas_only.swf",

                onSave: saveCB,

                onCapture: function () {
                    webcam.save();
                },

                debug: function (type, string) {
                    console.log(type + ": " + string);
                }
            });
//            /**
//             * 获取canvas画布的内容 getImageData
//             * 内容放回到canvas画布 putImageData
//             * 获取ImgData的每一个像素 ImgData.data
//             * getImageData(起始点的横坐标, 起始点的纵坐标, 获取的宽度, 获取的高度)
//             * putImageData(绘制点的横坐标, 绘制点点纵坐标, imgData的起始点横坐标, imgData的起始点纵坐标, 宽度, 高度)
//             */
        });
        
        function addPicture(){
        	webcam.capture();
        } 
</script>        
<div class="row">
	<div class="col-md-12">
		<form class="form-horizontal" id="form">
			<div class="box-body">
	            <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 50px;"><font color="red">*</font>账号:</label>
					<div class="col-xs-9">
	                   	<input type="text" class="form-control" id="account" name="account" placeholder="账号" value="${valmap.account}">
	               	</div>
	            </div>
	            <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 50px;"><font color="red">*</font>姓名:</label>
					<div class="col-xs-9">
	                   	<input type="text" class="form-control" id="name" name="name" placeholder="姓名" value="${valmap.name}">
	               	</div>
	            </div>
	            <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 50px;">电话:</label>
					<div class="col-xs-9">
	                   	<input type="text" class="form-control" id="tel" name="tel" placeholder="电话" value="${valmap.tel}">
	               	</div>
	            </div>
	            <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 50px;">EMAIL:</label>
					<div class="col-xs-9">
	                   	<input type="text" class="form-control" id="email" name="email" placeholder="EMAIL" value="${valmap.email}">
	               	</div>
	            </div>
	             <div class="form-group has-feedback">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 50px;">所属油站:</label>
					<div class="col-xs-9" onclick="getcustomertree();">
						<input type="hidden" id="customerid" name="customerid" value="${valmap.customerid}">
						<input type="text" class="form-control" id="customername" name="customername" readonly style="cursor:pointer;" placeholder="所属油站" value="${valmap.customername}">
						<span class="glyphicon glyphicon-search form-control-feedback"></span>
	               	</div>
	            </div>
	            
	             <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 50px;">录入人脸:</label>
					<%-- <div class="col-xs-9">
	                       <button type="button" class="btn btn-warning btn-xs" onclick="addPicture();"><i class="fa fa-paste"></i>拍照</button>
						    <img id="webcamImg1" src="${valmap.facedate }" /><p>
				            <input type="hidden" id="faceImage" name="faceImage" >
	               	</div> --%>
	            </div>
	             <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 50px;"><button type="button" class="btn btn-warning btn-xs" onclick="addPicture();"><i class="fa fa-paste"></i>拍照</button></label>
					<div class="col-xs-9">
	                       
						    <img id="webcamImg1" src="${valmap.facedate }" /><p>
				            <input type="hidden" id="faceImage" name="faceImage" >
	               	</div>
	            </div>
	           
	            <div class="form-group">
					<div id="webcam" style="width: 48%;float:left;padding: 0px 0px 0px 8px;"></div>
					<div style="width: 50%;float:right;"><img id="webcamImg" src="" /></div>
				</div>
				 <input type="hidden" id="camImg" name="camImg">
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
		//debug:true,//调试模式取消submit的默认提交功能   
        //errorClass:"label.error",//默认为错误的样式类为：error   
        focusInvalid:true,//当为false时，验证无效时，没有焦点响应  
        onkeyup:false,   
        rules:{
        	account:{
            	required:true,
            	remote:{
               	    url:"<%=ctx%>/user/checkname",//后台处理程序
               	 	async:false,				//同步验证设置
               	    type:"post",               //数据发送方式
               	    dataType:"json",           //接受数据格式   
               	    data:{//要传递的数据
               	    	id:function(){
               	    		return $("#id").val();
               	    	},
               	    	account:function(){
               	        	return $("#account").val();
               	        }
               	    }
               	}
            },
            name:{
            	required:true
            },
            commission:{
            	price:true
            }
        },
        messages:{
        	account:{
                required:"请填写账号！",
                remote:"账号重复，请重新填写！"
            },
            name:{
            	required:"请填写姓名！"
            },
           	commission:{
           		price:"请填写数字并保留两位小数！"
            }
        },
        errorPlacement:function(error,element){//错误信息显示位置
        	element.after(error);
       	}
    });
});

//获取油站树
function getcustomertree(){
	var dialog=new Dialog();
	dialog.Width = 400;
	dialog.Height = 300;
	dialog.Title="油站列表";
	dialog.URL="<%=ctx%>/tree/customertree";
	dialog.CancelEvent=function(customerid,customername){
		$("#customerid").val(customerid);
		$("#customername").val(customername);
		dialog.close();
	};
	dialog.show();
}

//部门保存
function save(){
	var ret="";
	if(validate){//验证重置
		validate.resetForm();
	}
	if(validate.form()){
		var id=$("#id").val();
		var account=$("#account").val();
		var name=$("#name").val();
		var tel=$("#tel").val();
		var email=$("#email").val();
		var customerid=$("#customerid").val();
		var facedate=$("#faceImage").val();
		$.ajax({
			type:'post',
			url:'<%=ctx %>/user/save',
			data:{'id':id,'account':account,'name':name,'tel':tel,'email':email,'customerid':customerid,'facedate':facedate},
			dataType:'text',
			async:false,
			success:function(data){
				if("成功"==data){
					ret="ok";
				}else{
					Dialog.alert(data);
				}
			}
		});
	}
	return ret;
}
</script>