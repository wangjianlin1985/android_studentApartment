<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.NewsInfo" %>
<%@ page import="com.chengxusheji.domain.RoomInfo" %>
<%@ page import="com.chengxusheji.domain.IntoType" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�RoomInfo��Ϣ
    List<RoomInfo> roomInfoList = (List<RoomInfo>)request.getAttribute("roomInfoList");
    //��ȡ���е�IntoType��Ϣ
    List<IntoType> intoTypeList = (List<IntoType>)request.getAttribute("intoTypeList");
    NewsInfo newsInfo = (NewsInfo)request.getAttribute("newsInfo");

%>
<HTML><HEAD><TITLE>�鿴�ۺ���Ϣ</TITLE>
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
    <td width=30%>��¼���:</td>
    <td width=70%><%=newsInfo.getNewsId() %></td>
  </tr>

  <tr>
    <td width=30%>���ҷ���:</td>
    <td width=70%>
      <%=newsInfo.getRoomObj().getRoomName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>��Ϣ����:</td>
    <td width=70%>
      <%=newsInfo.getInfoTypeObj().getInfoTypeName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>��Ϣ����:</td>
    <td width=70%><%=newsInfo.getInfoTitle() %></td>
  </tr>

  <tr>
    <td width=30%>��Ϣ����:</td>
    <td width=70%><%=newsInfo.getInfoContent() %></td>
  </tr>

  <tr>
    <td width=30%>��Ϣ����:</td>
        <% java.text.DateFormat infoDateSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=infoDateSDF.format(newsInfo.getInfoDate()) %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="����" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
