
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;
/**
  * @author Yu-Ju Chang, Le Anh Duy, Yue Zhang
 * @date 28th, November, 2015
 * Our group final project: Pacman game
 */
public class Window extends Canvas {
    public Window(int width, int height, String title, Game game){
        JFrame frame = new JFrame(title);
        // create new dimension to save time
        Dimension dimension = new Dimension(width,height);
        // set size of the frame according to the dimension
        frame.setSize(dimension);
        /* set maximum and minimum size the same to the user can't change the size
        which will cause problems
        */
        frame.setMaximumSize(dimension);
        frame.setMinimumSize(dimension);
        
        // Set default close method
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // the frame can't be resized
        frame.setResizable(false);
        // put the frame in the middle of the screen
        frame.setLocationRelativeTo(null);
        // add the game into the frame
        frame.add(game);
        // make frame visible
        frame.setVisible(true);
        frame.pack();
        //start the game
        game.start();
    }
}
