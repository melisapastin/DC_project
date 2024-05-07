import java.math.*;

// computing the digits of pi using the Chudnovsky algorithm (to test the CPU)
/* by performing a complex mathematical computation such as calculating pi with 
   a large number of digits, this program tests the CPU's computational capabilities. 
   The execution time provides insights into the CPU's performance for such tasks. */

class DigitsOfPi {
    // C represents the constant coefficient used in the Chudnovsky algorithm
    private static final BigDecimal C = new BigDecimal("426880");
    // SQRT_10005 represents the square root of 10005, used in the algorithm
    private static final BigDecimal SQRT_10005 = new BigDecimal("10005").sqrt(MathContext.DECIMAL128);
    // DIVISOR represents another constant divisor used in the algorithm
    private static final BigDecimal DIVISOR = new BigDecimal("545140134");

    // this method calculates the value of pi with the desired number of digits using the Chudnovsky algorithm
    public static BigDecimal computePi(int digits) {
        // 14.1816474627254776555 is chosen empirically to ensure rapid convergence of the Chudnovsky algorithm
        int k = (int) Math.ceil(digits / 14.1816474627254776555); 
        // k represents the number of iterations required for the algorithm to converge sufficiently for the specified number of digits
        // it reaches the desired accuracy/solution with relatively few iterations/computations

        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < k; i++) {
            /* inside the loop, two parts of the Chudnovsky series formula
               are calculated: the numerator and the denominator
               These calculations involve factorials, mathematical operations, and constants */
            BigDecimal numerator = new BigDecimal(factorial(6 * i)).multiply(new BigDecimal("13591409").add(new BigDecimal("545140134").multiply(BigDecimal.valueOf(i))));
            BigDecimal denominator = new BigDecimal(factorial(3 * i)).multiply(new BigDecimal(factorial(i)).pow(3)).multiply(new BigDecimal("-262537412640768000").pow(i));
            sum = sum.add(numerator.divide(denominator, digits + 5, BigDecimal.ROUND_HALF_UP));
        }

        // the final value of pi is calculated using the Chudnovsky algorithm formula (rounded)
        return C.multiply(SQRT_10005).divide(sum, digits, BigDecimal.ROUND_HALF_UP);
    }

    // this method calculates the factorial of a given integer n using BigInteger to handle large numbers
    public static BigInteger factorial(int n) {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            System.out.print("Enter the number of digits of pi to compute: ");
            int numDigits = scanner.nextInt();
            scanner.close();

            long startTime = System.nanoTime();
            BigDecimal pi = computePi(numDigits);
            long endTime = System.nanoTime();
            long duration = endTime - startTime; 

            System.out.println("Execution time: " + duration + " nanoseconds");
            System.out.println("Pi: " + pi);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number of digits.");
        }
    }
}
