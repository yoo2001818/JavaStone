package kr.kkiro.javastone.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kr.kkiro.javastone.game.card.Card;
import kr.kkiro.javastone.game.card.Card.Rarity;
import kr.kkiro.javastone.util.RandomUtil;
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
      Card[] cards = new Card[] {
        new MinionCard("/res/ramHog.png", "malloc 사냥꾼", "메모리를 훔치기 위해 열심히 나서는 우리 친구 malloc입니다.",
            Rarity.BASIC, 2, 2, 1, false, false),
        new MinionCard("/res/keyboardCat.png", "키보드 치는 고양이", "판사님 고양이가 썼습니다.",
            Rarity.BASIC, 2, 2, 3, false, false),
        new MinionCard("/res/redFox.png", "붉은 여우", "왜 지구를 품고 있는 걸까요?",
            Rarity.BASIC, 2, 3, 2, false, false),
        new MinionCard("/res/newbieDeveloper.png", "풋내기 개발자", "전투의 함성: 카드를 1장 뽑습니다.\n일하느라 일주일째 잠을 못자고 있는 풋내기 개발자입니다. 좀 쉬었으면 좋겠네요.",
            Rarity.BASIC, 2, 1, 1, false, false) {
          @Override
          public void enter(Minion minion, Session session, Player player) {
            player.drawCard();
          }
        },
        new MinionCard("/res/hackingMaster.png", "해킹대장", "전투의 함성: 내 하수인들이 공격력을 +1 얻습니다.\n해킹으로 다른 하수인들을 더 강하게 만들어 준다고 합니다. 벌레를 잘 가공한다는 소문이 있습니다.",
            Rarity.BASIC, 3, 2, 2, false, false) {
          @Override
          public void enter(Minion minion, Session session, Player player) {
            for (Minion m : player.getMinions()) {
              m.addStrength(1);
            }
          }
        },
        new MinionCard("/res/hurtWolf.png", "아픈 늑대", "돌진\n오늘 속이 좀 안좋다네요.",
            Rarity.BASIC, 3, 3, 1, false, true),
        new MinionCard("/res/tauntGorani.png", "도발하는 고라니", "도발\n계속 도로 위에 서있으면 로드킬당할지도 모르는데 가만히 서 있네요.",
            Rarity.BASIC, 4, 3, 5, true, false),
        new MinionCard("/res/gcHunter.png", "방어하는 청소부", "메모리가 더럽다고 열심히 청소하면서 철벽을 치는 청소부입니다.",
            Rarity.BASIC, 4, 2, 7, false, false),
        new MinionCard("/res/grimnull.png", "널포인터", "전투의 함성: 적 영웅에게 피해를 3 줍니다.\n이런! NullPointerException을 조심하세요.",
            Rarity.BASIC, 5, 4, 4, false, false) {
          @Override
          public void enter(Minion minion, Session session, Player player) {
            session.getOpponent(player).getHero().damage(3);
          }
        },
        new MinionCard("/res/codingGolem.png", "코딩하는 오우거", "근데 브라우저 켜놓고 딴짓이나 하고 있네요.",
            Rarity.BASIC, 6, 6, 7, false, false),
        new SpellCard("/res/arrow.png", "신비한 화살", "모든 적에게 3의 피해를 무작위로 나누어 입힙니다.", Rarity.BASIC, 1, true) {
          @Override
          public void run(Session session, Player player) {
            // Get opponent list
            List<Damageable> list = new ArrayList<>();
            for (Minion minion : session.getOpponent(player).minions) {
              list.add(minion);
            }
            list.add(session.getOpponent(player).getHero());
            session.nextSeqId();
            for (int i = 0; i < 3; ++i) {
              // Remove dead one
              Iterator<Damageable> it = list.iterator();
              while (it.hasNext()) {
                Damageable mob = it.next();
                if (mob.isDead()) it.remove();
              }
              // Then get random one
              Damageable target = list.get(RandomUtil.getRandom().nextInt(list.size()));
              target.setInteractSeq(session.getSeqId());
              target.damage(1);
            }
          }
        },
        new SpellCard("/res/explosion.png", "신비한 폭발", "모든 적 하수인에게 피해를 1 줍니다.", Rarity.BASIC, 2, true) {
          @Override
          public void run(Session session, Player player) {
            session.nextSeqId();
            for (Minion minion : session.getOpponent(player).minions) {
              minion.damage(1);
              minion.setInteractSeq(session.getSeqId());
            }
          }
        },
        new SpellCard("/res/knowledge.png", "신비한 지능", "카드를 2장 뽑습니다.", Rarity.BASIC, 3, true) {
          @Override
          public void run(Session session, Player player) {
            player.drawCard();
            player.drawCard();
          }
        },
        new TargetCard("/res/transform.png", "변이", "하수인 하나를 양 (1/1)으로 변신시킵니다.", Rarity.BASIC, 4, TargetCard.SELF_MINION | TargetCard.OTHER_MINION, false) {
          @Override
          public void runTarget(Session session, Player player, Damageable target) {
            Minion minion = (Minion) target;
            session.nextSeqId();
            minion.setInteractSeq();
            minion.setCard(new MinionCard("/res/sheep.png", "양", "메에에....", Rarity.BASIC, 0, 1, 1, false, false));
            minion.health = 1;
            minion.strength = 1;
          }
        },
        new TargetCard("/res/fire.png", "화염구", "피해를 6 줍니다.", Rarity.BASIC, 4, 15, false) {
          @Override
          public void runTarget(Session session, Player player, Damageable target) {
            session.nextSeqId();
            target.setInteractSeq(session.getSeqId());
            target.damage(6);
          }
        }
      };
      for (Card card : cards) {
        deck.push(card);
        deck.push(card);
      }
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
