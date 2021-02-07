import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class NumbersToWordsTest {

    NumbersToWords ntw = new NumbersToWords();
    NumbersToWords.AbstractProcessor processor = new NumbersToWords.DefaultProcessor();

    public NumbersToWordsTest() throws IOException {
    }

    @Test
    public void testGetNameAllTable() throws Exception {

        InputStream in = new FileInputStream("src/test/resources/ExcelNumbers.xls");
        HSSFWorkbook wb = new HSSFWorkbook(in);

        long inNumber = 0;
        String inString = null;

        Sheet sheet = wb.getSheetAt(0);
        for (Row row : sheet) {
            for (Cell cell : row) {
                CellType cellType = cell.getCellType();

                switch (cellType) {
                    case NUMERIC:
                        System.out.print((inNumber = (long) cell.getNumericCellValue()) + " = ");
                        break;

                    case STRING:
                        System.out.print((inString = cell.getStringCellValue()));
                        break;

                    default:
                        break;
                }
            }
            System.out.println();
            assertEquals(inString, processor.getName(inNumber));
        }
    }

}