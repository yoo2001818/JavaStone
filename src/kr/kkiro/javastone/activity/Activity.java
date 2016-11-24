package kr.kkiro.javastone.activity;

import java.awt.Graphics2D;

import kr.kkiro.javastone.util.app.ActivityApplication;
import kr.kkiro.javastone.util.canvas.CanvasContainer;
import kr.kkiro.javastone.util.canvas.IRenderListener;

public abstract class Activity extends CanvasContainer implements IRenderListener {

  private static final long serialVersionUID = -1368302366446559241L;
  
  private ActivityApplication application;

  public Activity() {
    this.setRenderListener(this);
  }
  
  public ActivityApplication getApplication() {
    return application;
  }
  
  public ActivityApplication getApp() {
    return application;
  }
  
  public void setApplication(ActivityApplication application) {
    this.application = application;
  }
  
  public abstract void init();
  public abstract void tick();
  public abstract void render();
  
  @Override
  public void render(Graphics2D g, int width, int height) {
  }
  
  public abstract void destroy();
  
}
