import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;



public class BaseFrame {

    public static JFrame jf = new JFrame("Expense Tracker");
    public static JButton addIncome;
    public static JButton addConsum;
    public static JButton deleteEntry;
    public static JTable dbTable = new JTable();
    public static DefaultTableModel dtm;
    public static String filePath = "entries.txt";

    public static void baseWindow() throws Exception {

        // создание главного окна
        jf.setSize(730, 530);
        jf.setResizable(false);
        jf.setVisible(true);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        jf.add(panel);


        // поля для записи дохода
        JLabel incomeLabel = new JLabel("Доход");
        incomeLabel.setBounds(20, -70, 200, 200);
        panel.add(incomeLabel);
        JTextField income = new JTextField(15);
        income.setBounds(20, 40, 200, 20);
        panel.add(income);

        // ограничение на ввод
        income.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent ke) {
                char kchr = ke.getKeyChar();
                if (kchr < 46 || kchr == 47  || kchr >57) {
                    ke.consume();
                }
            }
        });

        JLabel descrIncLabel = new JLabel("Описание дохода");
        descrIncLabel.setBounds(20, -25, 200, 200);
        panel.add(descrIncLabel);
        JTextField descrInc = new JTextField(15);
        descrInc.setBounds(20, 85, 200, 20);
        panel.add(descrInc);

        JLabel dataIncLabel = new JLabel("Дата");
        dataIncLabel.setBounds(20, 20, 200, 200);
        panel.add(dataIncLabel);
        JTextField dataInc = new JTextField(15);
        dataInc.setBounds(20, 130, 200, 20);
        panel.add(dataInc);

        // ограничение на ввод
        dataInc.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent ke) {
                char kchr = ke.getKeyChar();
                if (kchr < 46 || kchr == 47  || kchr >57) {
                    ke.consume();
                }
            }
        });

        // кнопка "добавить доход"
        addIncome = new JButton("Добавить");
        addIncome.setBounds(50, 160, 130, 30);
        panel.add(addIncome);

        addIncome.addActionListener(e -> {
            if (!(income.getText().equals("")) && (!descrInc.getText().equals("")) && (!dataInc.getText().equals(""))) {
                writeString("+" + income.getText() + " " + descrInc.getText() + " "
                        + dataInc.getText());
                String[] newRow = {"+" + income.getText(), descrInc.getText(), dataInc.getText()};
                dtm.addRow(newRow);

                income.setText(null);
                descrInc.setText(null);
                dataInc.setText(null);
            }
        });



        // поля для записи "расхода"
        JLabel consumLabel = new JLabel("Расход");
        consumLabel.setBounds(20, 150, 200, 200);
        panel.add(consumLabel);
        JTextField consum = new JTextField(15);
        consum.setBounds(20, 260, 200, 20);
        panel.add(consum);

        // ограничение на ввод
        consum.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent ke) {
                char kchr = ke.getKeyChar();
                if (kchr < 46 || kchr == 47  || kchr >57) {
                    ke.consume();
                }
            }
        });

        JLabel descrLabel = new JLabel("Описание расхода");
        descrLabel.setBounds(20, 195, 200, 200);
        panel.add(descrLabel);
        JTextField descrCons = new JTextField(15);
        descrCons.setBounds(20, 305, 200, 20);
        panel.add(descrCons);

        JLabel dataConsLabel = new JLabel("Дата");
        dataConsLabel.setBounds(20, 240, 200, 200);
        panel.add(dataConsLabel);
        JTextField dataCons = new JTextField(15);
        dataCons.setBounds(20, 350, 200, 20);
        panel.add(dataCons);

        // ограничение на ввод
        dataCons.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent ke) {
                char kchr = ke.getKeyChar();
                if (kchr < 46 || kchr == 47  || kchr >57) {
                    ke.consume();
                }
            }
        });

        // кнопка "добавить расход"
        addConsum = new JButton("Добавить");
        addConsum.setBounds(50, 380, 130, 30);
        panel.add(addConsum);

        addConsum.addActionListener(e1 -> {
            if (!(consum.getText().equals("")) && (!descrCons.getText().equals("")) && (!dataCons.getText().equals(""))) {
                writeString("-" + consum.getText() + " " + descrCons.getText() + " "
                        + dataCons.getText());
                String[] newRow = {"-" + consum.getText(), descrCons.getText(), dataCons.getText()};
                dtm.addRow(newRow);

                consum.setText(null);
                descrCons.setText(null);
                dataCons.setText(null);
            }
        });


        //кнопка "удаления записи"
        deleteEntry = new JButton("Удалить запись");
        deleteEntry.setBounds(50, 450, 130, 30);
        panel.add(deleteEntry);

        deleteEntry.addActionListener(e -> deleteThisEntry());


        buildTable(panel);
    }


    //запись в файл
    public static void writeString(String string){
        try {
            FileWriter writer = new FileWriter(filePath, true);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            bufferWriter.write(string + System.getProperty("line.separator"));
            bufferWriter.close();
        } catch (IOException e1) {
            System.out.println(e1);
        }

    }


    // Удаление выбранной записи
    public static void deleteThisEntry() {
        int selection = JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить " +
                "эту запись?", "Удаление записи", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (selection == JOptionPane.YES_OPTION) {
            deleteEntry();
        }
    }

    public static void deleteEntry() {

        int sr = dbTable.getSelectedRow();

        if (sr != -1) {
            List<String> entry = new ArrayList<>();

            try (Scanner scan = new Scanner(new File(filePath))) {
                while (scan.hasNextLine()) {
                    entry.add(scan.nextLine());
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }

            entry.remove(sr);
            dtm.removeRow(sr);
            dbTable.setModel(dtm);

            Writer writer = null;
            try {
                writer = new FileWriter(filePath, false);
                for (String line : entry) {
                    writer.write(line);
                    writer.write(System.getProperty("line.separator"));
                }
                writer.flush();
            } catch (Exception e2) {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        } else {
            JOptionPane pane = new JOptionPane();
            JOptionPane.showMessageDialog(pane, "Пожалуйста, выберите запись для удаления");
            pane.setSize(100, 100);
            pane.setVisible(false);
        }
    }



    // Создание таблицы
    public static void buildTable(JPanel panel) throws Exception {
        JScrollPane pane = new JScrollPane(dbTable);
        pane.setBounds(250, 20, 450, 460);

        panel.add(pane);

        Vector<String> header = new Vector<>();

        header.add("Сумма");
        header.add("Описание");
        header.add("Дата");

        dtm = (DefaultTableModel) dbTable.getModel();
        dtm.setColumnIdentifiers(header);

        //заполнение таблицы из файла
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String stringConfig;
            while ((stringConfig = br.readLine()) != null) {
                dtm.addRow(ReadWriteFile.parsString(stringConfig));
            }
        }
    }

}