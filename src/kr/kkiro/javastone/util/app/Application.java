package kr.kkiro.javastone.util.app;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public abstract class Application extends JPanel {

  private static final long serialVersionUID = -682263728978166326L;

  private int width, height;
  private int fps;
  private int tickId;
  private boolean running, paused;
  private Timer timer;
  private int elapsedTime = 1000;
  private long previousTime;
  
  public Application(int fps) {
    this.fps = fps;
  }
  
  public int getWidth() {
    return this.width;
  }
  
  public int getHeight() {
    return this.height;
  }
  
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(width, height);
  }
  
  public void resize(Dimension d) {
    this.width = d.width;
    this.height = d.height;
    this.setPreferredSize(d);
    this.setMinimumSize(d);
    this.setMaximumSize(d);
  }
  
  public final void resize(int width, int height) {
    this.resize(new Dimension(width, height));
  }
  
  public void start() {
    if (running == false) {
      timer = new Timer(1000 / fps, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          long currentTime = System.currentTimeMillis();
          elapsedTime = (int)(currentTime - previousTime);
          
          tickId ++;
          
          tick();
          render();
          
          previousTime = System.currentTimeMillis();
        }
      });
      
      timer.setRepeats(true);
      timer.setCoalesce(true);
      
      init();
    }
    timer.start();
    running = true;
    this.tickId = 0;
  }
  
  public void stop() {
    running = false;
    timer.stop();
  }

  public void pause() {
    paused = true;
    timer.stop();
  }
  
  public void unpause() {
    paused = false;
    if (timer == null) {
      start();
    } else {
      timer.start();
    }
  }
  
  public boolean isPaused() {
    return paused;
  }
  
  public boolean isRunning() {
    return running;
  }
  
  public int getDelay() {
    return elapsedTime;
  }
  
  public long getTime() {
    return previousTime;
  }
  
  public float getFPS() {
    return 1000f / elapsedTime;
  }
  
  public int getTickId() {
    return tickId;
  }
  
  public abstract void render();
  public abstract void init();
  public abstract void tick();

}
