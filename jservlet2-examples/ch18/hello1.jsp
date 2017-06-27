<HTML>
<HEAD><TITLE>Hello</TITLE></HEAD>
<BODY>
<H1>
<%
if (request.getParameter("name") == null) {
   out.println("Hello World");
}
else {
  out.println("Hello, " + request.getParameter("name"));
}
%>
</H1>
</BODY></HTML>
