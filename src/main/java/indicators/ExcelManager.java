package indicators;

import excel.Reader;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelManager {
    private final ArrayList<ArrayList<Double>> dataArray;

    public ExcelManager(String path, int index) throws IOException {
        Reader excelReader = new Reader(path, index);
        this.dataArray = excelReader.readExcel();
    }
    public ArrayList<ArrayList<Double>> getDataArray() {
        return dataArray;
    }
}