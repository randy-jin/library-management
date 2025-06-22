package com.library;

import java.util.*;

/**
 * Solution for finding all pairs of integers whose sum equals a target value.
 * This class provides two different approaches:
 * 1. HashMap approach - O(n) time complexity (optimal)
 * 2. Two-pointer approach - O(n log n) time complexity
 */
public class PairSumSolution {

    /**
     * Finds all unique pairs in the array that sum to the target value using HashMap.
     *
     * Algorithm:
     * 1. For each element, calculate its complement (target - current)
     * 2. Check if complement exists in our hash map
     * 3. If found, form a pair and ensure we don't add duplicates
     * 4. Add current element to hash map for future lookups
     *
     * Edge cases:
     * - Empty or single element arrays return empty list
     * - Handles duplicate values in input array correctly
     * - Avoids adding the same pair multiple times
     *
     * Time Complexity: O(n) - single pass through array with O(1) lookups
     * Space Complexity: O(n) - storing elements in HashMap and tracking seen pairs
     *
     * @param array the input array of integers
     * @param target the target sum value
     * @return list of pairs that sum to target, avoiding duplicates
     */
    public static List<int[]> findPairsWithSum(int[] array, int target) {
        // Handle edge case of null or too small array
        if (array == null || array.length < 2) {
            return new ArrayList<>();
        }

        // Map to store number -> index for quick lookups
        Map<Integer, Integer> numToIndex = new HashMap<>();
        // Set to track pairs we've already seen (avoid duplicates)
        Set<String> seenPairs = new HashSet<>();
        List<int[]> result = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {
            int current = array[i];
            // Calculate the complement needed to reach target
            int complement = target - current;

            // Check if we've seen the complement before
            if (numToIndex.containsKey(complement)) {
                // Order pair with smaller number first for consistency
                int first = Math.min(current, complement);
                int second = Math.max(current, complement);
                String pairKey = first + "," + second;

                // Only add unique pairs (handles duplicates in input)
                if (!seenPairs.contains(pairKey)) {
                    result.add(new int[]{first, second});
                    seenPairs.add(pairKey);
                }
            }

            // Store current number for future lookups
            // Note: If duplicates exist, this will update the index
            numToIndex.put(current, i);
        }

        return result;
    }

    /**
     * Alternative solution using two pointers approach after sorting.
     *
     * Algorithm:
     * 1. Create pairs of [value, originalIndex] to track original positions
     * 2. Sort the array by values
     * 3. Use two pointers (left and right) at array ends
     * 4. Move pointers based on sum comparison with target
     *
     * Advantages:
     * - Uses less memory than HashMap approach
     * - More cache-friendly due to sequential access pattern
     *
     * Disadvantages:
     * - Requires sorting (O(n log n))
     * - More complex implementation
     *
     * Edge cases:
     * - Empty or too small arrays
     * - Duplicate values in the array
     * - Need to track original indices to avoid using same element twice
     *
     * Time Complexity: O(n log n) due to sorting step
     * Space Complexity: O(n) for the valueIndexPairs array, O(1) otherwise
     */
    public static List<int[]> findPairsWithSumTwoPointers(int[] array, int target) {
        if (array == null || array.length < 2) {
            return new ArrayList<>();
        }

        // Create array of [value, originalIndex] pairs to preserve original indices
        int[][] valueIndexPairs = new int[array.length][2];
        for (int i = 0; i < array.length; i++) {
            valueIndexPairs[i][0] = array[i]; // Store value
            valueIndexPairs[i][1] = i;        // Store original index
        }

        // Sort pairs by value - O(n log n)
        Arrays.sort(valueIndexPairs, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> result = new ArrayList<>();
        Set<String> seenPairs = new HashSet<>(); // To avoid duplicate pairs
        int left = 0;
        int right = array.length - 1;

        // Two-pointer technique - O(n)
        while (left < right) {
            int sum = valueIndexPairs[left][0] + valueIndexPairs[right][0];

            if (sum == target) {
                // Found a pair, add to result if not seen before
                int first = Math.min(valueIndexPairs[left][0], valueIndexPairs[right][0]);
                int second = Math.max(valueIndexPairs[left][0], valueIndexPairs[right][0]);
                String pairKey = first + "," + second;

                if (!seenPairs.contains(pairKey)) {
                    result.add(new int[]{first, second});
                    seenPairs.add(pairKey);
                }

                // Move both pointers to find more pairs
                left++;
                right--;
            } else if (sum < target) {
                // Sum is too small, increase left pointer to get larger values
                left++;
            } else {
                // Sum is too large, decrease right pointer to get smaller values
                right--;
            }
        }

        return result;
    }

    /**
     * Utility method to print pairs in a readable format.
     * Converts the list of int arrays to a human-readable string format.
     *
     * @param pairs List of integer pairs to be printed
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
     * Main method to demonstrate the solution with the example from problem statement.
     * Runs both approaches on the same input and displays results.
     */
    public static void main(String[] args) {
        // Test case from the problem statement
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
     * Additional test cases to verify correctness of the solution.
     * Tests various edge cases and scenarios:
     * 1. No pairs found
     * 2. Multiple pairs with duplicate values
     * 3. Negative numbers
     * 4. Multiple instances of the same number
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

        // Test case 4: Same number twice (shows how the solution handles the case
        // mentioned in the problem statement where 3+3=6)
        int[] test4 = {3, 3, 3};
        int target4 = 6;
        System.out.println("Test 4 - Array: " + Arrays.toString(test4) + ", Target: " + target4);
        List<int[]> result4 = findPairsWithSum(test4, target4);
        printPairs(result4);
        System.out.println();
    }
}