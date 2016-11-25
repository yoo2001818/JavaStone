package kr.kkiro.javastone.game;

public interface Damageable {
  int getHealth();
  int getStrength();
  void addStrength(int strength);
  void addHealth(int health);
  boolean damage(int points);
  boolean isDead();
  int getInteractSeq();
  void setInteractSeq(int id);
  Player getPlayer();
}
