package com.library;

import java.util.*;

/**
 * Solution for finding all pairs of integers whose sum equals a target value.
 * Optimized for time complexity O(n) using HashMap approach.
 */
public class PairSumSolution {

    /**
     * Finds all unique pairs in the array that sum to the target value.
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     *
     * @param array the input array of integers
     * @param target the target sum value
     * @return list of pairs that sum to target, avoiding duplicates
     */
    public static List<int[]> findPairsWithSum(int[] array, int target) {
        if (array == null || array.length < 2) {
            return new ArrayList<>();
        }

        Map<Integer, Integer> numToIndex = new HashMap<>();
        Set<String> seenPairs = new HashSet<>();
        List<int[]> result = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {
            int current = array[i];
            int complement = target - current;

            // Check if complement exists in map
            if (numToIndex.containsKey(complement)) {
                // Create pair with smaller number first to avoid duplicates
                int first = Math.min(current, complement);
                int second = Math.max(current, complement);
                String pairKey = first + "," + second;

                // Only add if we haven't seen this pair before
                if (!seenPairs.contains(pairKey)) {
                    result.add(new int[]{first, second});
                    seenPairs.add(pairKey);
                }
            }

            // Add current number to map
            numToIndex.put(current, i);
        }

        return result;
    }

    /**
     * Alternative solution using two pointers approach (requires sorting).
     * Time Complexity: O(n log n) due to sorting
     * Space Complexity: O(1) excluding result storage
     */
    public static List<int[]> findPairsWithSumTwoPointers(int[] array, int target) {
        if (array == null || array.length < 2) {
            return new ArrayList<>();
        }

        // Create array of {value, originalIndex} pairs
        int[][] valueIndexPairs = new int[array.length][2];
        for (int i = 0; i < array.length; i++) {
            valueIndexPairs[i][0] = array[i];
            valueIndexPairs[i][1] = i;
        }

        // Sort by value
        Arrays.sort(valueIndexPairs, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> result = new ArrayList<>();
        Set<String> seenPairs = new HashSet<>();
        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            int sum = valueIndexPairs[left][0] + valueIndexPairs[right][0];

            if (sum == target) {
                int first = Math.min(valueIndexPairs[left][0], valueIndexPairs[right][0]);
                int second = Math.max(valueIndexPairs[left][0], valueIndexPairs[right][0]);
                String pairKey = first + "," + second;

                if (!seenPairs.contains(pairKey)) {
                    result.add(new int[]{first, second});
                    seenPairs.add(pairKey);
                }

                left++;
                right--;
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }

        return result;
    }

    /**
     * Utility method to print pairs in a readable format
     */
    public static void printPairs(List<int[]> pairs) {
        System.out.print("[");
        for (int i = 0; i < pairs.size(); i++) {
            int[] pair = pairs.get(i);
            System.out.print("(" + pair[0] + ", " + pair[1] + ")");
            if (i < pairs.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    /**
     * Main method to demonstrate the solution
     */
    public static void main(String[] args) {
        // Test case from the problem
        int[] array = {2, 4, 3, 7, 1, 5};
        int target = 6;

        System.out.println("Input Array: " + Arrays.toString(array));
        System.out.println("Target Sum: " + target);
        System.out.println();

        // Test HashMap approach
        System.out.println("HashMap Approach (O(n) time):");
        List<int[]> pairs1 = findPairsWithSum(array, target);
        printPairs(pairs1);
        System.out.println();

        // Test Two Pointers approach
        System.out.println("Two Pointers Approach (O(n log n) time):");
        List<int[]> pairs2 = findPairsWithSumTwoPointers(array, target);
        printPairs(pairs2);
        System.out.println();

        // Additional test cases
        runAdditionalTests();
    }

    /**
     * Additional test cases to verify correctness
     */
    public static void runAdditionalTests() {
        System.out.println("=== Additional Test Cases ===");

        // Test case 1: No pairs found
        int[] test1 = {1, 2, 3};
        int target1 = 10;
        System.out.println("Test 1 - Array: " + Arrays.toString(test1) + ", Target: " + target1);
        List<int[]> result1 = findPairsWithSum(test1, target1);
        printPairs(result1);
        System.out.println();

        // Test case 2: Multiple pairs with duplicates
        int[] test2 = {1, 1, 2, 2, 3, 3};
        int target2 = 4;
        System.out.println("Test 2 - Array: " + Arrays.toString(test2) + ", Target: " + target2);
        List<int[]> result2 = findPairsWithSum(test2, target2);
        printPairs(result2);
        System.out.println();

        // Test case 3: Negative numbers
        int[] test3 = {-1, 0, 1, 2, -1, -4};
        int target3 = 0;
        System.out.println("Test 3 - Array: " + Arrays.toString(test3) + ", Target: " + target3);
        List<int[]> result3 = findPairsWithSum(test3, target3);
        printPairs(result3);
        System.out.println();

        // Test case 4: Same number twice
        int[] test4 = {3, 3, 3};
        int target4 = 6;
        System.out.println("Test 4 - Array: " + Arrays.toString(test4) + ", Target: " + target4);
        List<int[]> result4 = findPairsWithSum(test4, target4);
        printPairs(result4);
        System.out.println();
    }
}