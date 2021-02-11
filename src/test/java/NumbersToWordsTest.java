import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.*;

public class NumbersToWordsTest {

    final Logger logger = LogManager.getLogger(NumbersToWordsTest.class.getName());

    NumbersToWords ntw = new NumbersToWords();
    final DefaultProcessor processor = new DefaultProcessor();


    public NumbersToWordsTest() throws IOException {
        BasicConfigurator.configure();
    }

    @Test
    public void testGetNameAllTable() throws Exception {

        InputStream in = new FileInputStream("src/test/resources/ExcelNumbers.xls");
        HSSFWorkbook wb = new HSSFWorkbook(in);

        BigInteger inNumber = new BigInteger(String.valueOf(0));
        String inString = null;

        Sheet sheet = wb.getSheetAt(0);
        String equation = "";
        for (Row row : sheet) {
            for (Cell cell : row) {
                CellType cellType = cell.getCellType();

                switch (cellType) {
                    case NUMERIC:
                        equation = (inNumber = BigDecimal.valueOf(cell.getNumericCellValue()).toBigInteger()) + " = ";
                        break;

                    case STRING:
                        equation += (inString = cell.getStringCellValue());
                        break;

                    default:
                        break;
                }
            }
            logger.info(equation);
            assertEquals(inString, processor.getName(new BigInteger(String.valueOf(inNumber))));
        }
    }

    @Test
    public void testGetNameAllTableError() throws Exception {

        InputStream in = new FileInputStream("src/test/resources/ErrorExcelNumbers.xls");
        HSSFWorkbook wb = new HSSFWorkbook(in);

        BigInteger inNumber = new BigInteger(String.valueOf(0));

        Sheet sheet = wb.getSheetAt(0);
        for (Row row : sheet) {
            for (Cell cell : row) {
                CellType cellType = cell.getCellType();

                if (cellType == CellType.NUMERIC) {
                    inNumber = BigDecimal.valueOf(cell.getNumericCellValue()).toBigInteger();
                }
            }
        }
        final BigInteger finalInNumber = inNumber;
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> processor.getName(new BigInteger(String.valueOf(finalInNumber))));
        assertNotNull(thrown.getMessage());
        logger.error(thrown);
    }
}