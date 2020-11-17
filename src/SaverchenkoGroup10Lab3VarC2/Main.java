package SaverchenkoGroup10Lab3VarC2;

import javax.swing.*;

public class Main {
    public static void main(String[] args)
    {
        if (args.length==0) {
            System.out.println("Невозможно табулировать многочлен, для которого не задано ни одного коэффициента!");
            System.exit(-1);
        }

        Double[] coefficients = new Double[args.length];
        int i = 0;
        try {
            for (String arg: args) {
                coefficients[i] = Double.parseDouble(arg);
                System.out.println(coefficients[i++]);
            }
        }
        catch (NumberFormatException ex) {
            System.out.println("Ошибка преобразования строки '" + args[i] + "' в число типа Double");
            System.exit(-2);
        }

        MainFrame frame = new MainFrame(coefficients);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
