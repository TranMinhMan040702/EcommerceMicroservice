package com.criscode.product.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        Map<Object, Object> config = new HashMap<>();
        config.put("cloud_name", "dwoejm4g6");
        config.put("api_key", "531723158945791");
        config.put("api_secret", "vgCHOLFDpPoPe_DXyjk653ztW1k");
        config.put("secure", true);
        Cloudinary cloudinary = new Cloudinary(config);
        return cloudinary;
    }

}
