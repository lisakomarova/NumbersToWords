import java.math.BigInteger;

public class ThousandProcessor extends AbstractProcessor {

    private final HundredProcessor hundredProcessor = new HundredProcessor();

    @Override
    public String getName(String value) throws IllegalArgumentException {
        StringBuilder buffer = new StringBuilder();

        int number;
        int numberTemp = 0;
        int numberTemp2;

        if (value.isEmpty()) {
            number = 0;
        } else {
            if (value.length() > 6) {
                number = Integer.valueOf(value.substring(value.length() - 6), 10); //оставляем 6 знаков для тысяч/тысячи
                numberTemp = Integer.valueOf(value.substring(value.length() - 5).substring(0, 2), 10);
            } else {
                number = Integer.valueOf(value, 10);
            }
        }

        if (number >= 1000) {
            if (value.length() > 5) {
                //определяем последние 2 знакa для нахождения окночания слова тысячи
                numberTemp = Integer.valueOf(value.substring(value.length() - 5).substring(0, 2), 10);
                numberTemp2 = Integer.valueOf(value.substring(value.length() - 4).substring(0, 1), 10);
            } else {
                if (value.length() > 4) {
                    numberTemp = Integer.valueOf(value.substring(value.length() - 5).substring(0, 2), 10);
                    numberTemp2 = Integer.valueOf(value.substring(value.length() - 4).substring(0, 1), 10);

                } else {
                    numberTemp2 = Integer.valueOf(value.substring(value.length() - 4).substring(0, 1), 10);
                }
            }

            //замена один/одна и два/две
            if ((numberTemp2 == 1 || numberTemp2 == 2) && (numberTemp != 11) && (numberTemp != 12)) {
                changeGender();// flagSyntax = true;
                buffer.append(hundredProcessor.getName(new BigInteger(String.valueOf(number / 1000))));
                //возвращаем флаг замены
                changeGender();//flagSyntax = false;
            } else {
                buffer.append(hundredProcessor.getName(new BigInteger(String.valueOf(number / 1000))));
            }

            buffer.append(SEPARATOR);
            int EXPONENT = 3;
            buffer.append(NumbersToWords.SCALE.getName(EXPONENT));
            if (((numberTemp2 == 2) || numberTemp2 == 3 || numberTemp2 == 4) && (numberTemp != 12) && (numberTemp != 13) && (numberTemp != 14)) {
                buffer.append("и");
            }
            if ((numberTemp2 == 1) && (numberTemp != 11)) {
                buffer.append("а");
            }
        }

        String hundredsName = hundredProcessor.getName(String.valueOf(number));

        if (!hundredsName.isEmpty() && (number >= 1000)) {
            buffer.append(SEPARATOR);
        }
        buffer.append(hundredsName);

        return buffer.toString();
    }

}
