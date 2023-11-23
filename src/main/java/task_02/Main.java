package task_02;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
Задание 2+
Используя JSON подключиться к НБУ и узнать текущий курс валют (PLN, USD, EURO).
 */

public class Main {
    
    public static void main(String[] args) throws Exception {
        // Ссылка на курс евро нбу
        String request = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
        // Отправляем ссылку  в метод для обработки запроса
        String result = performRequest(request);



        Gson gson = new GsonBuilder().create();
        // Происходит десериализация, из текстового JSON документа, получаем объектное представление
        // Тк. мы получаем массив, то дописываем []
        Currency [] currencies = gson.fromJson(result, Currency[].class);

        // Проходим по каждому курсу валют и выводим на экран
        for (Currency currency : currencies) {

            int cur = currency.getR030();

            switch (cur){
                case 985:  // PLN
                case 978:  // EUR
                case 752:  // USD
                    System.out.println( currency.getTxt() + " (" + currency.getR030() + " " +  currency.getCc()+  ") "
                            + currency.getRate() + " грн по НБУ на "
                            +currency.getExchangedate());
                    break;
            }
        }
    }

    // Делаем запрос и получаем JSON
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

} // TEST NEST TEST NEST  TEST NEST TEST NEST
