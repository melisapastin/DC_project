import java.math.*;
import java.util.*;

class DigitsOfPi {
    // Compute the first n digits of pi using the Gauss-Legendre algorithm
    public static BigDecimal computePi(int numDigits) {
        // Precision for the calculations
        MathContext mc = new MathContext(numDigits + 5, RoundingMode.HALF_UP);

        // Initial values for Gauss-Legendre algorithm
        // a0 = 1, b0 = 1/sqrt(2), t0 = 1/4, p0 = 1
        BigDecimal a = BigDecimal.ONE;
        BigDecimal b = BigDecimal.ONE.divide(BigDecimal.valueOf(2).sqrt(mc), mc);
        BigDecimal t = BigDecimal.valueOf(1).divide(BigDecimal.valueOf(4), mc);
        BigDecimal p = BigDecimal.ONE;

        // Perform iterations of Gauss-Legendre algorithm
        // aNext = (a + b) / 2
        // bNext = sqrt(a * b) 
        // tNext = t - p * (a - aNext) ^ 2  
        // pNext = 2 * p
        for (int i = 0; i < numDigits; i++) {
            BigDecimal aNext = a.add(b).divide(BigDecimal.valueOf(2), mc);
            BigDecimal bNext = a.multiply(b).sqrt(mc);
            BigDecimal tNext = t.subtract(p.multiply(a.subtract(aNext).pow(2)));
            BigDecimal pNext = p.multiply(BigDecimal.valueOf(2));

            a = aNext;
            b = bNext;
            t = tNext;
            p = pNext;
        }

        // Compute pi using Gauss-Legendre formula: pi =~ ((a + b) ^ 2) / (4 * t)
        BigDecimal pi = a.add(b).pow(2).divide(t.multiply(BigDecimal.valueOf(4)), mc);

        // Return pi with the specified precision
        return pi.setScale(numDigits, RoundingMode.DOWN);
    }

    // Function to calculate the CPU score formula ((digits * log(numArithmeticOperations)) / time (seconds))
    public static double calculateCPUScore(int numDigits, BigDecimal numArithmeticOperations, double computationTimeSeconds) {
        double cpuScore = (numDigits * Math.log(numArithmeticOperations.doubleValue())) / computationTimeSeconds;
        return cpuScore;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of digits of pi to compute: ");
        int numDigits = scanner.nextInt();

        // Calculate computation time in nanoseconds
        long startTime = System.nanoTime();
        BigDecimal pi = computePi(numDigits);
        long endTime = System.nanoTime();
        long computationTimeNanoseconds = endTime - startTime;

        // Convert computation time to seconds
        double computationTimeSeconds = computationTimeNanoseconds / 1_000_000_000.0;

        scanner.close();

        // Calculate and display CPU score
        // The number of arithmetic operations reflects how efficiently the algorithm computes pi relative to the computation time
        BigDecimal numArithmeticOperations = new BigDecimal(numDigits * 9); // Each iteration involves 9 arithmetic operations
        double cpuScore = calculateCPUScore(numDigits, numArithmeticOperations, computationTimeSeconds);
        System.out.println("CPU Score: " + cpuScore);

        // Display computation time in seconds
        System.out.println("Computation time: " + computationTimeSeconds + " seconds");

        System.out.println("First " + numDigits + " digits of pi:");
        System.out.println(pi);
    }
}

/* The score value gets bigger as the number of digits increases because the formula 
   (digits×log(numArithmeticOperations))/computationTime is dominated by the digits 
   term, which grows linearly with the number of digits, while the computation time 
   does not increase as rapidly. */