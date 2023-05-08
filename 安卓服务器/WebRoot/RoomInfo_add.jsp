<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.BuildingInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�BuildingInfo��Ϣ
    List<BuildingInfo> buildingInfoList = (List<BuildingInfo>)request.getAttribute("buildingInfoList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>��ӷ�����Ϣ</TITLE> 
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
/*��֤��*/
function checkForm() {
    var roomName = document.getElementById("roomInfo.roomName").value;
    if(roomName=="") {
        alert('�����뷿������!');
        return false;
    }
    var roomTypeName = document.getElementById("roomInfo.roomTypeName").value;
    if(roomTypeName=="") {
        alert('�����뷿������!');
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
    <TD align="left" vAlign=top >
    <s:form action="RoomInfo/RoomInfo_AddRoomInfo.action" method="post" id="roomInfoAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>��������:</td>
    <td width=70%>
      <select name="roomInfo.buildingObj.buildingId">
      <%
        for(BuildingInfo buildingInfo:buildingInfoList) {
      %>
          <option value='<%=buildingInfo.getBuildingId() %>'><%=buildingInfo.getBuildingName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input id="roomInfo.roomName" name="roomInfo.roomName" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input id="roomInfo.roomTypeName" name="roomInfo.roomTypeName" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>����۸�(Ԫ/��):</td>
    <td width=70%><input id="roomInfo.roomPrice" name="roomInfo.roomPrice" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>�ܴ�λ:</td>
    <td width=70%><input id="roomInfo.totalBedNumber" name="roomInfo.totalBedNumber" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>ʣ�ലλ:</td>
    <td width=70%><input id="roomInfo.leftBedNum" name="roomInfo.leftBedNum" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>���ҵ绰:</td>
    <td width=70%><input id="roomInfo.roomTelephone" name="roomInfo.roomTelephone" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>������Ϣ:</td>
    <td width=70%><textarea id="roomInfo.roomMemo" name="roomInfo.roomMemo" rows="5" cols="50"></textarea></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
