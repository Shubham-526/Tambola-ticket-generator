import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class TambolaTicketGenerator {

    private static final int ROWS = 3;
    private static final int COLS = 9;

    public static int[][] generateTambolaTicket() {
        int[][] ticket = new int[ROWS][COLS];
        boolean[] usedNumbers = new boolean[91]; // 0 to 90

        dfs(ticket, usedNumbers, 0, 0);

        return ticket;
    }

    private static boolean dfs(int[][] ticket, boolean[] usedNumbers, int row, int col) {
        if (col == COLS) {
            col = 0;
            row++;
        }

        if (row == ROWS) {
            return true; // Successfully filled the entire ticket
        }

        List<Integer> availableNumbers = getAvailableNumbers(usedNumbers, col);

        Collections.shuffle(availableNumbers); // Shuffle for randomness

        for (int num : availableNumbers) {
            if (countNumbersInRow(ticket, row) < 5) {
                ticket[row][col] = num;
                usedNumbers[num] = true;

                if (dfs(ticket, usedNumbers, row, col + 1)) {
                    return true; // Move to the next column
                }

                // Backtrack
                usedNumbers[num] = false;
                ticket[row][col] = 0; // Reset the cell
            }
        }

        // If unable to fill the row with exactly 5 numbers, backtrack to the previous row
        if (countNumbersInRow(ticket, row) < 5) {
            return dfs(ticket, usedNumbers, row, col + 1);
        }

        return false;
    }

    private static int countNumbersInRow(int[][] ticket, int row) {
        int count = 0;
        for (int col = 0; col < COLS; col++) {
            if (ticket[row][col] != 0) {
                count++;
            }
        }
        return count;
    }

    private static List<Integer> getAvailableNumbers(boolean[] usedNumbers, int col) {
        List<Integer> availableNumbers = new ArrayList<>();
        int start = col * 10 + 1;
        int end = Math.min(start + 10, usedNumbers.length);

        for (int i = start; i < end; i++) {
            if (!usedNumbers[i]) {
                availableNumbers.add(i);
            }
        }

        return availableNumbers;
    }

    public static List<int[][]> generateTambolaSet(int numSets) {
        List<int[][]> tambolaSets = new ArrayList<>();

        for (int i = 0; i < 6 * numSets; i++) {
            int[][] tambolaTicket = generateTambolaTicket();

            // Check if the set is unique in the database
            while (containsDuplicate(tambolaSets, tambolaTicket)) {
                tambolaTicket = generateTambolaTicket();
            }

            tambolaSets.add(tambolaTicket);
        }

        return tambolaSets;
    }

    private static boolean containsDuplicate(List<int[][]> tambolaSets, int[][] tambolaSet) {
        for (int[][] existingSet : tambolaSets) {
            if (Arrays.deepEquals(existingSet, tambolaSet)) {
                return true;
            }
        }
        return false;
    }

    public static void displayTambolaSet(List<int[][]> tambolaSet) {
        for (int i = 0; i < tambolaSet.size(); i++) {
            System.out.println("Set " + (i + 1) + ":\n");
            displayTambolaTicket(tambolaSet.get(i));
            System.out.println();
        }
    }

    public static void displayTambolaTicket(int[][] ticket) {
        for (int i = 0; i < ROWS; i++) {
            System.out.println(Arrays.toString(ticket[i]));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Take the number of sets (N) as input
        System.out.print("Enter the number of sets (N): ");
        int numSets = scanner.nextInt();

        // Generate 6N new sets of Tambola tickets
        List<int[][]> tambolaSets = generateTambolaSet(numSets);

        // Display the generated tambola sets
        displayTambolaSet(tambolaSets);
    }
}
