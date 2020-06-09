package Project.Contacts;

import Project.Phone;

import javax.sound.midi.ShortMessage;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

//Панель Основной с таблицей
class MainPanel extends JPanel {
    //метка
    private JLabel label = new JLabel("Список Контактов", JLabel.CENTER);
    private JLabel label2 = new JLabel("Поиск: ");
    private JPanel panel = new JPanel(new BorderLayout(3, 1));
    //поиск
    private static JTextField textField = new JTextField();
    //название колонок
    private String columNames[] = {"Имя", "Номер"};
    //таблица
    private static JTable table;
    //DefaultTableModel и запрет на редактирование
    private static DefaultTableModel tableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int i, int i1) {
            return false;
        }
    };
    private static TableRowSorter<TableModel> rowSorter;

    MainPanel() {

        super();
        setBorder(BorderFactory.createEtchedBorder());
        setLayout(new BorderLayout());
        //панель

        panel.add(label2, BorderLayout.WEST);
        panel.add(textField, BorderLayout.CENTER);
        panel.add(label, BorderLayout.SOUTH);

        label.setFont(new Font("Italic", Font.ITALIC, 20));  // меняем шрифт метки

        tableModel.setColumnIdentifiers(columNames);  // добавляем колонку

        for (Map.Entry<String, Long> entry : new Phone().getBook().entrySet())   //добавление из map в tablemodel
            tableModel.addRow(new Object[]{entry.getKey(), entry.getValue()});

        table = new JTable(tableModel);  //кидаем в таблицу из tablemodel
        // создаем TableRowSorter
        rowSorter = new TableRowSorter<>(table.getModel());

        table.setRowSorter(rowSorter);
        //table.setAutoCreateRowSorter(false); //сортировка
        table.getRowSorter().toggleSortOrder(0); //сортирует по первой колонке
        table.getTableHeader().setReorderingAllowed(false);  //запрет на изменение колонок

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        //события динамического поиска
        textField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = textField.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = textField.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    //метод для добавление новых контактов
    public static void setModel(String a, Long b) {
        tableModel.addRow(new Object[]{a, b});
    }

    //метод для удаление контактов
    public static void getTable() {
        int idx = table.getSelectedRow();  //получить номер строки

        new Phone().delete(tableModel.getValueAt(idx, 0)); //удаляет из map
        tableModel.removeRow(idx); //удаляет из таблицы
    }
}


//Панель с Кнопками
class ButPanel extends JPanel implements ActionListener {

    ButPanel() {
        super();
        //кнопки

        JButton addBt = new JButton("Добавить");
        JButton delBt = new JButton("Удалить");

        setLayout(new GridLayout(7, 1, 20, 10));

        add(addBt);
        add(delBt);

        addBt.addActionListener(this);
        delBt.addActionListener(this);

    }

    @Override //События кнопок
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("Добавить")) {
            //Новый поток
            //создает анонимный объект и дополнительное окно
            SwingUtilities.invokeLater(() -> new AddFrame());
        } else if (e.getActionCommand().equalsIgnoreCase("Удалить")) { //вызывает метод для удаления
            MainPanel.getTable(); //вызывает метод для удаления
        }
    }
}

//Главное Окно
class TextFrame extends JFrame {
    MainPanel MP = new MainPanel();  //Главная панель с таблицей
    ButPanel BT = new ButPanel(); //Дополнительная панель с кнопками

    TextFrame() {
        super("Contacts");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(MP, BorderLayout.CENTER);
        add(BT, BorderLayout.EAST);

        setBounds(400, 200, 400, 350);
        setVisible(true);
    }
}

public class SwingDemo {
    public static void main(String[] args) {
        // Новый поток
        SwingUtilities.invokeLater(() -> {
            new TextFrame(); //Создание главного Окна
        });
    }
}
