package kr.kkiro.javastone.game;

public class Hero implements IDamageable {
  
  protected Player player;
  protected int health;
  protected String name;
  
  
  public Player getPlayer() {
    return player;
  }
  
  @Override
  public int getHealth() {
    return health;
  }

  @Override
  public int getStrength() {
    return 0;
  }

  @Override
  public void addStrength(int strength) {
    // Do nothing as no weapon is specified
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
