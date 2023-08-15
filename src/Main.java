import com.sun.source.tree.WhileLoopTree;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        //we use scanner class here
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the number of players");
        int n=in.nextInt();
        //we are making use of a custom class - players here
        Players[] players = new Players[n];
        System.out.println(("Enter the names of the players"));
        for (int i = 0; i < n; i++) {
            //calling the constructor of player class by passing on the names
            players[i]=new Players(in.next());
        }
        Random random = new Random();
        while (true){
            System.out.println("Spinning the bottle....");
            //using timeunit sleep method to wait for a duration
            TimeUnit.SECONDS.sleep(random.nextInt(2,5));
            //using random class to choose a random player
            int i=random.nextInt(n);
            System.out.println("It's " + players[i].name + "'s turn!");
            //using custom question class to create a question object
            //which is used to get a question from selected list
            Questions question = new Questions();

            System.out.println("Truth or Dare (0 to exit)");
            String Truth_or_dare_choice = in.next();
            //using the object class to check if the input is equal to choice
            if (Objects.equals(Truth_or_dare_choice.toLowerCase(), "truth")) {
                System.out.println(question.question(true));
                System.out.println("enter 1.Answered 2.Not answered");
            }
            else if (Objects.equals(Truth_or_dare_choice.toLowerCase(), "dare")) {
                System.out.println(question.question(false));
                System.out.println("enter 1.Done 2.Not Done");
            }
            else if (Objects.equals(Truth_or_dare_choice, "0")) {
                break;
            } else
                System.out.println("Invalid choice, try again");

            int done=in.nextInt();
            if(done==1){
                players[i].points+=1;
            }
            for (int j = 0; j < n; j++) {
                //calling object to update points
                System.out.println(players[j].name+" : "+players[j].points);
            }

        }
    }
}