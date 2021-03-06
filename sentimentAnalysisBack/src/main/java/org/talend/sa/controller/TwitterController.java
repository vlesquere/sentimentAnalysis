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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/tweets")
@CrossOrigin(origins = "*")
public class TwitterController {

    private static final Logger log = LoggerFactory.getLogger(TwitterController.class);

    @Autowired
    private TweetRetrieval retriever;

    private ObjectMapper mapper = new ObjectMapper();

    @GetMapping
    public List<Sentiment> getTweets() throws IOException {

        SentimentAnalysis.init();

        List<Sentiment> sentiments = new ArrayList<>();
        List<Tweet> results = retriever.getTweets();

        for (Tweet tweet : results) {
            String tweetText = tweet.getText();

            Sentiment sentiment = new Sentiment();
            sentiment.setMessage(tweetText);
            sentiment.setScore(SentimentAnalysis.findSentimentStanford_NeuralNetwork(tweetText)); //score with neural network
            sentiments.add(sentiment);

            System.out.println("Score Open NLP : " + SentimentAnalysis.findSentimentOpenNLP(tweetText));
            try {
                List<String> tokens = StanfordUtils.tokenize(tweetText);
                Map<String, String> taggedTokens = StanfordUtils.posTag(tokens); //score without neural network

                System.out.println(" ---------------- ");
                System.out.println(tweetText);
                taggedTokens.keySet().forEach(taggedToken -> System.out.println(taggedToken + " : " + taggedTokens.get(taggedToken)));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return sentiments;
    }
}
