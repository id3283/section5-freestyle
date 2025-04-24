import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        ArrayList<Double> scores = new ArrayList<>();

        HashMap<String, Double> studentScores = new HashMap<>();
        studentScores.put("Mo", 4.0);
        studentScores.put("Dave", 2.5);

        Double score = studentScores.get("Victoria");
        System.out.println(score);


    }
}
