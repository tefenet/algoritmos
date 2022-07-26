import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;

import java.util.Arrays;

import java.util.stream.Collectors;

public class NumberTranslator {

    static class Transformer {
        private final String[] UNITS = {"", "un ", "dos ", "tres ", "cuatro ", "cinco ", "seis ", "siete ", "ocho ", "nueve ", "diez ",
                "once ", "doce ", "trece ", "catorce ", "quince ", "dieciseis ", "diecisiete ", "dieciocho ",
                "diecinueve ", "veinte "};
        private final String[] TENS = {"", "", "veinti", "treinta ", "cuarenta ", "cincuenta ", "sesenta ", "setenta ", "ochenta ",
                "noventa ", "cien "};
        private final String[] HUNDREDS = {"", "ciento ", "doscientos ", "trescientos ", "cuatrocientos ", "quinientos ", "seiscientos ",
                "setecientos ", "ochocientos ", "novecientos "};

        String transform_last_two(int tens, int units) {
            String ten = TENS[tens];
            String unit = UNITS[units];
            if (tens >= 3 && units != 0)
                return String.format("%sy %s", ten, unit);
            else
                return String.format("%s%s", ten, unit);
        }

        String transform_units_and_tens(String three_digits) {
            int last_two_digits = Integer.parseInt(three_digits.substring(three_digits.length() - 2));
            if (last_two_digits <= 20)
                return UNITS[last_two_digits];
            else
                return transform_last_two(last_two_digits / 10, last_two_digits % 10);
        }

        String transform(String three_digits) {
            if (three_digits.equals("100"))
                return "cien ";

            return String.format("%s%s",
                    HUNDREDS[Integer.parseInt(three_digits) / 100], transform_units_and_tens(three_digits));
        }
    }

    static class Spoken {
        static String input_number;
        static StringBuilder spoken_number;
        static Transformer transformer;

        Spoken(String numeric_word) {
            if (numeric_word.length() > 12)
                throw new IllegalArgumentException(String.format("el numero %s tiene mas de 12 cifras", numeric_word));
            //numeric_word default o zero?

            input_number = StringUtils.leftPad(numeric_word, 12, "0");
            spoken_number = new StringBuilder();
            transformer = new Transformer();
        }

        static void translate_millions(String millions, int billions) {
            if (millions.equals("001") && billions == 0)
                spoken_number.append("un millon ");
            else if (Integer.parseInt(millions) > 0 || billions > 0)
                spoken_number.append(String.format("%smillones ", transformer.transform(millions)));
        }

        static void translate_thousands(String thousands) {
            if (thousands.equals("001"))
                spoken_number.append("mil ");
            else if (Integer.parseInt(thousands) > 0)
                spoken_number.append(String.format("%smil ", transformer.transform(thousands)));

        }

        static void translate_hundreds(String hundreds) {
            if (Integer.parseInt(hundreds) <= 0) return;
            spoken_number.append(String.format("%s ", transformer.transform(hundreds)));
            if (Integer.parseInt(hundreds) % 10 == 1)
                spoken_number.insert(spoken_number.length() - 2, "o");
        }

        static String text_number(String numeric_word) {

            new Spoken(numeric_word);
            translate_thousands(input_number.substring(0, 3));
            translate_millions(input_number.substring(3, 6), Integer.parseInt(input_number.substring(0, 3)));
            translate_thousands(input_number.substring(6, 9));
            translate_hundreds(input_number.substring(9, 12));
            return spoken_number.length() == 0 ? "cero" : spoken_number.toString().strip();
        }
    }

    // given a number, i speak it in spanish
    public static void main(String[] args) {
        String new_case = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String currentLine = reader.readLine();
            while (currentLine!=null){
                if (currentLine.charAt(0) == '#')
                    new_case = Arrays.stream(currentLine.split(" ")).skip(1).collect(Collectors.joining());
                else
                    System.out.printf("%s. %s%n", new_case, Spoken.text_number(currentLine.strip()));
                currentLine = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
