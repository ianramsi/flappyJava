import javax.swing.*;

public static void main Strings args[]) throws Exception {

    int boardWidth = 360;
    int boardHeight = 640;

    JFrame frame = new JFrame("Flying Bird");
    frame.setSize (boardWidth, boardHeight);
    frame.setLocationRelativeTo(null);
    frame.resizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.setVisible(true);
}
