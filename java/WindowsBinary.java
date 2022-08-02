import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.math.BigInteger.TWO;
import static java.math.BigInteger.ZERO;

public class WindowsBinary {

    private static BigInteger summa(int numberOfWindows){
        Deque<BigInteger> tempResults=new LinkedList<>(Arrays.asList(ZERO, ZERO, ZERO,BigInteger.ONE)) ;
        IntStream.rangeClosed(4,numberOfWindows).forEach(i->
                tempResults.add(TWO.multiply(tempResults.getLast()).add(TWO.pow(i-4).subtract(tempResults.poll())))
        );
        return numberOfWindows < 3 ? ZERO : tempResults.getLast();
    }

    public static void main(String[] args) {
        String newCase = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String currentLine = reader.readLine();
            while (currentLine!=null){
                if (currentLine.charAt(0) == '#')
                    newCase = Arrays.stream(currentLine.split(" ")).skip(2).collect(Collectors.joining());
                else
                    System.out.printf("%s. %s%n", newCase, WindowsBinary.summa(Integer.parseInt(currentLine.strip())));
                currentLine = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
