package com.ossez.framework.common.tests.dao;


import com.ossez.framework.common.dao.Factory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.annotations.Synchronize;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ValidationException;
import java.util.*;

/**
 * ReservationTest Testing
 *
 * @author YuCheng Hu
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SearchTest {
    private static Logger logger = LoggerFactory.getLogger(SearchTest.class);
    HashMap<Integer, List> tokenMap = new HashMap<>();
    HashMap<String, Set> queryToken = new HashMap<>();

    @BeforeEach
    protected void setUp() throws Exception {
        logger.debug("-OPEN Database Connection-");
//        Factory.beginTransaction();
    }

    @AfterEach
    protected void tearDown() throws Exception {
        logger.debug("-CLOSE Database Connection-");
//        Factory.commitTransaction();
    }

    /**
     * Tests Index
     */
    @Test
    public void testIndex() throws ValidationException {
        // DO INDEX
        logger.debug(indexDocument("index 1 soup tomato cream salt"));
        logger.debug(indexDocument("index 2 cake sugar eggs flour sugar cocoa cream butter"));
        logger.debug(indexDocument("index 1 bread butter salt"));
        logger.debug(indexDocument("index 3 soup fish potato salt pepper"));
        logger.debug("Size of token map - [{}]", tokenMap.size());


        // LOAD DATA TO RAM
        convertToken();

        // DO QUERY
//        logger.debug(query("in: query butter"));
//        logger.debug(query("in: query sugar"));
//        logger.debug(query("in: query soup"));
        logger.debug(query("in: query (butter | potato) & (salt | tea)"));
        logger.debug("Size of query Token - [{}]", queryToken.size());
    }

    /**
     * Tests Query
     */
    @Test
    public void testQuery() throws ValidationException {

    }


    /**
     * Build reservationRequest
     *
     * @return
     */
    private String indexDocument(String token) {

        List<String> tokenList = Arrays.asList(StringUtils.split(token));

        if (tokenList.size() < 3)
            return "index error: The format of input error. Please using format: [index 1 soup tomato cream salt]";

        if (!StringUtils.equalsIgnoreCase(tokenList.get(0), "Index"))
            return "index error: The format of input error. Please using Index to start it";

        if (!NumberUtils.isCreatable(tokenList.get(1)))
            return "index error: The format of input error. The Doc Index must Number";


        Integer indexId = NumberUtils.toInt(tokenList.get(1));
        List<String> tokens = tokenList.subList(2, tokenList.size());


        if (ObjectUtils.isNotEmpty(tokenMap.get(indexId)))
            tokenMap.remove(indexId);

        tokenMap.put(indexId, tokens);


        return "index ok " + indexId;
    }

    /**
     * Build reservationRequest
     *
     * @return
     */
    private String query(String q) {
        List<String> qList = Arrays.asList(StringUtils.split(q));
        if (qList.size() < 3)
            return "query error: The format of input error. Please using format: [in: query butter]";

        if (!StringUtils.equalsIgnoreCase(qList.get(0), "in:"))
            return "index error: The format of input error. Please using [in:] to start it";

        if (!StringUtils.equalsIgnoreCase(qList.get(1), "query"))
            return "index error: The format of input error. String of command is [query]";

        q = StringUtils.replace(q, StringUtils.SPACE, StringUtils.EMPTY);
        q = StringUtils.replace(q, "in:query", StringUtils.EMPTY);
        List<String> queryList = processQueryString(q);

        // ONLY SEARCH 1 TOKEN
        if (queryList.size() == 1) {
            return "query results " + Arrays.toString(queryToken.get(queryList.get(0)).toArray());
        }



        Set<Integer> result = new HashSet<>();
        result.clear();

        for (String qStr : queryList) {
            qStr = StringUtils.remove(qStr, "(");
            qStr = StringUtils.remove(qStr, ")");

            logger.debug(">>>>>>>>>>{}", qStr);
//            if (StringUtils.contains(qStr, "|") && !StringUtils.startsWith(qStr, "|")) {
//                result = orCal(StringUtils.split(qStr, "|")[0], StringUtils.split(qStr, "|")[1]);
//            }
//
//            if (StringUtils.contains(qStr, "&") && !StringUtils.startsWith(qStr, "&")) {
//                result = andCal(StringUtils.split(qStr, "&")[0], StringUtils.split(qStr, "&")[1]);
//            }

//            if (StringUtils.startsWith(qStr, "|") || StringUtils.endsWith(qStr,"|")) {
//                result.addAll(queryToken.get(StringUtils.substringAfter(qStr, "|")));
//            }
//
//            if (StringUtils.startsWith(qStr, "&")|| StringUtils.endsWith(qStr,"&")) {
//                result.retainAll(queryToken.get(StringUtils.substringAfter(qStr, "&")));
//            }
        }


        return "query results " + Arrays.toString(result.toArray());
    }

    /**
     * LOAD INDEX
     */
    private void convertToken() {

        queryToken = new HashMap<String, Set>();
        tokenMap.forEach((k, v) -> {
            Integer docId = k;
            List<String> tokens = v;


            for (String token : tokens) {
                Set<Integer> docIdset = new HashSet<>();
                if (ObjectUtils.isEmpty(queryToken.get(token))) {
                    docIdset.add(docId);
                } else {
                    docIdset = queryToken.get(token);
                    docIdset.add(docId);
                }

                queryToken.put(token, docIdset);
            }


        });

    }


    /**
     * @return
     * @throws ValidationException
     */
    private List<String> processQueryString(String q) throws ValidationException {
        List<String> opList = Lists.newArrayList();

//        String input = "(a|b)&c";
        while (StringUtils.isNotEmpty(q)) {
            String op = StringUtils.EMPTY;

            if (StringUtils.contains(q, "(") && StringUtils.contains(q, ")")) {
                String opRight = StringUtils.substringAfterLast(q, "(");
                int startIndex = StringUtils.lastIndexOf(q, "(");
                int endIndex = startIndex + StringUtils.indexOf(opRight, ")");
                op = StringUtils.substring(q, startIndex, endIndex + 2);
            } else {
                op = q;
            }

            opList.add(op);
            q = StringUtils.remove(q, op);
        }

        return opList;
    }


    /**
     * OR Cal
     *
     * @param token1
     * @param token2
     * @return
     */
    private Set<Integer> orCal(String token1, String token2) {
        Set<Integer> result = new HashSet<>();
        result.addAll(queryToken.get(token1));
        result.addAll(queryToken.get(token2));

        return result;
    }

    /**
     * AND Cal
     *
     * @param token1
     * @param token2
     * @return
     */
    private Set<Integer> andCal(String token1, String token2) {
        Set<Integer> result = new HashSet<>();
        result.addAll(queryToken.get(token1));
        result.retainAll(queryToken.get(token2));

        return result;
    }
}


