package method;

import indicators.*;
import java.util.ArrayList;

public class Calculator {
    GeometricMean geometricMean;
    ArithmeticMean arithmeticMean;
    StandardDeviation standardDeviation;
    Scope scope;
    CovarianceCoeff covarianceCoeff;
    Quantity quantity;
    VariationCoeff variationCoeff;
    ConfInterval confInterval;
    Dispersion dispersion;
    Max max;
    Min min;
    ArrayList<ArrayList<?>> results = new ArrayList<>();
    ArrayList<Calculate> actions = new ArrayList<>();

    public Calculator() {
    }
    public void calculate(ArrayList<ArrayList<Double>> data) {
        results.clear();
        actions.clear();
        actions.add(geometricMean = new GeometricMean(data));
        actions.add(arithmeticMean = new ArithmeticMean(data));
        actions.add(standardDeviation = new StandardDeviation(data));
        actions.add(scope = new Scope(data));
        actions.add(quantity = new Quantity(data));
        actions.add(variationCoeff = new VariationCoeff(data));
        actions.add(confInterval = new ConfInterval(data));
        actions.add(dispersion = new Dispersion(data));
        actions.add(max = new Max(data));
        actions.add(min = new Min(data));
        covarianceCoeff = new CovarianceCoeff(data);
    }

    public ArrayList<ArrayList<String>> fillResults() {
        ArrayList<ArrayList<String>> resultsWithNames = new ArrayList<>();
        for (Calculate action : actions) {
            ArrayList<String> row = new ArrayList<>();
            row.add(action.getName());
            for (Object result : action.getResult()) {
                row.add(result.toString());
            }
            resultsWithNames.add(row);
        }
        return resultsWithNames;
    }

    public ArrayList<ArrayList<Double>> getCovariation() {
        return covarianceCoeff.getResult();
    }
    public String getCovariationName() {
        return covarianceCoeff.getName();
    }
}