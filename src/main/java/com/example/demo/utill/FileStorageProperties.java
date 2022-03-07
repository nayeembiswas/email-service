
package com.example.demo.utill;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @Project   email-service
 * @Author    Md. Nayeemul Islam
 * @Since     Mar 7, 2022
 * @version   1.0.0
 */

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir;
    
    
    
}