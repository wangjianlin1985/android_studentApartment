<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.RoomInfo" %>
<%@ page import="com.chengxusheji.domain.BuildingInfo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的BuildingInfo信息
    List<BuildingInfo> buildingInfoList = (List<BuildingInfo>)request.getAttribute("buildingInfoList");
    RoomInfo roomInfo = (RoomInfo)request.getAttribute("roomInfo");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改房间信息</TITLE>
<STYLE type=text/css>
BODY {
	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*验证表单*/
function checkForm() {
    var roomName = document.getElementById("roomInfo.roomName").value;
    if(roomName=="") {
        alert('请输入房间名称!');
        return false;
    }
    var roomTypeName = document.getElementById("roomInfo.roomTypeName").value;
    if(roomTypeName=="") {
        alert('请输入房间类型!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="RoomInfo/RoomInfo_ModifyRoomInfo.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>记录编号:</td>
    <td width=70%><input id="roomInfo.roomId" name="roomInfo.roomId" type="text" value="<%=roomInfo.getRoomId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>所在宿舍:</td>
    <td width=70%>
      <select name="roomInfo.buildingObj.buildingId">
      <%
        for(BuildingInfo buildingInfo:buildingInfoList) {
          String selected = "";
          if(buildingInfo.getBuildingId() == roomInfo.getBuildingObj().getBuildingId())
            selected = "selected";
      %>
          <option value='<%=buildingInfo.getBuildingId() %>' <%=selected %>><%=buildingInfo.getBuildingName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>房间名称:</td>
    <td width=70%><input id="roomInfo.roomName" name="roomInfo.roomName" type="text" size="20" value='<%=roomInfo.getRoomName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>房间类型:</td>
    <td width=70%><input id="roomInfo.roomTypeName" name="roomInfo.roomTypeName" type="text" size="20" value='<%=roomInfo.getRoomTypeName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>房间价格(元/月):</td>
    <td width=70%><input id="roomInfo.roomPrice" name="roomInfo.roomPrice" type="text" size="8" value='<%=roomInfo.getRoomPrice() %>'/></td>
  </tr>

  <tr>
    <td width=30%>总床位:</td>
    <td width=70%><input id="roomInfo.totalBedNumber" name="roomInfo.totalBedNumber" type="text" size="8" value='<%=roomInfo.getTotalBedNumber() %>'/></td>
  </tr>

  <tr>
    <td width=30%>剩余床位:</td>
    <td width=70%><input id="roomInfo.leftBedNum" name="roomInfo.leftBedNum" type="text" size="8" value='<%=roomInfo.getLeftBedNum() %>'/></td>
  </tr>

  <tr>
    <td width=30%>寝室电话:</td>
    <td width=70%><input id="roomInfo.roomTelephone" name="roomInfo.roomTelephone" type="text" size="20" value='<%=roomInfo.getRoomTelephone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>附加信息:</td>
    <td width=70%><textarea id="roomInfo.roomMemo" name="roomInfo.roomMemo" rows=5 cols=50><%=roomInfo.getRoomMemo() %></textarea></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
