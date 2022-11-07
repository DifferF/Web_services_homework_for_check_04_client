package task_04;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/*
Задание 4+
Реализуйте свои первые простые SOAP и RESTfull сервисы для перевода слов.
Сервис знает около 20 слов на английском языке. Вы вводите слово на русском,
а сервис дает ответ на английском.
*/

public class Main_04 {

    private static String qwerty;

    public static void main(String[] args) throws IOException {
            myGsonList();
            reply(myGson(start()));

    }

    public static String start(){
        System.out.println("Напишите слово: ");
        Scanner input = new Scanner(System.in);
        String str =  input.nextLine();
        qwerty = str;
        return str;
    }

    public static void reply(String stringReply){
        System.out.println(qwerty + " - " + stringReply );
    }

    public static String[] myGsonList() throws IOException {
        String request = "http://localhost:8080/wordList";
        String result = performRequest( request );
        System.out.println("Я словарь, знаю перевод таких слов:");
        Gson gson = new GsonBuilder().create();
        // Происходит десериализация, из текстового JSON документа, получаем объектное представление
        // Тк. мы получаем массив, то дописываем []
        String [] word = gson.fromJson(result, String[].class);
        for (int i = 0; i < word.length;i++){
            System.out.println(word[i]);
        }
        return word;
    }

    public static String[] myGsonList_2() throws IOException {
        String request = "http://localhost:8080/wordList";
        String result = performRequest( request );
        Gson gson = new GsonBuilder().create();
        String [] word = gson.fromJson(result, String[].class);
        return word;
    }

    public static String myGson(String str) throws IOException {
        String [] word = myGsonList_2();
        int index = 0;
        for (int i = 0; i < word.length;i++){
            if(word[i].equals(str)){
                index = i;
                break;
            }
        }
        String request = "http://localhost:8080/translator?name=" + index;

        String result = performRequest( request );
        Gson gson = new GsonBuilder().create();
        String qwee = gson.fromJson(result, String.class);

        return qwee;
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
