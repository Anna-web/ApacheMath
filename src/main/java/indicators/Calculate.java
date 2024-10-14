package indicators;

import java.util.ArrayList;

public interface Calculate {
    void calculate();
    ArrayList<?> getResult();
    String getName();
}