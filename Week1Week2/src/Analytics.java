import java.util.*;

class Event {
    String url;
    String userId;
    String source;

    Event(String url, String userId, String source) {
        this.url = url;
        this.userId = userId;
        this.source = source;
    }
}

public class Analytics {

    // pageUrl -> visit count
    private HashMap<String, Integer> pageViews = new HashMap<>();

    // pageUrl -> unique visitors
    private HashMap<String, Set<String>> uniqueVisitors = new HashMap<>();

    // traffic source -> count
    private HashMap<String, Integer> trafficSources = new HashMap<>();

    // process incoming event
    public void processEvent(Event e) {

        // count page views
        pageViews.put(e.url, pageViews.getOrDefault(e.url, 0) + 1);

        // track unique visitors
        uniqueVisitors.putIfAbsent(e.url, new HashSet<>());
        uniqueVisitors.get(e.url).add(e.userId);

        // track traffic source
        trafficSources.put(e.source, trafficSources.getOrDefault(e.source, 0) + 1);
    }

    // display dashboard
    public void getDashboard() {

        System.out.println("Top Pages:");

        List<Map.Entry<String, Integer>> list = new ArrayList<>(pageViews.entrySet());

        // sort pages by visit count
        list.sort((a, b) -> b.getValue() - a.getValue());

        int count = Math.min(10, list.size());

        for (int i = 0; i < count; i++) {

            String page = list.get(i).getKey();
            int views = list.get(i).getValue();
            int unique = uniqueVisitors.get(page).size();

            System.out.println((i + 1) + ". " + page +
                    " - " + views + " views (" + unique + " unique)");
        }

        System.out.println("\nTraffic Sources:");

        for (String source : trafficSources.keySet()) {
            System.out.println(source + " → " + trafficSources.get(source));
        }
    }

    public static void main(String[] args) {

        Analytics system = new Analytics();

        system.processEvent(new Event("/article/breaking-news", "user_123", "google"));
        system.processEvent(new Event("/article/breaking-news", "user_456", "facebook"));
        system.processEvent(new Event("/sports/championship", "user_123", "direct"));
        system.processEvent(new Event("/sports/championship", "user_789", "google"));

        system.getDashboard();
    }
}