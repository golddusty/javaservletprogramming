import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class NileItem extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {

    // We do not set the content type

    PrintWriter out = res.getWriter();

    Book book = (Book) req.getAttribute("item");

    out.println("<BR>");
    if (book != null) {
      out.println("<I>" + book.getTitle() + "</I>");
      out.println(" by " + book.getAuthor());
    }
    else {
      out.println("<I>No book record found</I>");
    }
    out.println("<BR>");
  }
}
