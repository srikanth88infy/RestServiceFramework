package com.dominikdorn.tuwien.evs.rest.requestHandling;

import com.dominikdorn.rest.requestHandling.OperationType;
import com.dominikdorn.rest.requestHandling.OperationTypeFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class OperationTypeFilterTest {

    OperationTypeFilter filter;

    @Before
    public void setUp() throws Exception {
        filter = new OperationTypeFilter();
    }

    @After
    public void tearDown() throws Exception {
    }


        @Test
    public void evaluateRequestTest_resource_withWrongParam() {
        String input = "/items/adfadsf";

        OperationType type = filter.evaluateRequest(input);
        assertEquals(OperationType.BAD_REQUEST, type);
    }

    @Test
    public void evaluateRequestTest_resourceWithoutSlash() {
        String input = "/items";

        OperationType type = filter.evaluateRequest(input);
        assertEquals(OperationType.ONLY_RESOURCE, type);
    }

    @Test
    public void evaluateRequestTest_resourceWithSlash() {
        String input = "/items/";

        OperationType type = filter.evaluateRequest(input);
        assertEquals(OperationType.ONLY_RESOURCE, type);
    }

    @Test
    public void evaluateRequestTest_resourceWithId() {
        String input = "/items/1";

        OperationType type = filter.evaluateRequest(input);
        assertEquals(OperationType.SPECIFIC_ID, type);
    }

    @Test
    public void evaluateRequestTest_resourceSearch() {
        String input = "/items/search";

        OperationType type = filter.evaluateRequest(input);
        assertEquals(OperationType.SEARCH, type);
    }

    @Test
    public void getResourceName_PathWithoutSlash() {
        String path = "/items";
        OperationType type = filter.evaluateRequest(path);
        assertEquals("items", filter.getResourceName(type, path));
    }

    @Test
    public void getResourceName_PathWitSlash() {
        String path = "/items/";
        OperationType type = filter.evaluateRequest(path);
        assertEquals("items", filter.getResourceName(type, path));
    }

    @Test
    public void getResourceName_Searching() {
        String path = "/items/search";
        OperationType type = filter.evaluateRequest(path);
        assertEquals("items", filter.getResourceName(type, path));
    }

    @Test
    public void getResourceName_specificId() {
        String path = "/items/5";
        OperationType type = filter.evaluateRequest(path);
        assertEquals("items", filter.getResourceName(type, path));
    }


}
