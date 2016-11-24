package kr.kkiro.javastone.game;

public class Minion implements IDamageable {
  
  // Owner
  protected Player player;
  // Original card
  protected Card card;
  protected int health;
  protected int strength;
  
  @Override
  public int getHealth() {
    return health;
  }

  @Override
  public int getStrength() {
    return strength;
  }

  @Override
  public void addStrength(int strength) {
    this.strength += strength;
  }

  @Override
  public void addHealth(int health) {
    this.health += health;
  }

  @Override
  public boolean damage(int points) {
    this.health -= points;
    return this.isDead();
  }

  @Override
  public boolean isDead() {
    return health <= 0;
  }

}
