package kr.kkiro.javastone.game;

import java.net.URL;

import kr.kkiro.javastone.game.card.MinionCard;

public class Minion implements Damageable {
  
  /**
   * Owner of the minion.
   */
  protected Player player;
  /**
   * Original card that is used to generate the minion.
   */
  protected MinionCard card;
  /**
   * Current health point of the minion.
   */
  protected int health;
  /**
   * Current strength point of the minion.
   */
  protected int strength;
  /**
   * Determines if the minion is ready.
   */
  protected boolean ready;
  /**
   * Marks whether if the minion is taunted (Opponent only can attack taunted minions, if
   * available)
   */
  protected boolean taunt;
  
  protected int interactSeq;
  
  public Minion(Player player, MinionCard card) {
    this.player = player;
    this.card = card;
    this.health = card.getHealth();
    this.strength = card.getStrength();
    this.ready = card.isCharge();
    this.taunt = card.isTaunt();
    // TODO Set up minion code
  }
  
  public Player getPlayer() {
    return player;
  }
  
  public void setPlayer(Player player) {
    this.player = player;
  }
  
  public MinionCard getCard() {
    return card;
  }
  
  public void setCard(MinionCard card) {
    this.card = card;
  }
  
  public URL getIcon() {
    return card.getIcon();
  }
  
  public String getName() {
    return card.getName();
  }

  public String getDescription() {
    return card.getDescription();
  }

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
  
  public boolean damage(Damageable target) {
    this.damage(target.getStrength());
    this.getCard().hit(this, target, getSession(), getPlayer());
    return this.isDead();
  }

  @Override
  public boolean isDead() {
    return health <= 0;
  }
  
  public boolean isTaunt() {
    return taunt;
  }
  
  public void setTaunt(boolean taunt) {
    this.taunt = taunt;
  }
  
  public boolean isReady() {
    return ready;
  }
  
  public void setReady(boolean ready) {
    this.ready = ready;
  }
  
  public Session getSession() {
    return player.session;
  }
  
  public void attack(Damageable target) {
    this.setInteractSeq();
    target.setInteractSeq(this.getInteractSeq());
    this.setReady(false);
    this.getCard().attack(this, target, getSession(), getPlayer());
    this.damage(target);
    if (target instanceof Minion) ((Minion) target).damage(this);
    else target.damage(this.getStrength());
    if (this.isDead()) this.getCard().exit(this, getSession(), getPlayer());
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
