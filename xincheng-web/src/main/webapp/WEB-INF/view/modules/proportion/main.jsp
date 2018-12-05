<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<%String ctx = request.getContextPath();%>
<input type="hidden" id="id" name="id" value="${valmap.id}">
<div class="row">
	<div class="col-md-12">
		<div class="box box-default">
			<div class="box-body">
				<form class="form-horizontal" id="form">
					<div class="form-group">
						<div class="row">
							<label class="col-xs-1 control-label" style="padding: 10px 0px 0px 10px;">油表价格:</label>
							<div class="col-xs-3" style="padding: 5px 0px 0px 10px;">
								<input type="text"  class="form-control"  id="outprice" name="outprice"  value="${valmap.outprice}">
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<label class="col-xs-1 control-label" style="padding: 10px 0px 0px 10px;">售&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价:</label>
							<div class="col-xs-3" style="padding: 5px 0px 0px 10px;">
								<input type="text"  class="form-control" id="inprice"  name="inprice"  value="${valmap.inprice}">
							</div>
						</div>
					</div>
				</form>
				<div class="row">
					<div class="col-xs-4"  style="padding: 10px 0px 0px 35px;">
							<button type="button" class="btn btn-primary btn-sm" onclick="save();">保存</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
//页面初始化
$(window).load(function() {
	
});
//保存
function save() {
	var inprice = $("#inprice").val();
	var outprice = $("#outprice").val();
	var regu =/^\d+(\.\d{1,2})?$/;
	var re = new RegExp(regu);
	if(!re.test(outprice)){
		Dialog.alert(" 油表价填写错误，请填写数字并保留两位小数！");
	}else if(!re.test(inprice)){
		Dialog.alert("售价填写错误，请填写数字并保留两位小数！");
	}else{
		var id = $("#id").val();
		$.ajax({
			type:'post',
			url:'<%=ctx %>/proportion/save',
			data:{'id':id,'inprice':inprice,'outprice':outprice},
			dataType:'text',
			async:false,
			success:function(data){
				Dialog.alert(" 保存成功！")
			}
		});
	}
}
</script>