import org.json.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Pokedex {

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> jNames = new ArrayList<>();
    private ArrayList<Integer> hp = new ArrayList<>();
    private ArrayList<Integer> defense = new ArrayList<>();
    private ArrayList<Integer> attack = new ArrayList<>();
    private ArrayList<String> abilities = new ArrayList<>();
    private ArrayList<Double> height = new ArrayList<>();
    private ArrayList<Double> weight = new ArrayList<>();
    private ArrayList<String> type1 = new ArrayList<>();
    private ArrayList<String> type2 = new ArrayList<>();
    private ArrayList<String> classification = new ArrayList<>();
    private ArrayList<Integer> generation = new ArrayList<>();


    public Pokedex() throws IOException {
        String file = Files.readString(Path.of("src/pokemon.json"));

        JSONArray jsonArray = new JSONArray(file);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            names.add(obj.getString("name"));
            jNames.add(obj.getString("japanese_name"));
            hp.add(obj.getInt("hp"));
            defense.add(obj.getInt("defense"));
            attack.add(obj.getInt("attack"));
            abilities.add(obj.getString("abilities"));
            if (obj.get("height_m") instanceof BigDecimal){
                height.add(obj.getBigDecimal("height_m").doubleValue());
            }
            else if (obj.get("height_m") instanceof Integer) {
                height.add((double) obj.getInt("height_m"));
            }
            else if (obj.get("height_m") instanceof String) {
                height.add(0.0);
            }
            else{
                System.out.println("Failed to add height_m of " + i);
            }

            if (obj.get("weight_kg") instanceof BigDecimal){
                height.add(obj.getBigDecimal("height_m").doubleValue());
            }
            else if (obj.get("weight_kg") instanceof Integer) {
                height.add((double) obj.getInt("height_m"));
            }
            else if (obj.get("weight_kg") instanceof String) {
                height.add(0.0);
            }else{
                System.out.println("Failed to add weight_kg of " + i);
            }
            type1.add(obj.getString("type1"));
            type2.add(obj.getString("type2"));
            classification.add(obj.getString("classfication"));
            generation.add(obj.getInt("generation"));
        }
        System.out.println("Finished!");
    }

    public int search(String s){
        for (String n : names){
            if (n.equals(s)){
                return names.indexOf(n);
            }
        }
        return -1;
    }
}