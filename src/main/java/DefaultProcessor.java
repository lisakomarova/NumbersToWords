public class DefaultProcessor extends AbstractProcessor {

    static private final String MINUS = "минус";
    static private final String ZERO = "ноль";

    private final AbstractProcessor processor = new CompositeProcessor(63);

    @Override
    public String getName(String value) throws IllegalArgumentException {
        boolean negative = false;
        if (value.startsWith("-")) {
            negative = true;
            value = value.substring(1);
        }

        int decimals = value.indexOf(".");
        String decimalValue = null;
        if (0 <= decimals) {
            decimalValue = value.substring(decimals + 1);
            value = value.substring(0, decimals);
        }

        String name = processor.getName(value);

        if (name.isEmpty()) {
            name = ZERO;
        } else {
            if (negative) {
                name = MINUS.concat(SEPARATOR).concat(name);
            }
        }

        if (!(null == decimalValue || decimalValue.isEmpty())) {

            StringBuilder zeroDecimalValue = new StringBuilder();
            for (int i = 0; i < decimalValue.length(); i++) {
                zeroDecimalValue.append("0");
            }
            if (decimalValue.equals(zeroDecimalValue.toString())) {
                name = name.concat(SEPARATOR).concat(" и ").concat(SEPARATOR).concat(
                        ZERO).concat(SEPARATOR).concat(
                        NumbersToWords.SCALE.getName(-decimalValue.length()));
            } else {
                name = name.concat(SEPARATOR).concat(" и ").concat(SEPARATOR).concat(
                        processor.getName(decimalValue)).concat(SEPARATOR).concat(
                        NumbersToWords.SCALE.getName(-decimalValue.length()));
            }
        }
        return name;
    }
}