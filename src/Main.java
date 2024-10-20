import files.Battle;
import files.Pokedex;
import files.Pokemon;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    //Save these for referencing later
    private static Pokedex pokedex;
    private static JTextPane display;
    private static JTextField input;

    //Sets the Current Pokémon to the first one in the list
    private static int currentPokemon = 0;

    //Variables that allow me to create a new Pokémon
    private static boolean creating = false;
    private static int creatingStep = -1;

    //Variables that store info about the Pokémon im creating
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
    private static Pokemon p;

    private static Pokemon pToFight1 = null;
    private static Pokemon pToFight2 = null;

    public static void main(String[] args) throws IOException {
        //Create a new window and set some of its properties
        JFrame frame = new JFrame("Pokédex");
        int width = 440;
        int height = 740;
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BufferedImage img = ImageIO.read(new File("src/assets/icon.png"));
        Image scaledImage = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        frame.setIconImage(scaledImage);

        //Create a panel with a background image
        JPanel panel = new JPanel() {
            final BufferedImage backgroundImage = ImageIO.read(new File("src/assets/background.png"));

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };

        //Create the large text box in the center, set some of its values, make the text centered, and add it to the panel
        display = new JTextPane();
        display.setBounds(30, 179, 383, 332);
        display.setFont(new Font("Arial", Font.PLAIN, 16));
        display.setEditable(false);
        display.setEnabled(false);
        display.setOpaque(false);
        display.setDisabledTextColor(Color.black);

        StyledDocument doc = display.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);

        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        display.setText("""
        Welcome to the Pokédex!
        To start, type the name of a pokémon in the search bar and press the search button!
        Or click either arrow to cycle through the pokémon.

        Press the reload button to come back to this page
        
        You can also press the "Camera" in the top right corner to create a new Pokémon or the red button at the top to simulate a battle between 2 pokémon
        """);
        panel.add(display);

        //Create the input field near the bottom of the screen
        input = new JTextField();
        input.setBounds(24, 549, 250, 89);
        input.setFont(new Font("Arial", Font.PLAIN, 30));
        input.setOpaque(false);
        panel.add(input);

        //Create the buttons, set their values, link them to their correlating methods, and add them to the panel
        //Search Button
        JButton searchButton = new JButton();
        searchButton.setOpaque(false);
        searchButton.setContentAreaFilled(false);
        searchButton.setBorderPainted(false);
        searchButton.setBounds(298, 537, 68, 61);
        searchButton.addActionListener(_ -> {
            try {
                searchPressed();
            } catch (IOException ex) {
                throw new RuntimeException(ex);

            }
        });
        panel.add(searchButton);

        //Left Arrow
        JButton leftArrowButton = new JButton();
        leftArrowButton.setOpaque(false);
        leftArrowButton.setContentAreaFilled(false);
        leftArrowButton.setBorderPainted(false);
        leftArrowButton.setBounds(298, 591, 68, 61);
        leftArrowButton.addActionListener(_ -> leftPressed());
        panel.add(leftArrowButton);

        //Right Arrow Button
        JButton rightArrowButton = new JButton();
        rightArrowButton.setOpaque(false);
        rightArrowButton.setContentAreaFilled(false);
        rightArrowButton.setBorderPainted(false);
        rightArrowButton.setBounds(365, 591, 68, 61);
        rightArrowButton.addActionListener(_ -> rightPressed());
        panel.add(rightArrowButton);

        //New Pokémon button
        JButton newButton = new JButton();
        newButton.setOpaque(false);
        newButton.setContentAreaFilled(false);
        newButton.setBorderPainted(false);
        newButton.setBounds(9, 5, 111, 111);
        newButton.addActionListener(_ -> newPokemon());
        panel.add(newButton);

        //Refresh button
        JButton refreshButton = new JButton();
        refreshButton.setOpaque(false);
        refreshButton.setContentAreaFilled(false);
        refreshButton.setBorderPainted(false);
        refreshButton.setBounds(365, 537, 68, 61);
        refreshButton.addActionListener(_ -> {
            try {
                refreshPressed();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        panel.add(refreshButton);

        //Battle button
        JButton battleButton = new JButton();
        battleButton.setOpaque(false);
        battleButton.setContentAreaFilled(false);
        battleButton.setBorderPainted(false);
        battleButton.setBounds(155, 12, 45, 45);
        battleButton.addActionListener(_ -> battlePressed());
        panel.add(battleButton);

        //Add the panel to the frame
        panel.setLayout(null);
        frame.add(panel);

        //Create a new Pokédex
        pokedex = new Pokedex();

        //Show the window
        frame.setVisible(true);
    }

    //Method for when the search button is pressed
    private static void searchPressed() throws IOException {
        //If we are creating a new Pokémon, call a different method
        if (creating){
            createSearchPressed();
            return;
        }
        //Search for the Pokémon with the name the user inputs
        Pokemon p = pokedex.getPokemon(input.getText());

        //Get the index of the Pokémon
        int i = pokedex.getPokemonIndex(p);

        //If i is -1, then the Pokémon doesn't exist
        if (i == -1){
            invalidPokemon();
        }
        //Otherwise, we update the current Pokémon, and set that text on screen
        else{
            currentPokemon = i;
            updateText();
        }

    }

    //Method for when the refresh button is pressed
    private static void refreshPressed() throws IOException {
        //If we are creating a new Pokémon, return (so nothing breaks)
        if (creating){
            return;
        }
        //The refresh not only refreshes the Pokédex, but also "resets" the program
        pokedex.parse();
        display.setText("""
        Welcome to the Pokédex!
        To start, type the name of a pokémon in the search bar and press the search button!
        Or click either arrow to cycle through the pokémon.

        Press the reload button to come back to this page
        
        You can also press the "Camera" in the top right corner to create a new Pokémon or the red button at the top to simulate a battle between 2 pokémon""");
        currentPokemon = 0;
    }

    //Method for when the left arrow is pressed
    private static void leftPressed(){
        //If we are creating a new Pokémon, return
        if (creating){
            return;
        }
        //Check if the active Pokémon is above zero, if so go back one Pokémon and update the screen
        if (currentPokemon > 0){
            currentPokemon--;
            updateText();
        }
        //If not, loop around to the END of the Pokémon list and update the screen
        else {
            currentPokemon = pokedex.getNumPokemon();
            updateText();
        }
    }

    //Method for when the right arrow is pressed
    private static void rightPressed(){
        //If we are creating a new Pokémon, return
        if (creating){
            return;
        }
        //Check if the active Pokémon is less than the max, if so go forward one Pokémon and update the screen
        if (currentPokemon < pokedex.getNumPokemon()){
            currentPokemon++;
            updateText();
        }
        //If not, loop around to the START of the Pokémon list and update the screen
        else if (currentPokemon == pokedex.getNumPokemon()){
            currentPokemon = 0;
            updateText();
        }
    }

    private static void battlePressed() {
        //If we are creating a new Pokémon, return
        if (creating){
            return;
        }

        if (pToFight1 == null){
            pToFight1 = pokedex.getPokemon(currentPokemon);
            display.setText("Set " + pToFight1.getName() + " as the first pokémon to battle!");

        }
        else if (pToFight2 == null){
            pToFight2 = pokedex.getPokemon(currentPokemon);
            display.setText("Set " + pToFight2.getName() + " as the second pokémon to battle!\n\nClick the battle button one more time to commence the battle.");
        }
        else{
            Battle b = new Battle(pToFight1, pToFight2);
            display.setText(b.start());
            pToFight1 = null;
            pToFight2 = null;
        }
    }

    private static void newPokemon(){
        //If we are creating a new Pokémon, return
        if (creating){
            return;
        }
        //Set creating to true which stops all other buttons from working, set the text to the start of the "Create a Pokémon" phase, and increments creatingSteps by 1
        creating = true;
        display.setText("""
                Welcome to "Create-a-Pokémon"
                Press the search button to continue""");
        creatingStep += 1;
    }

    /*
   Preconditions: creating must be true
   Post condition: Steps are taken to create a Pokémon
    */
    private static void createSearchPressed() throws IOException {
        //This method only runs when we are creating
        //The creating just started
        if (creatingStep == 0){
            display.setText("""
                Enter the Pokémon's name into the search bar
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        //Get Pokémon's name
        else if (creatingStep == 1){
            name = input.getText();
            input.setText("");
            display.setText("""
                Enter the Pokémon's Japanese Name into the search bar
                (You can leave this blank if you would like)
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        //Get Pokémon's japanese name
        else if (creatingStep == 2){
            jName = input.getText();
            input.setText("");
            display.setText("""
                Enter the Pokémon's HP into the search bar
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        //Get Pokémon's HP
        else if (creatingStep == 3){
            hp = Integer.parseInt(input.getText());
            input.setText("");
            display.setText("""
                Enter the Pokémon's Defense into the search bar
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        //Get Pokémon's defense
        else if (creatingStep == 4){
            defense = Integer.parseInt(input.getText());
            input.setText("");
            display.setText("""
                Enter the Pokémon's Attack Power into the search bar
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        //Get Pokémon's attack
        else if (creatingStep == 5){
            attack = Integer.parseInt(input.getText());
            input.setText("");
            display.setText("""
                Enter the Pokémon's Abilities into the search bar
                (To keep consistency, enter each ability separated with a comma)
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        //Get Pokémon's abilities
        else if (creatingStep == 6){
            abilities = input.getText();
            input.setText("");
            display.setText("""
                Enter the Pokémon's height (in m) into the search bar
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        //Get Pokémon's height
        else if (creatingStep == 7){
            height = Double.parseDouble(input.getText());
            input.setText("");
            display.setText("""
                Enter the Pokémon's weight (in kg) into the search bar
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        //Get Pokémon's weight
        else if (creatingStep == 8){
            weight = Double.parseDouble(input.getText());
            input.setText("");
            display.setText("""
                Enter the Pokémon's main type into the search bar
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        //Get Pokémon's main type
        else if (creatingStep == 9){
            type1 = input.getText();
            input.setText("");
            display.setText("""
                Enter the Pokémon's secondary type into the search bar
                (Leave this blank if the pokémon has no secondary type)
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        //Get Pokémon's secondary type
        else if (creatingStep == 10){
            type2 = input.getText();
            input.setText("");
            display.setText("""
                Enter the Pokémon's classification into the search bar
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        //Get Pokémon's classification
        else if (creatingStep == 11){
            classification = input.getText();
            input.setText("");
            display.setText("""
                Enter the Pokémon's generation into the search bar
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        //Get Pokémon's generation
        else if (creatingStep == 12){
            generation = Integer.parseInt(input.getText());
            input.setText("");
            display.setText("""
                You will see the info for your pokémon on the next screen. Press the search button to confirm.
                
                Press the search button when you are ready to continue""");
            creatingStep++;
        }
        //Display the Pokémon's info to the user, to confirm
        else if (creatingStep == 13){
            p = new Pokemon(name, jName, hp, defense, attack, abilities, height,
                    weight, type1, type2, classification, generation);

            display.setText(p.toString());
            creatingStep++;
        }
        //Create a new Pokémon in the json with all the info we collected
        else if (creatingStep == 14){
            pokedex.createPokemon(p);
            creatingStep = -1;
            creating = false;
            updateText();
        }
    }

    //Sets the text in the center of the screen to info about the current Pokémon
    private static void updateText(){
        Pokemon p = pokedex.getPokemon(currentPokemon);

        display.setText(p.toString());
        input.setText("");
    }

    //This gets called when the Pokémon the user searches for can't be found. Sets the center text
    private static void invalidPokemon() {
        display.setText("Unknown Pokémon: \"" + input.getText() +"\".\n\nMake sure you spelled the name of the pokémon correctly.\n(\"Charzard\" and \"Charizard\" are not the same)\n\nYou can also use the arrow keys to traverse the Pokémon");
    }
}