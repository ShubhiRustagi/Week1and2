import java.util.*;

class Client {
    String name;
    int riskScore;
    double accountBalance;

    public Client(String name, int riskScore, double accountBalance) {
        this.name = name;
        this.riskScore = riskScore;
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return name + ":" + riskScore;
    }
}

public class problem2 {

    // 🔹 Bubble Sort (Ascending by riskScore)
    public static void bubbleSort(Client[] arr) {
        int n = arr.length;
        int swaps = 0;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].riskScore > arr[j + 1].riskScore) {
                    // swap
                    Client temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    swaps++;
                    swapped = true;

                    // visualize swap
                    System.out.println("Swap: " + arr[j].name + " <-> " + arr[j + 1].name);
                }
            }

            if (!swapped) break; // optimization
        }

        System.out.println("\nBubble Sort (Ascending): " + Arrays.toString(arr));
        System.out.println("Total Swaps: " + swaps);
    }

    // 🔹 Insertion Sort (Descending by riskScore + accountBalance)
    public static void insertionSort(Client[] arr) {
        for (int i = 1; i < arr.length; i++) {
            Client key = arr[i];
            int j = i - 1;

            while (j >= 0 && compare(arr[j], key) < 0) {
                arr[j + 1] = arr[j]; // shift right
                j--;
            }
            arr[j + 1] = key;
        }

        System.out.println("\nInsertion Sort (Descending): " + Arrays.toString(arr));
    }

    // 🔹 Comparator: riskScore DESC, then accountBalance DESC
    private static int compare(Client c1, Client c2) {
        if (c1.riskScore != c2.riskScore) {
            return Integer.compare(c1.riskScore, c2.riskScore);
        }
        return Double.compare(c1.accountBalance, c2.accountBalance);
    }

    // 🔹 Top N highest risk clients
    public static void topRiskClients(Client[] arr, int topN) {
        System.out.print("\nTop " + topN + " High-Risk Clients: ");

        for (int i = 0; i < Math.min(topN, arr.length); i++) {
            System.out.print(arr[i].name + "(" + arr[i].riskScore + ") ");
        }
        System.out.println();
    }

    // 🔹 Main Method (User Input)
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of clients: ");
        int n = sc.nextInt();
        sc.nextLine();

        Client[] clients = new Client[n];

        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for client " + (i + 1));

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Risk Score: ");
            int risk = sc.nextInt();

            System.out.print("Account Balance: ");
            double balance = sc.nextDouble();
            sc.nextLine();

            clients[i] = new Client(name, risk, balance);
        }

        System.out.println("\nOriginal Data: " + Arrays.toString(clients));

        // Bubble Sort (Ascending)
        bubbleSort(clients);

        // Insertion Sort (Descending)
        insertionSort(clients);

        // Top 10 risks
        topRiskClients(clients, 10);

        sc.close();
    }
}