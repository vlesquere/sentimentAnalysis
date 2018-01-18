package org.talend.sa.service;

import org.springframework.social.twitter.api.Tweet;

import java.util.List;

public interface TweetRetrieval {

    public List<Tweet> getTweets();
}