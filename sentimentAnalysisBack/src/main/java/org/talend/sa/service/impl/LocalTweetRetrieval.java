package org.talend.sa.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;
import org.talend.sa.service.TweetRetrieval;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@ConditionalOnProperty(name = "tweet.reading", havingValue = "local")
public class LocalTweetRetrieval implements TweetRetrieval {

    @Override
    public List<Tweet> getTweets() {
        ObjectMapper mapper = new ObjectMapper();
        List<Tweet> tweets = new ArrayList<>();

        try {

            String listTweets = new String(Files.readAllBytes(Paths.get(this.getClass().getClassLoader().getResource("tweets.json").toURI())));
            List<TalendTweet> talendTweets = mapper.readValue(
                    listTweets,
                    mapper.getTypeFactory().constructParametricType(List.class, TalendTweet.class)
            );


            talendTweets.forEach(talendTweet -> {
                Tweet tweet = new Tweet(talendTweet.getId(),
                        talendTweet.getText(),
                        talendTweet.getCreatedAt(),
                        talendTweet.getFromUser(),
                        talendTweet.getProfileImageUrl(),
                        talendTweet.getToUserId(),
                        talendTweet.getFromUserId(),
                        talendTweet.getLanguageCode(),
                        talendTweet.getSource());
                tweets.add(tweet);
            });

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return tweets;
    }


    static class TalendTweet {
        private long id;
        private String text;
        private Date createdAt;
        private String fromUser;
        private String profileImageUrl;
        private Long toUserId;
        private long fromUserId;
        private String languageCode;
        private  String source;
        private String unmodifiedText;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Date getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
        }

        public String getFromUser() {
            return fromUser;
        }

        public void setFromUser(String fromUser) {
            this.fromUser = fromUser;
        }

        public String getProfileImageUrl() {
            return profileImageUrl;
        }

        public void setProfileImageUrl(String profileImageUrl) {
            this.profileImageUrl = profileImageUrl;
        }

        public Long getToUserId() {
            return toUserId;
        }

        public void setToUserId(Long toUserId) {
            this.toUserId = toUserId;
        }

        public long getFromUserId() {
            return fromUserId;
        }

        public void setFromUserId(long fromUserId) {
            this.fromUserId = fromUserId;
        }

        public String getLanguageCode() {
            return languageCode;
        }

        public void setLanguageCode(String languageCode) {
            this.languageCode = languageCode;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getUnmodifiedText() {
            return unmodifiedText;
        }

        public void setUnmodifiedText(String unmodifiedText) {
            this.unmodifiedText = unmodifiedText;
        }
    }

}
