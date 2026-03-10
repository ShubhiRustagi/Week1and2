import java.util.*;

class TokenBucket {

    int tokens;
    int maxTokens;
    long lastRefillTime;
    int refillRate; // tokens added per second

    TokenBucket(int maxTokens, int refillRate) {
        this.maxTokens = maxTokens;
        this.refillRate = refillRate;
        this.tokens = maxTokens;
        this.lastRefillTime = System.currentTimeMillis();
    }

    // refill tokens based on time passed
    void refill() {
        long now = System.currentTimeMillis();
        long seconds = (now - lastRefillTime) / 1000;

        int tokensToAdd = (int) (seconds * refillRate);

        if (tokensToAdd > 0) {
            tokens = Math.min(maxTokens, tokens + tokensToAdd);
            lastRefillTime = now;
        }
    }

    boolean allowRequest() {
        refill();

        if (tokens > 0) {
            tokens--;
            return true;
        }

        return false;
    }
}

public class RateLimiter {

    // clientId -> token bucket
    private HashMap<String, TokenBucket> clients = new HashMap<>();

    private int MAX_REQUESTS = 1000;
    private int REFILL_RATE = 1000 / 3600; // tokens per second

    public synchronized String checkRateLimit(String clientId) {

        clients.putIfAbsent(clientId, new TokenBucket(MAX_REQUESTS, REFILL_RATE));

        TokenBucket bucket = clients.get(clientId);

        if (bucket.allowRequest()) {
            return "Allowed (" + bucket.tokens + " requests remaining)";
        }

        return "Denied (0 requests remaining)";
    }

    public String getRateLimitStatus(String clientId) {

        TokenBucket bucket = clients.get(clientId);

        if (bucket == null) {
            return "No usage yet";
        }

        int used = bucket.maxTokens - bucket.tokens;

        return "{used: " + used +
                ", limit: " + bucket.maxTokens +
                ", remaining: " + bucket.tokens + "}";
    }

    public static void main(String[] args) {

        RateLimiter limiter = new RateLimiter();

        System.out.println(limiter.checkRateLimit("abc123"));
        System.out.println(limiter.checkRateLimit("abc123"));
        System.out.println(limiter.getRateLimitStatus("abc123"));
    }
}