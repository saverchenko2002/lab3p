package SaverchenkoGroup10Lab3VarC2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainFrame extends JFrame {

    private static final int HEIGHT = 500;
    private static final int WIDTH = 700;

    private JTextField textFieldFrom;
    private JTextField textFieldTo;
    private JTextField textFieldStep;

    private Icon imgAuthor;

    private Double[] coefficients;

    private JFileChooser fileChooser = null;
    private GornerTableCellRenderer renderer = new GornerTableCellRenderer();
    private GornerTableModel data;
    private Box hboxResult;

    public MainFrame(Double[] coefficients)
    {
        super("Табулирование многочлена на отрезке по схеме Горнера");
        this.coefficients=coefficients;
        setSize(WIDTH,HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setResizable(true);
        Image img = kit.getImage("src/SaverchenkoGroup10Lab3VarC2/icon1.png");
        setIconImage(img);

        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("Файл");
        menuBar.add(file);
        JMenu save = new JMenu("Сохранить как");
        file.add(save);

        JMenuItem saveTxt = save.add(new JMenuItem("текстовый файл"));
        saveTxt.setEnabled(false);
        saveTxt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fileChooser==null){
                    fileChooser=new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)
                    saveToTextFile(fileChooser.getSelectedFile());
            }
        });

        JMenuItem saveToGraphic = save.add(new JMenuItem("данные для построения графика"));
        saveToGraphic.setEnabled(false);
        saveToGraphic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fileChooser==null){
                    fileChooser=new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)
                    saveToGraphFile(fileChooser.getSelectedFile());
            }
        });

        JMenuItem saveToSvc = save.add(new JMenuItem("CSV-файл"));
        saveToSvc.setEnabled(false);
        saveToSvc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fileChooser==null){
                    fileChooser=new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)
                    saveToCsvFile(fileChooser.getSelectedFile());
            }
        });

        JMenuItem exit = file.add(new JMenuItem("Выход"));
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        exit.setAccelerator(KeyStroke.getKeyStroke("ctrl E"));

        JMenu table = new JMenu("Таблица");
        JMenuItem solve = table.add(new JMenuItem("Найти значение многочлена"));
        solve.setAccelerator(KeyStroke.getKeyStroke("ctrl F"));
        solve.setEnabled(false);
        solve.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String value = JOptionPane.showInputDialog(MainFrame.this, "Введите значение для поиска", "Поиск значения", JOptionPane.QUESTION_MESSAGE);
                renderer.simpleNum(false);
                System.out.println(value);
                renderer.setNeedle(value);
                getContentPane().repaint();
            }
        });
        JMenuItem find = table.add(new JMenuItem("Найти близкие к простым"));
        find.setEnabled(false);
        find.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                renderer.simpleNum(true);
                getContentPane().repaint();
            }
        });
        find.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        menuBar.add(table);

        JMenu help = new JMenu("Справка");
        menuBar.add(help);
        JMenuItem about = help.add(new JMenuItem("О программе",new ImageIcon("src/SaverchenkoGroup10Lab3VarC2/info.png")));
        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                imgAuthor = new ImageIcon("src/SaverchenkoGroup10Lab3VarC2/avatar_pic.png");
                JOptionPane.showMessageDialog(MainFrame.this, "Автор программы студент второго курса 10 группы Саверченко Сергей\nmail: sergey_saver@mail.ru", "О программе", JOptionPane.INFORMATION_MESSAGE, imgAuthor);
            }
        });
        about.setAccelerator(KeyStroke.getKeyStroke("ctrl I"));

        JLabel labelForFrom = new JLabel("X изменяется на интервале от:");
        textFieldFrom = new JTextField("0.0",10);
        textFieldFrom.setMaximumSize(textFieldFrom.getPreferredSize());
        JLabel labelForTo = new JLabel("до:");
        textFieldTo = new JTextField("1.0",10);
        textFieldTo.setMaximumSize(textFieldTo.getPreferredSize());
        JLabel labelForStep = new JLabel("с шагом:");
        textFieldStep = new JTextField("0.1",10);
        textFieldStep.setMaximumSize(textFieldStep.getPreferredSize());

        Box hboxRange = Box.createHorizontalBox();
        hboxRange.setBorder(BorderFactory.createBevelBorder(1));
        hboxRange.add(Box.createHorizontalGlue());
        hboxRange.add(labelForFrom);
        hboxRange.add(Box.createHorizontalStrut(10));
        hboxRange.add(textFieldFrom);
        hboxRange.add(Box.createHorizontalStrut(20));
        hboxRange.add(labelForTo);
        hboxRange.add(Box.createHorizontalStrut(10));
        hboxRange.add(textFieldTo);
        hboxRange.add(Box.createHorizontalStrut(20));
        hboxRange.add(labelForStep);
        hboxRange.add(Box.createHorizontalStrut(10));
        hboxRange.add(textFieldStep);
        hboxRange.add(Box.createHorizontalGlue());

        JButton buttonCalc = new JButton("Вычислить");
        buttonCalc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    Double from = Double.parseDouble(textFieldFrom.getText());
                    Double to = Double.parseDouble(textFieldTo.getText());
                    Double step = Double.parseDouble(textFieldStep.getText());
                    if (step>to-from) {
                        JOptionPane.showMessageDialog(MainFrame.this,
                                "Шаг больше промежутка", "Ошибочный ввод данных", JOptionPane.WARNING_MESSAGE);
                    }
                    else {
                        data = new GornerTableModel(from, to, step, coefficients);
                        JTable workTable = new JTable(data);
                        workTable.setDefaultRenderer(Double.class,renderer);
                        workTable.setRowHeight(30);
                        JScrollPane workTableScrollPane = new JScrollPane(workTable);
                        hboxResult.removeAll();
                        hboxResult.add(workTableScrollPane);
                        getContentPane().validate();
                        saveTxt.setEnabled(true);
                        saveToGraphic.setEnabled(true);
                        saveToSvc.setEnabled(true);
                        solve.setEnabled(true);
                        find.setEnabled(true);
                    }
                }
                catch (NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Ошибка в формате записи числа с плавающей точкой", "Ошибочный формат числа", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JButton buttonReset = new JButton("Очистить поля");
        buttonReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textFieldFrom.setText("0.0");
                textFieldTo.setText("1.0");
                textFieldStep.setText("0.1");
                hboxResult.removeAll();
                hboxResult.add(new JPanel());
                renderer.setNeedle(null);
                getContentPane().validate();
                saveTxt.setEnabled(false);
                saveToGraphic.setEnabled(false);
                saveToSvc.setEnabled(false);
                solve.setEnabled(false);
                find.setEnabled(false);
            }
        });

        Box hboxButtons = Box.createHorizontalBox();
        hboxButtons.setBorder(BorderFactory.createBevelBorder(1));
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.add(buttonCalc);
        hboxButtons.add(Box.createHorizontalStrut(30));
        hboxButtons.add(buttonReset);
        hboxButtons.add(Box.createHorizontalGlue());

        JTable workTable = new JTable();
        JScrollPane workTableScrollPane = new JScrollPane(workTable);

        hboxResult = Box.createHorizontalBox();
        hboxResult.add(new JPanel());

        setJMenuBar(menuBar);
        getContentPane().add(hboxRange, BorderLayout.NORTH);
        getContentPane().add(hboxButtons,BorderLayout.SOUTH);
        getContentPane().add(hboxResult,BorderLayout.CENTER);
    }

    protected void saveToTextFile(File selectedFile) {
        try{
            PrintStream out = new PrintStream(selectedFile);
            out.println("Результаты табулирования многочлена по схеме Горнера");
            out.print("Многочлен: ");
            for (int i=0; i<coefficients.length; i++) {
                out.print(coefficients[i] + "*X^" + (coefficients.length-i-1));
                if (i!=coefficients.length-1)
                    out.print(" + ");
            }
            out.println("");
            out.println("Интервал от " + data.getFrom() + " до " +
                    data.getTo() + " с шагом " + data.getStep());
            out.println("====================================================");
            for (int i = 0; i<data.getRowCount(); i++) {
                out.println("Значение в точке " + data.getValueAt(i,0)
                        + " по Горнеру равно " + data.getValueAt(i,1));
            }
            out.println("====================================================");
            for (int i = 0; i<data.getRowCount(); i++) {
                out.println("Значение в точке " + data.getValueAt(i,0)
                        + " по методу Math.pow равно " + data.getValueAt(i,2));
            }
            out.println("====================================================");
            for (int i = 0; i<data.getRowCount(); i++) {
                out.println("Разница между м. Горнера и Math.pow в точке" + data.getValueAt(i,0)
                        + " равна " + data.getValueAt(i,3));
            }
            out.close();
        }
        catch (FileNotFoundException e)
        {

        }
    }

    protected void saveToGraphFile(File selectedFile)
    {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(selectedFile));
            for(int i =0;i<data.getRowCount();i++){
                out.writeDouble((Double)data.getValueAt(i,0));
                out.writeDouble((Double)data.getValueAt(i,1));
                out.writeDouble((Double)data.getValueAt(i,2));
                out.writeDouble((Double)data.getValueAt(i,3));
            }
            out.close();
        }
        catch (IOException e)
        {

        }
    }

    protected void saveToCsvFile(File selectedFile)
    {
        try(FileWriter writer = new FileWriter(selectedFile)){
            for (int i = 0; i < data.getRowCount(); i++){
                writer.append("" + data.getValueAt(i, 0) + ',' + data.getValueAt(i, 1) + ',' + data.getValueAt(i, 2) + ',' + data.getValueAt(i, 3));
                writer.append(System.lineSeparator());
            }
            writer.close();
        } catch (IOException e){

        }
    }
}