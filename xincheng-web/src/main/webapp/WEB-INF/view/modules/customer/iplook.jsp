<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<% String ctx=request.getContextPath(); %>
<input type="hidden" id="id" name="id" value="${valmap.id}">
<div class="row">
    <div class="col-md-12">
   		<div class="box box-default">
   			<label style="padding:10px 0px 0px 20px;font-size: 20px;">${valmap.name}</label>
       		<div class="box-tools pull-right">
       			<button type="button" class="btn btn-success btn-xs" onclick="iptoadd();" style="margin:15px 15px 0px 0px"><i class="fa fa-plus"></i>添加</button>
       		</div>
        	<div class="box-body" id="tabbody" style="min-height:400px;">
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
	$("#tabbody").css("height",getheight()-65);
	//初始化jqGrid--用户
	init(getheight()-160);
});
//初始化jqGrid--用户
function init(height){
	//AJAX请求列表数据
    jQuery("#table").jqGrid({
    	//caption:'',
		datatype:'json',//将这里改为使用JSON数据 
		url:'<%=ctx%>/customer/getIPDataList',//这是Action的请求地址 
		postData:{'customerid':'${valmap.id}'},  
	    mtype:'POST',
	    height:height,
   	    autowidth:true,
        shrinkToFit:true,
        colNames:['id','加油站','ip','操作'],//列名
        colModel:[//列对应的字段
            {name:'id',index:'id',width:30,hidden:true,sortable:false},
            {name:'customername',index:'customername',width:120,sortable:false},  
        	{name:'ip',index:'ip',width:120,sortable:false}, 
         	{name:'Edit',index:'Edit',width:120}
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
        gridComplete:function(){
            var ids=jQuery("#table").jqGrid('getDataIDs');
            for(var i=0;i<ids.length;i++){
                var id = ids[i];
                var rowData=$("#table").getRowData(id);
                var money = rowData.money;
                var editBtn="";//此处会将点击行id传给_edit(id) js函数
                	editBtn+="<button type=\"button\" class=\"btn btn-danger btn-xs\" onclick=\"ipdelete(\'"+id+"\');\"><i class=\"fa fa-paste\"></i>删除ip</button>";
	                editBtn+="&nbsp;&nbsp;";
                jQuery("#table").jqGrid('setRowData',ids[i],{Edit:editBtn});
            }
		} 
    });
	//自适应调整
    setjqGridWidth('table');
}
//保存-更新-删除|刷新页面数据--用户
function query(){
	var postdata={'customerid':'${valmap.id}'};
    $("#table").jqGrid("setGridParam",{postData:postdata}).trigger("reloadGrid");
}
//删除ip
function ipdelete(id){
	Dialog.confirm('确定要删除吗？',
			function(){
				$.ajax({
					type:'post',
					url:'<%=ctx %>/customer/ipdelete',
					data:{'id':id},
					dataType:'text',
					async:false,
					success:function(data){
						//刷新
						query();
					}
				});
			}
		);
}
//添加ip
function iptoadd(){
	var customerid='${valmap.id}';
	var dialog=new Dialog();
	dialog.Width = 500;
	dialog.Height = 100;
	dialog.Title="添加ip";
	dialog.URL="<%=ctx%>/customer/iptoadd?customerid="+customerid;
	dialog.OKEvent=function(){
		var ret=dialog.innerFrame.contentWindow.save();
			if(ret=="ok"){
				dialog.close();
				//刷新
				query();
			}
		}
	dialog.CancelEvent=function(){
		dialog.close();
		//刷新
		query();
	};
	dialog.show();
	dialog.okButton.value="确定";
}

</script>