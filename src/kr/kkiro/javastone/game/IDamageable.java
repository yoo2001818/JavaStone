package kr.kkiro.javastone.game;

public interface IDamageable {
  int getHealth();
  int getStrength();
  void addStrength(int strength);
  void addHealth(int health);
  boolean damage(int points);
  boolean isDead();
}
