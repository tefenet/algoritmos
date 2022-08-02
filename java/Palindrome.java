public class Palindrome{
    /**
     *
     * @param aNumber is the number variable to be checked for palindrome
     * @return true if is palindrome, false otherwise
     */
    private static boolean isPalindrome(int aNumber){
        int remainder;
        int reversed =0;
        int copyNumber = aNumber;
        while(copyNumber >0){
            remainder = copyNumber %10;
            reversed =(reversed *10)+ remainder;
            copyNumber = copyNumber /10;
        }
        return aNumber == reversed;
    }

    public static void main(String[] args) {
        int inputNumber= Integer.parseInt(args[0]);
        while (!isPalindrome(inputNumber)) inputNumber++;
        System.out.println(inputNumber);
    }
}

