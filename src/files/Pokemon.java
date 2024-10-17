package files;

public class Pokemon {
    private String name;
    private String jName;
    private int hp;
    private int defense;
    private int attack;
    private String abilities;
    private double height;
    private double weight;
    private String type1;
    private String type2;
    private String classification;
    private int generation;

    public Pokemon(String _name, String _jName, int _hp, int _defense, int _attack, String _abilities, double _height, double _weight, String _type1, String _type2, String _classification, int _generation) {
        name = _name;
        jName = _jName;
        hp = _hp;
        defense = _defense;
        attack = _attack;
        abilities = _abilities;
        height = _height;
        weight = _weight;
        type1 = _type1;
        if (_type2.isEmpty()){
            type2 = "n/a";
        }
        else{
            type2 = _type2;
        }
        classification = _classification;
        generation = _generation;
    }

    public Pokemon() {
        name = "unknown";
        jName = "unknown";
        hp = 0;
        defense = 0;
        attack = 0;
        abilities = "unknown";
        height = 0.0;
        weight = 0.0;
        type1 = "unknown";
        type2 = "unknown";
        classification = "unknown";
        generation = 0;
    }

    public String getName() {
        return name;
    }

    public String getjName() {
        return jName;
    }

    public int getHp() {
        return hp;
    }

    public int getDefense() {
        return defense;
    }

    public int getAttack() {
        return attack;
    }

    public String getAbilities() {
        return abilities;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    public String getClassification() {
        return classification;
    }

    public int getGeneration() {
        return generation;
    }

    private String clean(String s){
        return s.replace("'", "").replace("[", "").replace("]", "");

    }

    private String clean(double i){
        if (i % 1 == 0) {
            return String.valueOf((int) i); // Remove decimal if it's a whole number
        } else {
            return String.valueOf(i); // Keep decimal if it's not
        }
    }

    @Override
    public String toString() {
        if (type2.equals("n/a")){
            return name + "\n(" + jName + ")\n\n"+ clean(height) + "m, " + clean(weight) +"kg\n" + hp + "h/" + defense + "d/" + attack + "a\nAbilities:\n" + clean(abilities) + "\n\n" + name + ", a " + classification + ", is a gen " + generation + " pokémon. This is a " + type1 + " type.";
        }
        return name + "\n(" + jName + ")\n\n"+ clean(height) + "m, " + clean(weight) +"kg\n" + hp + "h/" + defense + "d/" + attack + "a\nAbilities:\n" + clean(abilities) + "\n\n" + name + ", a " + classification + ", is a gen " + generation + " pokémon. This is a " + type1 + "/" + type2 + " type.";
    }
}
