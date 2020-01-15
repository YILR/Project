package Project;

import java.io.*;

public class Find {
    public static void main(String[] args) {
        Phone phone = new Phone();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            //цикл
            while (true) {
                System.out.println("Телефонный справочник!\nВыберите опцию:\nДобавить контакт - 1\nПоиск контакта - 2\nУдалить контакт - 3\nВыход - q");
                String s = reader.readLine();

                if (s.equals("1")) {
                    System.out.println("Напишите имя и номер контакта");
                    phone.insert(reader.readLine(), reader.readLine());

                } else if (s.equals("2")) {
                    System.out.println("Напишите имя");
                    phone.poisk(reader.readLine());

                } else if(s.equals("3")){
                    System.out.println("Напишите имя");
                    phone.delete(reader.readLine());

                } else if (s.equals("q"))
                    break;

                System.out.println();
            }
            reader.close();

        } catch (IOException e) {
            System.out.println("Неккоректно введено");
        }
    }
}
