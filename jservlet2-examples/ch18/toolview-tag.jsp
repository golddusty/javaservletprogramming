<%-- toolview-tag.jsp --%>

<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>

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

<%-- Fetch the tools array as a request attribute --%>
<jsp:useBean id="tools" class="Tool[]" scope="request"/>

<struts:iterate id="tool" collection="<%= tools %>">
  <HR SIZE=2 ALIGN=LEFT>

  <H3>
  <%-- Automatically HTML-escapes values --%>
  <struts:property name="tool" property="name" />

  <% if (((Tool)tool).isNewWithin(45)) { %>
    <FONT COLOR=#FF0000><B> (New!) </B></FONT>
  <% } else if (((Tool)tool).isUpdatedWithin(45)) { %>
    <FONT COLOR=#FF0000><B> (Updated!) </B></FONT>
  <% } %>

  </H3>
  <A HREF="<struts:property name="tool" property="homeURL"/>">
           <struts:property name="tool" property="homeURL"/></A><BR>

  <%-- Assume don't want HTML in comments --%>
  <struts:property name="tool" property="comments" />

</struts:iterate>

<%@ include file="/footer.jsp" %>
