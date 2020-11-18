package SaverchenkoGroup10Lab3VarC2;

import javax.swing.table.AbstractTableModel;

public class GornerTableModel extends AbstractTableModel {

    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;

    public GornerTableModel(Double from, Double to, Double step, Double[] coefficients){
        this.coefficients=coefficients;
        this.from=from;
        this.to=to;
        this.step=step;
    }

    public Double getFrom() { return from; }

    public Double getTo() { return to; }

    public Double getStep() { return step; }

    public Object getValueAt(int row, int col) {
        double x = from + step * row;
        if (col == 0)
            return x;
        else {
            Double result=0.0;
            if (col == 1) {
                result = coefficients[coefficients.length - 1];
                for (int i = 1; i < coefficients.length; i++) {
                    result *= x;
                    result += coefficients[coefficients.length - i - 1];
                }
                return result;
            }
            else if (col == 2) {
                result = 0.0;
                for (int i = 0; i < coefficients.length; i++)
                    result += Math.pow(x, coefficients.length - i - 1) * coefficients[i];
                return result;
            }
            else return result = Math.abs((Double)getValueAt(row,1) - (Double)getValueAt(row,2));
        }
    }

    public int getColumnCount() { return 4; }

    public int getRowCount() { return new Double(Math.ceil((to-from)/step)).intValue()+1; }

    public Class<?> getColumnClass(int columnIndex) { return Double.class; }

    public String getColumnName(int column) {
        if (column == 0) {
            return "Значение X";
        } else if (column == 1) {
            return "Значение многочлена";
        } else if (column == 2) {
            return "С помощью Math.pow()";
        }
        return "Разница между значениями";
    }
}