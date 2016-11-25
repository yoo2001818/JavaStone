package kr.kkiro.javastone.game.card;

import java.net.URL;

import kr.kkiro.javastone.game.Player;
import kr.kkiro.javastone.game.Session;

public abstract class Card {
  
  public static enum Rarity {
    BASIC, COMMON, RARE, EPIC, LEGENDARY;
  }
  
  protected URL icon;
  protected String name;
  protected String description;
  protected Rarity rarity;
  protected int cost;
  
  public Card(String icon, String name, String description, Rarity rarity, int cost) {
    this.icon = Card.class.getResource(icon);
    this.name = name;
    this.description = description;
    this.rarity = rarity;
    this.cost = cost;
  }

  public URL getIcon() {
    return icon;
  }
  
  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Rarity getRarity() {
    return rarity;
  }

  public int getCost() {
    return cost;
  }
  
  public abstract void run(Session session, Player player);
  
  @Override
  public String toString() {
    return name + " (" + cost + ")";
  }
}
