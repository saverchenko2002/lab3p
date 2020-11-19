package SaverchenkoGroup10Lab3VarC2;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class GornerTableCellRenderer implements TableCellRenderer {

    private String needle = null;
    private boolean search = false;

    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();

    private DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();

    public GornerTableCellRenderer () {

        panel.add(label);
        formatter.setMaximumFractionDigits(5);
        formatter.setGroupingUsed(false);
        DecimalFormatSymbols dottedDouble = formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(dottedDouble);

    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        String formattedDouble = formatter.format(value);

        if (Double.parseDouble(formattedDouble) < 0.){
            label.setText(formattedDouble);
            panel.add(label);
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        }

        if (Double.parseDouble(formattedDouble) == 0.){
            label.setText(formattedDouble);
            panel.add(label);
            panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        }

        if (Double.parseDouble(formattedDouble) > 0.){
            label.setText(formattedDouble);
            panel.add(label);
            panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        }

        if (!search) {
            if ((column == 1 || column == 2) && needle != null && needle.equals(formattedDouble))
                panel.setBackground(Color.MAGENTA);

            else
                panel.setBackground(Color.WHITE);
        }
        else {
            System.out.println("okay");
        }
        return panel;
    }

    public void setNeedle(String needle) { this.needle=needle; }

    public void simpleNum(boolean confirm) { this.search = confirm; }

}