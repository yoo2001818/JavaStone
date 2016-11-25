package kr.kkiro.javastone.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kr.kkiro.javastone.game.card.Card.Rarity;
import kr.kkiro.javastone.game.card.MinionCard;
import kr.kkiro.javastone.game.card.SpellCard;
import kr.kkiro.javastone.game.card.TargetCard;

public class Session {
  protected List<Player> players = new ArrayList<Player>();
  protected int turnId = 0;
  protected int seqId = 0;
  
  public Session() {
    // TODO Bootstrap the game session
    for (int i = 0; i < 2; ++i) {
      Deck deck = new Deck();
      deck.push(new MinionCard("/res/mallocHunter.png", "멀록 바다사냥꾼", "그런거 없다", Rarity.BASIC, 2, 1, 2, false, false));
      deck.push(new MinionCard("/res/gcHunter.png", "malloc GC사냥꾼", "그런거 없다", Rarity.BASIC, 2, 2, 1, false, false));
      deck.push(new MinionCard("/res/codingGolem.png", "코딩하는 골렘", "전투의 함성: 커피 2잔을 소환합니다.", Rarity.BASIC, 4, 2, 1, false, false));
      deck.push(new MinionCard("/res/tauntGorani.png", "도발하는 고라니", "도발", Rarity.BASIC, 4, 4, 3, true, false));
      deck.push(new MinionCard("/res/hurtWolf.png", "아픈 늑대", "돌진", Rarity.BASIC, 4, 1, 3, false, true));
      deck.push(new SpellCard("/res/explosion.png", "디콘 폭발", "자신의 영웅에게 30의 데미지를 입힙니다.", Rarity.LEGENDARY, 10, false) {
        @Override
        public void run(Session session, Player player) {
          session.nextSeqId();
          player.hero.setInteractSeq();
          player.hero.damage(30);
        }
      });
      deck.push(new TargetCard("/res/arrow.png", "신비한 화살", "선택한 하수인에게 6의 데미지를 입힙니다.", Rarity.BASIC, 3, TargetCard.SELF_MINION | TargetCard.OTHER_MINION, false) {
        @Override
        public void runTarget(Session session, Player player, Damageable target) {
          session.nextSeqId();
          target.setInteractSeq(session.getSeqId());
          target.damage(6);
        }
      });
      Player player = new Player(this, new HeroData(), deck);
      if (i == 0) player.setAI(true);
      players.add(player);
    }
    getCurrentPlayer().nextTurn();
  }
  
  public List<Player> getPlayers() {
    return players;
  }
 
  public Player getCurrentPlayer() {
    return players.get(turnId % players.size());
  }
  
  public Player getOpponent(Player player) {
    for (Player opponent : players) {
      if (player == opponent) continue;
      return opponent;
    }
    return null;
  }
  
  public int getTurnId() {
    return turnId;
  }
  
  public void nextTurn() {
    getCurrentPlayer().endTurn();
    // Clean up dead minions
    for (Player player : players) {
      Iterator<Minion> it = player.getMinions().iterator();
      while (it.hasNext()) {
        Minion minion = it.next();
        if (minion.isDead()) it.remove();
      }
    }
    turnId ++;
    getCurrentPlayer().nextTurn();
  }
  
  public int getSeqId() {
    return seqId;
  }
  
  public void nextSeqId() {
    seqId ++;
  }
}
