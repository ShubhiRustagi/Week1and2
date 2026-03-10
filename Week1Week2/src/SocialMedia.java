import java.util.*;

public class SocialMedia {

    // username -> userId
    private HashMap<String, Integer> users = new HashMap<>();

    // username -> attempt frequency
    private HashMap<String, Integer> attempts = new HashMap<>();

    // constructor with some existing users
    public SocialMedia() {
        users.put("john_doe", 101);
        users.put("alex99", 102);
        users.put("admin", 103);
    }

    // check if username is available
    public boolean checkAvailability(String username) {

        // update attempt count
        attempts.put(username, attempts.getOrDefault(username, 0) + 1);

        // return availability
        return !users.containsKey(username);
    }

    // suggest alternative usernames
    public List<String> suggestAlternatives(String username) {

        List<String> suggestions = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            String suggestion = username + i;

            if (!users.containsKey(suggestion)) {
                suggestions.add(suggestion);
            }
        }

        String modified = username.replace("_", ".");

        if (!users.containsKey(modified)) {
            suggestions.add(modified);
        }

        return suggestions;
    }

    // get most attempted username
    public String getMostAttempted() {

        String result = "";
        int max = 0;

        for (String user : attempts.keySet()) {

            int count = attempts.get(user);

            if (count > max) {
                max = count;
                result = user;
            }
        }

        return result + " (" + max + " attempts)";
    }

    public static void main(String[] args) {

        SocialMedia system = new SocialMedia();

        System.out.println(system.checkAvailability("john_doe"));
        System.out.println(system.checkAvailability("jane_smith"));

        System.out.println(system.suggestAlternatives("john_doe"));

        System.out.println(system.getMostAttempted());
    }
}