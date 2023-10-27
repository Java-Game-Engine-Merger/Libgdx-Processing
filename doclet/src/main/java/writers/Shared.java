package writers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// Program element
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
// Util classes
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import com.sun.source.doctree.DocCommentTree;
import com.sun.source.doctree.DocTree;
import com.sun.source.doctree.ParamTree;
import com.sun.source.doctree.SeeTree;
import com.sun.source.doctree.UnknownBlockTagTree;
import com.sun.source.util.DocTrees;
import com.sun.source.util.SimpleDocTreeVisitor;

// Doclet
import jdk.javadoc.doclet.DocletEnvironment;

public class Shared{

  // where things come from
  private String templateDirectory="templates";
  private String exampleDirectory="web_examples";
  private String includeDirectory="include";
  private String jsonDirectory="../../processing-website/content/references/translations/en/processing/";

  // what we're looking for
  private static Shared instance;
  private String webrefTagName="webref";
  private String[] omitWebTagNames= {"nowebref","notWebref"};
  private String seeAlsoTagName="see_external";
  private String coreClassName="PApplet";
  private ArrayList<String> descriptionSets;

  // where things go
  private String outputDirectory="web_reference";
  private String localOutputDirectory="local_reference";
  private String imageDirectory="images";
  private String fileExtension=".html";

  // utils from doclet environment
  private DocTrees docTreeUtils;
  private Elements elementUtils;
  private Types typeUtils;

  boolean noisy=false;
  public ArrayList<String> includedPackages;
  public ArrayList<String> corePackages;
  public ArrayList<String> rootClasses;

  private Shared() {
    includedPackages=new ArrayList<String>();
    corePackages=new ArrayList<String>();
    rootClasses=new ArrayList<String>();
    descriptionSets=new ArrayList<String>();

    addDescriptionTag("description");
  }

  public static Shared i() {
    if(instance==null) {
      instance=new Shared();
    }
    return instance;
  }

  public void setUtils(DocletEnvironment environment) {
    docTreeUtils=environment.getDocTrees();
    elementUtils=environment.getElementUtils();
    typeUtils=environment.getTypeUtils();
  }

  public void loadIncludedPackages(DocletEnvironment environment) {
    for(Element element:environment.getIncludedElements()) {
      if(isPackage(element)) {
        PackageElement packageElement=(PackageElement)element;
        includedPackages.add(packageElement.getQualifiedName().toString());
      }
    }
  }

  public DocTrees getDocTreeUtils() {
    return docTreeUtils;
  }

  public Elements getElementUtils() {
    return elementUtils;
  }

  public Types getTypeUtils() {
    return typeUtils;
  }

  public void setIncludeDirectory(String directory) {
    includeDirectory=directory;
  }

  public String getIncludeDirectory() {
    return includeDirectory;
  }

  public String INCLUDE_DIRECTORY() {
    return includeDirectory+"/";
  }

  public void setWebrefTagName(String tagName) {
    webrefTagName=tagName;
  }

  public String getWebrefTagName() {
    return webrefTagName;
  }

  public void setSeeAlsoTagName(String tagName) {
    seeAlsoTagName=tagName;
  }

  public String getSeeAlsoTagName() {
    return seeAlsoTagName;
  }

  public void setCoreClassName(String newClassName) {
    coreClassName=newClassName;
  }

  public String getCoreClassName() {
    return coreClassName;
  }

  public void addDescriptionTag(String tag) {
    System.out.println("Added description tag: "+tag);
    descriptionSets.add("/root/"+tag);
    descriptionSets.add("/root/js_mode/"+tag);
  }

  public ArrayList<String> getDescriptionSets() {
    return descriptionSets;
  }

  public void setOutputDirectory(String directory) {
    outputDirectory=directory;
  }

  public String getOutputDirectory() {
    return outputDirectory;
  }

  public void setFileExtension(String extension) {
    fileExtension=extension;
  }

  public String getFileExtension() {
    return fileExtension;
  }

  public void setTemplateDirectory(String directory) {
    templateDirectory=directory;
  }

  public String getTemplateDirectory() {
    return templateDirectory;
  }

  public String TEMPLATE_DIRECTORY() {
    return templateDirectory+"/";
  }

  public void setExampleDirectory(String directory) {
    exampleDirectory=directory;
  }

  public String getExampleDirectory() {
    return exampleDirectory;
  }

