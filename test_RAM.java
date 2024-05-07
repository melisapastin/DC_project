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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Generate a random array of 80 numbers between 10 (for debugging purposes) and 100 
        int[] array = generateRandomArray(80, 10, 100);

        // Print the generated array (for debugging purposes)
        System.out.println("Generated Array: " + Arrays.toString(array));

        // Prompt the user to enter a number to search for
        System.out.print("Enter a number between 0 and 100 to search for: ");
        int target = scanner.nextInt();

        // Perform linear search and measure execution time
        long startTime = System.nanoTime();
        int index = linearSearch(array, target);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        // Calculate RAM Score = (the length of the array / time (in nanoseconds) * memory utilization ratio * 1 000 000)
        // The memory utilization ratio provides insights into resource efficiency
        double memoryUtilization = (double) array.length / Runtime.getRuntime().totalMemory(); 
        double ramScore = (double) array.length / duration * memoryUtilization;
        ramScore = ramScore * 1000000; // Multiply by 1000000 because the time is computed in nanoseconds 

        // Print the result
        if (index != -1) {
            System.out.println("Number " + target + " found at index " + index);
        } else {
            System.out.println("Number " + target + " not found in the array");
        }

        // Print the RAM Score
        System.out.println("RAM Score: " + ramScore);

        scanner.close();
    }
}

/* Searching for a number at the beginning of the array would likely output a larger 
   RAM Score, while searching for a number at the end of the array would result in a 
   smaller RAM Score. This is because the RAM Score is inversely proportional to the 
   execution time, and the execution time tends to be shorter when the target number 
   is located closer to the beginning of the array. */
