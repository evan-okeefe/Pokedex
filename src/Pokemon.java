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

    @Override
    public String toString() {
        return name +":\n" +
                "   Japanese Name = " + jName + '\n' +
                "   hp = " + hp + '\n' +
                "   defense = " + defense+ '\n' +
                "   attack = " + attack + '\n' +
                "   abilities = " + abilities + '\n' +
                "   height = " + height + "m\n" +
                "   weight = " + weight + "kg\n" +
                "   type1 = " + type1 + '\n' +
                "   type2 = " + type2 + '\n' +
                "   classification = " + classification + '\n' +
                "   generation = " + generation;
    }
}
