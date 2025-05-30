package indicators;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import java.util.ArrayList;

public class StandardDeviation implements Calculate {
    String name;
    ArrayList<ArrayList<Double>> list;
    ArrayList<Double> result = new ArrayList<>();

    public StandardDeviation(ArrayList<ArrayList<Double>> list) {
        this.list = list;
        this.name = "Стандартное отклонение";
        calculate();
    }

    public void calculate() {
        ArrayList<DescriptiveStatistics> stats = new ArrayList<>();
        int y = 0;

        int numColumns = list.size();
        for (int i = 0; i < numColumns; i++) {
            stats.add(new DescriptiveStatistics());
        }

        for (ArrayList<Double> doubles : list) {
            for (int i = 0; i < doubles.size(); i++) {
                stats.get(y).addValue(doubles.get(i));
            }
            y++;
        }

        for (DescriptiveStatistics stat : stats) {
            double standardDeviation = stat.getStandardDeviation();
            result.add(standardDeviation);
        }
    }

    @Override
    public ArrayList<Double> getResult() {
        return result;
    }
    public String getName() {
        return name;
    }
}