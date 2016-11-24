package kr.kkiro.javastone.activity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import kr.kkiro.javastone.res.ResourceBitmap;
import kr.kkiro.javastone.util.ImageRenderer;

public class TitleActivity extends Activity {

  private static final long serialVersionUID = -3305472092836126607L;
  
  private JLabel gameTitle;
  
  public TitleActivity() {
    setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    setLayout(new BorderLayout(0, 0));
    
    JPanel panel = new JPanel();
    panel.setOpaque(false);
    add(panel, BorderLayout.NORTH);
    panel.setLayout(new BorderLayout(0, 0));
    
    gameTitle = new JLabel();
    panel.add(gameTitle, BorderLayout.CENTER);
    gameTitle.setHorizontalAlignment(SwingConstants.CENTER);
    gameTitle.setFont(new Font(gameTitle.getFont().getName(), Font.PLAIN, 80));
    gameTitle.setForeground(Color.BLACK);
    gameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
    gameTitle.setText("JavaStone");
    
    JPanel menuContainer = new JPanel();
    menuContainer.setOpaque(false);
    add(menuContainer, BorderLayout.CENTER);
    menuContainer.setLayout(new BoxLayout(menuContainer, BoxLayout.Y_AXIS));
    
    Component glue1 = Box.createGlue();
    menuContainer.add(glue1);
    
    JPanel menu = new JPanel();
    menu.setBorder(new EmptyBorder(10, 10, 10, 10));
    menuContainer.add(menu);
    menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
    
    JButton startBtn = new JButton("시작하기");
    menu.add(startBtn);
    startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    startBtn.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        getApplication().setActivity(new GameActivity());
      }
    });
    
    JButton endBtn = new JButton("종료하기");
    endBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    menu.add(endBtn);
    endBtn.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });
    
    Component glue2 = Box.createGlue();
    menuContainer.add(glue2);
  }
  
  @Override
  public void init() {
  }

  @Override
  public void tick() {
  }

  @Override
  public void render() {
    this.repaint();
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
  }
}
