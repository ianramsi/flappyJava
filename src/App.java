import javax.swing.JFrame;

public class App {
    public static void main(String[] args) {
        // Create the game window with title
        JFrame frame = new JFrame("Flappy Bird");
        // Create instance of our game
        Flappy flappy = new Flappy();
        
        // Add the game panel to the window
        frame.add(flappy);
        // Make sure the game closes properly
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Size the window to fit our game
        frame.pack();
        // Make the window visible
        frame.setVisible(true);
        // Center the window on screen
        frame.setLocationRelativeTo(null);
        // Prevent window resizing
        frame.setResizable(false);
    }
}