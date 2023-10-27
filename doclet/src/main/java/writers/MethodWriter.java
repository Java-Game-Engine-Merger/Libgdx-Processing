package writers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.lang.model.element.ExecutableElement;

import org.json.JSONException;
import org.json.JSONObject;

public class MethodWriter extends BaseWriter{

  public MethodWriter() {}

  /**
   *
   *
   * @param vars the inherited vars from the method's ClassDoc
   * @param doc  the method doc
   * @throws IOException
   */
  public static void write(
    HashMap<String,String> vars,
    ExecutableElement element,
    String classname,
    String foldername) throws IOException {
    TemplateWriter templateWriter=new TemplateWriter();

    ArrayList<String> syntax=templateWriter.writeLoopSyntax(
      "method.syntax.partial",
      getSyntax(element,getInstanceName(element)));

    JSONObject methodJSON=new JSONObject();

    String fileName=foldername+
      classname+
      "_"+
      getName(element).replace("()","_")+
      ".json";

    List<String> tags=Shared
      .i()
      .getTags(element)
      .get(Shared.i().getWebrefTagName());
    String category=getCategory(tags.get(0));
    String subcategory=getSubcategory(tags.get(0));

    if(!classname.equals("PGraphics")||
      getName(element).equals("beginDraw()")||
      getName(element).equals("endDraw()")) {
      try {
        methodJSON.put("type","method");
        methodJSON.put("name",getName(element));
        methodJSON.put("description",getWebDescriptionFromSource(element));
        methodJSON.put("brief",getWebBriefFromSource(element));
        methodJSON.put("category",category);
        methodJSON.put("subcategory",subcategory);
        methodJSON.put("syntax",syntax);
        methodJSON.put("parameters",getParameters(element));
        methodJSON.put("related",getRelated(element));
        methodJSON.put("returns",getReturnTypes(element));
      }catch(JSONException ex) {
        ex.printStackTrace();
      }

      try {
        if(Shared.i().isRootLevel(element.getEnclosingElement())) {
          methodJSON.put("classname","");
        }else {
          methodJSON.put(
            "classanchor",
            getLocalAnchor(element.getEnclosingElement()));
        }
      }catch(JSONException ex) {
        ex.printStackTrace();
      }

      try {
        FileWriter file=new FileWriter(fileName);
        file.write(methodJSON.toString());
        file.close();
      }catch(IOException e) {
        e.printStackTrace();
      }
    }
  }
}
