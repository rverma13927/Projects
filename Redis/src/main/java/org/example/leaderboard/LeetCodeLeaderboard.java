package org.example.leaderboard;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class LeetCodeLeaderboard {

    private static final String LEADERBOARD_KEY = "leetcode_leaderboard";
    private Jedis jedis;

    public LeetCodeLeaderboard() {
        // Connect to Redis server on localhost
        this.jedis = new Jedis("localhost",6379);
    }

    // Add or update the score for a participant
    public void addOrUpdateParticipant(String username, int score) {
        jedis.zadd(LEADERBOARD_KEY, score, username);
    }

    // Increment the score of a participant
    public void incrementScore(String username, int increment) {
        jedis.zincrby(LEADERBOARD_KEY, increment, username);
    }

    // Get the rank of a participant
    public long getRank(String username) {
        Long rank = jedis.zrevrank(LEADERBOARD_KEY, username);
        return rank == null ? -1 : rank + 1;  // rank is 0-based, so +1 for 1-based ranking
    }

    // Get the score of a participant
    public double getScore(String username) {
        Double score = jedis.zscore(LEADERBOARD_KEY, username);
        return score == null ? 0 : score;
    }

    // Get the top N participants from the leaderboard
    public Map<String, Double> getTopNParticipants(int n) {
        List<String> topN = jedis.zrevrange(LEADERBOARD_KEY, 0, n - 1);
        Map<String, Double> topParticipants = new HashMap<>();
        for (String user : topN) {
            topParticipants.put(user, jedis.zscore(LEADERBOARD_KEY, user));
        }
        return topParticipants;
    }

    // Remove a participant from the leaderboard
    public void removeParticipant(String username) {
        jedis.zrem(LEADERBOARD_KEY, username);
    }
    public void displayLeaderboard(int page, int pageSize) {
        int start = (page - 1) * pageSize;
        int stop = start + pageSize - 1;

        List<String> users = jedis.zrevrange("leetcode_leaderboard", start, stop);
        System.out.println("Page " + page + ":");
        for (String user : users) {
            System.out.println(user);
        }
    }


    public static void main(String[] args) {
        // Create a leaderboard instance
        LeetCodeLeaderboard leaderboard = new LeetCodeLeaderboard();

        // Add participants and their scores
        leaderboard.addOrUpdateParticipant("Alice", 1200);
        leaderboard.addOrUpdateParticipant("Bob", 1500);
        leaderboard.addOrUpdateParticipant("Charlie", 1800);
        leaderboard.addOrUpdateParticipant("David", 1700);

        // Increment Alice's score
        leaderboard.incrementScore("Alice", 300); // Alice's score becomes 1500

        // Get rank and score of a specific user
        System.out.println("Rank of Alice: " + leaderboard.getRank("Alice"));  // Should be 2
        System.out.println("Score of Alice: " + leaderboard.getScore("Alice"));  // Should be 1500

        // Display top 3 participants
        System.out.println("Top 3 Participants:");
        Map<String, Double> top3 = leaderboard.getTopNParticipants(3);
        top3.forEach((user, score) -> System.out.println(user + ": " + score));

        // Remove a participant
        leaderboard.removeParticipant("David");

        // Display leaderboard after removing David
        System.out.println("Leaderboard after removing David:");
        Map<String, Double> updatedTop3 = leaderboard.getTopNParticipants(3);
        updatedTop3.forEach((user, score) -> System.out.println(user + ": " + score));
    }
}
