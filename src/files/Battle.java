package files;

import java.util.Random;

public class Battle {

    private final Pokemon p1;
    private final Pokemon p2;

    private double health1;
    private double health2;

    public Battle(Pokemon p1, Pokemon p2) {
        this.p1 = p1;
        this.p2 = p2;

        health1 = p1.getHp();
        health2 = p2.getHp();
    }

    public String start(){
        Random r = new Random();

        StringBuilder output = new StringBuilder();

        Pokemon currentAttacker = r.nextBoolean() ? p1 : p2;
        Pokemon currentDefender = (currentAttacker == p1) ? p2 : p1;

        double defense1 = p1.getDefense();
        double defense2 = p2.getDefense();

        while (health1 > 0 && health2 > 0) {

            if (currentAttacker == p1){
                double damage =
                        (currentAttacker.getAttack() * getTypeEffectiveness(
                                currentAttacker.getType1(), currentDefender.getType1())
                        ) - defense2;

                if (damage < 0){
                    damage = 0;
                    defense2 -= (currentAttacker.getAttack() * getTypeEffectiveness(
                            currentAttacker.getType1(), currentDefender.getType1()));
                }

                output.append(p1.getName()).append(" does ").append(damage).append(" damage to ").append(p2.getName()).append("\n");
                health2 -= damage;
            }
            else{
                double damage =
                        (currentAttacker.getAttack() * getTypeEffectiveness(
                                currentAttacker.getType1(), currentDefender.getType1())
                        ) - defense1;

                if (damage < 0){
                    damage = 0;
                    defense1 -= (currentAttacker.getAttack() * getTypeEffectiveness(
                            currentAttacker.getType1(), currentDefender.getType1()));
                }

                output.append(p2.getName()).append(" does ").append(damage).append(" damage to ").append(p1.getName()).append("\n");
                health1 -= damage;
            }

            Pokemon temp = currentAttacker;
            currentAttacker = currentDefender;
            currentDefender = temp;
        }

        if (health1 <= 0) {
            output.append(p2.getName()).append(" wins!").append("\n");
            output.append(p1.getName()).append(" died").append("\n");
            output.append(p2.getName()).append(" had ").append(health2).append("\n");
        }
        else {
            output.append(p1.getName()).append(" wins!").append("\n");
            output.append(p2.getName()).append(" died").append("\n");
            output.append(p2.getName()).append(" had ").append(health2).append("\n");
        }
        return output.toString();
    }

    private static double getTypeEffectiveness(String attackType, String defendType) {
        return switch (attackType) {
            case "normal" -> switch (defendType) {
                case "fire", "water", "rock", "dragon" -> 0.5;
                case "grass", "ice", "bug", "steel" -> 2;
                default -> 1;
            };
            case "fire" -> switch (defendType) {
                case "rock", "steel" -> 0.5;
                case "ghost" -> 0;
                default -> 1;
            };
            case "water" -> switch (defendType) {
                case "water", "grass", "dragon" -> 0.5;
                case "fire", "ground", "rock" -> 2;
                default -> 1;
            };
            case "grass" -> switch (defendType) {
                case "fire", "grass", "poison", "flying", "bug", "dragon", "steel" -> 0.5;
                case "water", "ground", "rock" -> 2;
                default -> 1;
            };
            case "electric" -> switch (defendType) {
                case "grass", "electric", "dragon" -> 0.5;
                case "water", "flying" -> 2;
                case "ground" -> 0;
                default -> 1;
            };
            case "ice" -> switch (defendType) {
                case "fire", "water", "ice", "steel" -> 0.5;
                case "grass", "ground", "flying", "dragon" -> 2;
                default -> 1;
            };
            case "fighting" -> switch (defendType) {
                case "poison", "flying", "psychic", "bug", "fairy" -> 0.5;
                case "normal", "ice", "rock", "dark", "steel" -> 2;
                default -> 1;
            };
            case "poison" -> switch (defendType) {
                case "grass", "fairy" -> 0.5;
                case "poison", "ground", "rock", "ghost" -> 2;
                case "steel" -> 0;
                default -> 1;
            };
            case "ground" -> switch (defendType) {
                case "grass", "bug" -> 0.5;
                case "fire", "electric", "poison", "rock", "steel" -> 2;
                case "flying" -> 0;
                default -> 1;
            };
            case "flying" -> switch (defendType) {
                case "electric", "rock", "steel" -> 0.5;
                case "grass", "fighting", "bug" -> 2;
                default -> 1;
            };
            case "psychic" -> switch (defendType) {
                case "psychic", "steel" -> 0.5;
                case "fighting", "poison" -> 2;
                case "dark" -> 0;
                default -> 1;
            };
            case "bug" -> switch (defendType) {
                case "fire", "fighting", "poison", "flying", "ghost", "steel", "fairy" -> 0.5;
                case "grass", "psychic", "dark" -> 2;
                default -> 1;
            };
            case "rock" -> switch (defendType) {
                case "fighting", "ground", "steel" -> 0.5;
                case "fire", "ice", "flying", "bug" -> 2;
                default -> 1;
            };
            case "ghost" -> switch (defendType) {
                case "dark" -> 0.5;
                case "psychic", "ghost" -> 2;
                case "normal" -> 0;
                default -> 1;
            };
            case "dragon" -> switch (defendType) {
                case "steel" -> 0.5;
                case "dragon" -> 2;
                case "fairy" -> 0;
                default -> 1;
            };
            case "dark" -> switch (defendType) {
                case "fighting", "dark", "fairy" -> 0.5;
                case "ghost", "psychic" -> 2;
                default -> 1;
            };
            case "steel" -> switch (defendType) {
                case "fire", "water", "electric" -> 0.5;
                case "ice", "rock", "fairy" -> 2;
                default -> 1;
            };
            case "fairy" -> switch (defendType) {
                case "fire", "poison", "steel" -> 0.5;
                case "fighting", "dragon", "dark" -> 2;
                default -> 1;
            };

            default -> 1.0;
        };

    }
}
