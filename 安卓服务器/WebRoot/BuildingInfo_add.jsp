<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>���������Ϣ</TITLE> 
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
    var areaObj = document.getElementById("buildingInfo.areaObj").value;
    if(areaObj=="") {
        alert('����������У��!');
        return false;
    }
    var buildingName = document.getElementById("buildingInfo.buildingName").value;
    if(buildingName=="") {
        alert('��������������!');
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
    <s:form action="BuildingInfo/BuildingInfo_AddBuildingInfo.action" method="post" id="buildingInfoAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>����У��:</td>
    <td width=70%><input id="buildingInfo.areaObj" name="buildingInfo.areaObj" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input id="buildingInfo.buildingName" name="buildingInfo.buildingName" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>����Ա:</td>
    <td width=70%><input id="buildingInfo.manageMan" name="buildingInfo.manageMan" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>�����绰:</td>
    <td width=70%><input id="buildingInfo.telephone" name="buildingInfo.telephone" type="text" size="20" /></td>
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
