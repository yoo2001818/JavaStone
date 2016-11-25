package kr.kkiro.javastone.game;

import kr.kkiro.javastone.game.card.Card;

public class Hero implements Damageable {
  
  protected Player player;
  protected int health;
  protected HeroData data;
  protected int interactSeq = -1;
  
  public Hero(Player player, HeroData data) {
    this.player = player;
    this.data = data;
    // What an amazing hardcoded variable
    this.health = 30;
  }
  
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

  public String getName() {
    return data.getName();
  }

  public Card getHeroSpell() {
    return data.getHeroSpell();
  }

  public String getStart() {
    return data.getStart();
  }

  public String getStartAgainst() {
    return data.getStartAgainst();
  }

  public String getSuicide() {
    return data.getSuicide();
  }

  public String getThanks() {
    return data.getThanks();
  }

  public String getImpressed() {
    return data.getImpressed();
  }

  public String getOops() {
    return data.getOops();
  }

  public String getWellPlayed() {
    return data.getWellPlayed();
  }

  public String getGreetings() {
    return data.getGreetings();
  }

  public String getThreaten() {
    return data.getThreaten();
  }
  
  public Session getSession() {
    return player.session;
  }
  
  public int getInteractSeq() {
    return interactSeq;
  }
  
  public void setInteractSeq(int interactSeq) {
    this.interactSeq = interactSeq;
  }
  
  public void setInteractSeq() {
    this.setInteractSeq(getPlayer().session.seqId);
  }

}
