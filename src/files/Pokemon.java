package files;

public class Pokemon {
    //All the info that the pokemon needs to store
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

    /*
    Preconditions: All the parameters must be not null
    Postcondition: A pokemon is created
     */
    public Pokemon(String _name, String _jName, int _hp, int _defense, int _attack, String _abilities, double _height, double _weight, String _type1, String _type2, String _classification, int _generation) {
        //Initialize all the variables
        name = _name;
        jName = _jName;
        hp = _hp;
        defense = _defense;
        attack = _attack;
        abilities = _abilities;
        height = _height;
        weight = _weight;
        type1 = _type1;
        //I don't actually know why I put this here...
        if (_type2.isEmpty()){
            type2 = "n/a";
        }
        else{
            type2 = _type2;
        }
        classification = _classification;
        generation = _generation;
    }

    //I did this one for the credit
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

    //Returns the name
    public String getName() {
        return name;
    }

    //Returns the japanese name
    public String getjName() {
        return jName;
    }

    //Returns the hp
    public int getHp() {
        return hp;
    }

    //Returns the defense
    public int getDefense() {
        return defense;
    }

    //Returns the attack
    public int getAttack() {
        return attack;
    }

    //Returns the abilites
    public String getAbilities() {
        return abilities;
    }

    //Returns the height
    public double getHeight() {
        return height;
    }

    //Returns the weight
    public double getWeight() {
        return weight;
    }

    //Returns the main type
    public String getType1() {
        return type1;
    }

    //Returns the secondary type
    public String getType2() {
        return type2;
    }

    //Returns the classification
    public String getClassification() {
        return classification;
    }

    //Returns the generation
    public int getGeneration() {
        return generation;
    }

    //I made this so I could make the abillities that looked like "['ability 1', 'ability 2']" look like "ability 1, ability 2"
    private String clean(String s){
        return s.replace("'", "").replace("[", "").replace("]", "");

    }

    //Takes a double and removes the ".0" from the end if it is a whole number (like 5.0)
    private String clean(double i){
        //i % 1 would only be a non-zero number if there is a decimal at the end
        if (i % 1 == 0) {
            return String.valueOf((int) i);
        } else {
            return String.valueOf(i);
        }
    }

    //This is the method I call to put the info in the center of the screen
    @Override
    public String toString() {
        //If the secondary type is "n/a", then I don't put the secondary type on the screen
        if (type2.equals("n/a")){
            return name + "\n(" + jName + ")\n\n"+ clean(height) + "m, " + clean(weight) +"kg\n" + hp + "h/" + defense + "d/" + attack + "a\nAbilities:\n" + clean(abilities) + "\n\n" + name + ", a " + classification + ", is a gen " + generation + " pokémon. This is a " + type1 + " type.";
        }
        return name + "\n(" + jName + ")\n\n"+ clean(height) + "m, " + clean(weight) +"kg\n" + hp + "h/" + defense + "d/" + attack + "a\nAbilities:\n" + clean(abilities) + "\n\n" + name + ", a " + classification + ", is a gen " + generation + " pokémon. This is a " + type1 + "/" + type2 + " type.";
    }
}
