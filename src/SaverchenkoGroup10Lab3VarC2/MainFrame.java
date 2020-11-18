package SaverchenkoGroup10Lab3VarC2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainFrame extends JFrame {

    private static final int HEIGHT = 500;
    private static final int WIDTH = 700;

    private JTextField textFieldFrom;
    private JTextField textFieldTo;
    private JTextField textFieldStep;

    private Icon imgAuthor;

    private Double[] coefficients;

    private JFileChooser fileChooser = null;
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
        solve.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        JMenuItem find = table.add(new JMenuItem("Найти близкие к простым"));
        find.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
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

        setJMenuBar(menuBar);
        getContentPane().add(hboxRange, BorderLayout.NORTH);
    }
}
