package org.talend.sa.stanford;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentUtils;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.simple.SentimentClass;
import edu.stanford.nlp.simple.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.talend.sa.service.SentimentAnalysis;

import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.*;

public class StanfordUtils {

    private static final Logger log = LoggerFactory.getLogger(StanfordUtils.class);

    public static List<String> tokenize(String text) throws FileNotFoundException {
        List<String> labels = new ArrayList<String>();

        PTBTokenizer tokenizer = new PTBTokenizer(new StringReader(text), new CoreLabelTokenFactory(), null);

        List<CoreLabel> tokens = tokenizer.tokenize();
        for (CoreLabel token : tokens)
            labels.add(token.value());

        return labels;
    }

    public static Map<String, String> posTag(List<String> tokens) {
        Map<String, String> taggedTokens = new LinkedHashMap<>();

        Sentence sentence = new Sentence(tokens);
        List<String> tags = sentence.nerTags();

        SentimentClass sentimentClass = sentence.sentiment();
        log.info(tokens + " : " + sentimentClass.name());

        for (int i = 0; i < tokens.size(); i++) {
            taggedTokens.put(tokens.get(i), tags.get(i));
        }

        return taggedTokens;
    }

}