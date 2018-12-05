<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<% String ctx=request.getContextPath(); %>
<form id="forms" name="forms" action="<%=ctx%>/login/login" method="post">
<input type="hidden" id="setdepid" name="depid">
<input type="hidden" id="setroleid" name="roleid">
</form>
<!-- top start -->
<header class="main-header">
   	<!-- Logo -->
   	<a href="#" class="logo">
      	<!-- mini logo for sidebar mini 50x50 pixels -->
      	<span class="logo-mini"><b>${sysname}</b></span>
      	<!-- logo for regular state and mobile devices -->
      	<span class="logo-lg"><b>${sysname}</b></span>
   	</a>
   	<!-- Header Navbar: style can be found in header.less -->
   	<nav class="navbar navbar-static-top">
      	<!-- Sidebar toggle button-->
	    <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
	    	<span class="sr-only">Toggle navigation</span>
      	</a>
<!--       	<img src="<%=ctx %>/static/img/logo/logo.png" height="45px"> -->
      	<div class="navbar-custom-menu">
       		<ul class="nav navbar-nav">
         			<li class="dropdown user user-menu">
		            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
		            	<c:if test="${user.id=='1'||role.data=='0'}">
							<!-- 资产管理员 -->
		            		<img src="<%=ctx %>/static/img/admin.png" class="user-image" alt="User Image">
		            	</c:if>
		            	<c:if test="${role.data=='1'}">
		            		<!-- 部门主管 -->
		            		<img src="<%=ctx %>/static/img/manager.png" class="user-image" alt="User Image">
		            	</c:if>
		            	<c:if test="${role.data=='2'||(user.id!='1'&&role==null)}">
		            		<!-- 用户 -->
		            		<img src="<%=ctx %>/static/img/user.png" class="user-image" alt="User Image">
		            	</c:if>
		              	<span class="hidden-xs" id="name1">${user.name}</span>
		            </a>
           			<ul class="dropdown-menu">
           				<!-- User image -->
           				<li class="user-header">
               				<c:if test="${user.id=='1'||role.data=='0'}">
								<!-- 资产管理员 -->
			            		<img src="<%=ctx %>/static/img/admin.png" class="img-circle" alt="User Image">
			            	</c:if>
			            	<c:if test="${role.data=='1'}">
			            		<!-- 部门主管 -->
			            		<img src="<%=ctx %>/static/img/manager.png" class="img-circle" alt="User Image">
			            	</c:if>
			            	<c:if test="${role.data=='2'||(user.id!='1'&&role==null)}">
			            		<!-- 用户 -->
			            		<img src="<%=ctx %>/static/img/user.png" class="img-circle" alt="User Image">
			            	</c:if>
               				<p><span id="name2">${user.name}</span> - ${role.name}<small>所属部门 - ${dep.name}</small></p>
           				</li>
           				<!-- Menu Footer-->
           				<li class="user-footer">
               				<div class="pull-left">
                 					<a href="#0" class="btn btn-default btn-flat" onclick="userselfedit('${user.id}');">修改个人信息</a>
               				</div>
               				<div class="pull-right">
               						<!-- 单点登出   登出后跳转到指定服务器的页面 第一个service:跳转地址,第二个service:跳转单点登录页面,再次登录要跳转页面
               						<a href="http://localhost/cas/logout?service=http://localhost/cas/login?service=http://localhost/basis/" class="btn btn-default btn-flat">登出</a>
               						-->
               						<!-- 普通注销 -->
                 					<a href="<%=ctx %>/login/logout" class="btn btn-default btn-flat">注销</a>
               				</div>
           				</li>
           			</ul>
         			</li>
         			<!-- 所属部门 -->
         			<li>
       					<c:if test="${deplist.size()>1}">
       						<a href="#" style="color:black;">
	       						<select id="depid" onchange="chengeper();">
	       							<c:forEach items="${deplist}" var="depvar">
	       								<option value="${depvar.id}">${depvar.name}</option>
	       							</c:forEach>
	       						</select>
       						</a>
       					</c:if>
       					<c:if test="${deplist.size()==1}">
       						<input type="hidden" id="depid" value="${dep.id}">
       						<a href="#">${dep.name}</a>
       					</c:if>
         			</li>
         			<!-- 所有角色 -->
         			<li>
         				<c:if test="${rolelist.size()>1}">
       						<a href="#" style="color:black;">
	       						<select id="roleid" onchange="chengeper();">
	       							<c:forEach items="${rolelist}" var="rolevar">
	       								<option value="${rolevar.id}">${rolevar.name}</option>
	       							</c:forEach>
	       						</select>
       						</a>
       					</c:if>
       					<c:if test="${rolelist.size()==1}">
       						<input type="hidden" id="roleid" value="${role.id}">
       						<a href="#">${role.name}</a>
       					</c:if>
         			</li>
         			<!-- Control Sidebar Toggle Button -->
         			<li>
           			<a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
         			</li>
       		</ul>
     		</div>
   	</nav>
</header>
<!-- top end -->
<script>
//初始化参数
$(window).load(function(){
	$("#depid").val("${dep.id}");
	$("#roleid").val("${role.id}");
});
//修改个人信息
function userselfedit(id){
	var dialog=new Dialog();
	dialog.Width = 600;
	dialog.Height = 500;
	dialog.Title="修改个人信息";
	dialog.URL="<%=ctx%>/login/usertoedit?id="+id;
	dialog.OKEvent=function(){
		var ret=dialog.innerFrame.contentWindow.save();
		var name=dialog.innerFrame.contentWindow.getname();
		$("#name1").html(name);
		$("#name2").html(name);
		$.ajax({//刷新session--USER
			type:'post',
			url:'<%=ctx %>/login/reloadsession?id='+id,
			dataType:'text',
			async:false,
			success:function(data){}
		});
		
		if(ret=="ok"){
			dialog.close();
		}
	};
	dialog.CancelEvent=function(){
		dialog.close();
	};
	dialog.show();
	dialog.okButton.value="保存";
}
//更换权限
function chengeper(){
	var depid=$("#depid").val();
	var roleid=$("#roleid").val();
	$("#setdepid").val(depid);
	$("#setroleid").val(roleid);
	$("#forms").submit();
}
</script>