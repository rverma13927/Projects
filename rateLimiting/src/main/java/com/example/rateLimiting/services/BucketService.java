package com.example.rateLimiting.services;

import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;

@Service
public class BucketService {
    private HashMap<Integer, Bucket> bucketHashMap = new HashMap<>();

    public Bucket getBucket(Integer userId){
        if(bucketHashMap.containsKey(userId)){
            return bucketHashMap.get(userId);
        }
            Bucket bucket = Bucket.builder()
                    .addLimit(limit -> limit.capacity(10).refillGreedy(10, Duration.ofMinutes(1)))
                    .build();
            bucketHashMap.put(userId,bucket);
        return bucket;
    }
}
