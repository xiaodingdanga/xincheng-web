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
                	<label class="col-xs-1 control-label" style="padding:10px 0px 0px 20px;">账号:</label>
                	<div class="col-xs-3" style="padding:5px 0px 0px 0px;">
                  		<input type="text" class="form-control" id="account">
                	</div>
                	<label class="col-xs-1 control-label" style="padding:10px 0px 0px 20px;">姓名:</label>
                	<div class="col-xs-3" style="padding:5px 0px 0px 0px;">
                  		<input type="text" class="form-control" id="name">
                	</div>
                	<label class="col-xs-1 control-label" style="padding:10px 0px 0px 20px;">电话:</label>
                	<div class="col-xs-3" style="padding:5px 20px 0px 0px;">
                  		<input type="text" class="form-control" id="tel">
                	</div>
              	</div>
              	<div class="row">
                	<label class="col-xs-1 control-label" style="padding:10px 0px 0px 20px;">EMAIL:</label>
                	<div class="col-xs-3" style="padding:5px 0px 0px 0px;">
                  		<input type="text" class="form-control" id="email">
                	</div>
                	<div class="col-xs-8" style="padding:10px 20px 0px 20px;">
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
<%--           			<c:if test="${fn:contains(button,'b6df75e522f6474d93ea373f2d7bb993')}"> --%>
<!--           			<button type="button" class="btn btn-info btn-xs" onclick="imports();">导入</button> -->
<%--           			</c:if> --%>
<%--           			<c:if test="${fn:contains(button,'7d51f3bd445f4f4aabf1eea4832aece7')}"> --%>
<!--           			<button type="button" class="btn btn-info btn-xs" onclick="exports();">导出</button> -->
<%--           			</c:if> --%>
          			<c:if test="${fn:contains(button,'35511f43289d4a2280155ca3f574dd60')}">
          				<button type="button" class="btn btn-success btn-xs" onclick="useradd();"><i class="fa fa-plus"></i>添加用户</button>
          			</c:if>
