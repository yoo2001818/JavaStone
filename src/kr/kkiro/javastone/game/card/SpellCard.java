package kr.kkiro.javastone.game.card;

public abstract class SpellCard extends Card {

  protected boolean good;
  
  public SpellCard(String icon, String name, String description, Rarity rarity, int cost, boolean good) {
    super(icon, name, description, rarity, cost);
    this.good = good;
  }
  
  public boolean isGood() {
    return good;
  }

}
