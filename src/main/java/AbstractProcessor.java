import java.math.BigInteger;

abstract public class AbstractProcessor {

    static protected final String SEPARATOR = " ";
    static protected final int NO_VALUE = -1;

    //for setting gender characteristics of numeral один/одна два/две
    static boolean flagGender = false;

    static public void changeGender() {
        flagGender = !flagGender;
    }

    public String getName(BigInteger value) throws IllegalArgumentException {
        return getName(String.valueOf(value));
    }

    abstract public String getName(String value) throws IllegalArgumentException;
}