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
package org.talend.sa.service;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.apache.commons.lang3.StringUtils;

public class SentimentAnalysis {

    public static void init() {
        //init client library (Stanford/Open NLP)
    }

    /**
     * find sentiment for a sentence
     * @param tweet the sentence to analyze
     * @return
     *
     *  Sentiment scores
     *    0 very negative
     *    1 negative
     *    2 neutral
     *    3 positive
     *    4 very positive
     */
    public static int findSentiment(String tweet) {
        //use neural network in order to get sentiment
        int mainSentiment = 0;
        return mainSentiment;
    }
}
