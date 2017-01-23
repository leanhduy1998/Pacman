
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
 


/**
  * @author Yu-Ju Chang, Le Anh Duy, Yue Zhang
 * @date 28th, November, 2015
 * Our group final project: Pacman game
 */
public class Game extends Canvas implements Runnable, KeyListener {

    /**
     * @param args the command line arguments
     * @param width is the width of the game
     * @param height is the height of the game
     * @param thread is the thread of the game
     * @param isRunning is a boolean to show the condition whether the game is running
     * 
     * @param state is the state of the game
     * @param start_screen is the condition of "state" 
     * @param normal is the state of "state"
     * @param isRunning2 is for restarting the game
     * @param time is for controlling the time in the game
     * @param targetTime is for controlling the time in the game
     * @param showText is a condition for showing text
     */
    /*
    The Runnable interface should be implemented by any class whose instances 
    are intended to be executed by a thread
    
    The KeyListener is the listener interface for receiving keyboard events 
    
    */
    private final int width = 645;
    private final int height = 505;
    private Thread thread;
    private boolean isRunning = false;
    // create new player
    public static Player player = new Player(288,96);
    // create new level
    public static Level level = new Level("map.png");
    public static final int start_screen = 0, normal = 1, pause_screen = 2, lose_screen = 3, win_screen = 4;
    public static int state = start_screen;
    private boolean isRunning2 = false, showText = false, playingMusic = false, playingMusic1 = false;
    private int time = 0,targetTime = 20;
    boolean testCase = false;
    
    
    public Game(){
        new Window(width,height,"Pacman",this);
        // add KeyListener for receiving keyboard's event, adding this game using "this"
        addKeyListener(this);
        new Texture();
    }
    public synchronized void start(){
        // create new thread
        thread = new Thread(this);
        // if the game is already running, keep running
        if(isRunning){
            return;
        }
        // if not, run the game
        isRunning = true;
        // start the thread
        thread.start();
    }
    public synchronized void stop(){
        // if the game is already stop, stop the game
        if(!isRunning){
            return;
        }
        // if not, stop the game
        isRunning = false;
        // joining the thread
        try{
            // if we can join the thread, then do it
            thread.join();
        }
        catch(InterruptedException e){
            // else, Prints this throwable and its backtrace to the standard error stream
            e.printStackTrace();
        }
    }

