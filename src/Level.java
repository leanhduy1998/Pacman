
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/*
  * @author Yu-Ju Chang, Le Anh Duy, Yue Zhang
 * @date 28th, November, 2015
 * Our group final project: Pacman game
 */

public class Level {
    /*
    @param width is the width of the level map
    * @param height is the height of the level map
    * @param tiles is the array that hold the position of the tiles
    * @param fruits is the array list that hold position of fruits
    */
    public int width;
    public int height;
    
    public Tile[][] tiles;
    public ArrayList<Fruit> fruits = new ArrayList<>();
    public ArrayList<Enemy> enemies = new ArrayList<>();
    
    public Level(String path){
        // add the image using try and catch method, BufferedImage and ImageIO class
        try {
            BufferedImage map = ImageIO.read(getClass().getResource(path));
            // get width of the map
            this.width=map.getWidth();
            // get height of the mal
            this.height=map.getHeight();
            // create a pixel value by calculateing the area of the whole map
            int[] pixels = new int[width*height];
            // Returns an array of integer pixels in the default RGB color model
            // create a new Tile and assign value to it
            tiles = new Tile[width][height];
            map.getRGB(0, 0, width, height, pixels,0, width);
            
            // create a loop to search throgu the pixel and find the black blocks in the map.png
            for(int xx=0;xx<width;xx++){
                for(int yy=0;yy<height;yy++){
                    int val= pixels[xx + (yy*width)];
                    // if the value for the variable val is equal to color pink
                    if(val == 0xFFFF00DC){
                        /* create a new tile and trigger the constructor from
                        class Tile to set bound of the tile
                        */
                        tiles[xx][yy]= new Tile(xx*32,yy*32,"pink");
                        
                    }
                    else if(val == 0xFFFF6A00){
                       tiles[xx][yy]= new Tile(xx*32,yy*32,"orange"); 
                    }
                    else if(val == 0xFF4CFF00){
                       tiles[xx][yy]= new Tile(xx*32,yy*32,"green"); 
                    }
                    /* else if val is equal to the color blue on the map, which
                    will be the origin position of our player
                    */
                    
                    else if(val == 0xFF0026FF){
                        Game.player.x = xx*32;
                        Game.player.y = yy*32;
                    }
                    
                    /* else if val is equal to the color red on the map, which
                    will be the origin position of our enemies
                    */
                    else if(val == 0xFFFF0000){
                        enemies.add(new Enemy(xx*32,yy*32));
                    }
                    // else, other position will be fruits.
                    else{
                        // add new onject to fruits array
                        fruits.add(new Fruit(xx*32,yy*32));
                    }
                    
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void tick(){
        for(int i=0;i<enemies.size();i++){
            // call the method of the Enemy class
            enemies.get(i).tick();
        }
    }
    
    public void render(Graphics g){
        for(int x=0;x<width;x++){
            for(int y=0;y<height;y++){
                ////////////////////System.out.println(tiles[0].length);
                // if the tiles is not null, render the tiles
                if(tiles[x][y]!= null){
                 tiles[x][y].render(g);   
                }
            }
            // if i is smaller than the size of the ArrayList fruits, keep rendering
           for(int i=0;i<fruits.size();i++){
               fruits.get(i).render(g);
           }
           // if i is smaller than the size of the ArrayList enemies, keep rendering
           for(int i=0;i<enemies.size();i++){
               enemies.get(i).render(g);

               
           }
           
        } 
       
        
    }
}
