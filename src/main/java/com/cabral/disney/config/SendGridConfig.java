package com.cabral.disney.config;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendGridConfig {
    @Value("${twilio-sendgrid.mail.api-key}")
    private String SENDGRID_API_KEY;

    @Bean
    public SendGrid getSendGrid(){
        return new SendGrid(SENDGRID_API_KEY);
    }
}
