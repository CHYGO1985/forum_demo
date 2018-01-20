package com.jingjie.forum_demo.service;


import com.jingjie.forum_demo.util.ForumDemoAppUtil;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * The class provides the service to filter sensitive words in the posts.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 20, 2018
 */
@Service
public class SensitiveWordService implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveWordService.class);

    private TriNode rootNode = new TriNode();

    public TriNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(TriNode rootNode) {
        this.rootNode = rootNode;
    }

    /**
     *
     * The method is to build sensitive words dictionary from a sensitive
     * words file.
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {

        try {

            InputStream input = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("others/sensitive_words.txt");

            InputStreamReader reader = new InputStreamReader(input);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String text = "";
            while (( text = bufferedReader.readLine() ) != null) {

                text = text.trim();
                addWordToDic(text);
            }

            reader.close();
        }
        catch (Exception ex) {
            logger.error("Read sensitive word file failed: " +
                ex.getMessage());
        }
    }

    /**
     *
     * Filter all sensitive words in a string.
     *
     * @param text
     * @return
     */
    public String filterWords (String text) {

        if (StringUtils.isBlank(text)) {
            return text;
        }

        TriNode curNode = rootNode;

        // Algorithm" two pointers tech
        // 1. frontPointers, search from start to end of text
        // 2. sens word ponters, when forntPointers meet a char that is in
        // the trinode, move sensWordPointer to next one

        StringBuilder builder = new StringBuilder();
        int frontPointer = 0;
        int sensWordPointer = 0;

        while (sensWordPointer < text.length()) {

            // Check whether trinode means the end of a sens word

            // if find a sens word, move snesWordPointers + 1
            // frontPointer = sensWordPointer
            // append(replacement)
            // repeat the process
            if (curNode.isWord() == true) {

                builder.append(ForumDemoAppUtil.SENS_WORD_REPLACE);
                frontPointer = sensWordPointer;
                curNode = rootNode;
            }

            // check whether the current char pointed by frontPointer is in
            // the sens dic
            char curChar = text.charAt(sensWordPointer);

            // skip invalid simble
            if (isInvalidSymbol(curChar) == true) {
                sensWordPointer ++;
                continue;
            }

            Map<Character, TriNode> curMap = curNode.getMap();

            // if it is a sens char, sensWordPointer ++
            if (curMap.containsKey(curChar)) {

                sensWordPointer ++;
                curNode = curMap.get(curChar);
                continue;
            }

            // if it is not a sens char, add it to res string
            // frontPointer ++
            // sensWordPointer = frontpointer
            builder.append(text.charAt(frontPointer));
            frontPointer ++;
            sensWordPointer = frontPointer;
            curNode = rootNode;
        }

        if (curNode.isWord() == true) {

            builder.append(ForumDemoAppUtil.SENS_WORD_REPLACE);
        }

        return builder.toString();
    }

    /**
     *
     * Check whether a character is an valid east Asian word.
     *
     * @param c
     * @return
     */
    private boolean isInvalidSymbol (char c) {

        int ic = (int) c;

        // 0x2E80-0x9FFF is the spectrum of asian words
        return (CharUtils.isAsciiAlphanumeric(c) == false) && (ic < 0x2E80 || ic > 0x9FFF);
    }

    /**
     *
     * Add a word to dictionary.
     *
     * @param word
     */
    public void addWordToDic (String word) {

        // 1.check whether a char is in the curNode
        TriNode curNode = rootNode;

        for (int i = 0; i < word.length(); i ++) {

            char curChar = word.charAt(i);
            // filter invalid chars
            if (isInvalidSymbol(curChar) == true)
                continue;

            Map<Character, TriNode> curMap = curNode.getMap();
            // if not, add curChar to curNode and create a new node for current
            // char, go the next char in the content
            if (curMap.containsKey(curChar) == false) {

                curMap.put(curChar, new TriNode());
            }

            // 2. if cur node has current char, go to next char of contents
            // get the trinode of the current char and assign it as the curNode
            // them repeat 1
            curNode = curMap.get(curChar);
        }

        // if it is the end of content, user a NULL node and mark it as word
        curNode.setWord(true);
    }

    private class TriNode {

        private boolean isWord;
        private Map<Character, TriNode> map;

        public TriNode () {

            isWord = false;
            map = new HashMap<>();

        }

        public boolean isWord() {
            return isWord;
        }

        public void setWord(boolean word) {
            isWord = word;
        }

        public Map<Character, TriNode> getMap() {
            return map;
        }

        public void setMap(Map<Character, TriNode> map) {
            this.map = map;
        }
    }

    // test
    /*
    public static void main (String[] args) {

        SensitiveWordService service = new SensitiveWordService();
        service.addWordToDic("赌博");
        // service.addWordToDic("赌博");
        System.out.println(service.filterWords("你好赌@#$博"));
    }
    */
}
