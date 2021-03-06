<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<%String ctx = request.getContextPath();%>
<input type="hidden" id="id" name="id" >
<input type="hidden" id="chipnum1" name="chipnum1" >
<input type="hidden" id="serialnum" name="serialnum" >
<input type="hidden" id="isIP" name="isIP"  value="${valmap.isIP}">
<div class="row">
	<div class="col-md-12">
		<form   id="form"  action="<%=ctx %>/consumption/read"  method="post">
			<div class="box box-default">
				<div class="box-body"  id="tabbody" >
					<div class="form-group">
						<div class="row" style="padding:15px 0px 0px 0px">
							<label class="col-xs-3 control-label" style="padding: 10px 0px 0px 150px;font-size:30px">芯片号:</label>
							<div class="col-xs-5" style="padding: 5px 0px 0px 10px;">
								<input type="text"  size="15" style="width:100%;height:50px;font-size:25px" class="form-control"  id="chipnum" name="chipnum" >
							</div>
							<div class="col-xs-4"  style="padding: 5px 0px 0px 30px;">
								<input type="button" class="btn btn-primary btn-sm"  size="15" style="width:50%;height:50px;" value="读卡" onclick="read()" />
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="box box-default">
				<div class="box-body" style="min-height:350px;">
					<div class="form-group">
						<div class="row" style="padding-top:15px">	
							<label class="col-xs-3 control-label" style="padding: 10px 0px 0px 150px;font-size:30px">油类型:</label>
							<div class="radio">
								<c:forEach items="${list}"  var="map" >
	  								<label style="padding: 0px 0px 0px 25px;font-size:30px">
										<input type="radio" name="oilid" id="oilid" value="${map.oilid}"  style="margin:15px 0px 0px -15px"><span>${map.oilname}</span>
									</label>
								</c:forEach>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row" style="padding-top:15px">	
							<label class="col-xs-3 control-label" style="padding: 10px 0px 0px 120px;font-size:30px">卡内余额:</label>
							<label class="col-xs-5 control-label" style="padding: 10px 0px 0px 30px;font-size:30px" id=money></label>
						</div>
					</div>
					<div class="form-group">
						<div class="row" style="padding:15px 0px 0px 0px">
							<label class="col-xs-3 control-label" style="padding: 10px 0px 0px 60px;font-size:30px">本次消费金额:</label>
							<div class="col-xs-5" style="padding: 5px 0px 0px 10px;">
								<input type="text"  size="15" style="width:100%;height:50px;font-size:25px" class="form-control"  id="price" name="price" >
							</div>
							<div class="col-xs-4" style="padding: 5px 0px 0px 40px;">
								<input type="button" class="btn btn-primary btn-sm"  size="15" style="width:50%;height:50px;" value="购买"  onclick="order()"/>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row" style="padding:30px 0px 0px 0px" >
							<div align="center" hidden="hiddden" id="returndiv">
								<span style="color: red ;font-size:30px; text-align: center;" id="returninfo"></span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
<script>
//页面初始化
$(window).load(function() {
	$(".radio label").eq(0).children().prop("checked",true);
	$("#chipnum").focus();
	$('#chipnum').bind('keypress',function(event){
        if(event.keyCode == "13") {
        	read();
        }
    });
	$('#price').bind('keypress',function(event){
        if(event.keyCode == "13") {
        	order();
        }
    });
});

//读卡
function read() {
	var chipnum=$("#chipnum").val();
	if(chipnum!==""){
		$.ajax({
			type:'post',
			url:'<%=ctx %>/consumption/read',
			data:{'chipnum':chipnum},
			dataType:'json',
			async:false,
			success:function(data){
				var code = data.code;
				var flag =  data.flag;
				if(code!=""){
					$("#returninfo").text(code);
					$("#returndiv").show();
					$("#money").text("");
					$("#id").val("");
					$("#chipnum1").val("");
					$("#serialnum").val("");
					$("#chipnum").focus();
				}else{
					var money = data.money;
					var id = data.id;
					var chipnum = data.chipnum;
					var serialnum = data.serialnum;
					$("#money").text(money);
					$("#id").val(id);
					$("#chipnum1").val(chipnum);
					$("#serialnum").val(serialnum);
					$("#price").focus();
					$("#returndiv").hide();
				}
				$("#chipnum").val("");
				$("#price").val("");
				$(".radio label").eq(0).children().prop("checked",true);
			}
		});	
	}
}
//校验两位小数
function regu(price){
	var regu =/^\d+(\.\d{1,2})?$/;
	var re = new RegExp(regu);
	return re.test(price);
}
//保存
function order() {
		var id=$("#id").val();
		var money=$("#money").text();
		var price  = $("#price").val();
		var chipnum  = $("#chipnum1").val();
		var serialnum  = $("#serialnum").val();
		var oilid  = $("input[type='radio']:checked").val();
		var oilname = $("input[type='radio']:checked").next().text();
		var isIP = $("#isIP").val();
		var reg = regu(price);
		if(money==""){
			$("#returninfo").text("请先读取磁卡！");
			$("#returndiv").show();
			$("#chipnum").focus();
			$(".radio label").eq(0).children().prop("checked",true);
		}else if(price==""){
			$("#returninfo").text("请输入价格！");
			$("#returndiv").show();
			$("#price").focus();
		}else if(price=="0"){
			$("#returninfo").text("消费金额不可为0！");
			$("#returndiv").show();
			$("#price").focus();
		}else if(!reg){
			$("#returninfo").text("请填写数字并保留两位小数！");
			$("#returndiv").show();
			$("#price").focus();
		}else if(price>1000){
			$("#returninfo").text("单次消费金额不可超过1000元！");
			$("#returndiv").show();
			$("#price").focus();
		}else{
			var str = "确认消费：本次购买"+oilname+"油，共消费："+price+"元！"
			Dialog.confirm(str,function(){
				$.ajax({
					type:'post',
					url:'<%=ctx %>/consumption/saveorder',
					data:{'id':id,'chipnum':chipnum,'serialnum':serialnum,'price':price,'oilid':oilid,'isIP':isIP},
					dataType:'json',
					async:false,
					success:function(data){
// 						var state = data.state;
// 						if(state=='6'){
<%-- 							window.location.href="<%=ctx%>/login/logout"; --%>
// 						}else{
							$("#money").text("");
							$("#price").val("");
							$("#returninfo").text(data.code);
							$("#returndiv").show();
							$("#chipnum").focus();
							$("#id").val("");
							$("#chipnum1").val("");
							$("#serialnum").val("");
							$(".radio label").eq(0).children().prop("checked",true);
// 						}
					}
				});	
			})
		}
}
</script>