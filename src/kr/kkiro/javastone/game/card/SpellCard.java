package kr.kkiro.javastone.game.card;

public abstract class SpellCard extends Card {

  public SpellCard(String icon, String name, String description, Rarity rarity, int cost, boolean isGood) {
    super(icon, name, description, rarity, cost);
  }

}
