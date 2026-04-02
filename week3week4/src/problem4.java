import java.util.*;

class Asset {
    String name;
    double returnRate;
    double volatility;

    public Asset(String name, double returnRate, double volatility) {
        this.name = name;
        this.returnRate = returnRate;
        this.volatility = volatility;
    }

    @Override
    public String toString() {
        return name + ":" + returnRate + "%";
    }
}

public class problem4 {

    // 🔹 Merge Sort (Stable, Ascending by returnRate)
    public static void mergeSort(Asset[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            merge(arr, left, mid, right);
        }
    }

    public static void merge(Asset[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Asset[] L = new Asset[n1];
        Asset[] R = new Asset[n2];

        for (int i = 0; i < n1; i++)
            L[i] = arr[left + i];

        for (int j = 0; j < n2; j++)
            R[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            // Stable: pick left if equal
            if (L[i].returnRate <= R[j].returnRate) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }

        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    // 🔹 Quick Sort (Hybrid)
    public static void quickSort(Asset[] arr, int low, int high) {
        if (low < high) {

            // Hybrid: use insertion sort for small partitions
            if (high - low < 10) {
                insertionSort(arr, low, high);
                return;
            }

            int pivotIndex = medianOfThree(arr, low, high);
            swap(arr, pivotIndex, high);

            int pi = partition(arr, low, high);

            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    // 🔹 Partition (DESC by returnRate, ASC by volatility)
    public static int partition(Asset[] arr, int low, int high) {
        Asset pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (compare(arr[j], pivot) < 0) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    // 🔹 Comparator: return DESC, volatility ASC
    private static int compare(Asset a, Asset b) {
        if (a.returnRate != b.returnRate) {
            return Double.compare(b.returnRate, a.returnRate); // DESC
        }
        return Double.compare(a.volatility, b.volatility); // ASC
    }

    // 🔹 Insertion Sort (used in hybrid quicksort)
    public static void insertionSort(Asset[] arr, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            Asset key = arr[i];
            int j = i - 1;

            while (j >= low && compare(arr[j], key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    // 🔹 Median-of-Three Pivot Selection
    public static int medianOfThree(Asset[] arr, int low, int high) {
        int mid = (low + high) / 2;

        if (compare(arr[low], arr[mid]) > 0) swap(arr, low, mid);
        if (compare(arr[low], arr[high]) > 0) swap(arr, low, high);
        if (compare(arr[mid], arr[high]) > 0) swap(arr, mid, high);

        return mid;
    }

    // 🔹 Random Pivot (optional)
    public static int randomPivot(int low, int high) {
        Random rand = new Random();
        return low + rand.nextInt(high - low + 1);
    }

    // 🔹 Swap helper
    public static void swap(Asset[] arr, int i, int j) {
        Asset temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // 🔹 Main Method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of assets: ");
        int n = sc.nextInt();
        sc.nextLine();

        Asset[] assets = new Asset[n];

        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter asset " + (i + 1));

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Return Rate (%): ");
            double ret = sc.nextDouble();

            System.out.print("Volatility: ");
            double vol = sc.nextDouble();
            sc.nextLine();

            assets[i] = new Asset(name, ret, vol);
        }

        System.out.println("\nOriginal Data: " + Arrays.toString(assets));

        // 🔹 Merge Sort (Ascending)
        mergeSort(assets, 0, n - 1);
        System.out.println("Merge Sort (Ascending): " + Arrays.toString(assets));

        // 🔹 Quick Sort (Descending + Volatility)
        quickSort(assets, 0, n - 1);
        System.out.println("Quick Sort (Desc + Volatility): " + Arrays.toString(assets));

        sc.close();
    }
}