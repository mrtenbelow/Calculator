import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws Exception {

            try {
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                System.out.println(calc(input));
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid input");
            }
        }

    public static String calc(String input) throws Exception {

        if (isRoman(input)) {
            int result = operation(romanToArabic(input));
            if (result > 0 && result < 4000) {
                return arabicToRoman(String.valueOf(result));
            } else {
                throw new Exception();
            }
        } else if (isArabic(input)) {
            return String.valueOf(operation(input));
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static int operation(String input) {
        int answer = 0;
        String[] p = input.split("\\s");
        switch (p[1]) {
            case "+" -> answer = Integer.parseInt(p[0]) + Integer.parseInt(p[2]);
            case "-" -> answer = Integer.parseInt(p[0]) - Integer.parseInt(p[2]);
            case "/" -> answer = Integer.parseInt(p[0]) / Integer.parseInt(p[2]);
            case "*" -> answer = Integer.parseInt(p[0]) * Integer.parseInt(p[2]);
        }
        return answer;
    }

    public static String romanToArabic(String input) {
        String[] arr = input.split("\\s");
        return romanSymbolToArabic(arr[0]) + " " + arr[1] + " " + romanSymbolToArabic(arr[2]);
    }

    public static String romanSymbolToArabic(String input) {
        String romanNumeral = input.toUpperCase();
        int result = 0;
        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();
        int i = 0;
        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }
        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException(input + " cannot be converted to a Roman Numeral");
        }
        if (result > 3999) {
            throw new IllegalArgumentException();
        } else {
            return String.valueOf(result);
        }
    }

    public static String arabicToRoman(String input) {
        int number = Integer.parseInt(input);
        if ((number <= 0) || (number > 4000)) {
            throw new IllegalArgumentException(number + " is not in range (0,4000]");
        }
        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();
        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }

    public static boolean isRoman(String input) throws Exception {
        if (input.length() < 5)
            throw new Exception();
        String regex = "^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";
        String[] arr = input.split("\\s");
        return arr[0].matches(regex) && arr[2].matches(regex) && isOperator(arr[1]);
    }

    public static boolean isArabic(String input) throws Exception {
        if (input.length() < 5 || input.length() > 7)
            throw new Exception();
        String[] arr = input.split("\\s");
        try {
            Integer.parseInt(arr[0]);
            Integer.parseInt(arr[2]);
            return ((Integer.parseInt(arr[0]) <= 10) && (Integer.parseInt(arr[2]) <= 10));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isOperator(String input) {
        if (input.length() > 2) {
            return false;
        }
        String regex = "^[[+-][*/]]$";
        return input.matches(regex);
    }
}

