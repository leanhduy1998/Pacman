
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/*
 * @author Yu-Ju Chang, Le Anh Duy, Yue Zhang
 * @date 28th, November, 2015
 * Our group final project: Pacman game
 */

public class Enemy extends Rectangle{
    /*
    @param random is an integer showing the state of the enemy moving randomly
    @param smart is an integer showing the state of the enemy moving with AI
    @param state is the state of the enemy
    
    */
    public Enemy(int x, int y){
        setBounds(x,y,32,32);
        direction = randomGen.nextInt(4);
    }
    private int random =0,smart=1, find_path=2;
    private int state = random;
    private int right=0,left=1,up=2,down=3;
    public Random randomGen = new Random();
    private int time=0;
    private int targetTime=60*4;
    private int speed=4;
    private int direction=-1;
    private int lastDirection=-1;
    private int time1=0,frameValue=0,targetTime1=10;
    public void render(Graphics g){
        // create the same spritesheet as the class Game has
        //SpriteSheet enemySpriteSheet =Game.enemySpriteSheet;
        /* first value is the coordinate of the image picture
           second and third value is the position of the enemies
           fourth and fifth is the scaler
           sixth is theImageObserver, which we don't have
        */
        // this method is for drawing images.
        g.drawImage(Texture.enemy[frameValue], x, y,32,32, null);
        
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
    public void tick(){
        // if the direction is to the right and canMove is true
        if(state==random){
            if(x==Game.player.x && y== Game.player.y){
                Game.state=Game.lose_screen;
            }
            if(direction==right){
                if(canMove(x+speed,y)){
                    // move the enemy
                    if(randomGen.nextInt(100)>35){
                    x=x+speed; 
                    }
                }
            // else, move randomly
                else{
                    direction=randomGen.nextInt(4);
                }
            }
        // else if the direction is to the left and canMove is true
            else if(direction==left){
                if(canMove(x-speed,y)){
                    if(randomGen.nextInt(100)>35){
                        x=x-speed;
                    }
                
                }
            // else, move randomly
            else{
                direction=randomGen.nextInt(4);
            }
            }
        // else if the direction is up and canMove is true
            else if(direction==up){
                if(canMove(x,y-speed)){
                    if(randomGen.nextInt(100)>35)y=y-speed;
                }
            // else, move randomly
                else{
                    direction=randomGen.nextInt(4);
                }
            }
        // else if the direction is down and canMove is true
            else if(direction==down){
                if(canMove(x,y+speed)){
                    if(randomGen.nextInt(100)>35)y=y+speed;
                }
            // else, move randomly
                else{
                    direction=randomGen.nextInt(4);
                }
            }
        // if the enemy touch the player, show lose screen
            if(x==Game.player.x && y== Game.player.y){
                Game.state=Game.lose_screen;
            }
        }
        if(x==Game.player.x && y== Game.player.y){
                Game.state=Game.lose_screen;
        }
        //update time
        // if the specific time reached, enemy becomes dumb and random, and reupdate time
        time++;
        if(time == targetTime){
            if(randomGen.nextInt(100)>35){
                state=smart;
            }
            else{
                state=random;
            }
                
                time=0;
        }
        // if the specific time reached, enemy becomes smart, and reupdate time

        //////////////////////////////////////////////////////////////////////
        // the enemy now has AI
        
        if(state==smart){ 
            boolean move=false;
            /*
            if the x and y coordinate of the enemy is smaller than the x and y
            coordinate of the player, 
            */
            // if the enemy toucht the player, show lose screen
            if(x==Game.player.x && y== Game.player.y){
                Game.state=Game.lose_screen;
                move=true;
            }
            // if the player is to the right side of the enemy, enemy moves right
            if(x<Game.player.x){
                if(canMove(x+speed,y)){
                    if(randomGen.nextInt(100)>35){x=x+speed;
                    move=true;
                    lastDirection=right;
                    }
                }
            }
            // if the player is to the right side of the enemy, enemy moves left
            if(x>Game.player.x){
                if(canMove(x-speed,y)){
                    if(randomGen.nextInt(100)>35){x=x-speed;
                    move=true;
                    lastDirection=left;
                    }
                }
            }
            // if the player is above the enemy, enemy moves up
            if(y>Game.player.y){
                if(canMove(x,y-speed)){
                    if(randomGen.nextInt(100)>35){y=y-speed;
                    move=true;
                    lastDirection=up;
                    }
                }
            }
            // if the player is below the enemy, enemy moves down
            if(y<Game.player.y){
                if(canMove(x,y+speed)){
                    if(randomGen.nextInt(100)>35){y=y+speed;
                    move=true;
                    lastDirection=down;
                    }
                }
            }
            
            // if the enemy toucht the player, show lose screen
            if(x==Game.player.x && y== Game.player.y){
                Game.state=Game.lose_screen;
                move=true;
            }
            
            

            /* if the enemy is not moving because it stays in the same x or y direction
            with the player, provide a move in the different direction which will lead
            the enemy closer to the player
            */
            
            if(!move){
               state=find_path; 
            
            }
          
            }
        
        else if(state==find_path){
                /* if the lastDirection is right and the enemy stucks, 
                move accord to the y coordinate to chase the user. Same with all
                other situations
                */
                if(lastDirection==right){
                        // if the player is above the enemy, enemy moves up
                        if(y<Game.player.y){
                            if(canMove(x,y+speed)){
                                if(randomGen.nextInt(100)>35){y=y+speed;
                                state=smart;
                            }
                            }
                        }
                            else{ if(canMove(x,y-speed)){
                                if(randomGen.nextInt(100)>35){y=y-speed;
                                state=smart;
                            }
                            }
                            }    
                        
                            if(canMove(x+speed,y)){
                                if(randomGen.nextInt(100)>35)x=x+speed;
                        }
                        
                }
                // if the last direction is left
                else if(lastDirection==left){
                    
                         // if the player is above the enemy, enemy moves up
                        if(y<Game.player.y){
                            if(canMove(x,y+speed)){
                                if(randomGen.nextInt(100)>35){y=y+speed;
                                state=smart;
                                }
                            }
                        }
                            else{
                            if(canMove(x,y-speed)){
                                if(randomGen.nextInt(100)>35){y=y-speed;
                                state=smart;
                            }    
                            }
                            }
                        
                            // if the player is to the right side of the enemy, enemy moves left
                            if(canMove(x-speed,y)){
                                if(randomGen.nextInt(100)>35)x=x-speed;
                                }
                            
                        }
                        
                // if the last direction is up
                else if(lastDirection==up){
                    if(x<Game.player.x){
                        if(canMove(x-speed,y)){
                                if(randomGen.nextInt(100)>35){x=x-speed;
                                state=smart;
                            }}}
                            else{
                            
                                    
                            if(canMove(x+speed,y)){
                                if(randomGen.nextInt(100)>35){x=x+speed;
                                state=smart;
                            }}
                            }
                    
                            if(canMove(x,y-speed)){
                                if(randomGen.nextInt(100)>35)y=y-speed;
                            }
                        
                }
                else if(lastDirection==down){
                // if the last direction is down
                        if(x<Game.player.x){
                            if(canMove(x+speed,y)){
                                if(randomGen.nextInt(100)>35){x=x+speed;
                                state=smart;
                            }}}
                            else{
                            if(canMove(x-speed,y)){
                                if(randomGen.nextInt(100)>35){x=x-speed;
                                state=smart;
                                }    
                            }}
                        
                            if(canMove(x,y+speed)){
                                if(randomGen.nextInt(100)>35)y=y+speed;
                            }
                        
                }
                if(x==Game.player.x && y== Game.player.y){
                Game.state=Game.lose_screen;
            }
                
                }
        /*
        this is for updating the time1 value and make animation for the enemy
        */
                time1++;
        if(time1==targetTime1){
            frameValue=0;
        }if(time1==targetTime1*2){
            frameValue=1;
        }
        if(time1==targetTime1*3){
            frameValue=2;
        }
        if(time1==targetTime1*4){
            frameValue=3;
        }
        if(time1==targetTime1*5){
            frameValue=4;
        }
        if(time1==targetTime1*6){
            frameValue=5;
            time1=0;
        }   
        // if the enemy touch the player, show lose screen
        if(x==Game.player.x && y== Game.player.y){
                Game.state=Game.lose_screen;
            }
            
            }
            
            
        }
        
    
    
