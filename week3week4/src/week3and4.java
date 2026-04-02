import java.util.*;

class Transaction {
    String id;
    double fee;
    String timestamp;

    public Transaction(String id, double fee, String timestamp) {
        this.id = id;
        this.fee = fee;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return id + ":" + fee + "@" + timestamp;
    }
}

public class week3and4 {

    // 🔹 Bubble Sort (by fee)
    public static void bubbleSort(List<Transaction> list) {
        int n = list.size();
        int passes = 0, swaps = 0;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            passes++;

            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).fee > list.get(j + 1).fee) {
                    Collections.swap(list, j, j + 1);
                    swaps++;
                    swapped = true;
                }
            }

            if (!swapped) break; // early stop
        }

        System.out.println("\nBubble Sort (by fee): " + list);
        System.out.println("Passes: " + passes + ", Swaps: " + swaps);
    }

    // 🔹 Insertion Sort (by fee + timestamp)
    public static void insertionSort(List<Transaction> list) {
        for (int i = 1; i < list.size(); i++) {
            Transaction key = list.get(i);
            int j = i - 1;

            while (j >= 0 && compare(list.get(j), key) > 0) {
                list.set(j + 1, list.get(j)); // shift
                j--;
            }
            list.set(j + 1, key);
        }

        System.out.println("\nInsertion Sort (fee + timestamp): " + list);
    }

    // 🔹 Compare by fee, then timestamp
    private static int compare(Transaction t1, Transaction t2) {
        if (t1.fee != t2.fee) {
            return Double.compare(t1.fee, t2.fee);
        }
        return t1.timestamp.compareTo(t2.timestamp);
    }

    // 🔹 Find high-fee outliers (>50)
    public static void findOutliers(List<Transaction> list) {
        System.out.print("\nHigh-fee outliers (>50): ");
        boolean found = false;

        for (Transaction t : list) {
            if (t.fee > 50) {
                System.out.print(t + " ");
                found = true;
            }
        }

        if (!found) {
            System.out.print("None");
        }
        System.out.println();
    }

    // 🔹 Main method with user input
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Transaction> transactions = new ArrayList<>();

        System.out.print("Enter number of transactions: ");
        int n = sc.nextInt();
        sc.nextLine(); // consume newline

        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for transaction " + (i + 1));

            System.out.print("ID: ");
            String id = sc.nextLine();

            System.out.print("Fee: ");
            double fee = sc.nextDouble();
            sc.nextLine(); // consume newline

            System.out.print("Timestamp (HH:MM): ");
            String ts = sc.nextLine();

            transactions.add(new Transaction(id, fee, ts));
        }

        System.out.println("\nOriginal Transactions: " + transactions);

        // Apply sorting based on size
        if (n <= 100) {
            bubbleSort(transactions);
        } else if (n <= 1000) {
            insertionSort(transactions);
        } else {
            System.out.println("\nLarge dataset: Use advanced sorting (not implemented)");
        }

        // Always show insertion sort for fee + timestamp
        insertionSort(transactions);

        // Detect outliers
        findOutliers(transactions);

        sc.close();
    }
}