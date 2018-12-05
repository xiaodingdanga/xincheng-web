<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<%String ctx = request.getContextPath();%>
<div class="row">
	<div class="col-md-12">
		<div class="box box-default">
			<div class="box-body">
				<div class="row">
					<label class="col-xs-1 control-label" style="padding: 10px 0px 0px 40px;">姓名:</label>
					<div class="col-xs-3" style="padding: 5px 0px 0px 0px;">
						<input type="text" class="form-control" id="name">
					</div>
					<label class="col-xs-1" style="padding: 10px 0px 0px 20px;">开始时间:</label>
					<div class="col-xs-3" style="padding: 5px 0px 0px 0px;">
						<input type="text" class="form-control" style="height: 34px;" id="startdate" name="startdate" readonly onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});" value="">
					</div>
					<label class="col-xs-1" style="padding: 10px 0px 0px 20px;">结束时间:</label>
					<div class="col-xs-3" style="padding: 5px 10px 0px 0px;">
						<input type="text" class="form-control" style="height: 34px;" id="enddate" name="enddate" readonly onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startdate\')}',maxDate:'%y-%M-{%d}'});" value="">
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12" style="padding: 10px 20px 0px 20px;">
						<div class="pull-right">
							<button type="button" class="btn btn-primary btn-sm" onclick="query();">
								<i class="fa fa-search"></i>查询
							</button>
							<button type="button" class="btn btn-primary btn-sm" onclick="rest();">
								<i class="fa fa-eraser"></i>重置
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="box box-default">
			<div class="box-body" id="tabbody" style="min-height: 400px;">
				<!-- 数据start -->
				<div class="jqGrid_wrapper">
					<table id="table"></table>
					<div id="pager"></div>
				</div>
				<!-- 数据end -->
			</div>
		</div>
	</div>
</div>
<script>
$(window).load(function(){
	$("#tabbody").css("height",getheight()-230);
});
//初始化jqGrid--用户
function init(height){
	var startdate =$("#startdate").val();
	var enddate =$("#enddate").val();
	var name =$("#name").val();
	//AJAX请求列表数据
    jQuery("#table").jqGrid({
    	//caption:'',
		datatype:'json',//将这里改为使用JSON数据 
		url:'<%=ctx%>/report/yjmxgetDataList',//这是Action的请求地址 
		postData:{'startdate':startdate,'enddate':enddate,'name':name},  
	    mtype:'POST',
	    height:height,
   	    autowidth:true,
        shrinkToFit:true,
        colNames:['id','姓名','分销总量(升)','提成金额(元/升)','所获佣金','分销日期'],//列名
        colModel:[//列对应的字段
            {name:'id',index:'id',width:30,hidden:true,sortable:false},
        	{name:'username',index:'username',width:120,sortable:false},  
            {name:'volume',index:'volume',width:120,sortable:false},  
        	{name:'commission',index:'commission',width:100,sortable:false},  
        	{name:'money',index:'money',width:100,sortable:false},  
        	{name:'createdate',index:'createdate',width:150,sortable:false},  
        ],
//      	multiselect:true,//是否支持多选   
     	viewrecords:true,//是否显示行数 
   		rowNum:10,//每页显示记录数-1：显示所有数据
        rowList:[10,20,30,50,100],//可调整每页显示的记录数  
	    pager:'pager', //分页工具栏 
	    pagerpos:'center',//分页栏显示位置，默认center
   	    pgtext:"当前第{0}页/共{1}页",
        recordtext:"当前为第{0}到第{1}行数据，共{2}数据",
        emptyrecords: "无显示数据",
        loadtext: "查询中......",
        pgbuttons:true,//是否显示翻页按钮
        pginput:true,//是否显示分页跳转输入框
        forceFit:true,//调整列宽时不会改变表格宽度
        reccount:0,
        rownumbers:true,
        hidegrid:false,
    });
	//自适应调整
    setjqGridWidth('table');
}
//保存-更新-删除|刷新页面数据--用户
function query(){
	//初始化jqGrid--用户
	init(getheight()-300);
	var startdate =$("#startdate").val();
	var enddate =$("#enddate").val();
	var name =$("#name").val();
	var postdata={'startdate':startdate,'enddate':enddate,'name':name};
    $("#table").jqGrid("setGridParam",{postData:postdata}).trigger("reloadGrid");
}
//重置
function rest(){
	$("#startdate").val("");
	$("#enddate").val("");
	$("#name").val("");
}
</script>