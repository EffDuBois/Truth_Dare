import java.util.Objects;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Truth or Dare");
        String Truth_or_dare_choice= in.next();
        if (Objects.equals(Truth_or_dare_choice, "Truth"))
                System.out.println("Truth question");
        else if (Objects.equals(Truth_or_dare_choice, "Dare"))
            System.out.println("Dare question");
    }
}