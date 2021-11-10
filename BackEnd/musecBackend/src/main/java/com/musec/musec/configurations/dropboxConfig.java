package com.musec.musec.configurations;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class dropboxConfig {
    private static String ACCESS_TOKEN = "";

    @Bean
    public DbxClientV2 dropbox() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("musec/backend").build();
        return new DbxClientV2(config, ACCESS_TOKEN);
    }
}
