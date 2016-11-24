package kr.kkiro.javastone.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageRenderer {
  public static void drawTiled(Graphics2D g, int width, int height, int xpos, int ypos, BufferedImage image) {
    int imgWidth = image.getWidth();
    int imgHeight = image.getHeight();
    int imgPosX = xpos % imgWidth;
    int imgPosY = ypos % imgHeight;
    if (imgPosX < 0) imgPosX = imgWidth + imgPosX;
    if (imgPosY < 0) imgPosY = imgHeight + imgPosY;
    int imgTimeX = (imgPosX + width) / imgWidth;
    int imgTimeY = (imgPosY + height) / imgHeight;
    
    for (int y = 0; y <= imgTimeY; ++y) {
      for (int x = 0; x <= imgTimeX; ++x) {
        g.drawImage(image, x * imgWidth - imgPosX, y * imgHeight - imgPosY, null);
      }
    }
  }
  public static void drawCover(Graphics2D g, int width, int height, BufferedImage image) {
    int imgWidth = image.getWidth();
    int imgHeight = image.getHeight();
    int x = 0, y = 0, w, h;
    if (((float)width / height) > ((float)imgWidth / imgHeight)) {
      w = width;
      h = width * imgHeight / imgWidth;
      y = (h - height);
    } else {
      h = height;
      w = height * imgWidth / imgHeight;
      x = (w - width) / 2;
    }
    g.drawImage(image, -x, -y, w, h, null);
  }
}
