package kr.kkiro.javastone.game.card;

import kr.kkiro.javastone.game.Damageable;
import kr.kkiro.javastone.game.Hero;
import kr.kkiro.javastone.game.Minion;
import kr.kkiro.javastone.game.Player;
import kr.kkiro.javastone.game.Session;

public abstract class TargetCard extends SpellCard {

  public static final int SELF_HERO = 1;
  public static final int SELF_MINION = 2;
  public static final int OTHER_HERO = 4;
  public static final int OTHER_MINION = 8;
  
  protected int targets;
  
  public TargetCard(String icon, String name, String description, Rarity rarity, int cost, int targets, boolean isGood) {
    super(icon, name, description, rarity, cost, isGood);
    this.targets = targets;
    // Ignore isGood for now
  }

  @Override
  public final void run(Session session, Player player) {
    throw new RuntimeException("run() should not be called (no target specified)");
  }
  
  public boolean isSelfHero() {
    return (targets & SELF_HERO) != 0;
  }
  public boolean isSelfMinion() {
    return (targets & SELF_MINION) != 0;
  }
  public boolean isOtherHero() {
    return (targets & OTHER_HERO) != 0;
  }
  public boolean isOtherMinion() {
    return (targets & OTHER_MINION) != 0;
  }
  
  public boolean checkTarget(Session session, Player player, Damageable target) {
    // Check validity
    if ((targets & SELF_HERO) == 0 && target == player.getHero()) {
      return false;
    }
    if ((targets & SELF_MINION) == 0 && target instanceof Minion && target.getPlayer() == player) {
      return false;
    }
    if ((targets & OTHER_HERO) == 0 && target instanceof Hero && target.getPlayer() != player) {
      return false;
    }
    if ((targets & SELF_MINION) == 0 && target instanceof Minion && target.getPlayer() != player) {
      return false;
    }
    return true;
  }
  
  public abstract void runTarget(Session session, Player player, Damageable target);
  

}
