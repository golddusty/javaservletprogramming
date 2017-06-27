import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class NileBooks extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    out.println("<HTML><HEAD><TITLE>Welcome to Nile</TITLE></HEAD>");
    out.println("<BODY>");

    // Show items in an online catalog
    RequestDispatcher dispatcher =
      req.getRequestDispatcher("/servlet/NileItem");

    out.println("Feast your eyes on this beauty:");
    req.setAttribute("item", Book.getBook("156592391X"));
    dispatcher.include(req, res);

    // Remove the "item" attribute after use
    req.removeAttribute("item");

    out.println("Or how about this one:");
    req.setAttribute("item", Book.getBook("0395282659"));
    dispatcher.include(req, res);

    out.println("And, since I like you, they're all 20% off!");

    out.println("</BODY></HTML>");
  }
}
