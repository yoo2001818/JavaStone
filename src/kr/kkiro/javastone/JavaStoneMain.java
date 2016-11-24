package kr.kkiro.javastone;

import java.awt.Dimension;

import kr.kkiro.javastone.activity.TitleActivity;
import kr.kkiro.javastone.res.ResourceBitmap;
import kr.kkiro.javastone.util.app.ActivityApplication;
import kr.kkiro.javastone.util.app.ApplicationApplet;

public class JavaStoneMain extends ApplicationApplet {

  private static final long serialVersionUID = -4289250413853697572L;

  public JavaStoneMain() {
    super(new ActivityApplication(60, new TitleActivity()));
    try {
      ResourceBitmap.load();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    // Create applet and launch frame
    final JavaStoneMain applet = new JavaStoneMain();
    applet.launchFrame("JavaStone", new Dimension(800, 600));
  }

}
