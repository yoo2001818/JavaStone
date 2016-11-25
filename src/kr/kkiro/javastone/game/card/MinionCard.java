package kr.kkiro.javastone.game.card;

import kr.kkiro.javastone.game.Minion;
import kr.kkiro.javastone.game.Player;
import kr.kkiro.javastone.game.Session;

public class MinionCard extends Card {

  protected int health;
  protected int strength;
  protected boolean taunt;
  protected boolean charge;
  
  public MinionCard(String icon, String name, String description, Rarity rarity, int cost,
      int health, int strength, boolean taunt, boolean charge) {
    super(icon, name, description, rarity, cost);
    this.health = health;
    this.strength = strength;
    this.taunt = taunt;
    this.charge = charge;
  }
  
  public int getHealth() {
    return health;
  }
  
  public int getStrength() {
    return strength;
  }
  
  public boolean isTaunt() {
    return taunt;
  }
  
  public boolean isCharge() {
    return charge;
  }

  @Override
  public void run(Session session, Player player) {
    // Spawn the minion
    Minion minion = new Minion(player, this);
    player.addMinion(minion);
  }

  @Override
  public String toString() {
    String data = super.toString() + "(" + getStrength() + "/" + getHealth() + ")";
    if (taunt) data += "(도발)";
    if (charge) data += "(돌진)";
    return data;
  }
  
}
