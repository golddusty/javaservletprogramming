import java.io.*;
import java.awt.*;
import javax.servlet.*;
import javax.servlet.http.*;

import Acme.JPM.Encoders.GifEncoder;

public class HelloWorldGraphics extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    ServletOutputStream out = res.getOutputStream();  // binary output!

    Frame frame = null;
    Graphics g = null;

    try {
      // Create an unshown frame
      frame = new Frame();
      frame.addNotify();

      // Get a graphics region, using the Frame
      Image image = frame.createImage(400, 60);
      g = image.getGraphics();

      // Draw "Hello World!" to the off-screen graphics context
      g.setFont(new Font("Serif", Font.ITALIC, 48));
      g.drawString("Hello World!", 10, 50);

      // Encode the off-screen image into a GIF and send it to the client
      res.setContentType("image/gif");
      GifEncoder encoder = new GifEncoder(image, out);
      encoder.encode();
    }
    finally {
      // Clean up resources
      if (g != null) g.dispose();
      if (frame != null) frame.removeNotify();
    }
  }
}
