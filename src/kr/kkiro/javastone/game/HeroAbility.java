package kr.kkiro.javastone.game;

import kr.kkiro.javastone.game.card.Card;

public class HeroAbility {

  protected Card card;
  protected Hero hero;
  
  public HeroAbility(Card card, Hero hero) {
    this.card = card;
    this.hero = hero;
  }
  
  public Card getCard() {
    return card;
  }
  public void setCard(Card card) {
    this.card = card;
  }
  public Hero getHero() {
    return hero;
  }
  public void setHero(Hero hero) {
    this.hero = hero;
  }

}
