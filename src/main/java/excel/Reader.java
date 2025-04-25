package excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Reader {
    FileInputStream file;
    XSSFWorkbook workbook;
    XSSFSheet sheet;
    String filePath;

    public Reader(String filePath, int index) throws IOException {
        file = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(file);
        sheet = workbook.getSheetAt(index);
        this.filePath = filePath;
    }

    public ArrayList<ArrayList<Double>> readExcel() {
        ArrayList<ArrayList<Double>> result = new ArrayList<>();
        try {
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            Iterator<Row> rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) {
                Row headerRow = rowIterator.next();
                int numberOfColumns = headerRow.getLastCellNum();

                System.out.println("numberOfColumns:");

                System.out.println(numberOfColumns);

                for (int i = 0; i < numberOfColumns; i++) {
                    result.add(new ArrayList<>());
                }

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    for (int i = 0; i < numberOfColumns; i++) {
                        Cell cell = row.getCell(i);
                        if (cell != null) {
                            if (cell.getCellType() == CellType.NUMERIC) {
                                result.get(i).add(cell.getNumericCellValue());
                            } else if (cell.getCellType() == CellType.FORMULA) {
                                CellValue cellValue = evaluator.evaluate(cell);
                                if (cellValue.getCellType() == CellType.NUMERIC) {
                                    result.get(i).add(cellValue.getNumberValue());
                                }
                            }
                        }
                    }
                }
            }
        } finally {
            System.out.println("result:");
            System.out.println(result);
            try {
                if (workbook != null) {
                    workbook.close();
                }
                if (file != null) {
                    file.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}