package Project;

import java.io.*;
import java.util.*;

public class Phone {
    private static Map<String, Long> book = new TreeMap<>();
    private File file = new File("C:/Solution/src/Project/File.txt");

    public Phone() {
        reading();
    }

    public Map<String, Long> getBook() {
        return book;
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
    private void  writing() {
        try (FileWriter writer = new FileWriter(file, false)) {

            for (Map.Entry map : book.entrySet()) {
                writer.write(map.getKey() + "\n" + map.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //вставка
    public boolean insert(String a, String b) {
        if (book.containsKey(a)) {
            System.out.println("Контакт с таким именем уже существует!");
            return false;
        } else {
            try {
                book.put(a, Long.parseLong(b));
                writing();
                System.out.println("Контакт сохранен");
            } catch (NumberFormatException e) {
                System.out.println("Нужно во 2-ю строку вводить числа!");
            }
        }
        return true;
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
    public void delete(Object s) {

        if (book.containsKey(s)) {
            book.remove(s);
            writing();
            System.out.println("Контакт удален " +s);

        } else System.out.println("Контакт с таким именем не существует!" + s);

    }
}
