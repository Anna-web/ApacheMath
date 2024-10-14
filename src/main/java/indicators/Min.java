package indicators;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import java.util.ArrayList;

public class Min implements Calculate {
    String name;
    ArrayList<ArrayList<Double>> list;
    ArrayList<Double> result = new ArrayList<>();

    public Min(ArrayList<ArrayList<Double>> list) {
        this.list = list;
        this.name = "Минимум";
        calculate();
    }

    public void calculate() {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (ArrayList<Double> doubles : list) {
            doubles.forEach(stats::addValue);
            result.add(stats.getMin());
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