<%--           			<c:if test="${fn:contains(button,'47c0b839431d4b96805c67e7e4e56c8d')}"> --%>
<!--           				<button type="button" class="btn btn-danger btn-xs" onclick="batchuserdel();"><i class="fa fa-times"></i>批量删除用户</button> -->
<%--           			</c:if> --%>
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
		url:'<%=ctx%>/user/getDataList',//这是Action的请求地址 
		postData:{'account':'','name':'','tel':'','email':''},  
	    mtype:'POST',
	    height:height,
   	    autowidth:true,
        shrinkToFit:true,
        colNames:['id','账号','姓名','电话','EMAIL','所属油站','操作'],//列名
        colModel:[//列对应的字段
            {name:'id',index:'id',width:30,hidden:true,sortable:false},
            {name:'account',index:'account',width:60,sortable:false},  
        	{name:'name',index:'name',width:60,sortable:false},   
        	{name:'tel',index:'tel',width:60,sortable:false},   
        	{name:'email',index:'email',width:60,sortable:false},  
        	{name:'customername',index:'customername',width:60,sortable:false},  
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
        gridComplete:function(){
            var ids=jQuery("#table").jqGrid('getDataIDs');
            for(var i=0;i<ids.length;i++){
                var id = ids[i];
                var rowData=$("#table").getRowData(id);
                var money  = rowData.money;
                var type  = rowData.type;
                var editBtn="";//此处会将点击行id传给_edit(id) js函数
                <c:if test="${fn:contains(button,'ae87c55416a5467289ee68c2914981f5')}">
                	editBtn+="<button type=\"button\" class=\"btn btn-warning btn-xs\" onclick=\"useredit(\'"+id+"\');\"><i class=\"fa fa-paste\"></i>修改</button>";
                </c:if>
                <c:if test="${fn:contains(button,'47c0b839431d4b96805c67e7e4e56c8d')}">
	                editBtn+="&nbsp;&nbsp;";
	                editBtn+="<button type=\"button\" class=\"btn btn-danger btn-xs\" onclick=\"userdel(\'"+id+"\');\"><i class=\"fa fa-times\"></i>删除</button>";
               	</c:if>
               	<c:if test="${fn:contains(button,'339ce4fb960f44feaecc443f33c30817')}">
	                editBtn+="&nbsp;&nbsp;";
	                editBtn+="<button type=\"button\" class=\"btn btn-success btn-xs\" onclick=\"resp(\'"+id+"\');\"><i class=\"fa fa-undo\"></i>密码重置</button>";
                </c:if>
                if(type=="1"){
	               editBtn+="&nbsp;&nbsp;";
	               editBtn+="<button type=\"button\" class=\"btn btn-primary btn-xs\" onclick=\"txedit(\'"+id+"\',\'"+money+"\');\"><i class=\"fa fa-paste\"></i>提现</button>";
                }
                jQuery("#table").jqGrid('setRowData',ids[i],{Edit:editBtn});
            }
		} 
    });
	//自适应调整
    setjqGridWidth('table');
}
//保存-更新-删除|刷新页面数据--用户
function query(){
	var account=$("#account").val();
	var name=$("#name").val();
	var tel=$("#tel").val();
	var email=$("#email").val();
	var postdata={'account':account,'name':name,'tel':tel,'email':email};
    $("#table").jqGrid("setGridParam",{postData:postdata}).trigger("reloadGrid");
}
//重置
function rest(){
	$("#account").val('');
	$("#name").val('');
	$("#tel").val('');
	$("#email").val('');
}
//重置密码
function resp(id){
	Dialog.confirm('确定要重置吗？',
		function(){
			$.ajax({
				type:'post',
				url:'<%=ctx %>/user/respassword',
				data:{'id':id},
				dataType:'text',
				async:false,
				success:function(data){
					Dialog.alert("重置完毕！新密码为："+data);
				}
			});
		}
	);
}
//添加用户
function useradd(){
	var dialog=new Dialog();
	dialog.Width = 600;
	dialog.Height = 600;
	dialog.Title="添加用户";
	dialog.URL="<%=ctx%>/user/toadd";
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
function useredit(id){
	var dialog=new Dialog();
	dialog.Width = 600;
	dialog.Height = 600;
	dialog.Title="修改用户";
	dialog.URL="<%=ctx%>/user/toedit?id="+id;
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
//批量删除
function batchuserdel(){
	var idobj=$("#table").jqGrid('getGridParam','selarrrow');
	if(idobj.length>0){
		Dialog.confirm('确定要删除吗？',
			function(){
				var ids="";
				for(var i=0;i<idobj.length;i++){
					ids+=idobj[i]+",";
				}
				ids=ids.substring(0,ids.length-1);
				$.ajax({
					type:'post',
					url:'<%=ctx %>/user/batchdelete',
					data:{'ids':ids},
					dataType:'text',
					async:false,
					success:function(data){
						//刷新
						query();
					}
				});
			}
		);
	}else{
		Dialog.alert('请选择要删除的数据！');
	}
}
//单个删除
function userdel(id){
	Dialog.confirm('确定要删除吗？',
		function(){
			$.ajax({
				type:'post',
				url:'<%=ctx %>/user/delete',
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
//关键字查询
function changekey(){
	var key=$("#key").val();
	if(key==""){
		//初始化zTree
		$.fn.zTree.init($("#tree"),setting,zNodes);
	}else{
		$.fn.zTree.destroy();
		//起始节点初始化
		var settingquery={
			//设置异步获取方式
			async:{
		 		enable:true,
		 		dataType:"text",
		 		type:"post",
		 		url:"<%=ctx%>/tree/depdataquery?key="+key
		  	},
		  	callback:{
				onClick:zTreeOnClick
			},
		  	//设置显示方式
		    view:{showIcon:false}
		};
		//初始化zTree
		$.fn.zTree.init($("#tree"),settingquery,null);
	}
}
//导出
function exports(){
	var account=$("#account").val();
	var name=$("#name").val();
	var tel=$("#tel").val();
	var email=$("#email").val();
	window.location.href="<%=ctx%>/user/exports.action?account="+account+"&name="+name+"&tel="+tel+"&email="+email;
}
//导入
function imports(){
	var dialog=new Dialog();
	dialog.Width = 800;
	dialog.Height = 500;
	dialog.Title="用户信息导入";
	dialog.URL="<%=ctx%>/user/imports";
	dialog.CancelEvent=function(){
		//刷新
		query();
		dialog.close();
	};
	dialog.show();
}
//提现
function txedit(id,money){
	var dialog=new Dialog();
	dialog.Width = 500;
	dialog.Height = 150;
	dialog.Title="佣金提现";
	dialog.URL="<%=ctx%>/user/txtoedit?id="+id+"&money="+money;
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