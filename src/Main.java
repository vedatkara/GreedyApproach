import java.io.*;
import java.util.Scanner;

public class Main {

    public static int Greedy(int year, int promo, int coach, int[] demands, int[] salaries) {
        int minCost = 0, diff, remaining = 0, nextDiff, cost, cost_1, cost_2;
        boolean is_there_remaining = false; // flag to consider remaining players from previous year

        for (int i = 1; i <= year; i++) {
            diff = demands[i] - promo;  // difference between current year's demand and promotion number.

            /* If there are remaining players from previous year subtract them from difference. */
            if (is_there_remaining) {
                diff -= remaining;
                is_there_remaining = false;

            /* else reset number of remaining players */
            } else
                remaining = 0;

            // If there is difference just pay for the coach
            if (diff > 0)
                minCost += coach * diff;

            // Else decide whether keeping the players or release them considering next year's demand and minimum cost
             else if (diff < 0) {
                    nextDiff = demands[i + 1] - promo;

                    if (nextDiff > 0) {
                        cost_2 = 0;
                        diff *= -1;
                        cost = coach * nextDiff; // Cost for no remaining players

                        for (int j = 1; j <= nextDiff; j++) {
                            cost_1 = salaries[j] + ((nextDiff - j) * coach) + ((j - diff) * coach); // cost with remaining players

                            if(cost_1 < cost) { // If keeping the players is more efficient enter
                                if(j > diff)
                                    cost_2 = (j - diff) * coach + salaries[j];
                                else
                                    cost_2 = salaries[j];

                                is_there_remaining = true; // Flag to know there are remaining players
                                remaining = j;
                            }
                        }
                        minCost += cost_2; // Update minimum cost
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