<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>���ڰ�׿ѧ����Ԣ����ϵͳ-��ҳ</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">��ҳ</a></li>
			<li><a href="<%=basePath %>ClassInfo/ClassInfo_FrontQueryClassInfo.action" target="OfficeMain">�༶��Ϣ</a></li> 
			<li><a href="<%=basePath %>Student/Student_FrontQueryStudent.action" target="OfficeMain">ѧ����Ϣ</a></li> 
			<li><a href="<%=basePath %>BuildingInfo/BuildingInfo_FrontQueryBuildingInfo.action" target="OfficeMain">������Ϣ</a></li> 
			<li><a href="<%=basePath %>RoomInfo/RoomInfo_FrontQueryRoomInfo.action" target="OfficeMain">������Ϣ</a></li> 
			<li><a href="<%=basePath %>LiveInfo/LiveInfo_FrontQueryLiveInfo.action" target="OfficeMain">ס����Ϣ</a></li> 
			<li><a href="<%=basePath %>IntoType/IntoType_FrontQueryIntoType.action" target="OfficeMain">��Ϣ����</a></li> 
			<li><a href="<%=basePath %>NewsInfo/NewsInfo_FrontQueryNewsInfo.action" target="OfficeMain">�ۺ���Ϣ</a></li> 
		</ul>
		<br />
	</div> 
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>desk.jsp" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p>˫������� QQ:287307421��254540457 &copy;��Ȩ���� <a href="http://www.shuangyulin.com" target="_blank">˫���������</a>&nbsp;&nbsp;<a href="<%=basePath%>login/login_view.action"><font color=red>��̨��½</font></a></p>
	</div>
</div>
</body>
</html>
