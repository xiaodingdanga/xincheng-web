<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<% String ctx=request.getContextPath(); %>
<input type="hidden" id="id" name="id" value="${valmap.id}">
<input type="hidden" id="userids" name="userids" value="${valMap.userids}">
<div class="row">
	<div class="col-md-12">
			<div class="box-body">
			 	<div class="row">
					<label class="col-xs-1 control-label" style="padding:5px 0px 0px 10px;">票名:</label>
					<div class="col-xs-3">
	                   	<input type="text" class="form-control" id="ticketName" name="ticketName"  value="">
	               	</div>
	            
					<label class="col-xs-1 control-label" style="padding:5px 0px 0px 10px;">用户名:</label>
					<div class="col-xs-3">
	                   	<input type="text" class="form-control" id="userName" name="userName"  value="">
	               	</div>
                	<div class="col-xs-4" style="padding:10px 20px 0px 20px;">
                  		<div class="pull-right">
		          			<button type="button" class="btn btn-primary btn-sm" onclick="query();"><i class="fa fa-search"></i>查询</button>
		      				<button type="button" class="btn btn-primary btn-sm" onclick="rest();"><i class="fa fa-eraser"></i>重置</button>
		          		</div>
                	</div>
              	</div>
   			 </div>
   			 <div class="box box-default">
        	<div class="box-body" id="tabbody" style="min-height:200px;">
        		<!-- 数据start -->
                <div class="jqGrid_wrapper">
                 	<table id="table"></table>
                 	<div id="pager"></div>
                </div>
                <!-- 数据end -->
        	</div>
      	</div>
</div>
<script>
$(window).load(function(){
// 	$("#treebody").css("height",getheight()-210);
	$("#tabbody").css("height",getheight()-335);
	//初始化jqGrid--用户
	init(getheight()-215);
	//初始化zTree
// 	$.fn.zTree.init($("#tree"),setting,zNodes);
});
//初始化jqGrid--用户
function init(height){
	var userids = $("#userids").val();
	//AJAX请求列表数据
    jQuery("#table").jqGrid({
    	//caption:'',
		datatype:'json',//将这里改为使用JSON数据 
		url:'<%=ctx%>/user/ticketAuthorityLookDataLIst',//这是Action的请求地址 
		postData:{'customerid':'${user.customerid}','ticketName':'','userName':'','userids':userids},  
	    mtype:'POST',
	    height:height,
   	    autowidth:true,
        shrinkToFit:true,
        colNames:['ticketid','票名','userid','用户名','票类型','操作'],//列名
        colModel:[//列对应的字段
            {name:'ticketid',index:'ticketid',width:30,hidden:true,sortable:false},
            {name:'ticketName',index:'ticketName',width:80,sortable:false},
            {name:'userid',index:'userid',width:30,hidden:true,sortable:false},
            {name:'userName',index:'userName',width:60,sortable:true},  
            {name:'type',index:'type',width:60,sortable:false,formatter: "select", editoptions:{value:"0:门票;1:通票;2:套票"}},
        	{name:'Edit',index:'Edit',width:60}
        ],
     	multiselect:true,//是否支持多选   
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
        gridComplete:function(){
            var ids=jQuery("#table").jqGrid('getDataIDs');
            for(var i=0;i<ids.length;i++){
                var id = ids[i];
                var rowData=$("#table").getRowData(id);
                var editBtn="";//此处会将点击行id传给_edit(id) js函数
                var userid=rowData.userid;
                var ticketid=rowData.ticketid;
		                editBtn+="&nbsp;&nbsp;";
		                editBtn+="<button type=\"button\" class=\"btn btn-danger btn-xs\" onclick=\"del(\'"+userid+"\',\'"+ticketid+"\');\"><i class=\"fa fa-times\"></i>删除</button>";
		                editBtn+="&nbsp;&nbsp;";
		                jQuery("#table").jqGrid('setRowData',ids[i],{Edit:editBtn});
            }
		}
    });
	//自适应调整
    setjqGridWidth('table');
}
function query(){
	var ticketName=$("#ticketName").val();
	var userName=$("#userName").val();
	var postdata={'ticketName':ticketName,'userName':userName};
    $("#table").jqGrid("setGridParam",{postData:postdata}).trigger("reloadGrid");
}
function rest(){
	$("#ticketName").val('');
	$("#userName").val('');
}
function del(userid,ticketid){
	$.ajax({
		type:'post',
		url:'<%=ctx %>/user/deleteUserTicketRelation',
		data:{'userid':userid,'ticketid':ticketid},
		dataType:'text',
		async:false,
		success:function(data){
			query();
		}
	});
	
}
</script>