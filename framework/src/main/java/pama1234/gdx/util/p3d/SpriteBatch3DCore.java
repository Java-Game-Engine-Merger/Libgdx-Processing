package pama1234.gdx.util.p3d;

import static com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute.Diffuse;
import static pama1234.gdx.util.ShaderUtil.createDefaultShader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Mesh.VertexDataType;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.FlushablePool;

public abstract class SpriteBatch3DCore extends ModelBatch implements Batch{
  public static class RenderablePool extends FlushablePool<Renderable>{
    @Override
    protected Renderable newObject() {
      return new Renderable();
    }

    @Override
    public Renderable obtain() {
      Renderable renderable=super.obtain();
      renderable.environment=null;
      renderable.material=null;
      renderable.meshPart.set("",null,0,0,0);
      renderable.shader=null;
      renderable.userData=null;
      return renderable;
    }
  }

  public static class MeshPool extends FlushablePool<Mesh>{
    public static final float[] VERTICES_EMPTY = new float[0];
    public static final short[] INDICES_EMPTY = new short[0];
    //    public VertexAttributes attr=MeshBuilder.createAttributes(Usage.Position|Usage.Normal);
    public VertexAttributes attr=new VertexAttributes(
      new VertexAttribute(Usage.Position,3,ShaderProgram.POSITION_ATTRIBUTE),
      //      new VertexAttribute(Usage.ColorPacked,4,ShaderProgram.COLOR_ATTRIBUTE),
      new VertexAttribute(Usage.TextureCoordinates,2,ShaderProgram.TEXCOORD_ATTRIBUTE+"0"));
    @Override
    protected Mesh newObject() {
      // TODO find exact maxVertices num 6 or 20(SPRITE_SIZE)
      return new Mesh(false,SPRITE_SIZE,SPRITE_SIZE,attr);
    }

    @Override
    public Mesh obtain() {
      Mesh m=super.obtain();
      m.setVertices(VERTICES_EMPTY);
      m.setIndices(INDICES_EMPTY);
      return m;
    }
  }

  public static final int VERTEX_SIZE=2+1+2;
  public static final int SPRITE_SIZE=4*VERTEX_SIZE;

  public ModelBuilder modelBuilder;

  public final RenderablePool renderablesPool=new RenderablePool();
  public final MeshPool meshPool=new MeshPool();
  public Camera usedCamera;
  //  public Material defaultMaterial=new Material(new ColorAttribute(Diffuse,Color.WHITE));

  //  public Mesh mesh;
  public final float[] vertices;
  public int idx=0;
  public Texture lastTexture=null;
  public float invTexWidth=0,invTexHeight=0;

  //  public Color tint;
  public final Color color=new Color(1,1,1,1);
  public float colorPacked=Color.WHITE_FLOAT_BITS;

  public boolean blendingDisabled=false;
  public int blendSrcFunc=GL20.GL_SRC_ALPHA;
  public int blendDstFunc=GL20.GL_ONE_MINUS_SRC_ALPHA;
  public int blendSrcFuncAlpha=GL20.GL_SRC_ALPHA;
  public int blendDstFuncAlpha=GL20.GL_ONE_MINUS_SRC_ALPHA;

  public Matrix4 projectionMatrix=new Matrix4();
  public Matrix4 transformMatrix=new Matrix4();
  public Matrix4 combinedMatrix=new Matrix4();

  public ShaderProgram shader=createDefaultShader();
  public ShaderProgram customShader=null;
  public boolean ownsShader;

  public boolean drawing;

  /// DEBUG

  /** Number of render calls since the last {@link #begin()}. **/
  public int renderCalls=0;
  /** Number of rendering calls, ever. Will not be reset unless set manually. **/
  public int totalRenderCalls=0;
  /** The maximum number of sprites rendered in one batch so far. **/
  public int maxSpritesInBatch=0;

  public SpriteBatch3DCore() {
    this(1000);
  }

  public SpriteBatch3DCore(int verticesArraySize) {
    super();
    vertices=new float[verticesArraySize*SPRITE_SIZE];

    if(verticesArraySize>8191) throw new IllegalArgumentException("Can't have more than 8191 sprites per batch: "+verticesArraySize);

    VertexDataType vertexDataType=(Gdx.gl30!=null)?VertexDataType.VertexBufferObjectWithVAO:VertexDataType.VertexArray;

    //    mesh=new Mesh(vertexDataType,false,verticesArraySize*4,verticesArraySize*6,
    //      new VertexAttribute(Usage.Position,3,ShaderProgram.POSITION_ATTRIBUTE),
    //      //      new VertexAttribute(Usage.ColorPacked,4,ShaderProgram.COLOR_ATTRIBUTE),
    //      new VertexAttribute(Usage.TextureCoordinates,2,ShaderProgram.TEXCOORD_ATTRIBUTE+"0"));

    modelBuilder=new ModelBuilder();
  }

