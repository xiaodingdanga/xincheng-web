<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<% String ctx=request.getContextPath(); %>
<input type="hidden" id="id" name="id" value="${valmap.id}">
<div class="row">
	<div class="col-md-12">
		<form class="form-horizontal" id="form">
			<div class="box-body">
	            <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 50px;"><font color="red">*</font>油表价格:</label>
					<div class="col-xs-9">
	                   	<input type="text" class="form-control" id="outprice" name="outprice" placeholder="" value="${pricemap.outprice }">
	               	</div>
	            </div>
	             <div class="form-group">
					<label class="col-xs-3 control-label" style="padding:5px 0px 0px 50px;"><font color="red">*</font>销售价格:</label>
					<div class="col-xs-9">
	                   	<input type="text" class="form-control" id="inprice" name="inprice" placeholder="" value="${pricemap.inprice }">
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
	//自定义validate验证输入的数字小数点位数不能大于两位
    jQuery.validator.addMethod("minNumber",function(value, element){
        var returnVal = true;
        inputZ=value;
        var ArrMen= inputZ.split(".");    //截取字符串
        if(ArrMen.length==2){
            if(ArrMen[1].length>2){    //判断小数点后面的字符串长度
                returnVal = false;
                return false;
            }
        }
        return returnVal;
    },"小数点后最多为两位");         //验证错误信息
	//validate验证
	validate=$("#form").validate({
		//debug:true,//调试模式取消submit的默认提交功能   
        //errorClass:"label.error",//默认为错误的样式类为：error   
        focusInvalid:true,//当为false时，验证无效时，没有焦点响应  
        onkeyup:false,   
        rules:{
        	outprice:{
            	required:true,
            	number: true,     //输入必须是数字
                min: 0.01,          //输入的数字最小值为0.01，不能为0或者负数
                minNumber: $("#outprice").val()    //调用自定义验证
            },
            inprice:{
            	required:true,
            	number: true,     //输入必须是数字
                min: 0.01,          //输入的数字最小值为0.01，不能为0或者负数
                minNumber: $("#inprice").val()    //调用自定义验证
            }
        },
        messages:{
        	outprice:{
            	required:"请填写油表显示价格！",
            		number: "请填写数字并保留两位小数！",//请正确输入金额
                    min: "请填写数字并保留两位小数！",//输入最小金额为0.01
                    length: "请填写数字并保留两位小数！"//输入数字最多小数点后两位
            },
            inprice:{
            	required:"请填写实际销售价格！",
            	number: "请填写数字并保留两位小数！",//请正确输入金额
                min: "请填写数字并保留两位小数！",//输入最小金额为0.01
                length: "请填写数字并保留两位小数！"//输入数字最多小数点后两位
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
		var outprice=$("#outprice").val();
		var inprice=$("#inprice").val();
		$.ajax({
			type:'post',
			url:'<%=ctx %>/customer/pricesave',
			data:{'id':id,'outprice':outprice,'inprice':inprice},
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