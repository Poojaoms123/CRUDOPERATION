package com.anervea.employee.utility;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class OTPService {
    private static final Integer EXPIRE_MINS=10;
    private LoadingCache<String, Integer> cache;

    public OTPService(){
        super();
        cache= CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(String s) throws Exception {
                return 0;
            }
        });
    }

    public int generateOTP(String key){
        Random rand=new Random();
        int otp=1000+rand.nextInt(9000);
        //int otp=123456;
        System.out.println(" generated otp:-"+ otp);
        cache.put(key, otp);

        return otp;
    }

    public int getOTP(String key){
        try {
            System.out.println("get OTP:-"+ cache.get(key));
            return cache.get(key);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void clearOTP(String key){
            cache.invalidate(key);
    }


}
