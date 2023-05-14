import java.io.*;
import java.util.Scanner;

public class Main {

    public int DP(int n, int p, int c, int[] yearlyPlayerDemand, int[] playerSalary){
        int[][] dp = new int[n + 1][p + 1];

        for (int i = 1; i <= n; i++) {
            int demand = yearlyPlayerDemand[i - 1];
            int salary = playerSalary[i - 1];

            for (int j = 0; j <= p; j++) {
                dp[i][j] = Integer.MAX_VALUE;

                if (j >= demand) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - demand]);
                }

                if (j < p) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + salary);
                }
            }
        }
        int minTotalCost = dp[n][p];
        return minTotalCost;
    }

    public static void main(String[] args) throws FileNotFoundException {
        int n = 3, p= 5, c= 5, index = 1;
        String line;
        String str_values[];

        int[] demands = new int[100];
        File file = new File("yearly_player_demand.txt");
        Scanner scan = new Scanner(file);
        scan.nextLine();
        while(scan.hasNext()){
            line = scan.nextLine();
            str_values = line.split("\t");
            demands[index] = Integer.parseInt(str_values[1]);
            index++;
        }
        scan.close();

        index = 1;
        int[] salaries = new int[500];
        file = new File("players_salary.txt");
        scan = new Scanner(file);
        scan.nextLine();
        while(scan.hasNext()){
            line = scan.nextLine();
            str_values = line.split("\t");
            salaries[index] = Integer.parseInt(str_values[1]);
            System.out.println(salaries[index]);
            index++;
        }


    }

}