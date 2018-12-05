<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<% String ctx=request.getContextPath(); %>
<div class="row">
	<div class="col-md-12">
		<div class="box box-default">
			<!-- 
			<div class="box-header with-border">
				<h3 class="box-title">Different Width</h3>
            </div> -->
			<div class="box-body">
				<div class="row">
					<label class="col-xs-1 control-label"
						style="padding: 10px 0px 0px 20px;">石油名称:</label>
					<div class="col-xs-3" style="padding: 5px 0px 0px 0px;">
						<input type="text" class="form-control" id="name">
					</div>
					<div class="pull-right" style="padding: 10px 20px 0px 20px;">
						<button type="button" class="btn btn-primary btn-sm" onclick="query();"><i class="fa fa-search"></i>查询</button>
						<button type="button" class="btn btn-primary btn-sm" onclick="rest();"><i class="fa fa-eraser"></i>重置</button>
					</div>
				</div>
			</div>
		</div>
		<div class="box box-default">
			<div class="box-header with-border">
				<h3 class="box-title">&nbsp;</h3>
				<div class="box-tools pull-right">
					<c:if
						test="${fn:contains(button,'35511f43289d4a2280155ca3f574dd60')}">
						<button type="button" class="btn btn-success btn-xs"
							onclick="oiladd();">
							<i class="fa fa-plus"></i>添加石油
						</button>
					</c:if>
				</div>
			</div>
			<div class="box-body" id="tabbody" style="min-height: 400px;">
				<!--数据start -->
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
	$("#tabbody").css("height",getheight()-210);
	//初始化jqGrid--用户
	init(getheight()-300);
});
//初始化jqGrid--用户
function init(height){
	//AJAX请求列表数据
    jQuery("#table").jqGrid ({
    	//caption:'',
		datatype:'json',//将这里改为使用JSON数据 
		url:'<%=ctx%>/oil/getDataList',//这是Action的请求地址 
		postData:{'id':'','name':''},//此数组内容直接赋值到url上，参数类型：{name1:value1…}  
	    mtype:'POST',
	    height:height,
   	    autowidth:true,
        shrinkToFit:true,
        colNames:['id','石油名称','提成金额','操作'],//列名
        colModel:[//列对应的字段
            {name:'id',index:'id',width:30,hidden:true,sortable:false},//index传向服务器端用来排序的列名称
            {name:'name',index:'name',width:60,sortable:false},
        	{name:'disprice',index:'disprice',width:60,sortable:false},
         	{name:'Edit',index:'Edit',width:60}
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
        rownumbers:true,//ture则会在表格左边新增一列，显示行顺序号，从1开始递增
        hidegrid:false,
        gridComplete:function(){
            var ids=jQuery("#table").jqGrid('getDataIDs');
            for(var i=0;i<ids.length;i++){
                var id = ids[i];
                var rowData=$("#table").getRowData(id);
                var editBtn="";//此处会将点击行id传给_edit(id) js函数
                	editBtn+="<button type=\"button\" class=\"btn btn-warning btn-xs\" onclick=\"oiledit(\'"+id+"\');\"><i class=\"fa fa-paste\"></i>修改</button>";
	                editBtn+="&nbsp;&nbsp;&nbsp";
//                 	editBtn+="&nbsp;&nbsp;";
	                editBtn+="<button type=\"button\" class=\"btn btn-danger btn-xs\" onclick=\"oildel(\'"+id+"\');\"><i class=\"fa fa-times\"></i>删除</button>";
                jQuery("#table").jqGrid('setRowData',id,{Edit:editBtn});
            }
		} 
    });
	//自适应调整
    setjqGridWidth('table');
}
//保存-更新-删除|刷新页面数据--用户
function query(){
	var name=$("#name").val();
// 	alert(name);
	var postdata={'name':name};
	$("#table").jqGrid("setGridParam",{postData:postdata}).trigger("reloadGrid");//reloadGrid重新加载当前表格
}
//重置
function rest(){
	$("#name").val('');
}
//添加用户
function oiladd(){
	var dialog=new Dialog();
	dialog.Width = 500;
	dialog.Height = 200;
	dialog.Title="添加石油";
	dialog.URL="<%=ctx%>/oil/toadd";
	dialog.OKEvent=function(){
		var ret=dialog.innerFrame.contentWindow.save();
		if(ret=="ok"){
			dialog.close();
			//刷新
			query();
		}
	};
	dialog.CancelEvent=function(){
		dialog.close();
	};
	dialog.show();
	dialog.okButton.value="保存";
}
//修改用户
function oiledit(id){
	var dialog=new Dialog();
	dialog.Width = 500;
	dialog.Height = 200;
	dialog.Title="修改石油信息";
	dialog.URL="<%=ctx%>/oil/toedit?id="+id;
	dialog.OKEvent=function(){
		var ret=dialog.innerFrame.contentWindow.save();
		if(ret=="ok"){
			dialog.close();
			//刷新
			query();
		}
	};
	dialog.CancelEvent=function(){
		dialog.close();
	};
	dialog.show();
	dialog.okButton.value="保存";
}
//单个删除
function oildel(id){
	Dialog.confirm('确定要删除吗？',
		function(){
			$.ajax({
				type:'post',
				url:'<%=ctx %>/oil/delete',
				data:{'id':id},
				dataType:'text',
				async:false,
				success:function(data){//回调函数beforeSend、error、dataFilter、success、complete
					//刷新
					query();
				}
			});
		}
	);
}
</script>