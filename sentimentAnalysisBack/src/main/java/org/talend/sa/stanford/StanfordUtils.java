package org.talend.sa.stanford;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.sentiment.SentimentUtils;
import edu.stanford.nlp.simple.Sentence;
import org.talend.sa.service.SentimentAnalysis;

import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.*;

public class StanfordUtils {

    public static List<String> tokenize(String text) throws FileNotFoundException {
        List<String> labels = new ArrayList<String>();

        return labels;
    }

    public static Map<String, String> posTag(List<String> tokens) {
        Map<String, String> taggedTokens = new LinkedHashMap<>();

        return taggedTokens;
    }

}