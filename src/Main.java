import files.Pokedex;
import files.Pokemon;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    static Pokedex pokedex;
    static JTextPane display;
    static JTextField input;

    static int currentPokemon = 0;

    static boolean creating = false;

    static int creatingStep = -1;

    private static String name;
    private static String jName;
    private static int hp;
    private static int defense;
    private static int attack;
    private static String abilities;
    private static double height;
    private static double weight;
    private static String type1;
    private static String type2;
    private static String classification;
    private static int generation;
    static Pokemon p;

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Pokédex");
        int width = 440;
        int height = 740;
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BufferedImage img = ImageIO.read(new File("src/assets/icon.png"));
        Image scaledImage = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        frame.setIconImage(scaledImage);

        JPanel panel = new JPanel() {
            final BufferedImage backgroundImage = ImageIO.read(new File("src/assets/background.png"));

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };

        // Large text
        display = new JTextPane();
        display.setBounds(30, 179, 383, 332);  // Adjusted for placement
        display.setFont(new Font("Arial", Font.PLAIN, 16));
        display.setEditable(false);
        display.setEnabled(false);
        display.setDisabledTextColor(Color.black);
        display.setBackground(Color.white);

        StyledDocument doc = display.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);

        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        display.setText("""
        Welcome to the Pokédex!
        To start, type the name of a pokémon in the search bar and press the search button!
        Or click either arrow to cycle through the pokémon.

        Press the reload button to come back to this page
        
        You can also press the \"Camera\" in the top right corner to create a new Pokémon""");
        panel.add(display);

        // Input
        input = new JTextField();
        input.setBounds(24, 549, 250, 89);
        input.setFont(new Font("Arial", Font.PLAIN, 30));
        input.setBackground(Color.white);
        panel.add(input);

        // Buttons
        JButton searchButton = new JButton();
        searchButton.setOpaque(false);
        searchButton.setContentAreaFilled(false);
        searchButton.setBorderPainted(false);
        searchButton.setBounds(298, 537, 68, 61);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    searchPressed();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(searchButton);

        JButton leftArrowButton = new JButton();
        leftArrowButton.setOpaque(false);
        leftArrowButton.setContentAreaFilled(false);
        leftArrowButton.setBorderPainted(false);
        leftArrowButton.setBounds(298, 591, 68, 61);
        leftArrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftPressed();
            }
        });
        panel.add(leftArrowButton);

        JButton rightArrowButton = new JButton();
        rightArrowButton.setOpaque(false);
        rightArrowButton.setContentAreaFilled(false);
        rightArrowButton.setBorderPainted(false);
        rightArrowButton.setBounds(365, 591, 68, 61);
        rightArrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightPressed();
            }
        });
        panel.add(rightArrowButton);

        JButton newButton = new JButton();
        newButton.setOpaque(false);
        newButton.setContentAreaFilled(false);
        newButton.setBorderPainted(false);
        newButton.setBounds(9, 5, 111, 111);
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newPokemon();
            }
        });
        panel.add(newButton);

        JButton refreshButton = new JButton();
        refreshButton.setOpaque(false);
        refreshButton.setContentAreaFilled(false);
        refreshButton.setBorderPainted(false);
        refreshButton.setBounds(365, 537, 68, 61);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshPressed();
            }
        });
        panel.add(refreshButton);

        panel.setLayout(null);
        frame.add(panel);
        panel.setLayout(null);
        frame.add(panel);

        pokedex = new Pokedex();

        frame.setVisible(true);
    }

    private static void searchPressed() throws IOException {
        if (creating){
            createSearchPressed();
            return;
        }
        Pokemon p = pokedex.getPokemon(input.getText());

        int i = pokedex.getPokemonIndex(p);

        if (i == -1){
            invalidPokemon();
        }
        else{
            currentPokemon = i;
            updateText();
        }

    }

    private static void refreshPressed(){
        if (creating){
            return;
        }
        display.setText("""
                Welcome to the Pokédex!
                To start, type the name of a pokémon in the search bar and press the search button!
                Or click either arrow to cycle through the pokémon.
                
                Press the reload button to come back to this page""");
        currentPokemon = 0;
    }

    private static void leftPressed(){
        if (creating){
            return;
        }
        if (currentPokemon > 0){
            currentPokemon--;
            updateText();
        }
        else if (currentPokemon == 0){
            currentPokemon = pokedex.getNumPokemon();
            updateText();
        }
    }

    private static void rightPressed(){
        if (creating){
            return;
        }
        if (currentPokemon < pokedex.getNumPokemon()){
            currentPokemon++;
            updateText();
        }
        else if (currentPokemon == pokedex.getNumPokemon()){
            currentPokemon = 0;
            updateText();
        }
    }

    private static void newPokemon(){
        if (creating){
            return;
        }
        creating = true;
        display.setText("""
                Welcome to \"Create-a-Pokémon\"
                Press the search button to continue""");
        creatingStep += 1;
        System.out.println(creatingStep);
    }

    private static void createSearchPressed() throws IOException {
        if (creatingStep == 0){
            display.setText("""
                Enter the Pokémon's name into the search bar
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        else if (creatingStep == 1){
            name = input.getText();
            input.setText("");
            display.setText("""
                Enter the Pokémon's Japanese Name into the search bar
                (You can leave this blank if you would like)
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        else if (creatingStep == 2){
            jName = input.getText();
            input.setText("");
            display.setText("""
                Enter the Pokémon's HP into the search bar
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        else if (creatingStep == 3){
            hp = Integer.parseInt(input.getText());
            input.setText("");
            display.setText("""
                Enter the Pokémon's Defense into the search bar
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        else if (creatingStep == 4){
            defense = Integer.parseInt(input.getText());
            input.setText("");
            display.setText("""
                Enter the Pokémon's Attack Power into the search bar
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        else if (creatingStep == 5){
            attack = Integer.parseInt(input.getText());
            input.setText("");
            display.setText("""
                Enter the Pokémon's Abilities into the search bar
                (To keep consistency, enter each ability separated with a comma)
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        else if (creatingStep == 6){
            abilities = input.getText();
            input.setText("");
            display.setText("""
                Enter the Pokémon's height (in m) into the search bar
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        else if (creatingStep == 7){
            height = Double.parseDouble(input.getText());
            input.setText("");
            display.setText("""
                Enter the Pokémon's weight (in kg) into the search bar
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        else if (creatingStep == 8){
            weight = Double.parseDouble(input.getText());
            input.setText("");
            display.setText("""
                Enter the Pokémon's main type into the search bar
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        else if (creatingStep == 9){
            type1 = input.getText();
            input.setText("");
            display.setText("""
                Enter the Pokémon's secondary type into the search bar
                (Leave this blank if the pokémon has no secondary type)
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        else if (creatingStep == 10){
            type2 = input.getText();
            input.setText("");
            display.setText("""
                Enter the Pokémon's classification into the search bar
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        else if (creatingStep == 11){
            classification = input.getText();
            input.setText("");
            display.setText("""
                Enter the Pokémon's generation into the search bar
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        else if (creatingStep == 12){
            generation = Integer.parseInt(input.getText());
            input.setText("");
            display.setText("""
                You will see the info for your pokémon on the next screen. Press the search button to confirm.
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        else if (creatingStep == 13){
            p = new Pokemon(name, jName, hp, defense, attack, abilities, height,
                    weight, type1, type2, classification, generation);

            display.setText(p.toString());
            creatingStep++;
        }
        else if (creatingStep == 14){
            pokedex.createPokemon(p);
            creatingStep = -1;
            creating = false;
            updateText();
        }
    }

    private static void updateText(){
        Pokemon p = pokedex.getPokemon(currentPokemon);

        display.setText(p.toString());
        input.setText("");
    }

    private static void invalidPokemon() {
        display.setText("Unknown Pokémon: \"" + input.getText() +"\".\n\nMake sure you spelled the name of the pokémon correctly.\n(\"Charzard\" and \"Charizard\" are not the same )\n\nYou can also use the arrow keys to traverse the Pokémon");
    }


}