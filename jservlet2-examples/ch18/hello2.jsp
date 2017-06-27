<%-- hello2.jsp --%>
<HTML>
<HEAD><TITLE>Hello</TITLE></HEAD>
<BODY>
<H1>
Hello, <%= getName(request) %>
</H1>
</BODY>
</HTML>

<%!
private static final String DEFAULT_NAME = "World";

private String getName(HttpServletRequest req) {
  String name = req.getParameter("name");
  if (name == null)
    return DEFAULT_NAME;
  else
    return name;
}
%>
