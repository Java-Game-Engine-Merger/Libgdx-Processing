package writers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.ExecutableElement;

import org.json.JSONException;
import org.json.JSONObject;

public class FunctionWriter extends BaseWriter{

  static ArrayList<String> writtenFunctions;

  public FunctionWriter() {}

  public static void write(ExecutableElement element) throws IOException {
    if(!needsWriting(element)) return;

    String anchor=getAnchor(element);
    TemplateWriter templateWriter=new TemplateWriter();

    ArrayList<String> syntax=templateWriter.writeLoopSyntax(
      "function.syntax.partial",
      getSyntax(element,""));

    JSONObject functionJSON=new JSONObject();

    String fileName=jsonDir+getName(element).replace("()","_")+".json";

    List<String> tags=Shared
      .i()
      .getTags(element)
      .get(Shared.i().getWebrefTagName());
    String category=getCategory(tags.get(0));
    String subcategory=getSubcategory(tags.get(0));

    try {
      functionJSON.put("type","function");
      functionJSON.put("name",getName(element));
      functionJSON.put("description",getWebDescriptionFromSource(element));
      functionJSON.put("brief",getWebBriefFromSource(element));
      functionJSON.put("category",category);
      functionJSON.put("subcategory",subcategory);
      functionJSON.put("syntax",syntax);
      functionJSON.put("parameters",getParameters(element));
      functionJSON.put("related",getRelated(element));
      functionJSON.put("returns",getReturnTypes(element));
    }catch(JSONException ex) {
      ex.printStackTrace();
    }

    try {
      FileWriter file=new FileWriter(fileName);
      file.write(functionJSON.toString());
      file.close();
    }catch(IOException e) {
      e.printStackTrace();
    }
  }
}
