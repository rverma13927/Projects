package com.example.rateLimiting.config;


import com.example.rateLimiting.services.BucketService;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitingHandler implements HandlerInterceptor {

    @Autowired
    BucketService bucketService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Integer userId = Integer.valueOf(request.getHeader("id"));
        Bucket bucket = bucketService.getBucket(userId);
        if (bucket.tryConsume(1)) {
            System.out.println("Testing user" + userId);
            System.out.println(bucket.getAvailableTokens());
        } else {
            System.out.println("noooooooo");
            return false;
        }
        return true;
    }

}
