package ru.maksim;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class FileDownloader {

    /**
     * Возвращает данные из файла okved.json.
     *
     * @return HashMap с String ОКВЭД без разделителей и элементом ClassificationItem.
     */
    public HashMap<String, ClassificationItem> getDataFromFile() {
        final String rawUrl = "https://raw.githubusercontent.com/bergstar/testcase/master/okved.json";
        final HashMap<String, ClassificationItem> map = new HashMap<>();

        try {
            final  URL url = new URL(rawUrl);
            final  HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);

            if (connection.getResponseCode() == 200) {
                try (InputStream input = connection.getInputStream();
                     InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {

                    final ObjectMapper mapper = new ObjectMapper();
                    final  List<ClassificationItem> classificationItems = mapper.readValue(
                            reader,
                            mapper.getTypeFactory().constructCollectionType(List.class, ClassificationItem.class)
                    );

                    if (classificationItems != null) {
                        fillTreeMap(map, classificationItems);
                        System.out.println("Map заполнена. Элементов: " + map.size());
                    }
                }
            } else {
                System.err.println("Ошибка HTTP: " + connection.getResponseCode());
            }
            connection.disconnect();
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке данных: " + e.getMessage());
            e.printStackTrace();
        }

        return map;
    }


    private void fillTreeMap(HashMap<String, ClassificationItem> map,
                             List<ClassificationItem> items) {
        if (items == null) return;

        for (ClassificationItem item : items) {
            if (item != null && item.getCode() != null) {
                map.put(item.getCode().replace(".", ""), item);

                if (item.getItems() != null && !item.getItems().isEmpty()) {
                    fillTreeMap(map, item.getItems());
                }
            }
        }
    }

}
