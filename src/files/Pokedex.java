package files;

import org.json.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

public class Pokedex {

    //Save 2 arrays (I rlly only need one, but need full credit on this project)
    Pokemon[] pokemon;
    Pokemon[] grassTypes;

    //When a new pokedex is created, parse the JSON file
    public Pokedex() throws IOException {
        parse();
    }

    /*
    Holy crap was this function difficult. I copied this from one of my old projects, and
    I forgot how much of a headache I gwt when attempting to visualize parsing JSON data

    I'm going to try to explain this as best as I can, but no promises it'll make sense
    */
    public void parse() throws IOException {
        //First I load up the file
        String file = Files.readString(Path.of("src/data/pokemon.json"));

        //Then I turn the file into an Array of JSON objects
        JSONArray jsonArray = new JSONArray(file);

        //I set my pokemon list length to how many objects are in the JSONArray
        pokemon = new Pokemon[jsonArray.length()];

        //I loop through each JSON Object
        for (int i = 0; i < jsonArray.length(); i++) {
            //Save a variable so I can access all the goodies inside the object
            JSONObject obj = jsonArray.getJSONObject(i);
            //I get all the info I am using, and save them in variables
            String name = obj.getString("name");
            String jName = obj.getString("japanese_name");
            int hp = obj.getInt("hp");
            int defense = obj.getInt("defense");
            int attack = obj.getInt("attack");
            String ability = obj.getString("abilities");
            double height = 0;

            //For some reason the data for height and weight in this set were mixed
            //Some was a BigDecimal, some was an int, and some was a String
            //So these 2 sets of if blocks save the data as the proper type
            if (obj.get("height_m") instanceof BigDecimal) {
                height = obj.getBigDecimal("height_m").doubleValue();
            } else if (obj.get("height_m") instanceof Integer) {
                height = (double) obj.getInt("height_m");
            } else if (obj.get("height_m") instanceof String) {
                height = 0.0;
            } else {
                System.out.println("Failed to add height_m of " + i);
            }

            double weight = 0;
            if (obj.get("weight_kg") instanceof BigDecimal) {
                weight = obj.getBigDecimal("height_m").doubleValue();
            } else if (obj.get("weight_kg") instanceof Integer) {
                weight = (double) obj.getInt("height_m");
            } else if (obj.get("weight_kg") instanceof String) {
                weight = 0.0;
            } else {
                System.out.println("Failed to add weight_kg of " + i);
            }

            String type1 = obj.getString("type1");
            String type2 = obj.getString("type2");
            String classification = obj.getString("classfication");
            int generation = obj.getInt("generation");


            //I now set the pokemon at the active index equal to a new pokemon with all
            //the information I got from the JSON file
            pokemon[i] = new Pokemon(name, jName, hp, defense, attack, ability, height,
                    weight, type1, type2, classification, generation);
        }

        //Now I see how many grass type pokemon there are
        int count = 0;
        for (Pokemon p : pokemon){
            if (p.getType1().equals("grass")) {
                count++;
            }
        }
        //I initialize the grassTypes list with the right amount of spots
        grassTypes = new Pokemon[count];
        //Then loop through all the pokemon, adding the grass types
        int i = 0;
        for (Pokemon p : pokemon){
            if (p.getType1().equals("grass")) {
                grassTypes[i] = p;
                i++;
            }
        }
    }

    //This function returns an index of where a specific pokemon is
    public int search(String s) {
        int counter = 0;
        //Loop through all the pokemon and return the index if it is the right one
        for (Pokemon p : pokemon) {
            if (p.getName().equalsIgnoreCase(s)) {
                return counter;
            }
            counter++;
        }
        //Otherwise, return -1 (which i use to check for invalid searches in Main)
        return -1;
    }

    //Returns the pokemon at the index passed in
    public Pokemon getPokemon(int i) {
        return pokemon[i];
    }

    //calls search and returns the pokemon at the index returned
    public Pokemon getPokemon(String s) {
        if (search(s) == -1) {
            return null;
        }
        return pokemon[search(s)];
    }

    //Gets the total number of pokemon (minus 1 so i dont have to do that in other places)
    public int getNumPokemon() {
        return pokemon.length - 1;
    }

    //Gets the index of a pokemon that is passed in, by looping theough and returning when the pokemon at index i matches the pokemon passed in
    public int getPokemonIndex(Pokemon p) {
        for (int i = 0; i < pokemon.length - 1; i++) {
            if (p == pokemon[i]) {
                return i;
            }
        }
        return -1;
    }

    /*
    So remember when I said the parse function hurt my head? This one is worse.
    This is the function that gets called when I want to add another pokemon to the JSON file.
    Mr. Guillaume, if you are reading this, NEVER make your kids learn how to do JSON.
    Please. I'm begging you
     */
    public void createPokemon(Pokemon _p) throws IOException {

        //We start by making a new array, thats equal to 1 MORE than the pokemon array
        Pokemon[] newArray = new Pokemon[pokemon.length + 1];
        newArray[pokemon.length] = _p;

        //Then we add all the old pokemon into the new list.
        for (int i = 0; i < pokemon.length; i++) {
            newArray[i] = pokemon[i];
        }

        //And it's our best friend JSONArray again!
        JSONArray jsonArray = new JSONArray();

        //We loop through each pokemon
        for (Pokemon p : pokemon) {
            //For each pokemon we make a new JSON Object
            JSONObject obj = new JSONObject();
            //We then put ALL OF THE DATA from the pokemon into the JSON object
            obj.put("name", p.getName());
            obj.put("japanese_name", p.getjName());
            obj.put("hp", p.getHp());
            obj.put("defense", p.getDefense());
            obj.put("attack", p.getAttack());
            obj.put("abilities", p.getAbilities());
            obj.put("height_m", p.getHeight());
            obj.put("weight_kg", p.getWeight());
            obj.put("type1", p.getType1());
            obj.put("type2", p.getType2());
            obj.put("classfication", p.getClassification());
            obj.put("generation", p.getGeneration());

            //Then we add this object to the array and do it all again
            jsonArray.put(obj);
        }

        //Finally we write the JSONArray into a file
        Files.writeString(Path.of("src/data/pokemon.json"),
                jsonArray.toString(2));

        //And call everyone's favorite function again
        parse();
    }

    //I dont rlly need this, but I want the credit
    public int countGrassTypes() {
        return grassTypes.length;
    }

    //I dont need this either, but I just want to be safe and make sure i get all the credit
    public Pokemon searchGrassType(String s) {
        for (Pokemon p : grassTypes) {
            if (p.getName().equalsIgnoreCase(s)) {
                return p;
            }
        }
        return null;
    }
}