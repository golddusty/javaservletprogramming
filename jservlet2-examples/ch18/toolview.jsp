<%-- toolview.jsp --%>

<%
  String title = "Tool Listing";
  String deck = "A list of content creation tools";
  String desc = "Without tools, people are nothing more than animals.  And " +
                "pretty weak ones at that.  Here's a list of servlet-based " +
                "content creation tools you can use so you won't be a " +
                "servlet weakling.";
%>

<%@ include file="/header.jsp" %>

<%@ page session="false" %>
<%@ page errorPage="/errorTaker.jsp" %>

<jsp:useBean id="toolbean" class="ToolBean" scope="application">
  <jsp:setProperty name="toolbean" property="toolsFile"
                value='<%= application.getInitParameter("toolsFile") %>' />
</jsp:useBean>

<%
  Tool[] tools = toolbean.getTools(request.getParameter("state"));

  for (int i = 0; i < tools.length; i++) {
    Tool tool = tools[i];
%>
  <HR SIZE=2 ALIGN=LEFT>

  <H3>
  <%= tool.name %>

  <% if (tool.isNewWithin(45)) { %>
    <FONT COLOR=#FF0000><B> (New!) </B></FONT>
  <% } else if (tool.isUpdatedWithin(45)) { %>
    <FONT COLOR=#FF0000><B> (Updated!) </B></FONT>
  <% } %>

  </H3>
  <A HREF="<%= tool.homeURL %>"><%= tool.homeURL %></A><BR>

  <%= tool.comments %>

<% } %>

<%@ include file="/footer.jsp" %>
