import java.util.*;

class Transaction {
    int id;
    int amount;
    String merchant;
    String account;
    long time; // timestamp in minutes

    Transaction(int id, int amount, String merchant, String account, long time) {
        this.id = id;
        this.amount = amount;
        this.merchant = merchant;
        this.account = account;
        this.time = time;
    }
}

public class TwoSum {

    List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    // Classic Two-Sum
    public void findTwoSum(int target) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {
                Transaction prev = map.get(complement);
                System.out.println("(" + prev.id + ", " + t.id + ")");
            }

            map.put(t.amount, t);
        }
    }

    // Two-Sum with 1-hour window
    public void findTwoSumWithWindow(int target, long windowMinutes) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {
                Transaction prev = map.get(complement);

                if (Math.abs(t.time - prev.time) <= windowMinutes) {
                    System.out.println("Within window: (" + prev.id + ", " + t.id + ")");
                }
            }

            map.put(t.amount, t);
        }
    }

    // Duplicate detection
    public void detectDuplicates() {

        HashMap<String, List<Transaction>> map = new HashMap<>();

        for (Transaction t : transactions) {

            String key = t.amount + "_" + t.merchant;

            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(t);
        }

        for (String key : map.keySet()) {

            List<Transaction> list = map.get(key);

            if (list.size() > 1) {
                System.out.print("Duplicate: amount=" + list.get(0).amount +
                        ", merchant=" + list.get(0).merchant + " accounts: ");

                for (Transaction t : list) {
                    System.out.print(t.account + " ");
                }

                System.out.println();
            }
        }
    }

    // K-Sum (simple recursive)
    public void findKSum(int k, int target) {
        kSumHelper(0, k, target, new ArrayList<>());
    }

    private void kSumHelper(int start, int k, int target, List<Integer> path) {

        if (k == 0 && target == 0) {
            System.out.println(path);
            return;
        }

        if (k == 0 || start >= transactions.size())
            return;

        for (int i = start; i < transactions.size(); i++) {

            path.add(transactions.get(i).id);

            kSumHelper(i + 1,
                    k - 1,
                    target - transactions.get(i).amount,
                    path);

            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {

        TwoSum system = new TwoSum();

        system.addTransaction(new Transaction(1, 500, "StoreA", "acc1", 600));
        system.addTransaction(new Transaction(2, 300, "StoreB", "acc2", 615));
        system.addTransaction(new Transaction(3, 200, "StoreC", "acc3", 630));
        system.addTransaction(new Transaction(4, 500, "StoreA", "acc4", 700));

        System.out.println("Two Sum:");
        system.findTwoSum(500);

        System.out.println("\nTwo Sum within 60 minutes:");
        system.findTwoSumWithWindow(500, 60);

        System.out.println("\nDuplicate Transactions:");
        system.detectDuplicates();

        System.out.println("\nK-Sum (k=3, target=1000):");
        system.findKSum(3, 1000);
    }
}