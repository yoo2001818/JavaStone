package kr.kkiro.javastone.activity;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import kr.kkiro.javastone.game.Damageable;
import kr.kkiro.javastone.game.Hero;
import kr.kkiro.javastone.game.HeroAbility;
import kr.kkiro.javastone.game.Minion;
import kr.kkiro.javastone.game.Player;
import kr.kkiro.javastone.game.Session;
import kr.kkiro.javastone.game.card.Card;
import kr.kkiro.javastone.game.card.MinionCard;
import kr.kkiro.javastone.game.card.TargetCard;
import kr.kkiro.javastone.panel.CardView;
import kr.kkiro.javastone.panel.PlayerSection;
import kr.kkiro.javastone.panel.PlayerSection.SelectListener;

public class GameActivity extends Activity implements SelectListener {
  
  protected static class CardListModel extends AbstractListModel<Card> {
    protected List<Card> list;
    
    public List<Card> getList() {
      return list;
    }
    
    public void setList(List<Card> list) {
      this.list = list;
      this.fireContentsChanged(this, 0, list.size() - 1);
    }
    
    @Override
    public Card getElementAt(int index) {
      if (list == null) return null;
      return list.get(index);
    }
    
    @Override
    public int getSize() {
      if (list == null) return 0;
      return list.size();
    }
  }
  
  protected Session session;
  protected Player player;
  protected Object selected;
  protected int selectedIndex;
  protected boolean isSelecting = false;
  
  protected List<PlayerSection> playerSections = new LinkedList<>();
  protected JList<Card> cardList;
  protected CardListModel cardModel;
  
