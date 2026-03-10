import java.util.*;

public class PlagDetection {

    // ngram -> set of documents containing it
    private HashMap<String, Set<String>> index = new HashMap<>();

    private int n = 5; // using 5-grams

    // break document into n-grams
    private List<String> getNGrams(String text) {

        String[] words = text.toLowerCase().split("\\s+");
        List<String> grams = new ArrayList<>();

        for (int i = 0; i <= words.length - n; i++) {

            StringBuilder gram = new StringBuilder();

            for (int j = 0; j < n; j++) {
                gram.append(words[i + j]).append(" ");
            }

            grams.add(gram.toString().trim());
        }

        return grams;
    }

    // add document to database
    public void addDocument(String docId, String text) {

        List<String> grams = getNGrams(text);

        for (String gram : grams) {

            index.putIfAbsent(gram, new HashSet<>());

            index.get(gram).add(docId);
        }
    }

    // analyze new document
    public void analyzeDocument(String docId, String text) {

        List<String> grams = getNGrams(text);

        HashMap<String, Integer> matchCount = new HashMap<>();

        for (String gram : grams) {

            if (index.containsKey(gram)) {

                for (String doc : index.get(gram)) {

                    matchCount.put(doc, matchCount.getOrDefault(doc, 0) + 1);
                }
            }
        }

        System.out.println("Extracted " + grams.size() + " n-grams");

        for (String doc : matchCount.keySet()) {

            int matches = matchCount.get(doc);

            double similarity = (matches * 100.0) / grams.size();

            System.out.println("Found " + matches + " matching n-grams with " + doc);
            System.out.println("Similarity: " + similarity + "%");

            if (similarity > 60) {
                System.out.println("PLAGIARISM DETECTED");
            } else if (similarity > 10) {
                System.out.println("Suspicious similarity");
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {

        PlagDetection system = new PlagDetection();

        String essay1 = "machine learning is a field of artificial intelligence that focuses on data";
        String essay2 = "machine learning is a field of artificial intelligence that focuses on data analysis";

        system.addDocument("essay_089.txt", essay1);

        system.analyzeDocument("essay_123.txt", essay2);
    }
}