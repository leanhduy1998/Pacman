
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;



/**
  * @author Yu-Ju Chang, Le Anh Duy, Yue Zhang
 * @date 28th, November, 2015
 * Our group final project: Pacman game
 */
public class Fruit extends Rectangle {
    public Fruit(int x, int y){
        setBounds(x,y,10,10);
    }
    public void render(Graphics g){
        // set the color of the fruits
        g.setColor(Color.white);
        // create rectangles that will be the fruits
        g.fillRect(x+10, y+10, width, height);
    }
}
