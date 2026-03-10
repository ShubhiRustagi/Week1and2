import java.util.*;

public class EcommerceFlash {

    // productId -> stock count
    private HashMap<String, Integer> stock = new HashMap<>();

    // productId -> waiting list (FIFO)
    private HashMap<String, LinkedHashMap<Integer, Boolean>> waitingList = new HashMap<>();

    // constructor
    public EcommerceFlash() {
        stock.put("IPHONE15_256GB", 100);
        waitingList.put("IPHONE15_256GB", new LinkedHashMap<>());
    }

    // check stock availability
    public int checkStock(String productId) {
        return stock.getOrDefault(productId, 0);
    }

    // purchase item safely
    public synchronized String purchaseItem(String productId, int userId) {

        int currentStock = stock.getOrDefault(productId, 0);

        if (currentStock > 0) {
            stock.put(productId, currentStock - 1);
            return "Success, " + (currentStock - 1) + " units remaining";
        }

        // add user to waiting list
        LinkedHashMap<Integer, Boolean> queue = waitingList.get(productId);

        if (queue == null) {
            queue = new LinkedHashMap<>();
            waitingList.put(productId, queue);
        }

        queue.put(userId, true);

        return "Added to waiting list, position #" + queue.size();
    }

    public static void main(String[] args) {

        EcommerceFlash system = new EcommerceFlash();

        System.out.println(system.checkStock("IPHONE15_256GB") + " units available");

        System.out.println(system.purchaseItem("IPHONE15_256GB", 12345));
        System.out.println(system.purchaseItem("IPHONE15_256GB", 67890));

        // simulate purchases until stock finishes
        for (int i = 0; i < 100; i++) {
            system.purchaseItem("IPHONE15_256GB", i);
        }

        System.out.println(system.purchaseItem("IPHONE15_256GB", 99999));
    }
}