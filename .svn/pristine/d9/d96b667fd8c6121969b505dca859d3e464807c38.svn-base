<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<% String ctx=request.getContextPath(); %>
<input type="hidden" id="userids" name="userids" value="${valMap.userids}">
<input type="hidden" id="ticketids" name="ticketids" value="${ticketids }">
<div class="row">
    <div class="col-md-12">
		<div class="box box-default">
            <div class="box-body">
              	<div class="row">
                	<label class="col-xs-1 control-label" style="padding:10px 0px 0px 20px;">用户:</label>
                	<div class="col-xs-3" style="padding:5px 0px 0px 0px;">
                  		<input type="text" class="form-control" id="userNames" value="${valMap.userNames}">
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
</div>
<script>
$(window).load(function(){
// 	$("#treebody").css("height",getheight()-210);
	$("#tabbody").css("height",getheight());
	//初始化jqGrid--用户
	init(getheight()-210);
	//初始化zTree
// 	$.fn.zTree.init($("#tree"),setting,zNodes);
});
//初始化jqGrid--用户
function init(height){
	//AJAX请求列表数据
    jQuery("#table").jqGrid({
    	//caption:'',
		datatype:'json',//将这里改为使用JSON数据 
		url:'<%=ctx%>/user/getCusTicketsDataList',//这是Action的请求地址 
		postData:{'customerid':'${user.customerid}'},  
	    mtype:'POST',
	    height:height,
   	    autowidth:true,
        shrinkToFit:true,
        colNames:['id','票名','票价','票类型'],//列名
        colModel:[//列对应的字段
            {name:'id',index:'id',width:30,hidden:true,sortable:false},
            {name:'name',index:'name',width:30,sortable:false},
            {name:'price',index:'price',width:60,sortable:true},  
        	{name:'type',index:'type',width:60,sortable:false,formatter: "select", editoptions:{value:"0:门票;2:套票;1:通票"}},   
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
        onSelectRow:function(id,st){//id:选中行id,st:当前是否选中----单选赋值
        	var ticketids=$("#ticketids").val();
            if(st){//选中
            		ticketids+=id+",";
            }else{//取消
            	ticketids=ticketids.replace(id+",","");//用户删除id
            }
            $("#ticketids").val(ticketids);
      	},
        onSelectAll:function(ids,st){//全选数据过滤
      		if(ids.length>0){
	      		var ticketids=$("#ticketids").val();
				for(var i=0;i<ids.length;i++){
		            if(st){//选中
		            	if(ticketids.indexOf(ids[i])<0){//不包含
		            		ticketids+=ids[i]+",";
			            }
		            }else{//取消
			            if(ticketids.indexOf(ids[i])>=0){//包含
			            	ticketids=ticketids.replace(ids[i]+",","");//用户删除id
			            }
		            }
      		 	}
	            $("#ticketids").val(ticketids);
	      	}
      	},
        gridComplete:function(){
            var ids=jQuery("#table").jqGrid('getDataIDs');
            for(var i=0;i<ids.length;i++){
                var id = ids[i];
                var ticketids = '${ticketids}';
                var ticketarr = ticketids.substring(0,ticketids.length-1).split(",");
                if(ticketids.indexOf(id)>-1){
                	$("#table").setSelection(id);
                }
//                 if(customerid==oldcustomerid||type==''){
// 	                <c:if test="${fn:contains(button,'ae87c55416a5467289ee68c2914981f5')}">
// 	                	editBtn+="<button type=\"button\" class=\"btn btn-warning btn-xs\" onclick=\"useredit(\'"+id+"\');\"><i class=\"fa fa-paste\"></i>修改</button>";
// 	                </c:if>
// 	                <c:if test="${fn:contains(button,'47c0b839431d4b96805c67e7e4e56c8d')}">
// 		                editBtn+="&nbsp;&nbsp;";
// 		                editBtn+="<button type=\"button\" class=\"btn btn-danger btn-xs\" onclick=\"userdel(\'"+id+"\');\"><i class=\"fa fa-times\"></i>删除</button>";
// 	               	</c:if>
// 	               	<c:if test="${fn:contains(button,'339ce4fb960f44feaecc443f33c30817')}">
// 		                editBtn+="&nbsp;&nbsp;";
// 		                editBtn+="<button type=\"button\" class=\"btn btn-success btn-xs\" onclick=\"resp(\'"+id+"\');\"><i class=\"fa fa-undo\"></i>密码重置</button>";
// 	                </c:if>
//                 }
//                 jQuery("#table").jqGrid('setRowData',ids[i],{Edit:editBtn});
            }
		}
    });
	//自适应调整
    setjqGridWidth('table');
}
//保存票权限
function save(){
	var ret="ok";
	var userids=$("#userids").val();
	var ticketids=$("#ticketids").val();
	if(ticketids==null || ticketids==""){
		Dialog.alert("请选择票");
		ret="false";
		return;
	}
		$.ajax({
			type:'post',
			url:'<%=ctx %>/user/saveDisTicketAuthority',
			data:{'userids':userids,'ticketids':ticketids},
			dataType:'text',
			async:false,
			success:function(data){
				//刷新
				query();
			}
		});
		return ret;
}
</script>