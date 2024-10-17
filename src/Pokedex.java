import org.json.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Pokedex {

    Pokemon[] pokemon;

    public Pokedex() throws IOException {
        String file = Files.readString(Path.of("src/pokemon.json"));

        JSONArray jsonArray = new JSONArray(file);

        pokemon = new Pokemon[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String name = obj.getString("name");
            String jName = obj.getString("japanese_name");
            int hp = obj.getInt("hp");
            int defense = obj.getInt("defense");
            int attack = obj.getInt("attack");
            String ability = obj.getString("abilities");

            double height = 0;
            if (obj.get("height_m") instanceof BigDecimal){
                height = obj.getBigDecimal("height_m").doubleValue();
            }
            else if (obj.get("height_m") instanceof Integer) {
                height = (double) obj.getInt("height_m");
            }
            else if (obj.get("height_m") instanceof String) {
                height = 0.0;
            }
            else{
                System.out.println("Failed to add height_m of " + i);
            }

            double weight = 0;
            if (obj.get("weight_kg") instanceof BigDecimal){
                weight = obj.getBigDecimal("height_m").doubleValue();
            }
            else if (obj.get("weight_kg") instanceof Integer) {
                weight = (double) obj.getInt("height_m");
            }
            else if (obj.get("weight_kg") instanceof String) {
                weight = 0.0;
            }else{
                System.out.println("Failed to add weight_kg of " + i);
            }

            String type1 = obj.getString("type1");
            String type2 = obj.getString("type2");
            String classification = obj.getString("classfication");
            int generation = obj.getInt("generation");


            pokemon[i] = new Pokemon(name, jName, hp, defense, attack, ability, height,
                    weight, type1, type2, classification, generation);
        }
    }


    public int search(String s){
        int counter = 0;
        for (Pokemon p : pokemon){
            if (p.getName().equals(s)){
                return counter;
            }
            counter++;
        }
        return -1;
    }

    public Pokemon getPokemon(int i){
        return pokemon[i];
    }

    public Pokemon getPokemon(String s){
        return pokemon[search(s)];
    }
}