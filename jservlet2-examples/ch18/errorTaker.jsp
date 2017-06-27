<%-- errorTaker.jsp --%>

<%@ page isErrorPage="true" %>
<%@ page import="com.oreilly.servlet.*" %>

<% response.setStatus(500); %>

<HTML>
<HEAD><TITLE>Error: <%= exception.getClass().getName() %></TITLE></HEAD>
<BODY>

<H1>
<%= exception.getClass().getName() %>
</H1>

We encountered an error while executing your page:

<PRE>
<%= ServletUtils.getStackTraceAsString(exception) %>
</PRE>

<% String name = request.getServerName(); %>
Please contact <A HREF="mailto:webmaster@<%= name %>">webmaster@<%= name %></A>
to report the problem.

</BODY>
</HTML>
