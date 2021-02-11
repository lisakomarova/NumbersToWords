public class CompositeProcessor extends AbstractProcessor {

    private final ThousandProcessor thousandProcessor = new ThousandProcessor();
    private final AbstractProcessor lowProcessor;
    private final int exponent;

    public CompositeProcessor(int exponent) {
        if (exponent <= 6) {
            lowProcessor = thousandProcessor;
        } else {
            lowProcessor = new CompositeProcessor(exponent - 3);
        }
        this.exponent = exponent;
    }

    public String getToken() {
        return NumbersToWords.SCALE.getName(getPartDivider());
    }

    protected AbstractProcessor getHighProcessor() {
        return thousandProcessor;
    }

    protected AbstractProcessor getLowProcessor() {
        return lowProcessor;
    }

    public int getPartDivider() {
        return exponent;
    }

    @Override
    public String getName(String value) throws IllegalArgumentException {
        StringBuilder buffer = new StringBuilder();

        int numberTemp = 0;
        int numberTemp2;

        String high, low;
        if (value.length() < getPartDivider()) {
            high = "";
            low = value;
        } else {
            if (value.length() >= 66) {
                throw new IllegalArgumentException("Для данного числа нет данных в справочнике");
            }
            int index = value.length() - getPartDivider();
            high = value.substring(0, index);
            low = value.substring(index);
        }

        String highName = getHighProcessor().getName(high);
        String lowName = getLowProcessor().getName(low);

        if (!highName.isEmpty()) {

            if (high.length() >= 2) {
                numberTemp = Integer.valueOf(high.substring(high.length() - 2).substring(0, 2), 10);
                numberTemp2 = Integer.valueOf(high.substring(high.length() - 1).substring(0, 1), 10);
            } else {
                numberTemp2 = Integer.valueOf(high.substring(high.length() - 1).substring(0, 1), 10);
            }

            buffer.append(highName);
            buffer.append(SEPARATOR);
            buffer.append(getToken());
            if (((numberTemp2 == 2) || numberTemp2 == 3 || numberTemp2 == 4) && (numberTemp != 12) && (numberTemp != 13) && (numberTemp != 14)) {
                buffer.append("а");
            } else {
                if (!(numberTemp2 == 1 && (numberTemp != 11))) {
                    buffer.append("ов");
                }
            }
            if (!"".equals(lowName)) {
                buffer.append(SEPARATOR);
            }
        }

        if (!lowName.isEmpty()) {
            buffer.append(lowName);
        }

        return buffer.toString();
    }
}