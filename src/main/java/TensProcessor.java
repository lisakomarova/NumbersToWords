public class TensProcessor extends AbstractProcessor {

    static private final String[] TOKENS = new String[]{"двадцать", "тридцать", "сорок",
            "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто"};

    private final UnitProcessor unitProcessor = new UnitProcessor();

    @Override
    public String getName(String value) {
        StringBuilder buffer = new StringBuilder();
        boolean tensFound = false;

        int number;
        if (value.length() > 3) {
            number = Integer.valueOf(value.substring(value.length() - 3), 10);
        } else {
            number = Integer.valueOf(value, 10);
        }

        number %= 100;

        if (number >= 20) {
            buffer.append(TOKENS[(number / 10) - 2]);
            number %= 10;
            tensFound = true;
            // numbers 20-99
        }

        if (number != 0) {
            if (tensFound) {
                buffer.append(SEPARATOR);
            }
            buffer.append(unitProcessor.getName(String.valueOf(number)));
        }
        return buffer.toString();
    }

}