  public GameActivity() {
    setLayout(new BorderLayout(0, 0));
    
    bottomPanel = new JPanel();
    add(bottomPanel, BorderLayout.SOUTH);
    bottomPanel.setLayout(new BorderLayout(0, 0));
    
    JPanel panel_2 = new JPanel();
    bottomPanel.add(panel_2, BorderLayout.WEST);
    
    cardModel = new CardListModel();
    
    cardList = new JList<Card>();
    cardList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    cardList.setVisibleRowCount(-1);
    cardList.setModel(cardModel);
    cardList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        setSelected(cardList.getSelectedValue());
      }
    });
    cardList.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        setSelected(cardList.getSelectedValue());
      }
    });
    JScrollPane scrollPane = new JScrollPane(cardList);
    panel_2.add(scrollPane);
    scrollPane.setPreferredSize(new Dimension(200, 150));
    notifyLabel.setHorizontalAlignment(SwingConstants.CENTER);
    
    bottomPanel.add(notifyLabel, BorderLayout.NORTH);
    
    cardModePanel = new JPanel();
    bottomPanel.add(cardModePanel, BorderLayout.CENTER);
    cardModePanel.setLayout(new BorderLayout(0, 0));
    
    JPanel panel = new JPanel();
    panel.setBorder(new EmptyBorder(0, 5, 0, 5));
    FlowLayout flowLayout = (FlowLayout) panel.getLayout();
    flowLayout.setAlignment(FlowLayout.RIGHT);
    cardModePanel.add(panel, BorderLayout.SOUTH);
    
    submitCardBtn = new JButton("카드 내기");
    panel.add(submitCardBtn);
    submitCardBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        submitCard();
      }
    });
    
    endTurnBtn = new JButton("턴 종료");
    endTurnBtn.setEnabled(false);
    panel.add(endTurnBtn);
    endTurnBtn.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        endTurn();
      }
    });
    
    cardView = new CardView();
    cardView.setBorder(new EmptyBorder(5, 5, 5, 5));
    cardModePanel.add(cardView, BorderLayout.CENTER);
    
    
    JPanel playerView = new JPanel();
    playerView.setBorder(null);
    
    JScrollPane scrollPane_1 = new JScrollPane(playerView);
    scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    playerView.setLayout(new BoxLayout(playerView, BoxLayout.Y_AXIS));
    
    session = new Session();
    player = session.getPlayers().get(1);
    setSelected(player.getDeck().getCards().get(0));
    
    boolean evenOdd = false;
    for (Player player : session.getPlayers()) {
      PlayerSection playerSection = new PlayerSection(player, evenOdd);
      playerView.add(playerSection);
      playerSections.add(playerSection);
      playerSection.addSelectListener(this);
      evenOdd = !evenOdd;
    }
    add(scrollPane_1, BorderLayout.CENTER);
    
    updateView();
   
  }
  
  public Player getPlayer() {
    return player;
  }
  
  public void setPlayer(Player player) {
    this.player = player;
  }
  
  public void setSelected(Object selected) {
    if (isSelecting) return;
    selectedIndex = cardList.getSelectedIndex();
    this.selected = selected;
    updateSelected();
  }
  
  public void updateSelected() {
    if (isSelecting) return;
    if (selected instanceof HeroAbility) {
      HeroAbility ability = (HeroAbility)selected;
      Card card = ability.getCard();
      updateCards(card);
      submitCardBtn.setText("카드 내기");
      submitCardBtn.setEnabled(session.getCurrentPlayer() == player && card.getCost() <=
          player.getPoints() && ability.getHero() == player.getHero() &&
          player.getHero().isAbilityEnabled());
    }
    if (selected instanceof Card) {
      Card card = (Card)selected;
      updateCards(card);
      submitCardBtn.setText("카드 내기");
      submitCardBtn.setEnabled(session.getCurrentPlayer() == player && card.getCost() <=
          player.getPoints());
    }
    if (selected instanceof Minion) {
      Minion minion = (Minion)selected;
      session.nextSeqId();
      minion.setInteractSeq();
      for (PlayerSection section : playerSections) {
        section.updateView();
      }
      updateCards(minion.getCard());
      submitCardBtn.setText("공격");
      submitCardBtn.setEnabled(session.getCurrentPlayer() == player && minion.isReady());
    }
    if (selected instanceof Hero) {
      Hero hero = (Hero)selected;
      session.nextSeqId();
      hero.setInteractSeq();
      for (PlayerSection section : playerSections) {
        section.updateView();
      }
    }
  }
  
  public void updateCards(Card card) {
    cardView.setCard(card);
  }
  
  public void updateView() {
    cardModel.setList(player.getDeck().getCards());
    endTurnBtn.setEnabled(session.getCurrentPlayer() == player);
    for (PlayerSection section : playerSections) {
      section.updateView();
    }
    updateSelected();
  }
  
  public void submitCard() {
    if (isSelecting) {
      isSelecting = false;
      updateSelected();
      return;
    }
    Object obj = selected;
    if (selected instanceof HeroAbility) {
      // Swap selected
      obj = ((HeroAbility) selected).getCard();
    }
    if (obj instanceof TargetCard) {
      if (((TargetCard)obj).getCost() > player.getPoints()) return;
      // Start minion set mode
      isSelecting = true;
      submitCardBtn.setText("취소");
      submitCardBtn.setEnabled(true);
    } else if (obj instanceof Card) {
      // Okay..
      int index = cardList.getSelectedIndex();
      Card card = player.getDeck().getCards().remove(index);
      if (card.getCost() > player.getPoints()) return;
      player.useCard(card);
      cardList.setSelectedIndex(0);
      updateView();
    } else if (obj instanceof Minion) {
      // Start minion set mode
      isSelecting = true;
      submitCardBtn.setText("취소");
      submitCardBtn.setEnabled(true);
    }
  }
  
  public void endTurn() {
    session.nextTurn();
    updateView();
  }
  

  @Override
  public void entitySelected(Object entity) {
    if (isSelecting) {
      Object obj = selected;
      if (selected instanceof HeroAbility) {
        // Swap selected
        obj = ((HeroAbility) selected).getCard();
      }
      if (obj instanceof Minion && entity instanceof Damageable) {
        // :)
        Minion self = (Minion) obj;
        Damageable target = (Damageable) entity;
        Player player = target.getPlayer();
        if (player.hasTaunt() && !(target instanceof Minion && ((Minion)target).isTaunt())) {
          JOptionPane.showMessageDialog(this, "도발 속성이 있는 하수인을 먼저 제거해야 합니다.");
          return;
        }
        session.nextSeqId();
        self.attack(target);
        isSelecting = false;
        updateView();
      } else if (obj instanceof TargetCard && entity instanceof Damageable) {
        TargetCard card = (TargetCard) obj;
        if (card.getCost() > player.getPoints()) {
          isSelecting = false;
          updateView();
          return;
        }
        boolean result = card.checkTarget(session, player, (Damageable) entity);
        if (result) {
          session.nextSeqId();
          player.setPoints(player.getPoints() - card.getCost());
          if (selectedIndex >= 0 && selected == player.getDeck().getCards().get(selectedIndex))
            player.getDeck().getCards().remove(selectedIndex);
          cardList.setSelectedIndex(0);
          if (selected instanceof HeroAbility) {
            ((HeroAbility) selected).getHero().setAbilityEnabled(false);
          }
          card.runTarget(session, player, (Damageable) entity);
          isSelecting = false;
          updateView();
          return;
        } else {
          JOptionPane.showMessageDialog(this, "사용할 수 없는 대상입니다.");
          return;
        }
      }
      return;
    }
    selectedIndex = -1;
    this.setSelected(entity);
  }

  private static final long serialVersionUID = 3076740735714147035L;
  private CardView cardView;
  private JButton submitCardBtn;
  private JButton endTurnBtn;
  private JPanel bottomPanel;
  private JPanel cardModePanel;
  private JLabel notifyLabel = new JLabel("");

  @Override
  public void init() {
    // TODO Auto-generated method stub

  }

  @Override
  public void tick() {
    Player current = session.getCurrentPlayer();
    if (current.isAI() && getApp().getTickId() % 90 == 0) {
      if (current.runAIStep()) {
        session.nextTurn();
      }
      updateView();
    }
    if (session.getOpponent(player).getHero().isDead()) {
      JOptionPane.showMessageDialog(this, "게임에서 승리했습니다! 축하합니다.");
      // Go back to title
      getApplication().setActivity(new TitleActivity());
    }
    if (player.getHero().isDead()) {
      JOptionPane.showMessageDialog(this, "게임에서 패배했습니다.");
      // Go back to title
      getApplication().setActivity(new TitleActivity());
    }
  }

  @Override
  public void render() {
  }
  
  @Override
  public void render(Graphics2D g, int width, int height) {
    super.render(g, width, height);
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
    // ImageRenderer.drawCover(g, width, height, ResourceBitmap.BACKGROUND);
    // TiledImageRenderer.drawTiled(g, width, height, 0, getApp().getTickId() * -3, ResourceBitmap.BACKGROUND);
  }

  @Override
  public void destroy() {
    // TODO Auto-generated method stub

  }
}
