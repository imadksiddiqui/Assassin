
import java.io.*;
import java.util.*;

public class AssassinMain {


    public static final boolean RANDOM = true;

    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("names.txt");
        if (!inputFile.canRead()) {
            System.out.println("Required input file not found; exiting.\n" + inputFile.getAbsolutePath());
            System.exit(1);
        }
        Scanner input = new Scanner(inputFile);

        Set<String> names = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        while (input.hasNextLine()) {
            String name = input.nextLine().trim().intern();
            if (name.length() > 0) {
                names.add(name);
            }
        }
        input.close();

        ArrayList<String> nameList = new ArrayList<>(names);
        Random rand = new Random();
        Collections.shuffle(nameList, rand);

        Assassin assassin = new Assassin(nameList);

        Scanner console = new Scanner(System.in);
        while (!assassin.isGameOver()) {
            oneKill(console, assassin);
        }

        System.out.println("Game was won by " + assassin.winner());
        System.out.println();
        System.out.println("Final graveyard is as follows:");
        System.out.println(assassin.graveyard());
    }

    public static void oneKill(Scanner console, Assassin assassin) {
        System.out.println("Current kill ring:");
        System.out.println(assassin.killRing());
        System.out.println("Current graveyard:");
        System.out.println(assassin.graveyard());

        System.out.println();
        System.out.print("next victim? ");
        String name = console.nextLine().trim();

        if (assassin.graveyardContains(name)) {
            System.out.println(name + " is already dead.");
        } 
        else if (!assassin.killRingContains(name)) {
            System.out.println("Unknown person.");
        } 
        else {
            assassin.kill(name);
        }
        System.out.println();
    }
}
