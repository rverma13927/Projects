package org.example.PubSub;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class RedisSubscriber {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);

        JedisPubSub jedisPubSub = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                System.out.println("Received message: " + message + " from channel: " + channel);
            }
        };

        System.out.println("Subscribed to channel: chat-room");
        jedis.subscribe(jedisPubSub, "chat-room");
        System.out.println("Unsubscribed from channel: chat-room");
    }
}
