package org.talend.sa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.talend.sa.service.TweetRetrieval;

import javax.inject.Inject;

@Configuration
public class SocialConfig {

    @Value("${consumer.key}")
    private String consumerKey;

    @Value("${consumer.secret}")
    private String consumerSecret;

    @Value("${spring.social.twitter.appId}")
    private String accessToken;

    @Value("${spring.social.twitter.appSecret}")
    private String accessTokenSecret;

    @Bean
    public TwitterTemplate getTemplate() {
        return new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }

}