  @Override
  public void flush() {
    super.flush();
    renderablesPool.flush();
    meshPool.flush();
  }

  /**
   * 每当绘制完一个图形，进行假的flush操作，将需要绘制的图形录入ModelBatch
   */
  //    public float[] tf=new float[1600000];
  public short[] indices=new short[] {0,1,2,2,3,0};
  public void softFlush() {
    setupMatrices();
    if(idx==0) return;

    renderCalls++;
    totalRenderCalls++;
    int spritesInBatch=idx/SPRITE_SIZE;
    if(spritesInBatch>maxSpritesInBatch) maxSpritesInBatch=spritesInBatch;
    int count=spritesInBatch*6;

    lastTexture.bind();
    //    Mesh mesh=this.mesh;
    //    mesh.setVertices(vertices,0,idx);
    //    Buffer indicesBuffer=(Buffer)mesh.getIndicesBuffer(true);
    //    indicesBuffer.position(0);
    //    indicesBuffer.limit(count);

    if(blendingDisabled) {
      Gdx.gl.glDisable(GL20.GL_BLEND);
    }else {
      Gdx.gl.glEnable(GL20.GL_BLEND);
      if(blendSrcFunc!=-1) Gdx.gl.glBlendFuncSeparate(blendSrcFunc,blendDstFunc,blendSrcFuncAlpha,blendDstFuncAlpha);
    }

    //    mesh.render(customShader!=null?customShader:shader,GL20.GL_TRIANGLES,0,count);
    //    System.out.println(Arrays.toString(mesh.getIndicesBuffer(false).array()));
    //    return;

    //    System.out.println("idx: "+idx+" "+idx/SPRITE_SIZE+" "+spritesInBatch);

    for(int i=0;i<idx;i+=SPRITE_SIZE) {
      Renderable r=renderablesPool.obtain();

      r.worldTransform.set(transformMatrix);
      //      r.worldTransform.set(combinedMatrix);
      //      r.worldTransform.set(projectionMatrix).mul(transformMatrix);
      //      r.material=defaultMaterial;
      r.material=new Material(
        new BlendingAttribute(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA),
        IntAttribute.createCullFace(GL20.GL_NONE),

        new ColorAttribute(Diffuse,color),
        TextureAttribute.createDiffuse(lastTexture));
      //            r.material=new Material();

      Mesh m=meshPool.obtain();
      //      Mesh m=mesh;
      //            int k=0;
      //            for(int j=0;j<vertices.length;j++) {
      //              tf[k]=vertices[j];
      //              k++;
      //              if(j%5==1) {
      //                tf[k+1]=0;
      //                k++;
      //              }
      //            }
      m.setVertices(vertices,i,SPRITE_SIZE);
      //      System.out.println(Arrays.toString(m.getVertices(new float[20])));
      m.setIndices(indices);
      //      m.render(1,1);
      //      m.setVertices(tf,i,SPRITE_SIZE);

      //      Buffer buf=(Buffer)m.getIndicesBuffer(true);
      //      buf.position(0);
      //      buf.limit(6);

      r.meshPart.set("triangle "+i/SPRITE_SIZE,m,0,SPRITE_SIZE,GL20.GL_TRIANGLES);

      render(r);
      //      modelBuilder.begin();
      //      //      modelBuilder.part(r.meshPart,r.material);
      //      //      modelBuilder.createRect();
      //      float[] fa=new float[12];
      ////      System.arraycopy(vertices,i,fa,0,SPRITE_SIZE);
      //      System.arraycopy(tf,i,fa,0,12);
      //      modelBuilder.part("1",GL20.GL_TRIANGLES,VertexAttributes.Usage.Position,new Material(
      //        new ColorAttribute(Diffuse,color),
      //        TextureAttribute.createDiffuse(lastTexture))).addMesh(fa,null);
      //      //      modelBuilder.part("triangle "+i/SPRITE_SIZE,m,GL20.GL_TRIANGLES,new Material(
      //      //        new ColorAttribute(Diffuse,color),
      //      //        TextureAttribute.createDiffuse(lastTexture)));
      //      var model=modelBuilder.end();
      //      var modelInstance=new ModelInstance(model);
      //      modelInstance.transform=transformMatrix;
      //      //      r.meshPart.set("triangle "+i/SPRITE_SIZE,mesh,i,20,GL20.GL_TRIANGLES);
      //
      //      //      r.shader=shaderProvider.getShader(r);
      //      //      System.out.println(shaderProvider.getShader(r));
      //      render(modelInstance);
    }

    idx=0;
  }

