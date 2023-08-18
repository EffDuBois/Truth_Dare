import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Questions {
    public String question(Boolean type) throws IOException {
        Random rand=new Random();
        //getting the list of questions from the method
        String[] list = getQuestionList(type);
        //choosing a random question
        return list[rand.nextInt(list.length)];
    }

    private String[] getQuestionList(Boolean type) throws IOException {
        File file;
        //choosing file depending on the player choice param
        if (type){
            //used file class to get a file object path
            file = new File("D:\\Code\\Projects\\Others\\Truth_Dare\\src\\Question_list\\Truth.txt");
        }else{
            file = new File("D:\\Code\\Projects\\Others\\Truth_Dare\\src\\Question_list\\Dare.txt");
        }
        //used bufferedreader class to read from the file line by line
        Scanner in = new Scanner(file);
        BufferedReader br= new BufferedReader(new FileReader(file));
        String st;
        //used an arraylist to read and append all the lines one by one to an array
        ArrayList<String> List = new ArrayList<>();
        while ((st = br.readLine()) != null){
            List.add(st);
        }
        //converted the arraylist to a normal string array and returned
        String[] Static_List = new String[List.size()];
        return List.toArray(Static_List);
    }
}
