package indicators;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import java.util.ArrayList;

public class VariationCoeff implements Calculate {

    String name;
    ArrayList<ArrayList<Double>> list;
    ArrayList<Double> result = new ArrayList<>();

    public VariationCoeff(ArrayList<ArrayList<Double>> list) {
        this.list = list;
        this.name = "Коэффициент вариации";
        calculate();
    }

    public void calculate() {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (ArrayList<Double> doubles : list) {
            doubles.forEach(stats::addValue);
            result.add(stats.getStandardDeviation() / stats.getMean());
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