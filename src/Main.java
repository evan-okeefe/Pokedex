import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Pokedex");
        int width = 440;
        int height = 740;

        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*
        BufferedImage img = ImageIO.read(new File("images/guillaume.jpeg"));
        Image scaledImage = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        frame.setIconImage(scaledImage);
         */

        JPanel panel = new JPanel();
        panel.setLayout(null);

        frame.add(panel);
        frame.setVisible(true);
    }


}