<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
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
                	<label class="col-xs-1 control-label" style="padding:10px 0px 0px 20px;">会员卡号:</label>
                	<div class="col-xs-3" style="padding:5px 0px 0px 0px;">
                  		<input type="text" class="form-control" id="serialnum">
                	</div>
                	<label class="col-xs-1 control-label" style="padding:10px 0px 0px 20px;">姓名:</label>
                	<div class="col-xs-3" style="padding:5px 0px 0px 0px;">
                  		<input type="text" class="form-control" id="name">
                	</div>
                	<label class="col-xs-1 control-label" style="padding:10px 0px 0px 20px;">电话号:</label>
                	<div class="col-xs-3" style="padding:5px 10px 0px 0px;">
                  		<input type="text" class="form-control" id="tel">
                	</div>
                	</div>
                	<div class="row">
                	<div class="col-xs-12" style="padding:10px 20px 0px 20px;">
                  		<div class="pull-right">
		          			<button type="button" class="btn btn-primary btn-sm" onclick="query();"><i class="fa fa-search"></i>查询</button>
		      				<button type="button" class="btn btn-primary btn-sm" onclick="rest();"><i class="fa fa-eraser"></i>重置</button>
		          		</div>
                	</div>
              	</div>
          	</div>
  		</div>
   		<div class="box box-default">
   			<div class="box-header with-border">
          		<h3 class="box-title">&nbsp;</h3>
          		<div class="box-tools pull-right">
          			<button type="button" class="btn btn-success btn-xs" onclick="add();"><i class="fa fa-plus"></i>添加</button>
          		</div>
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
	$("#tabbody").css("height",getheight()-210);
	//初始化jqGrid--用户
	init(getheight()-290);
});
//初始化jqGrid--用户
function init(height){
	//AJAX请求列表数据
    jQuery("#table").jqGrid({
    	//caption:'',
		datatype:'json',//将这里改为使用JSON数据 
		url:'<%=ctx%>/member/getDataList',//这是Action的请求地址 
		postData:{'serialnum':'','name':'','tel':''},  
	    mtype:'POST',
	    height:height,
   	    autowidth:true,
        shrinkToFit:true,
        colNames:['id','会员卡号','芯片号','是否绑定','姓名','电话','类型','是否启用','开卡日期','分销员工','操作'],//列名
        colModel:[//列对应的字段
            {name:'id',index:'id',width:30,hidden:true,sortable:false},
            {name:'serialnum',index:'serialnum',width:100,sortable:false},  
        	{name:'chipnum',index:'chipnum',width:120,sortable:false},   
        	{name:'state',index:'state',width:80,formatter: "select", editoptions:{value:"0:已绑定;1:未绑定"}}, 
        	{name:'name',index:'name',width:80,sortable:false},  
        	{name:'tel',index:'tel',width:120,sortable:false},  
        	{name:'cardtype',index:'cardtype',width:80,formatter: "select", editoptions:{value:"0:储值卡;1:信用卡"}}, 
        	{name:'isuse',index:'isuse',width:80,formatter: "select", editoptions:{value:"0:启用;1:停用"}}, 
        	{name:'createdate',index:'createdate',width:150,sortable:false},  
        	{name:'disname',index:'disname',width:120,sortable:false},  
         	{name:'Edit',index:'Edit',width:150}
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
        autoScroll: true, 
        gridComplete:function(){
            var ids=jQuery("#table").jqGrid('getDataIDs');
            for(var i=0;i<ids.length;i++){
                var id = ids[i];
                var rowData=$("#table").getRowData(id);
                var state = rowData.state;
                var isuse = rowData.isuse;
                var editBtn="";//此处会将点击行id传给_edit(id) js函数
                	editBtn+="<button type=\"button\" class=\"btn btn-warning btn-xs\" onclick=\"toedit(\'"+id+"\');\"><i class=\"fa fa-paste\"></i>修改</button>";
	                editBtn+="&nbsp;&nbsp;";
	                if(state=='0'&&isuse=='1'){
	                	 editBtn+="<button type=\"button\" class=\"btn btn-success btn-xs\" onclick=\"isuse(\'"+id+"\',\'0\');\"><i class=\"fa fa-plus\"></i>启用</button>";
	 	                editBtn+="&nbsp;&nbsp;";
	                }
	                if(state=='0'&&isuse=='0'){
	                	editBtn+="<button type=\"button\" class=\"btn btn-danger btn-xs\" onclick=\"isuse(\'"+id+"\',\'1\');\"><i class=\"fa fa-times\"></i>停用</button>";
		                editBtn+="&nbsp;&nbsp;";
	                }
                jQuery("#table").jqGrid('setRowData',ids[i],{Edit:editBtn});
            }
		} 
    });
	//自适应调整
    setjqGridWidth('table');
    //横向滚动条	
    jQuery("#table").closest(".ui-jqgrid-bdiv").css({ 'overflow-x' : 'scroll' });
}
//保存-更新-删除|刷新页面数据--用户
function query(){
	var serialnum=$("#serialnum").val();
	var name=$("#name").val();
	var tel=$("#tel").val();
	var postdata={'serialnum':serialnum,'name':name,'tel':tel};
    $("#table").jqGrid("setGridParam",{postData:postdata}).trigger("reloadGrid");
}
//重置
function rest(){
	$("#serialnum").val('');
	$("#name").val('');
	$("#tel").val('');
}

//录入卡信息
function add(){
	var dialog=new Dialog();
	dialog.Width = 600;
	dialog.Height = 400;
	dialog.Title="添加会员卡";
	dialog.URL="<%=ctx%>/member/toadd";
	dialog.OKEvent=function(){
		var ret=dialog.innerFrame.contentWindow.next();
		}
	dialog.CancelEvent=function(){
		dialog.close();
		//刷新
		query();
	};
	dialog.show();
	dialog.okButton.value="下一个";
	dialog.addButton("save","保存",function(){
		var ret=dialog.innerFrame.contentWindow.save();
		if(ret=='ok'){
			dialog.close();
			//刷新
			query();
		}
	});
}
//修改用户
function toedit(id){
	var dialog=new Dialog();
	dialog.Width = 600;
	dialog.Height = 400;
	dialog.Title="修改会员卡信息";
	dialog.URL="<%=ctx%>/member/toedit?id="+id;
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
//停用
function isuse(id,isuse){
	var str=""
	if(isuse=='0'){
		str="确定要启用吗？"
	}else{
		str="确定要停用吗？"
	}
	Dialog.confirm(str,
			function(){
				$.ajax({
					type:'post',
					url:'<%=ctx %>/member/isuse',
					data:{'id':id,'isuse':isuse},
					dataType:'text',
					async:false,
					success:function(data){
						//刷新
						query();
					}
				});
			})
		}
</script>