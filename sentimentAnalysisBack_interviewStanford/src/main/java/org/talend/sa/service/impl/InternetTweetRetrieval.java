package org.talend.sa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Component;
import org.talend.sa.service.TweetRetrieval;

import java.util.List;

@Component
@ConditionalOnProperty(name = "tweet.reading", havingValue = "internet")
public class InternetTweetRetrieval implements TweetRetrieval {

    @Autowired
    private Twitter twitter;

    @Override
    public List<Tweet> getTweets() {
        SearchResults results = twitter.searchOperations().search(new SearchParameters("#talend"));
        return results.getTweets();
    }
}
