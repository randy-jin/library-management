package com.library;

/**
 * Unit tests for the PairSumSolution class
 */
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PairSumSolutionTest {

    @Test
    void testBasicCase() {
        int[] array = {2, 4, 3, 7, 1, 5};
        int target = 6;
        List<int[]> result = PairSumSolution.findPairsWithSum(array, target);

        assertEquals(2, result.size());

        // Verify pairs (order may vary)
//        Set<String> expectedPairs = Set.of("1,5", "2,4", "3,3");
        Set<String> expectedPairs = Set.of("1,5", "2,4");
        Set<String> actualPairs = new HashSet<>();

        for (int[] pair : result) {
            actualPairs.add(pair[0] + "," + pair[1]);
        }

        assertEquals(expectedPairs, actualPairs);
    }

    @Test
    void testEmptyArray() {
        int[] array = {};
        int target = 5;
        List<int[]> result = PairSumSolution.findPairsWithSum(array, target);

        assertTrue(result.isEmpty());
    }

    @Test
    void testNullArray() {
        int[] array = null;
        int target = 5;
        List<int[]> result = PairSumSolution.findPairsWithSum(array, target);

        assertTrue(result.isEmpty());
    }

    @Test
    void testSingleElement() {
        int[] array = {5};
        int target = 10;
        List<int[]> result = PairSumSolution.findPairsWithSum(array, target);

        assertTrue(result.isEmpty());
    }

    @Test
    void testNoPairsFound() {
        int[] array = {1, 2, 3};
        int target = 10;
        List<int[]> result = PairSumSolution.findPairsWithSum(array, target);

        assertTrue(result.isEmpty());
    }

    @Test
    void testWithDuplicates() {
        int[] array = {1, 1, 2, 2, 3, 3};
        int target = 4;
        List<int[]> result = PairSumSolution.findPairsWithSum(array, target);

        assertEquals(2, result.size());

        Set<String> expectedPairs = Set.of("1,3", "2,2");
        Set<String> actualPairs = new HashSet<>();

        for (int[] pair : result) {
            actualPairs.add(pair[0] + "," + pair[1]);
        }

        assertEquals(expectedPairs, actualPairs);
    }

    @Test
    void testWithNegativeNumbers() {
        int[] array = {-1, 0, 1, 2, -1, -4};
        int target = 0;
        List<int[]> result = PairSumSolution.findPairsWithSum(array, target);

        assertEquals(1, result.size());
        assertEquals(-1, result.get(0)[0]);
        assertEquals(1, result.get(0)[1]);
    }

    @Test
    void testBothMethodsProduceSameResult() {
        int[] array = {2, 4, 3, 7, 1, 5, 6, 0};
        int target = 6;

        List<int[]> result1 = PairSumSolution.findPairsWithSum(array, target);
        List<int[]> result2 = PairSumSolution.findPairsWithSumTwoPointers(array, target);

        assertEquals(result1.size(), result2.size());

        // Convert to sets for comparison
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();

        for (int[] pair : result1) {
            set1.add(pair[0] + "," + pair[1]);
        }

        for (int[] pair : result2) {
            set2.add(pair[0] + "," + pair[1]);
        }

        assertEquals(set1, set2);
    }
}