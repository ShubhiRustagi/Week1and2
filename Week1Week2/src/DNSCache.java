import java.util.*;

class DNSEntry {
    String ipAddress;
    long expiryTime;

    DNSEntry(String ip, int ttl) {
        ipAddress = ip;
        expiryTime = System.currentTimeMillis() + ttl * 1000;
    }

    boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

public class DNSCache {

    private int capacity = 5;

    // LRU cache
    private LinkedHashMap<String, DNSEntry> Cache =
            new LinkedHashMap<>(capacity, 0.75f, true);

    private int hits = 0;
    private int misses = 0;

    // simulate upstream DNS lookup
    private String queryUpstream(String domain) {
        return "172.217." + new Random().nextInt(255) + "." + new Random().nextInt(255);
    }

    // resolve domain
    public String resolve(String domain) {

        DNSEntry entry = Cache.get(domain);

        if (entry != null && !entry.isExpired()) {
            hits++;
            return "Cache HIT → " + entry.ipAddress;
        }

        misses++;

        String ip = queryUpstream(domain);
        DNSEntry newEntry = new DNSEntry(ip, 300);

        if (Cache.size() >= capacity) {
            String firstKey = Cache.keySet().iterator().next();
            Cache.remove(firstKey);
        }

        Cache.put(domain, newEntry);

        return "Cache MISS → Query upstream → " + ip;
    }

    // remove expired entries
    public void cleanExpired() {

        Iterator<Map.Entry<String, DNSEntry>> it = Cache.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, DNSEntry> entry = it.next();

            if (entry.getValue().isExpired()) {
                it.remove();
            }
        }
    }

    // display cache statistics
    public void getCacheStats() {

        int total = hits + misses;

        double hitRate = total == 0 ? 0 : (hits * 100.0) / total;

        System.out.println("Hits: " + hits);
        System.out.println("Misses: " + misses);
        System.out.println("Hit Rate: " + hitRate + "%");
    }

    public static void main(String[] args) {

        DNSCache dns = new DNSCache();

        System.out.println(dns.resolve("google.com"));
        System.out.println(dns.resolve("google.com"));

        dns.getCacheStats();
    }
}