
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


/**
  * @author Yu-Ju Chang, Le Anh Duy, Yue Zhang
 * @date 28th, November, 2015
 * Our group final project: Pacman game
 */
public class Tile extends Rectangle {
    String color="";
    public Tile(int x, int y,String color){
        // set the bound of each tile with the position according to x and y
        setBounds(x,y,32,32);
        this.color=color;
    }
    public void render(Graphics g){
        // if the color of the picture is pink, make color of specific tiles pink
        if(color.equals("pink")){
        g.setColor(new Color(255,96,228));    
        }
        // if the color of the picture is orange, make color of specific tiles orange
        else if(color.equals("orange")){
        g.setColor(Color.orange);    
        }
        // if the color of the picture is green, make color of specific tiles green
        else if(color.equals("green")){
        g.setColor(Color.green);    
        }
        // draw tiles
        g.fillRect(x, y, width, height);
    }
    
}
