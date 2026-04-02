import java.util.*;

public class problem6 {

    // 🔹 Linear Search (unsorted)
    public static int linearSearch(int[] arr, int target) {
        int comparisons = 0;

        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i] == target) {
                System.out.println("Linear: Found at index " + i);
                System.out.println("Comparisons: " + comparisons);
                return i;
            }
        }

        System.out.println("Linear: Not Found");
        System.out.println("Comparisons: " + comparisons);
        return -1;
    }

    // 🔹 Binary Search - Find insertion point (lower_bound)
    public static int insertionPoint(int[] arr, int target) {
        int low = 0, high = arr.length;
        int comparisons = 0;

        while (low < high) {
            int mid = (low + high) / 2;
            comparisons++;

            if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        System.out.println("Insertion Point Index: " + low);
        System.out.println("Comparisons (Binary): " + comparisons);
        return low;
    }

    // 🔹 Floor (largest ≤ target)
    public static Integer floor(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        Integer result = null;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid] == target) {
                return arr[mid];
            } else if (arr[mid] < target) {
                result = arr[mid];
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }

    // 🔹 Ceiling (smallest ≥ target)
    public static Integer ceiling(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        Integer result = null;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid] == target) {
                return arr[mid];
            } else if (arr[mid] > target) {
                result = arr[mid];
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return result;
    }

    // 🔹 Main Method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of risk bands: ");
        int n = sc.nextInt();

        int[] risks = new int[n];

        System.out.println("Enter risk values:");
        for (int i = 0; i < n; i++) {
            risks[i] = sc.nextInt();
        }

        System.out.print("\nEnter threshold value: ");
        int target = sc.nextInt();

        System.out.println("\nOriginal (Unsorted): " + Arrays.toString(risks));

        // 🔹 Linear Search
        linearSearch(risks, target);

        // 🔹 Sort for Binary Search
        Arrays.sort(risks);
        System.out.println("\nSorted Risks: " + Arrays.toString(risks));

        // 🔹 Binary Insertion Point
        insertionPoint(risks, target);

        // 🔹 Floor & Ceiling
        Integer fl = floor(risks, target);
        Integer ce = ceiling(risks, target);

        System.out.println("\nFloor (" + target + "): " + (fl != null ? fl : "None"));
        System.out.println("Ceiling (" + target + "): " + (ce != null ? ce : "None"));

        sc.close();
    }
}