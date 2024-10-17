package files;

import org.json.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

public class Pokedex {

    Pokemon[] pokemon;

    public Pokedex() throws IOException {
        parse();
    }

    public void parse() throws IOException {
        String file = Files.readString(Path.of("src/data/pokemon.json"));

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


            pokemon[i] = new Pokemon(name, jName, hp, defense, attack, ability, height,
                    weight, type1, type2, classification, generation);
        }
    }

    public int search(String s) {
        int counter = 0;
        for (Pokemon p : pokemon) {
            if (p.getName().equalsIgnoreCase(s)) {
                return counter;
            }
            counter++;
        }
        return -1;
    }

    public Pokemon getPokemon(int i) {
        return pokemon[i];
    }

    public Pokemon getPokemon(String s) {
        if (search(s) == -1) {
            return null;
        }
        return pokemon[search(s)];
    }

    public int getNumPokemon() {
        return pokemon.length - 1;
    }

    public int getPokemonIndex(Pokemon p) {
        for (int i = 0; i < pokemon.length - 1; i++) {
            if (p == pokemon[i]) {
                return i;
            }
        }
        return -1;
    }

    public void createPokemon(Pokemon _p) throws IOException {

        Pokemon[] newArray = new Pokemon[pokemon.length + 1];
        newArray[pokemon.length] = _p;

        for (int i = 0; i < pokemon.length; i++) {
            newArray[i] = pokemon[i];
        }

        JSONArray jsonArray = new JSONArray();

        for (Pokemon p : pokemon) {
            JSONObject obj = new JSONObject();
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

            jsonArray.put(obj);
        }

        Files.writeString(Path.of("src/data/pokemon.json"), jsonArray.toString(2));

        parse();
    }
}