package org.example;

import java.util.*;

class SearchTheNumber {
    // Generate a random array of integers
    public static int[] generateRandomArray(int size, int min, int max) {
        Random rand = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(max - min + 1) + min; // Generate a random number between min and max 
        }
        return array;
    }

    // Linear search
    public static int linearSearch(int[] array, int target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i; // The element was found
            }
        }
        return -1; // The element was not found
    }

    // Calculate RAM Score (arrayLength * memoryUtilization) / durationInSeconds
    public static double calculateRAMScore(int arrayLength, double memoryUtilization, double durationInSeconds) {
        return (arrayLength * memoryUtilization) / durationInSeconds;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Generate a random array of 70 numbers between 10 (for debugging purposes) and 100 
        int[] array = generateRandomArray(70, 10, 100);

        // Print the generated array (for debugging purposes)
        System.out.println("Generated Array: " + Arrays.toString(array));

        // Prompt the user to enter a number to search for
        System.out.print("Enter a number between 0 and 100 to search for: ");
        int target = scanner.nextInt();

        // Perform linear search and measure execution time
        long startTime = System.nanoTime();
        int index = linearSearch(array, target);
        long endTime = System.nanoTime();
        long durationInNanoseconds = endTime - startTime;

        // Convert the duration to seconds
        double durationInSeconds = durationInNanoseconds / 1_000_000_000.0;

        // Calculate memory utilization
        // The memory utilization ratio provides insights into resource efficiency
        // It indicates the proportion of memory used by the array compared to the total memory available to the program
        /* Runtime.getRuntime().totalMemory() returns the total amount of memory that the Java Virtual Machine (JVM) 
           has allocated for the Java process to use */
        double memoryUtilization = (double) array.length / Runtime.getRuntime().totalMemory();

        // Calculate RAM Score using the new method
        double ramScore = calculateRAMScore(array.length, memoryUtilization, durationInSeconds);

        // Print the result
        if (index != -1) {
            System.out.println("Number " + target + " found at index " + index);
        } else {
            System.out.println("Number " + target + " not found in the array");
        }

        System.out.printf("Execution time: %.9f seconds\n", durationInSeconds);

        // Print the RAM Score
        System.out.println("RAM Score: " + ramScore);
        PopulateRAMtableFromDatabase.InsertTestResultsIntoDatabase(ramScore,durationInSeconds);
        scanner.close();
    }
}

/* Searching for a number at the beginning of the array would likely output a larger 
   RAM Score, while searching for a number at the end of the array (or one that 
   cannot be found in the array at all) would result in a smaller RAM Score. This is 
   because the RAM Score is inversely proportional to the execution time, and the 
   execution time tends to be shorter when the target number is located closer to 
   the beginning of the array. */
