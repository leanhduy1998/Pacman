
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/*
   * @author Yu-Ju Chang, Le Anh Duy, Yue Zhang
 * @date 28th, November, 2015
 * Our group final project: Pacman game
 */

public class Texture {
    public static BufferedImage[] player = new BufferedImage[6];
    public static BufferedImage[] enemy = new BufferedImage[6];
    public BufferedImage playerspritesheet;
    public BufferedImage enemyspritesheet;
    public Texture(){
        // read the playerspritesheet.png for the player
        try {
            playerspritesheet = ImageIO.read(getClass().getResource("playerspritesheet.png"));
        } catch (IOException e) {
            System.out.println("failed");
        }
        // read the enemyspritesheet.png for the enemy
        try {
            enemyspritesheet = ImageIO.read(getClass().getResource("enemyspritesheet.png"));
        } catch (IOException e) {
            System.out.println("failed");
        }
        // get the images of the player in the picture
        player[0]= playerGetSprite(0,0);
        player[1] = playerGetSprite(15,0);
        player[2]= playerGetSprite(30,0);
        player[3]= playerGetSprite(0,15);
        player[4]= playerGetSprite(15,15);
        player[5]= playerGetSprite(30,15);
        // get the enemy of the player in the picture
        enemy[0]=enemyGetSprite(0,0);
        enemy[1]=enemyGetSprite(15,0);
        enemy[2]=enemyGetSprite(30,0);
        enemy[3]=enemyGetSprite(0,15);
        enemy[4]=enemyGetSprite(15,15);
        enemy[5]=enemyGetSprite(30,15);
        
    }
    public BufferedImage playerGetSprite(int xx, int yy){
        // get part of the image for the player
        return playerspritesheet.getSubimage(xx, yy, 12, 12);
    }
    public BufferedImage enemyGetSprite(int xx, int yy){
        // get part of the image for the enemy
        return enemyspritesheet.getSubimage(xx, yy, 12, 12);
    }
}
