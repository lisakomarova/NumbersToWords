import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class NumbersToWords {

    static public final Scale SCALE = Scale.one;
    private static final ArrayList<ScaleUnit> SCALE_UNITS = new ArrayList<>();

    public NumbersToWords() throws IOException {
        InputStream in = new FileInputStream("src/main/resources/Book.xls");
        HSSFWorkbook wb = new HSSFWorkbook(in);
        Sheet sheet = wb.getSheetAt(0);
        for (Row row : sheet) {
            Iterator<Cell> cells = row.iterator();
            ScaleUnit tempScaleUnit = new ScaleUnit();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                CellType cellType = cell.getCellType();
                switch (cellType) {
                    case NUMERIC:
                        tempScaleUnit.exponent = (int) cell.getNumericCellValue();
                        break;

                    case STRING:
                        tempScaleUnit.names = new String[]{cell.getStringCellValue()};
                        break;
                    default:
                        break;
                }
            }
            SCALE_UNITS.add(tempScaleUnit);
        }
    }

    public enum Scale {
        one;

        public String getName(int exponent) {
            for (ScaleUnit unit : SCALE_UNITS) {
                if (unit.getExponent() == exponent) {
                    return unit.getName(this.ordinal());
                }
            }
            return "";
        }
    }

    static public class ScaleUnit {
        private int exponent;
        private String[] names;

        private ScaleUnit() {
        }

        public int getExponent() {
            return exponent;
        }

        public String getName(int index) {
            return names[index];
        }
    }
}

