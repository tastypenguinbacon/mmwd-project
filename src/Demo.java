import SimulatedAnnealing.SimulatedAnnealingSolver;

import java.util.Random;

/**
 * Created by user on 26.11.16.
 */
public class Demo {
    private static final String optimal = "abcdefghijklmnopqrstuvwxyz";

    private static int hammingDistance(String first, String second){
        if (first.length() != second.length())
            return -1;

        int res = 0;
        for(int i = 0; i < first.length(); i++)
            res += (first.charAt(i) == second.charAt(i)) ? 0 : 1;
        return res;
    }
    public static double Value(String str){
        return hammingDistance(optimal, str);
    }

    private static String mutate(String str){
        Random r1 = new Random();
        int position = r1.nextInt(str.length());

        Random r2 = new Random();
        char ch = (char)(r2.nextInt(26) + 'a');

        StringBuilder newStr = new StringBuilder(str);
        newStr.setCharAt(position, ch);

        return newStr.toString();
    }

    public static void main(String[] args) {

        SimulatedAnnealingSolver<String> solver = new SimulatedAnnealingSolver<String>(
                "kmgniwsodfhydmsferinoqtngh",
                Demo::mutate,
                (String str) -> (double) hammingDistance(str, optimal),
                10.0,
                2500,
                0.97);

        System.out.println(optimal);
        solver.find();

//        System.out.println("\n");
//        System.out.println(mutate("abc"));
//        System.out.println((char) (-1 + 'a'));
//        System.out.println("\n");
//        Random r2 = new Random();
//        StringBuilder newStr = new StringBuilder("abc");
//        newStr.setCharAt(0, '1');
//        newStr.setCharAt(1, 'Z');
//        newStr.setCharAt(2, 'q');
//        System.out.println(newStr.toString());
//        for(int i = 0; i < 100; i++)
//            System.out.print(r2.nextInt(new String("abc").length()) + " ");

    }
}
