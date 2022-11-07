package task_03;

/*
Задание 3+
С помощью скрипта из вложений carsshop.sql создать базу данных carsshop.
И создать SOAP-сервис, который с помощью jdbc будет брать dao объектов из таблиц.
Создать клиента к этому сервису и получить данные.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        // получить всех клиентов
        System.out.println("________________allClient() ");
        allClient();

        // создать клиента
        System.out.println("________________creatorClient() ");
        creatorClient("Name_Client",45,"09796778000");

        // удалить клиента по имени
        System.out.println("________________удалить клиента по имени ");
        dellClients(inputNameClient());
    }

    // создать клиента
    public static String creatorClient(String name, int age, String phone) throws IOException {
        String request = "http://localhost:8080/creatorClient?name=" +name+ "&age=" +age+ "&phone=" +phone;
        String result = performRequest( request );
        Gson gson = new GsonBuilder().create();
        // Происходит десериализация, из текстового JSON документа, получаем объектное представление
        String qwee = gson.fromJson(result, String.class);
        System.out.println(qwee);
        return qwee;
    }

    // получить всех клиентов
    public static void allClient() throws IOException {
        String request = "http://localhost:8080/allClient";
        String result = performRequest( request );
        Gson gson = new GsonBuilder().create();
        Client [] clients = gson.fromJson(result, Client[].class);
        for (Client client_1 : clients) {
            System.out.println(client_1.getId() + " " + client_1.getName() + " " + client_1.getAge() + " " + client_1.getPhone());
        }
    }

    public static String inputNameClient() throws IOException {
        allClient();
        System.out.println("Имя Клиента: ");
        Scanner input = new Scanner(System.in);
        String str =  input.nextLine();
        return str;
    }

    // удалить клиента по имени
    public static void dellClients(String clientName) throws IOException {

        String request = "http://localhost:8080/dellClients?name="+clientName;
        String result = performRequest( request );
        Gson gson = new GsonBuilder().create();
        String qwee = gson.fromJson(result, String.class);
        System.out.println(qwee);
    }


    private static String performRequest(String urlStr) throws IOException {
        // Создаем объект урлы
        URL url = new URL(urlStr);
        // Создаем объект для постоения строки
        StringBuilder sb = new StringBuilder();
        // Создаем соединение и переходим по этой урле
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        try {
            // Полученный из http потока стрим передаем в бафферед ридер
            BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
            char[] buf = new char[1000000];
            // Посимвольно добавляет данные к итоговой строке
            int r = 0;
            do {
                if ((r = br.read(buf)) > 0)
                    sb.append(new String(buf, 0, r));
            } while (r > 0);
        } finally {
            http.disconnect();
        }
        return sb.toString();
    }
}
