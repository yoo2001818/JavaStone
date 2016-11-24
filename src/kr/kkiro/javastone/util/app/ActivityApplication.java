package kr.kkiro.javastone.util.app;

import java.awt.BorderLayout;

import kr.kkiro.javastone.activity.Activity;

public class ActivityApplication extends Application {

  private static final long serialVersionUID = 5230851394915229537L;
  private Activity startActivity;
  private Activity activity; 
  public ActivityApplication(int fps, Activity startActivity) {
    super(fps);
    this.setStartActivity(startActivity);
    this.setLayout(new BorderLayout());
  }
  
  public Activity getStartActivity() {
    return startActivity;
  }
  
  public void setStartActivity(Activity startActivity) {
    this.startActivity = startActivity;
  }
  
  public Activity getActivity() {
    return activity;
  }
  
  public void setActivity(Activity activity) {
    if (this.activity != null) {
      this.remove(this.activity);
      this.activity.destroy();
      this.activity.setApplication(null);
    }
    this.activity = activity;
    activity.setApplication(this);
    this.add(activity, BorderLayout.CENTER);
    this.revalidate();
    activity.init();
  }

  @Override
  public void init() {
    setActivity(this.startActivity);
  }

  @Override
  public void tick() {
    this.activity.tick();
  }
  
  @Override
  public void render() {
    this.activity.render();
  }

}
