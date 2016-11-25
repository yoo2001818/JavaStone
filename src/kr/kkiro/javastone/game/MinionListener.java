package kr.kkiro.javastone.game;

public interface MinionListener {
  public void install();
  // Uninstall is different to dying, as it happens after dying
  public void uninstall();
  public void die();
}
