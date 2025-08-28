import java.util.Scanner;
public class KnockKnock {
    public static void main(String args[]) {
        Scanner inputGetter = new Scanner(System.in);
        String response;
        System.out.println("knock knock");
        response = inputGetter.nextLine();
        System.out.println("Dwayne.");
        response = inputGetter.nextLine();
        System.out.println("Dwayne the bathtub, please. I'm drowning!");
    }
}