  public String EXAMPLE_DIRECTORY() {
    return exampleDirectory+"/";
  }

  public void setJSONDirectory(String directory) {
    jsonDirectory=directory;
  }

  public String getJSONDirectory() {
    return jsonDirectory;
  }

  public String JSON_DIRECTORY() {
    return jsonDirectory+"/";
  }

  public void setImageDirectory(String directory) {
    imageDirectory=directory;
  }

  public String getImageDirectory() {
    return imageDirectory;
  }

  public String IMAGE_DIRECTORY() {
    return imageDirectory+"/";
  }

  public void setLocalOutputDirectory(String directory) {
    localOutputDirectory=directory;
  }

  public String getLocalOutputDirectory() {
    return localOutputDirectory;
  }

  public String LOCAL_OUTPUT_DIRECTORY() {
    return localOutputDirectory+"/";
  }

  public void seOutputDirectory(String directory) {
    outputDirectory=directory;
  }

  public String geOutputDirectory() {
    return outputDirectory;
  }

  public String OUTPUT_DIRECTORY() {
    return outputDirectory+"/";
  }

  public boolean isPackage(Element element) {
    return element.getKind()==ElementKind.PACKAGE;
  }

  public boolean isClassOrInterface(Element element) {
    return isClass(element)||isInterface(element);
  }

  public boolean isClass(Element element) {
    return element.getKind()==ElementKind.CLASS;
  }

  public boolean isInterface(Element element) {
    return element.getKind()==ElementKind.INTERFACE;
  }

  public boolean isMethod(Element element) {
    return element.getKind()==ElementKind.METHOD;
  }

  public boolean isConstructor(Element element) {
    return element.getKind()==ElementKind.CONSTRUCTOR;
  }

  public boolean isField(Element element) {
    return element.getKind()==ElementKind.FIELD;
  }

  public PackageElement getContainingPackage(Element element) {
    return elementUtils.getPackageOf(element);
  }

  public TypeElement getContainingClass(Element element) {
    TypeElement containingClass=null;
    if(isClass(element)) {
      containingClass=(TypeElement)element;
    }else if(!isPackage(element)) {
      containingClass=(TypeElement)element.getEnclosingElement();
    }
    return containingClass;
  }

  public boolean isRootLevel(Element element) {
    if(isClassOrInterface(element)) {
      TypeElement typeElement=(TypeElement)element;
      return rootClasses.contains(typeElement.getQualifiedName().toString());
    }else {
      Element parent=element.getEnclosingElement();
      if(!isClassOrInterface(parent)) return false;
      TypeElement parentTypeElement=(TypeElement)parent;
      return rootClasses.contains(
        parentTypeElement.getQualifiedName().toString());
    }
  }

  public boolean isCore(Element element) {
    PackageElement packageElement=getContainingPackage(element);
    if(packageElement==null) return false;
    return corePackages.contains(packageElement.getQualifiedName().toString());
  }

  public boolean isWebref(Element element) {
    Map<String,List<String>> elementTags=getTags(element);
    if(elementTags.get(webrefTagName)!=null) {
      return elementTags.get(webrefTagName).size()>0;
    }
    return false;
  }

  public boolean shouldOmit(Element element) {
    Map<String,List<String>> elementTags=getTags(element);

    for(String tag:omitWebTagNames) {
      if(elementTags.get(tag)!=null&&elementTags.get(tag).size()>0) {
        return true;
      }
    }

    // if none found, we should include
    return false;
  }

  public TypeElement getClassElement(String signature) {
    TypeElement classElement=getElementUtils().getTypeElement(signature);
    if(classElement==null) {
      for(String potentialPackage:includedPackages) {
        classElement=getElementUtils().getTypeElement(potentialPackage+"."+signature);

        if(classElement!=null) break;
      }
    }
    return classElement;
  }

  public String getSimpleSyntaxName(ExecutableElement element) {
    String name=element.getSimpleName().toString()+"(";
    boolean addedAParam=false;
    for(VariableElement par:element.getParameters()) {
      String[] a=par.asType().toString().split("[.]");
      if(addedAParam) {
        name+=",";
      }
      name+=a[a.length-1];
      if(!addedAParam) {
        addedAParam=true;
      }
    }
    name+=")";
    return name;
  }

