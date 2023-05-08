<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.RoomInfo" %>
<%@ page import="com.chengxusheji.domain.BuildingInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的BuildingInfo信息
    List<BuildingInfo> buildingInfoList = (List<BuildingInfo>)request.getAttribute("buildingInfoList");
    RoomInfo roomInfo = (RoomInfo)request.getAttribute("roomInfo");

%>
<HTML><HEAD><TITLE>查看房间信息</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>记录编号:</td>
    <td width=70%><%=roomInfo.getRoomId() %></td>
  </tr>

  <tr>
    <td width=30%>所在宿舍:</td>
    <td width=70%>
      <%=roomInfo.getBuildingObj().getBuildingName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>房间名称:</td>
    <td width=70%><%=roomInfo.getRoomName() %></td>
  </tr>

  <tr>
    <td width=30%>房间类型:</td>
    <td width=70%><%=roomInfo.getRoomTypeName() %></td>
  </tr>

  <tr>
    <td width=30%>房间价格(元/月):</td>
    <td width=70%><%=roomInfo.getRoomPrice() %></td>
  </tr>

  <tr>
    <td width=30%>总床位:</td>
    <td width=70%><%=roomInfo.getTotalBedNumber() %></td>
  </tr>

  <tr>
    <td width=30%>剩余床位:</td>
    <td width=70%><%=roomInfo.getLeftBedNum() %></td>
  </tr>

  <tr>
    <td width=30%>寝室电话:</td>
    <td width=70%><%=roomInfo.getRoomTelephone() %></td>
  </tr>

  <tr>
    <td width=30%>附加信息:</td>
    <td width=70%><%=roomInfo.getRoomMemo() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
