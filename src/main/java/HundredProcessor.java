import java.math.BigInteger;

public class HundredProcessor extends AbstractProcessor {

    static private final String[] TOKENS = new String[]{"сто", "двести", "триста",
            "четыреста", "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот"};

    private final TensProcessor tensProcessor = new TensProcessor();

    @Override
    public String getName(String value) throws IllegalArgumentException {
        StringBuilder buffer = new StringBuilder();
        boolean tensFound = false;

        int number;

        if (value.length() > 4) {
            number = Integer.valueOf(value.substring(value.length() - 4), 10);
        } else {
            number = Integer.valueOf(value, 10);
        }

        number %= 1000;

        if (number >= 100) {
            buffer.append(TOKENS[(number / 100) - 1]);
            number %= 100;
            tensFound = true;
        }

        if (number != 0) {
            if (tensFound) {
                buffer.append(SEPARATOR);
            }
            buffer.append(tensProcessor.getName(new BigInteger(String.valueOf(number))));
        }
        return buffer.toString();
    }

}
