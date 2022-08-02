import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OcurrenceCounter {

    public static void main(String[] args) {
        int count = 0;
        int startIndex = 0;
        String inputText=args[0];
        System.out.println("ingrese un patrón de texto a buscar");
        Pattern pattern=Pattern.compile(System.console().readLine());
        Matcher m = pattern.matcher(inputText);
        while(m.find(startIndex)){
            count++;
            startIndex = m.start() + 1;
        }
        System.out.println(count);

    }
}