  @Override
  public void begin() {
    if(drawing) throw new IllegalStateException("SpriteBatch.end must be called before begin.");
    renderCalls=0;

    //    Gdx.gl.glDepthMask(false);
    Gdx.gl.glDepthMask(true);
    if(customShader!=null) customShader.bind();
    else shader.bind();
    setupMatrices();

    drawing=true;

    super.begin(usedCamera);
  }

  @Override
  public void end() {
    if(!drawing) throw new IllegalStateException("SpriteBatch.begin must be called before end.");
    if(idx>0) {
      softFlush();
      //      flush();
    }
    lastTexture=null;
    drawing=false;

    GL20 gl=Gdx.gl;
    //    gl.glDepthMask(true);
    if(isBlendingEnabled()) gl.glDisable(GL20.GL_BLEND);

    super.end();
  }

  @Override
  public void setColor(Color tint) {
    color.set(tint);
    colorPacked=tint.toFloatBits();
  }

  @Override
  public void setColor(float r,float g,float b,float a) {
    color.set(r,g,b,a);
    colorPacked=color.toFloatBits();
  }

  @Override
  public Color getColor() {
    return color;
  }

  @Override
  public void setPackedColor(float packedColor) {
    Color.abgr8888ToColor(color,packedColor);
    this.colorPacked=packedColor;
  }

  @Override
  public float getPackedColor() {
    return colorPacked;
  }

  @Override
  public void disableBlending() {
    if(blendingDisabled) return;
    softFlush();
    blendingDisabled=true;
  }

  @Override
  public void enableBlending() {
    if(!blendingDisabled) return;
    softFlush();
    blendingDisabled=false;
  }

  @Override
  public void setBlendFunction(int srcFunc,int dstFunc) {
    setBlendFunctionSeparate(srcFunc,dstFunc,srcFunc,dstFunc);
  }

  @Override
  public void setBlendFunctionSeparate(int srcFuncColor,int dstFuncColor,int srcFuncAlpha,int dstFuncAlpha) {
    if(blendSrcFunc==srcFuncColor&&blendDstFunc==dstFuncColor&&blendSrcFuncAlpha==srcFuncAlpha
      &&blendDstFuncAlpha==dstFuncAlpha) return;
    softFlush();
    blendSrcFunc=srcFuncColor;
    blendDstFunc=dstFuncColor;
    blendSrcFuncAlpha=srcFuncAlpha;
    blendDstFuncAlpha=dstFuncAlpha;
  }

  @Override
  public void setProjectionMatrix(Matrix4 projection) {
    projectionMatrix.set(projection);
    if(drawing) setupMatrices();
  }

  @Override
  public void setTransformMatrix(Matrix4 transform) {
    transformMatrix.set(transform);
    if(drawing) setupMatrices();
  }

  public void setupMatrices() {
    combinedMatrix.set(projectionMatrix).mul(transformMatrix);
    //    if(customShader!=null) {
    //      customShader.setUniformMatrix("u_projTrans",combinedMatrix);
    //      customShader.setUniformi("u_texture",0);
    //    }else {
    //      shader.setUniformMatrix("u_projTrans",combinedMatrix);
    //      shader.setUniformi("u_texture",0);
    //    }
  }

  @Override
  public void setShader(ShaderProgram shader) {
    if(shader==customShader) // avoid unnecessary flushing in case we are drawing
      return;
    if(drawing) {
      softFlush();
    }
    customShader=shader;
    if(drawing) {
      if(customShader!=null) customShader.bind();
      else this.shader.bind();
      setupMatrices();
    }
  }

  @Override
  public ShaderProgram getShader() {
    if(customShader==null) {
      return shader;
    }
    return customShader;
  }

  @Override
  public boolean isBlendingEnabled() {
    return !blendingDisabled;
  }

  @Override
  public boolean isDrawing() {
    return drawing;
  }

  @Override
  public int getBlendSrcFunc() {
    return blendSrcFunc;
  }

  @Override
  public int getBlendDstFunc() {
    return blendDstFunc;
  }

  @Override
  public int getBlendSrcFuncAlpha() {
    return blendSrcFuncAlpha;
  }

  @Override
  public int getBlendDstFuncAlpha() {
    return blendDstFuncAlpha;
  }

  @Override
  public Matrix4 getProjectionMatrix() {
    return projectionMatrix;
  }

  @Override
  public Matrix4 getTransformMatrix() {
    return transformMatrix;
  }
}
