<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.RoomInfo" %>
<%@ page import="com.chengxusheji.domain.IntoType" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的RoomInfo信息
    List<RoomInfo> roomInfoList = (List<RoomInfo>)request.getAttribute("roomInfoList");
    //获取所有的IntoType信息
    List<IntoType> intoTypeList = (List<IntoType>)request.getAttribute("intoTypeList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加综合信息</TITLE> 
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
    var infoTitle = document.getElementById("newsInfo.infoTitle").value;
    if(infoTitle=="") {
        alert('请输入信息标题!');
        return false;
    }
    var infoContent = document.getElementById("newsInfo.infoContent").value;
    if(infoContent=="") {
        alert('请输入信息内容!');
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
    <s:form action="NewsInfo/NewsInfo_AddNewsInfo.action" method="post" id="newsInfoAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>寝室房间:</td>
    <td width=70%>
      <select name="newsInfo.roomObj.roomId">
      <%
        for(RoomInfo roomInfo:roomInfoList) {
      %>
          <option value='<%=roomInfo.getRoomId() %>'><%=roomInfo.getRoomName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>信息类型:</td>
    <td width=70%>
      <select name="newsInfo.infoTypeObj.typeId">
      <%
        for(IntoType intoType:intoTypeList) {
      %>
          <option value='<%=intoType.getTypeId() %>'><%=intoType.getInfoTypeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>信息标题:</td>
    <td width=70%><input id="newsInfo.infoTitle" name="newsInfo.infoTitle" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>信息内容:</td>
    <td width=70%><textarea id="newsInfo.infoContent" name="newsInfo.infoContent" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>信息日期:</td>
    <td width=70%><input type="text" readonly id="newsInfo.infoDate"  name="newsInfo.infoDate" onclick="setDay(this);"/></td>
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
