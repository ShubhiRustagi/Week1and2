import java.util.*;

public class SearchEngine {

    // query -> frequency
    private HashMap<String, Integer> queryFreq = new HashMap<>();

    // add or update search query
    public void updateFrequency(String query) {
        queryFreq.put(query, queryFreq.getOrDefault(query, 0) + 1);
    }

    // return top 10 suggestions for prefix
    public List<String> search(String prefix) {

        List<Map.Entry<String, Integer>> matches = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : queryFreq.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                matches.add(entry);
            }
        }

        // sort by frequency descending
        matches.sort((a, b) -> b.getValue() - a.getValue());

        List<String> results = new ArrayList<>();

        int limit = Math.min(10, matches.size());

        for (int i = 0; i < limit; i++) {
            Map.Entry<String, Integer> e = matches.get(i);
            results.add(e.getKey() + " (" + e.getValue() + " searches)");
        }

        return results;
    }

    public static void main(String[] args) {

        SearchEngine engine = new SearchEngine();

        engine.updateFrequency("java tutorial");
        engine.updateFrequency("javascript");
        engine.updateFrequency("java download");
        engine.updateFrequency("java tutorial");
        engine.updateFrequency("java 21 features");
        engine.updateFrequency("java tutorial");

        System.out.println("Suggestions for 'jav':");

        List<String> results = engine.search("jav");

        for (String r : results) {
            System.out.println(r);
        }
    }
}