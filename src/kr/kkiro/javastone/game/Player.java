package kr.kkiro.javastone.game;

import java.util.ArrayList;
import java.util.List;

public class Player {
  protected Hero hero;
  protected List<Minion> minions = new ArrayList<>();
  protected Deck deck;
  protected Deck drawDeck;
  protected int points;
  protected int maxPoints;
}
