import java.io.*;
import java.util.Scanner;

public class Main {

    public static int Greedy(int year, int promo, int coach, int[] demands, int[] salaries) {
        int minCost = 0, diff, remaining = 0, nextDiff = 0, cost = 0, cost_1, cost_2 = 0;
        boolean is_there_remaining = false;

        for (int i = 1; i <= year; i++) {
            diff = demands[i] - promo;

            if (is_there_remaining) {
                diff -= remaining;
                is_there_remaining = false;
            } else
                remaining = 0;

            if (diff > 0)
                minCost += coach * diff;

             else if (diff < 0) {
                    nextDiff = demands[i + 1] - promo;

                    if (nextDiff > 0) {
                        cost_2 = 0;
                        diff *= -1;
                        cost = coach * nextDiff;

                        for (int j = 1; j <= nextDiff; j++) {
                            cost_1 = salaries[j] + ((nextDiff - j) * coach) + ((j - diff) * coach);

                            if(cost_1 < cost) {
                                if(j > diff)
                                    cost_2 = (j - diff) * coach + salaries[j];
                                else
                                    cost_2 = salaries[j];

                                is_there_remaining = true;
                                remaining = j;
                            }
                        }
                        minCost += cost_2;
                    }

            }
        }
        return minCost;
    }


    public static void main(String[] args) throws FileNotFoundException {
        int n = 50, p = 7, c = 10, index = 1;
        String line;
        String[] str_values;

        int[] demands = new int[100];
        File file = new File("yearly_player_demand.txt");
        Scanner scan = new Scanner(file);
        scan.nextLine();
        while (scan.hasNext()) {
            line = scan.nextLine();
            str_values = line.split("\t");
            demands[index] = Integer.parseInt(str_values[1]);
            index++;
        }
        scan.close();

        index = 1;
        int[] salaries = new int[311];
        salaries[0] = 0;
        file = new File("players_salary.txt");
        Scanner scn = new Scanner(file);
        scn.nextLine();
        while (scn.hasNext()) {
            line = scn.nextLine();
            str_values = line.split("\t");
            salaries[index] = Integer.parseInt(str_values[1]);
            index++;
        }

        System.out.println("Greedy Results: " + Greedy(n, p, c, demands, salaries));


    }

}