// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.sa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.talend.sa.model.Sentiment;
import org.talend.sa.service.SentimentAnalysis;
import org.talend.sa.service.TweetRetrieval;
import org.talend.sa.stanford.StanfordUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/tweets")
@CrossOrigin(origins = "*")
public class TwitterController {

    @Autowired
    private TweetRetrieval retriever;

    private ObjectMapper mapper = new ObjectMapper();

    @GetMapping
    public List<Sentiment> getTweets() throws IOException {

        List<Sentiment> sentiments = new ArrayList<>();

        List<Tweet> results = retriever.getTweets();

        for (Tweet tweet : results) {
            String tweetText = tweet.getText();

            Sentiment sentiment = new Sentiment();
            sentiment.setMessage(tweetText);

            sentiments.add(sentiment);

            /**
             *  Sentiment scores
             *    0 very negative
             *    1 negative
             *    2 neutral
             *    3 positive
             *    4 very positive
            */

        }

        return sentiments;
    }
}
