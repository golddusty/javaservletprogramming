<%-- errorMaker.jsp --%>

<%@ page session="false" %>             <%-- Don't send needless cookies --%>
<%@ page errorPage="/errorTaker.jsp" %> <%-- General error handling page --%>

<%-- All we're good for is throwing an exception --%>
<%
  if (System.currentTimeMillis() > 0) {
    throw new Exception("oops");
  }
%>
