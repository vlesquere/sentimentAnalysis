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
import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

public class SentimentAnalysis {

    private static StanfordCoreNLP pipeline;

    public static void init() {
        pipeline = new StanfordCoreNLP("nlp.properties");
    }

    /**
     * find sentiment for a sentence
     *
     * @param tweet the sentence to analyze
     * @return Sentiment scores
     * 0 very negative
     * 1 negative
     * 2 neutral
     * 3 positive
     * 4 very positive
     */
    public static int findSentimentStanford_NeuralNetwork(String tweet) {
        int mainSentiment = 0;

        if (StringUtils.isNotEmpty(tweet)) {
            Annotation annotation = pipeline.process(tweet);
            for (CoreMap map : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = map.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                if (sentiment > mainSentiment)
                    mainSentiment = sentiment;
            }
        }

        return mainSentiment;
    }

    public static int findSentimentOpenNLP(String tweet) {
        int mainSentiment = 0;
        DoccatModel model = null;

        try {
            InputStreamFactory isf = new InputStreamFactory() {
                public InputStream createInputStream() throws IOException {
                    return new FileInputStream("en-sentiment.train");
                }
            };

            ObjectStream<String> lineStream = new PlainTextByLineStream(isf, "UTF-8");
            ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

            model = DocumentCategorizerME.train("en", sampleStream, TrainingParameters.defaultParams(), new DoccatFactory());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mainSentiment;
    }
}
