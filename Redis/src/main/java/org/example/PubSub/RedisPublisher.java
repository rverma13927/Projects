package org.example.PubSub;

import redis.clients.jedis.Jedis;

public class RedisPublisher {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);

        jedis.publish("chat-room", "Hello, this is a message to all subscribers!");
        jedis.publish("chat-room", "Another message to the chat-room.");

        jedis.close();
        System.out.println("Messages published successfully.");
    }
}