  public Element getElementFromSignature(
    String signature,
    Element baseElement) {
    TypeElement classElement=null;
    Element memberElement=null;

    String baseSignature=signature;
    if(baseSignature.charAt(0)=='#') {
      TypeElement containingClass=getContainingClass(baseElement);
      baseSignature=containingClass.getSimpleName().toString()+baseSignature;
    }

    String[] signatureSections=baseSignature.split("#");
    String classSignature="";
    String memberSignature=signatureSections[signatureSections.length-1].replace(" ","")
      .replace("\n","");

    if(signatureSections.length==1) {
      memberElement=getClassElement(memberSignature);
      return memberElement;
    }

    if(signatureSections.length>1) {
      for(int index=0;index<signatureSections.length-1;index++) {
        if(classSignature.length()>0) {
          classSignature+=".";
        }
        classSignature+=signatureSections[index];
      }
    }

    classElement=getClassElement(classSignature);
    if(classElement==null) return null;

    for(Element subElement:classElement.getEnclosedElements()) {
      String nameWithNoSpaces=subElement.toString().replace(" ","");
      if(isField(subElement)) {
        if(nameWithNoSpaces.equals(memberSignature)) {
          memberElement=subElement;
          break;
        }
      }else if(isMethod(subElement)||isConstructor(subElement)) {
        ExecutableElement sub=(ExecutableElement)(subElement);
        String name=getSimpleSyntaxName(sub);
        if(name.equals(memberSignature)) {
          memberElement=subElement;
          break;
        }
      }
    }

    return memberElement;
  }

  public Map<String,List<String>> getTags(Element element) {
    DocCommentTree dcTree=docTreeUtils.getDocCommentTree(element);
    Map<String,List<String>> elementTags=new TreeMap<String,List<String>>();
    new TagScanner(elementTags).visit(dcTree,null);

    return elementTags;
  }

  public List<ParamTree> getElementParamTags(Element element) {
    List<ParamTree> params=new ArrayList<ParamTree>();

    DocCommentTree dcTree=docTreeUtils.getDocCommentTree(element);
    if(dcTree==null) return params;

    for(DocTree d:dcTree.getBlockTags()) {
      if(d.getKind()==DocTree.Kind.PARAM) {
        params.add((ParamTree)d);
      }
    }

    return params;
  }

  public List<SeeTree> getElementSeeTags(Element element) {
    List<SeeTree> sees=new ArrayList<SeeTree>();

    DocCommentTree dcTree=docTreeUtils.getDocCommentTree(element);
    if(dcTree==null) return sees;

    for(DocTree d:dcTree.getBlockTags()) {
      if(d.getKind()==DocTree.Kind.SEE) {
        sees.add((SeeTree)d);
      }
    }

    return sees;
  }

  public String flatTextContent(List<? extends DocTree> content) {
    String s="";
    for(DocTree d:content) {
      s+=d.toString();
    }
    return s;
  }

  public String getCommentFullBodyText(Element element) {
    DocCommentTree commentTree=getDocTreeUtils().getDocCommentTree(element);
    if(commentTree==null) return "";
    return flatTextContent(commentTree.getFullBody());
  }

  public void setNoisy(boolean b) {
    noisy=b;
  }

  public boolean isNoisy() {
    return noisy;
  }

  public void createOutputDirectory(String dir) {
    File f=new File(LOCAL_OUTPUT_DIRECTORY()+dir);
    f.mkdirs();

    f=new File(OUTPUT_DIRECTORY()+dir);
    f.mkdirs();

    System.out.println("\n=== Created output directory: "+dir+"===");
  }

  public void createBaseDirectories() {
    File f=new File(LOCAL_OUTPUT_DIRECTORY());
    f.mkdirs();

    f=new File(OUTPUT_DIRECTORY());
    f.mkdirs();
    System.out.println("\n=== Created base directories! ===");
  }

  /**
   * A visitor to gather the block tags found in a comment.
   */
  class TagScanner extends SimpleDocTreeVisitor<Void,Void>{

    private final Map<String,List<String>> tags;

    TagScanner(Map<String,List<String>> tags) {
      this.tags=tags;
    }

    @Override
    public Void visitDocComment(DocCommentTree tree,Void p) {
      return visit(tree.getBlockTags(),null);
    }

    @Override
    public Void visitUnknownBlockTag(UnknownBlockTagTree tree,Void p) {
      String name=tree.getTagName();
      String content=flatTextContent(tree.getContent());
      tags.computeIfAbsent(name,n->new ArrayList<>()).add(content);
      return null;
    }
  }
}
