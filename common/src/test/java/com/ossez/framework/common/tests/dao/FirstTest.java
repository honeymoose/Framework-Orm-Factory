package com.ossez.framework.common.tests.dao;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test Logger and function
 *
 * @author YuCheng Hu
 */
public class FirstTest {
    private static Logger logger = LoggerFactory.getLogger(FirstTest.class);

    /**
     * Check test function
     */
    @Test
    public void testString() {
        logger.debug("TEST String");
    }
}
