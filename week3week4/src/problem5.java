import java.util.*;

public class problem5 {

    public static int linearFirst(String[] arr, String target) {
        int comparisons = 0;

        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i].equals(target)) {
                System.out.println("Linear First Occurrence Index: " + i);
                System.out.println("Comparisons: " + comparisons);
                return i;
            }
        }

        System.out.println("Not Found (Linear First)");
        System.out.println("Comparisons: " + comparisons);
        return -1;
    }

    public static int linearLast(String[] arr, String target) {
        int comparisons = 0;
        int index = -1;

        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i].equals(target)) {
                index = i;
            }
        }

        System.out.println("Linear Last Occurrence Index: " + index);
        System.out.println("Comparisons: " + comparisons);
        return index;
    }

    public static int binarySearch(String[] arr, String target) {
        int low = 0, high = arr.length - 1;
        int comparisons = 0;

        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;

            if (arr[mid].equals(target)) {
                System.out.println("Binary Search Found at Index: " + mid);
                System.out.println("Comparisons: " + comparisons);
                return mid;
            } else if (arr[mid].compareTo(target) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        System.out.println("Not Found (Binary)");
        System.out.println("Comparisons: " + comparisons);
        return -1;
    }

    public static int countOccurrences(String[] arr, String target) {
        int first = firstOccurrence(arr, target);
        int last = lastOccurrence(arr, target);

        if (first == -1) return 0;
        return last - first + 1;
    }

    public static int firstOccurrence(String[] arr, String target) {
        int low = 0, high = arr.length - 1;
        int result = -1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid].equals(target)) {
                result = mid;
                high = mid - 1; // move left
            } else if (arr[mid].compareTo(target) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }

    public static int lastOccurrence(String[] arr, String target) {
        int low = 0, high = arr.length - 1;
        int result = -1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid].equals(target)) {
                result = mid;
                low = mid + 1; // move right
            } else if (arr[mid].compareTo(target) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of account logs: ");
        int n = sc.nextInt();
        sc.nextLine();

        String[] logs = new String[n];

        System.out.println("Enter account IDs:");
        for (int i = 0; i < n; i++) {
            logs[i] = sc.nextLine();
        }

        System.out.print("\nEnter account ID to search: ");
        String target = sc.nextLine();

        System.out.println("\nOriginal Logs: " + Arrays.toString(logs));

        linearFirst(logs, target);
        linearLast(logs, target);

        Arrays.sort(logs);
        System.out.println("\nSorted Logs: " + Arrays.toString(logs));

        int index = binarySearch(logs, target);

        int count = countOccurrences(logs, target);
        System.out.println("Total Occurrences of " + target + ": " + count);
        sc.close();
    }
}