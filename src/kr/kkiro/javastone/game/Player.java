package kr.kkiro.javastone.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import kr.kkiro.javastone.game.card.Card;
import kr.kkiro.javastone.game.card.TargetCard;
import kr.kkiro.javastone.util.RandomUtil;

public class Player {
  protected Session session;
  protected Hero hero;
  protected List<Minion> minions = new ArrayList<>();
  protected Deck deck;
  protected Deck drawDeck;
  protected int points = 0;
  protected int maxPoints = 0;
  // Card draw failure accumulator
  protected int drawFailAccum = 0;
  // Initial card disposal marker
  protected boolean drawDisposed = false;
  // Should AI take over?
  protected boolean ai = false;
  
  public Player(Session session, HeroData heroData, Deck drawDeck) {
    this.session = session;
    this.hero = new Hero(this, heroData);
    this.drawDeck = drawDeck;
    // Draw initial 3 cards
    this.deck = new Deck();
    drawDeck.shuffle();
    for (int i = 0; i < 3; ++i) {
      Card card = drawDeck.shift();
      this.deck.push(card);
    }
  }
  
  public Hero getHero() {
    return hero;
  }
  public void setHero(Hero hero) {
    this.hero = hero;
  }
  public List<Minion> getMinions() {
    return minions;
  }
  public void setMinions(List<Minion> minions) {
    this.minions = minions;
  }
  public Deck getDeck() {
    return deck;
  }
  public void setDeck(Deck deck) {
    this.deck = deck;
  }
  public Deck getDrawDeck() {
    return drawDeck;
  }
  public void setDrawDeck(Deck drawDeck) {
    this.drawDeck = drawDeck;
  }
  public int getPoints() {
    return points;
  }
  public void setPoints(int points) {
    this.points = points;
  }
  public int getMaxPoints() {
    return maxPoints;
  }
  public void setMaxPoints(int maxPoints) {
    this.maxPoints = maxPoints;
  }
  public Session getSession() {
    return session;
  }
  public boolean isAI() {
    return ai;
  }
  
  public void setAI(boolean ai) {
    this.ai = ai;
  }
  
  public boolean runAICard(Card card) {
    Random random = RandomUtil.getRandom();
    if (card.getCost() <= points) {
      if (card instanceof TargetCard) {
        TargetCard targetCard = (TargetCard) card;
        // Is it good?
        if (targetCard.isGood()) {
          if (targetCard.isSelfMinion()) {
            // Select random minion from me
            List<Minion> minions = this.getMinions();
            if (minions.size() > 0) {
              Minion minion = minions.get(random.nextInt(minions.size()));
              // Tada
              this.useCard(targetCard, minion);
              return false;
            }
          }
          if (targetCard.isSelfHero()) {
            this.useCard(targetCard, this.hero);
            return false;
          }
        } else {
          if (targetCard.isOtherMinion()) {
            // Select random minion from opponent
            List<Minion> minions = session.getOpponent(this).getMinions();
            if (minions.size() > 0) {
              Minion minion = minions.get(random.nextInt(minions.size()));
              // Tada
              this.useCard(targetCard, minion);
              return false;
            }
          }
          if (targetCard.isOtherHero()) {
            this.useCard(targetCard, session.getOpponent(this).hero);
            return false;
          }
        }
      } else {
        this.useCard(card);
        return false;
      }
    }
    return true;
  }
  
  public boolean runAIStep() {
    // Use all cards
    for (int i = 0; i < this.deck.cards.size(); ++i) {
      Card card = this.deck.cards.get(i);
      if (!runAICard(card)) {
        this.deck.cards.remove(i);
        i--;
        return false;
      }
    }
    // Check hero ability
    if (hero.isAbilityEnabled()) {
      if (!runAICard(hero.getAbility().getCard())) {
        hero.setAbilityEnabled(false);
        return false;
      }
    }
    // Attack all minions
    for (Minion minion : getMinions()) {
      if (minion.isReady()) {
        // Get opponent's target
        Damageable target = session.getOpponent(this).getTarget();
        if (target != null) {
          session.nextSeqId();
          minion.attack(target);
          return false;
        }
      }
    }
    // We're done
    return true;
  }
  
  public boolean hasTaunt() {
    for (Minion minion : getMinions()) {
      if (minion.isTaunt() && !minion.isDead()) return true;
    }
    return false;
  }
  
  public Damageable getTarget() {
    // Scan for taunted minion first
    for (Minion minion : getMinions()) {
      if (minion.isDead()) continue;
      if (minion.isTaunt()) return minion;
    }
    // Then regular minion
    for (Minion minion : getMinions()) {
      if (minion.isDead()) continue;
      return minion;
    }
    // Then hero
    return hero;
  }
  
  public void drawCard() {
    if (getDrawDeck().isEmpty()) {
      drawFailAccum ++;
      getHero().damage(drawFailAccum);
      return;
    } else {
      Card card = getDrawDeck().shift();
      getDeck().push(card);
    }
  }
  
  public void endTurn() {
  }
  
  public void nextTurn() {
    session.nextSeqId();
    // Increment points (This behavior is unique to turn event, so we don't need additional
    // action for this)
    if (maxPoints < 10) maxPoints += 1;
    points = maxPoints;
    // Draw one card (Or damage the player)
    drawCard();
    // Prepare all minions
    for (Minion minion : getMinions()) {
      minion.setReady(true);
      minion.getCard().turnStart(minion, session, this);
    }
    hero.abilityEnabled = true;
  }
  
  public void addMinion(Minion minion) {
    session.nextSeqId();
    minion.setInteractSeq();
    minions.add(minion);
    minion.getCard().enter(minion, session, this);
  }
  
  public void useCard(Card card) {
    if (card.getCost() > points) return;
    session.nextSeqId();
    points -= card.getCost();
    // Card should be removed from hand in advance
    card.run(session, this);
  }
  
  public void useCard(TargetCard card, Damageable target) {
    if (card.getCost() > points) return;
    session.nextSeqId();
    points -= card.getCost();
    // Card should be removed from hand in advance
    card.runTarget(session, this, target);
  }
}
