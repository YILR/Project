package Project.Contacts;

import Project.Phone;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public  class AddFrame extends JDialog implements ActionListener, KeyListener {

    private JPanel panel = new JPanel();  //панель 1
    private JLabel label = new JLabel("Введите имя контакта:");
    private JLabel label2 = new JLabel("Введите номер контакта:");
    private TextField textField = new TextField();
    private TextField intField = new TextField();
    private JPanel jPanel = new JPanel();  //панель 2
    private JButton yesBt, noBt;

    AddFrame(){

        setTitle("Добавление контакта");
        setModalityType(ModalityType.TOOLKIT_MODAL); //Делает модульным окно
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        yesBt = new JButton("OK");
        noBt = new JButton("Отмена");
        // панель1 добавляем кнопки
        jPanel.setLayout(new GridLayout(1,2,5,5));
        jPanel.add(yesBt);
        jPanel.add(noBt);
        // панель2 добавляем метки и ввод текста
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setLayout(new GridLayout(7,1,15,0));
        panel.add(label);
        panel.add(textField);
        panel.add(label2);
        panel.add(intField);
        panel.add(new JPanel()); //создание пустой панели
        panel.add(jPanel);

        yesBt.addActionListener(this);
        noBt.addActionListener(this);
        intField.addKeyListener(this);

        add(panel, BorderLayout.CENTER);
        setBounds(400,200,300,250);
        setResizable(false); // запрет на изменение размера
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equalsIgnoreCase("OK")){
            String name = textField.getText();
            Long num = Long.parseLong(intField.getText());

            //проверяет существует ли такой контакт
            if(new Phone().insert(name, intField.getText())) {
                MainPanel.setModel(name, num);      //вызывает метод для добавление нового контакта
                setVisible(false);
            }else JOptionPane.showMessageDialog(this,"Конаткт с таким именем уже существует!");

        }else setVisible(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {  }
    @Override
    public void keyPressed(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) {
        try {
            Long.parseLong(intField.getText());
            yesBt.setEnabled(true); //активность кнопки, если не выбрасывает исключение
        }catch (Exception ae){
            yesBt.setEnabled(false);
        }
    }
}
