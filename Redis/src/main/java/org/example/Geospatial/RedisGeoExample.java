package org.example.Geospatial;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.args.GeoUnit;
import redis.clients.jedis.resps.GeoRadiusResponse;
import java.util.List;

public class RedisGeoExample {
    public static void main(String[] args) {
        // Connect to Redis
        Jedis jedis = new Jedis("localhost", 6379);

        // Add geospatial data for restaurants
        jedis.geoadd("restaurants", 13.361389, 38.115556, "Restaurant1");
        jedis.geoadd("restaurants", 15.087269, 37.502669, "Restaurant2");
        jedis.geoadd("restaurants", 12.971598, 77.594566, "Restaurant3");

        // Find restaurants within 500 km of a given point
        List<GeoRadiusResponse> results = jedis.georadius("restaurants", 13.361389, 38.115556, 1500.0, GeoUnit.KM, redis.clients.jedis.params.GeoRadiusParam.geoRadiusParam().withDist());

        // Print the results
        for (GeoRadiusResponse response : results) {
            System.out.println("Name: " + response.getMemberByString() + ", Distance: " + response.getDistance() + " km");
        }

        // Calculate the distance between the two restaurants in kilometers
        Double distance = jedis.geodist("restaurants", "Restaurant1", "Restaurant2", GeoUnit.KM);

        // Output the distance
        if (distance != null) {
            System.out.println("Distance between Restaurant1 and Restaurant2: " + distance + " km");
        } else {
            System.out.println("Could not calculate the distance. Make sure the keys exist.");
        }


        jedis.close();
    }
}
