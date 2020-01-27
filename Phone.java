package Project;

import java.io.*;
import java.util.*;

class Phone {
    private Map<String, Long> book = new TreeMap<>();
    private File file = new File("C:/Solution/src/Project/File.txt");


    Phone() {
        reading();
    }

    //чтение файла
    private void reading() {
        try (FileReader rd = new FileReader(file)) {

            BufferedReader br = new BufferedReader(rd);
            String line;

            while ((line = br.readLine()) != null) {
                if (line.equals("")) break;
                book.put(line, Long.parseLong(br.readLine()));
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //запись файла
    private void writing() {
        try (FileWriter writer = new FileWriter(file, false)) {

            for (Map.Entry map : book.entrySet()) {
                writer.write(map.getKey() + "\n" + map.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //вставка
    public void insert(String a, String b) {
        if (book.containsKey(a)) {
            System.out.println("Контакт с таким именем уже существует!");

        } else {
            try {
                book.put(a, Long.parseLong(b));
                writing();
                System.out.println("Контакт сохранен");
            } catch (NumberFormatException e) {
                System.out.println("Нужно во 2-ю строку вводить числа!");
            }
        }
    }

    //поиск
    public void poisk(String s) {

        for (Map.Entry map : book.entrySet()) {
            if (map.getKey().equals(s)) {
                System.out.println(map.getKey() + " " + map.getValue());
                return;
            }
        }
        System.out.println("Контакт с таким именем не найден!");
    }

    //удаление
    public void delete(String s) {

        if (book.containsKey(s)) {
            book.remove(s);
            writing();
            System.out.println("Контакт удален");

        } else System.out.println("Контакт с таким именем не существует!");

    }
}
