import java.io.*;
import java.sql.*;
import java.util.*;
import org.jdom.*;
import org.jdom.input.*;

public class Tool {
  // Data about this tool record
  public int id;
  public String name;
  public String homeURL;
  public String comments;
  public String stateFlag;
  public Timestamp createdTime;
  public Timestamp modifiedTime;

  // Tea can only access bean properties, so accessor methods are required
  public int getId() { return id; }
  public String getName() { return name; }
  public String getHomeURL() { return homeURL; }
  public String getComments() { return comments; }
  public String getStateFlag() { return stateFlag; }
  public Timestamp getCreatedTime() { return createdTime; }
  public Timestamp getModifiedTime() { return modifiedTime; }


  public int getCreatedAgeInDays() {
    return (int) ((System.currentTimeMillis() - createdTime.getTime()) /
            (24 * 60 * 60 * 1000));  // millis in a day
  }

  public int getModifiedAgeInDays() {
    return (int) ((System.currentTimeMillis() - modifiedTime.getTime()) /
            (24 * 60 * 60 * 1000));  // millis in a day
  }


  // Ideally we'd use methods like these, but Tea only allows property
  // access on an object.  These won't be visible.
  public boolean isNewWithin(int days) {
    return getCreatedAgeInDays() < days;
  }

  public boolean isUpdatedWithin(int days) {
    return getModifiedAgeInDays() < days;
  }


  public static Tool[] loadTools(String toolsFile) throws Exception {
    // Read the tool data from an XML file containing <tool> elements
    // Use the JDOM API to keep things simple (http://jdom.org)
    List toolObjects = new LinkedList();

    SAXBuilder builder = new SAXBuilder();
    Document document = builder.build(new File(toolsFile));
    Element root = document.getRootElement();
    List toolElements = root.getChildren("tool");
    Iterator i = toolElements.iterator();
    while (i.hasNext()) {
      Element tool = (Element) i.next();
      Tool t = new Tool();
      t.id = tool.getAttribute("id").getIntValue();
      t.name = tool.getChild("name").getTextTrim();
      t.homeURL = tool.getChild("homeURL").getTextTrim();
      t.comments = tool.getChild("comments").getTextTrim();
      t.stateFlag = tool.getChild("stateFlag").getTextTrim();
      t.createdTime = Timestamp.valueOf(
                        tool.getChild("createdTime").getTextTrim());
      t.modifiedTime = Timestamp.valueOf(
                         tool.getChild("modifiedTime").getTextTrim());
      toolObjects.add(t);
    }

    return (Tool[]) toolObjects.toArray(new Tool[0]);
  }
}
