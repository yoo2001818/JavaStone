package kr.kkiro.javastone.util.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JApplet;
import javax.swing.JFrame;

public abstract class ApplicationApplet extends JApplet {

  private static final long serialVersionUID = -5813856680334396632L;
  
  private Application application;
  
  public ApplicationApplet(Application application) {
    this.application = application;
  }

  public Application getApplication() {
    return application;
  }
  
  @Override
  public void init() {
    init(this.getSize());
    /*try {
      UIManager.setLookAndFeel(
          UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
    }*/
  }
  
  public void init(Dimension d) {
    this.setLayout(new BorderLayout());
    this.add(application, BorderLayout.CENTER);
    application.resize(d);
    /*try {
      UIManager.setLookAndFeel(
          UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
    }*/
  }
  
  @Override
  public void start() {
    application.unpause();
  }
  
  @Override
  public void stop() {
    application.pause();
  }

  @Override
  public void destroy() {
    application.stop();
  }
  
  @Override
  public void resize(int width, int height) {
    application.resize(width, height);
  }
  
  public void launchFrame(String name, Dimension size) {
    final JFrame frame = new JFrame(name);
    frame.setBackground(Color.BLACK);
    //frame.setResizable(false);
    // Add game panel
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(this, BorderLayout.CENTER);
    
    // Initialize game with default size
    this.init(size);
    // Pack the frame to preferred size
    frame.pack();
    frame.validate();
    frame.doLayout();
    // Start the game
    this.start();

    // Set frame position to center
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    // Call resize event if the window is resized
    frame.addComponentListener(new ComponentAdapter() {
      public void componentResized(ComponentEvent e) {
        ApplicationApplet.this.resize(frame.getContentPane().getWidth(), frame.getContentPane().getHeight());
      }
    });
    // Stop the game if the window is closed
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e)
      {
        ApplicationApplet.this.stop();
        ApplicationApplet.this.destroy();
        System.exit(0);
      }
    });
  }
  
  
}
