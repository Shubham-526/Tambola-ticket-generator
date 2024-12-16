import java.util.*;

public class TambolaTicketGeneratorImprovised {
    HashSet<int[][]> existingTambolaTickets = new HashSet<>(); //If using the database then load this from the database.
    public static final int ROW = 3;
    public static final int COLS = 9;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Take the number of sets (N) as input
        System.out.print("Enter the number of sets (N): ");
        int numSets = scanner.nextInt();
        // The list containing the generated tambola tickets
        List<int[][]> tambolaSets = new ArrayList<>();

        for (int i = 0; i < numSets; i++) {
            for (int[][] tambolaTicket : generateTambolaSet()) {
                tambolaSets.add(tambolaTicket);
            }
        }
    }

    private static List<int[][]> generateTambolaSet() {
        boolean[] numberUsed = new boolean[90];
        List<int[][]> tambolaSet = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            int[][] tambolaTicket = generateTambolaTicket(numberUsed);
            tambolaSet.add(tambolaTicket);
        }
        return null;
    }

    private static int[][] generateTambolaTicket(boolean[] numberUsed) {
        int[][] ticket = new int[3][9];
        dfs(ticket, numberUsed, 0, 0);
        return ticket;
    }

    private static boolean dfs(int[][] ticket, boolean[] numberUsed, int row, int col) {
        if(col==COLS){
            col =0;
            row++;
        }
        if (row == ROW) {
            return true; // Successfully filled the entire ticket
        }
        List<Integer> availableNumbers = getAvailableNumbers(numberUsed, col);
        Collections.shuffle(availableNumbers);
        for(Integer num: availableNumbers){
            ticket[row][col] = num;
            numberUsed[num] = true;
            dfs(ticket,numberUsed,row,col++);
        }
    return true;
    }

    private static List<Integer> getAvailableNumbers(boolean[] numberUsed, int col) {
        List<Integer> availableNumbers = new ArrayList<>();
        int start = 10 * col;
        int end = 10 * (col + 1);
        if (col != 0) {
            availableNumbers.add(0); // Zero is an alias for empty space
        }
        for (int i = start; i < end; i++) {
            if (!numberUsed[i]) {
                availableNumbers.add(i);
            }
        }
        return availableNumbers;
    }
}

