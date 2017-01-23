
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
  * @author Yu-Ju Chang, Le Anh Duy, Yue Zhang
 * @date 28th, November, 2015
 * Our group final project: Pacman game
 */
public class Player extends Rectangle {
    public boolean right,left,up,down;
    private int speed=4;
    private int time=0,targetTime=10;
    public static int frameValue=0;
    
    
    public Player(int x, int y){
        // set bound for the player
        setBounds(x,y,32,32);
    }
    public void tick(){
        /*
        boolean canMove will be used to make sure the the player doesn't go through
        the blocks. The user can only move when the boolean canMove is true
        */
        // if the player is moving right/left, change the x coordinate according to the speed
        if(right&& canMove(x+speed,y)){
            x+=speed;
        }
        if(left && canMove(x-speed,y)){
            x-=speed;
        }
        // if the player is moving up/down, change the y coordinate according to the speed
        if(up && canMove(x,y-speed)){
            y-=speed;
        }
        if(down&& canMove(x,y+speed)){
            y+=speed;
        }
        Level level = Game.level;
        for(int i=0;i<level.fruits.size();i++){
            if(this.intersects(level.fruits.get(i))){
                level.fruits.remove(i);
                if(level.fruits.isEmpty()){
                Game.state=Game.win_screen;
            }
                break;
            }
            // if there is no more fruit, we win
            
            
            
        }
        time++;
        if(time==targetTime){
            frameValue=0;
        }if(time==targetTime*2){
            frameValue=1;
        }
        if(time==targetTime*3){
            frameValue=2;
        }
        if(time==targetTime*4){
            frameValue=3;
        }
        if(time==targetTime*5){
            frameValue=4;
        }
        if(time==targetTime*6){
            frameValue=5;
            time=0;
        }
    }
    private boolean canMove(int nextx, int nexty){
        /*
        this boolean will predict whether the player is in front of the blocks or
        not. If so, canMove is false. If not, canMove is true
        */
        // bound is the bound of the game
        Rectangle bound = new Rectangle(nextx,nexty,width,height);
        // create level with same properties as level in Game class
        Level level = Game.level;
        
        for(int xx=0;xx<level.tiles.length;xx++){
            for(int yy=0;yy<level.tiles[0].length;yy++){
                /* if the tiles is different than null, it means there's a block there
                therefore, and if the bound is intersect with the tiles, than canMove is false
                */
                if(level.tiles[xx][yy]!=null){
                    if(bound.intersects(level.tiles[xx][yy])){
                        return false;
                    }
                }
                
            }
        }
        return true;
    }
    public void render(Graphics g){
       
        /* first value is the coordinate of the image picture
           second and third value is the position of the player
           fourth and fifth is the scaler
           sixth is theImageObserver, which we don't have
        */
        // this method is for drawing images.
        
        g.drawImage(Texture.player[frameValue], x, y,26,26, null);
        
    }
}