    private void tick(){
        if(state == normal){
          player.tick();
        level.tick(); 
        if(!playingMusic1){
             //add music
                Music music = new Music("normal.wav");
            playingMusic1=true;
            }
        }
        else if(state == lose_screen){
            if(isRunning2){
                isRunning2 = false;
                level = new Level("map.png");
                player = new Player(320,160);
                state = normal; 
            }
        }
        else if(state == win_screen){
            if(isRunning2){
                isRunning2 = false;
                level = new Level("map.png");
                player = new Player(320,160);
                state = normal; 
            }
        }
        // update time
        time++;
        // if time is equal targetTime, show text, else, don't
        if(time == targetTime){
            time = 0;
            if(showText){
              showText = false;  
            }
            else{
              showText = true;  
            }   
        }
    }
    private void render(){
        /*
        This method is for rendering the game.
        */
        /* 
        create an object from BufferStrategy class to organize complex memory of
        Canvas class. 
        */
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            // doing triple buffering to render, show and display the game
            this.createBufferStrategy(3);
            return;
        }
        // Creates a graphics context for the drawing buffer
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);  
        
        
        
         // fill the screen with black so that our background will not be flashy all the time
        g.fillRect(0, 0, width, height);
        
        /* render player so that player can be displayed on the frame
        this method must be called before the method g.dispose is called, else g will
        be disposed and nothing will display!
        */
        // only when the state of the game is normal that the game will render player and level
        if(state == normal){
            player.render(g);
        //render the level
        level.render(g);
       
        }
        // if the state of the game is at the begining, draw start screen
        else if(state==start_screen){
            // if music is not playing, play music
            if(!playingMusic){
             //add music
                Music music = new Music("start.wav");
            playingMusic=true;
            }
            
            int boxWidth = 500;
            int boxHeight = 200;
            // set the xx and yy coordinate so that the pause screen would be in the middle of the screen
            int xx = width/2 - boxWidth/2;
            int yy = height/2 - boxHeight/2-20;
            // set color of the pause screen and draw it
            g.setColor(new Color(74,61,255));
            g.fillRect(xx, yy, boxWidth, boxHeight);
            // add a String on the frame
            // set color of the String
            if(showText){
            g.setColor(Color.white);
            g.setFont(new Font(Font.DIALOG,Font.BOLD,26));
            g.drawString("Please enter to start the game!!!", xx+60, yy+70);
            g.drawString("'P' for pause", xx+180, yy+130);
            }
            
        }
        // if the user pauses the game
        
        else if(state == lose_screen){
            int boxWidth = 500;
            int boxHeight = 200;
            // set the xx and yy coordinate so that the pause screen would be in the middle of the screen
            int xx = width/2 - boxWidth/2;
            int yy = height/2 - boxHeight/2 - 20;
            // set color of the pause screen and draw it
            g.setColor(new Color(74,61,255));
            g.fillRect(xx, yy, boxWidth, boxHeight);
            // add a String on the frame
            // set color of the String
            g.setColor(Color.white);
            g.setFont(new Font(Font.DIALOG,Font.BOLD,26));
            if(showText){
            g.drawString("!!!!!!!!!!!!You lost!!!!!!!!!!!!!", xx+100, yy+70);
            g.drawString("'R' for restart", xx+180, yy+130);
            }
            
            
        }
        else if(state == win_screen){
            
            int boxWidth = 500;
            int boxHeight = 200;
            // set the xx and yy coordinate so that the pause screen would be in the middle of the screen
            int xx = width/2 - boxWidth/2;
            int yy = height/2 - boxHeight/2 - 20;
            // set color of the pause screen and draw it
            g.setColor(new Color(74,61,255));
            g.fillRect(xx, yy, boxWidth, boxHeight);
            // add a String on the frame
            // set color of the String
            g.setColor(Color.white);
            g.setFont(new Font(Font.DIALOG,Font.BOLD,26));
            if(showText){
            g.drawString("!!!!!!!!!!!!You win!!!!!!!!!!!!!", xx+100, yy+70);
            g.drawString("'R' for restart", xx+180, yy+130);
            }
            
            
        }
        else if(state == pause_screen){
            int boxWidth = 500;
            int boxHeight = 200;
            // set the xx and yy coordinate so that the pause screen would be in the middle of the screen
            int xx = width/2 - boxWidth/2;
            int yy = height/2 - boxHeight/2 - 20;
            // set color of the pause screen and draw it
            g.setColor(new Color(74,61,255));
            g.fillRect(xx, yy, boxWidth, boxHeight);
            // add a String on the frame
            // set color of the String
            
            if(showText){
            g.setColor(Color.white);
            g.setFont(new Font(Font.DIALOG,Font.BOLD,13));
            g.drawString("Credit to Le Anh Duy", xx+60, yy+40);
            g.drawString("Credit to Yu-Ju Chang", xx+60, yy+80);
            g.drawString("Credit to Yue Zhang", xx+60, yy+120);
            g.setFont(new Font(Font.DIALOG,Font.BOLD,26));
            g.drawString("Press 'P' to return! ", xx+60, yy+180);  
            }
            
            
        }
        
        
        // Disposes of this graphics context when it's not used anymore
        g.dispose();
        // display our game on the frame
        bs.show();
        
    }
    public void run(){
        /*
        calculate the value of fps
        */
        /* The purpose of the requestFocus() is to get the focus on the game component,
        so that it becomes the focused Window
        */
        
         requestFocus();
        
        // set the lastTime value to the current value of the running Java Virtual Machine's high-resolution time source, in nanoseconds.
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        // create variable to save how many nano seconds for each updates
        // Unit: 1sec/60ticks
        double ns = 1000000000 / amountOfTicks;
        // delta is the difference between the update of time
        double delta = 0;
        // set timer variable to current time in miliseconds
        long timer = System.currentTimeMillis();
        // set frames =0    
        int frames = 0;
        while(isRunning)
        {           
            // set the now value to the value of time at this present in nano second
            long now = System.nanoTime();
            // calculate the difference in time from the now to the lastTime
            // Unit: sec/sec/tick = tick
            delta += (now - lastTime) / ns;
            // update the lastTime variable
            lastTime = now;
            // if delta is bigger than 1 frame, use tick method and updating the delta
            while(delta >=1)
            {
                tick();
                delta--;
                // update the frame
                frames++;
                // render the game
                render();
            }
            /* if the system's milisecond is bigger than the previous update
            by 1000 milisecond, do this
            */
            if(System.currentTimeMillis() - timer > 1000)
            {   // plus 1000 to the timer
                timer += 1000;
                // update the frames on the screen
                System.out.println("FPS: "+ frames);
                // reupdate  the frames
                frames = 0;
            }
        }       
        // stop the game
        stop();
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        Game game =new Game();
        game.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        /* This method is used to receive the event about the keyboard arrows when
        they are pressed
        
        
        the method getKeyCode is to return the integer keyCode associated with the key in this event.
        the method VK_RIGHT returns the constant for the non-numpad right arrow key.
        
        if the right arrow was pressed, boolean right of Player class is true, which
        will increase the x coordinate of the player and so on with other arrow keys.
        
        */
        // if the state of the game is normal
        if(state==normal){
            if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            player.right=true;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            player.down=true;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            player.up=true;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            player.left=true;
        }   
        if(e.getKeyCode()== KeyEvent.VK_P){
            state=pause_screen;
        }
        // test case
        if(e.getKeyCode()== KeyEvent.VK_T){
            if(!testCase){
                testCase=true;
                level.fruits.clear();
                level.fruits.add(new Fruit(32,32));
           
                level.enemies.remove(0);
                level.enemies.remove(1);
            }
        }
        }
        // if the state of the game is at the begining, and if "enter" is pressed when pause, start the game
        else if(state==start_screen){
            if(e.getKeyCode()== KeyEvent.VK_ENTER){
                state=normal;
                
            }
        }
            /// if the state of the game is at pause, and if "p" is pressed when pause, resume the game
            else if(state==pause_screen){
                if(e.getKeyCode()== KeyEvent.VK_P){
                    state=normal;
        }
            }
            else if(state==lose_screen){
                if(e.getKeyCode()== KeyEvent.VK_R){
                    isRunning2=true;
                    testCase=false;
                    
        }
            }
                else if(state==win_screen){
                if(e.getKeyCode()== KeyEvent.VK_R){
                    isRunning2=true;
                    testCase=false;
                }
        }
            
        
            
      
    }

    @Override
    public void keyReleased(KeyEvent e) {
       /*
        this method is required so the player wouldn't move forever after releasing
        the arrow
        
        this method is basically the opposite of the keyPressed method
        */ 
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            player.right=false;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            player.down=false;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            player.up=false;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            player.left=false;
        }
    }
    
}
