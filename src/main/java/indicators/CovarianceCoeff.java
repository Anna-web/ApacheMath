package indicators;

import org.apache.commons.math3.stat.correlation.Covariance;
import javax.swing.*;
import java.util.ArrayList;

public class CovarianceCoeff implements Calculate {
    String name;
    ArrayList<ArrayList<Double>> list;
    ArrayList<ArrayList<Double>> covarianceMatrix = new ArrayList<>();

    public CovarianceCoeff(ArrayList<ArrayList<Double>> list) {
        this.list = list;
        this.name = "Коэффициенты ковариации";
        calculate();
    }

    public void calculate() {
        Covariance covariance = new Covariance();
        for (ArrayList<Double> innerList1 : list) {
            ArrayList<Double> row = new ArrayList<>();
            for (ArrayList<Double> innerList2 : list) {
                if (innerList1.size() == innerList2.size()) {
                    row.add(covariance.covariance(innerList1.stream().mapToDouble(Double::doubleValue).toArray(), innerList2.stream().mapToDouble(Double::doubleValue).toArray()));
                } else {
                    JOptionPane.showMessageDialog(null, "Impossible to create a matrix of covariance coefficients");
                }
            }
            covarianceMatrix.add(row);
        }
    }

    @Override
    public ArrayList<ArrayList<Double>> getResult() {
        return covarianceMatrix;
    }
    public String getName() {
        return name;
    }
}