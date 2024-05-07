import java.util.*;

// searching algorithm: linear search (to test the RAM)
/* this program tests how efficiently a simple linear search 
   algorithm performs when searching through a small array stored in memory. 
   The time taken for the search operation can vary based on factors such as 
   the size of the array, the distribution of random numbers, and the efficiency 
   of the search algorithm. */

class SearchTheNumber {

    public static void main(String[] args) {
        // scanner object to read user input
        Scanner scanner = new Scanner(System.in);

        // generate a random array of 70 numbers between 0 and 100
        int[] array = generateRandomArray(70, 0, 100);

        // print the generated array (for debugging purposes)
        System.out.println("Generated Array: " + Arrays.toString(array));

        // prompt the user to enter a number to search for
        System.out.print("Enter a number between 0 and 100 to search for: ");
        int target = scanner.nextInt();

        // perform linear search and measure execution time
        long startTime = System.nanoTime();
        int index = linearSearch(array, target);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        // print the result
        if (index != -1) {
            System.out.println("Number " + target + " found at index " + index);
        } else {
            System.out.println("Number " + target + " not found in the array");
        }

        // print the execution time in nanoseconds
        System.out.println("Execution time: " + duration + " nanoseconds");

        scanner.close();
    }

    // this method generates a random array of integers
    private static int[] generateRandomArray(int size, int min, int max) {
        Random rand = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            /* rand.nextInt(max - min + 1) generates a random integer between 0 (inclusive) and max - min (exclusive)
               the + 1 ensures that the upper bound is inclusive
               adding min to the result shifts the range of random numbers to start from min */
            array[i] = rand.nextInt(max - min + 1) + min; // generate a random number between min and max (0 and 100 in this case)
        }
        return array;
    }

    // linear search
    private static int linearSearch(int[] array, int target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i; // the element was found
            }
        }
        return -1; // the element was not found
    }
}
