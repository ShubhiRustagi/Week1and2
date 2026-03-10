import java.util.*;

public class CacheSystem {

    // capacities
    private int L1_CAP = 10000;
    private int L2_CAP = 100000;

    // L1 cache (LRU using LinkedHashMap)
    private LinkedHashMap<String, String> L1 =
            new LinkedHashMap<>(L1_CAP, 0.75f, true);

    // L2 cache
    private LinkedHashMap<String, String> L2 =
            new LinkedHashMap<>(L2_CAP, 0.75f, true);

    // simulated database
    private HashMap<String, String> L3 = new HashMap<>();

    // access count tracking
    private HashMap<String, Integer> accessCount = new HashMap<>();

    // stats
    int L1Hits = 0, L2Hits = 0, L3Hits = 0;

    public CacheSystem() {

        // preload database
        L3.put("video_123", "VideoData123");
        L3.put("video_456", "VideoData456");
        L3.put("video_999", "VideoData999");
    }

    public String getVideo(String videoId) {

        // L1 lookup
        if (L1.containsKey(videoId)) {
            L1Hits++;
            return "L1 Cache HIT";
        }

        // L2 lookup
        if (L2.containsKey(videoId)) {
            L2Hits++;

            String data = L2.get(videoId);

            promoteToL1(videoId, data);

            return "L2 Cache HIT → Promoted to L1";
        }

        // L3 database lookup
        if (L3.containsKey(videoId)) {

            L3Hits++;

            String data = L3.get(videoId);

            addToL2(videoId, data);

            return "L3 Database HIT → Added to L2";
        }

        return "Video not found";
    }

    private void promoteToL1(String id, String data) {

        if (L1.size() >= L1_CAP) {
            String first = L1.keySet().iterator().next();
            L1.remove(first);
        }

        L1.put(id, data);
    }

    private void addToL2(String id, String data) {

        if (L2.size() >= L2_CAP) {
            String first = L2.keySet().iterator().next();
            L2.remove(first);
        }

        L2.put(id, data);
    }

    public void getStatistics() {

        int total = L1Hits + L2Hits + L3Hits;

        double l1Rate = total == 0 ? 0 : (L1Hits * 100.0) / total;
        double l2Rate = total == 0 ? 0 : (L2Hits * 100.0) / total;
        double l3Rate = total == 0 ? 0 : (L3Hits * 100.0) / total;

        System.out.println("L1 Hit Rate: " + l1Rate + "%");
        System.out.println("L2 Hit Rate: " + l2Rate + "%");
        System.out.println("L3 Hit Rate: " + l3Rate + "%");
    }

    public static void main(String[] args) {

        CacheSystem cache = new CacheSystem();

        System.out.println(cache.getVideo("video_123"));
        System.out.println(cache.getVideo("video_123"));

        System.out.println(cache.getVideo("video_999"));

        cache.getStatistics();
    }
}