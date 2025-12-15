package ru.maksim;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

public class App {
    private static final Normalizer normalizer = new Normalizer();
    private static final FileDownloader fileDownloader = new FileDownloader();
    private static final PhoneOkvedMatcher phoneOkvedMatcher = new PhoneOkvedMatcher();

    public static void main(String[] args) {
        final int timeToNextUpdate = getTimeToNextUpdate(args);

        Date nextUpdate = new Date();
        HashMap<String, ClassificationItem> dataFromFile = getStringClassificationItemHashMap(nextUpdate, timeToNextUpdate);

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nВведите номер телефона (q - для завершения работы) ");
            String phone = scanner.nextLine();
            if (phone.equals("q")) {
                return;
            }
            final Pair<Optional<String>, String> normalizedPair = normalizer.normalize(phone);
            if (normalizedPair.getLeft().isPresent()) {
                System.out.println("Phone " + normalizedPair.getLeft().get());
                if (new Date().after(nextUpdate)) {
                    nextUpdate = new Date();
                    dataFromFile = getStringClassificationItemHashMap(nextUpdate, timeToNextUpdate);
                }
                phoneOkvedMatcher.match(normalizedPair.getLeft().get(), dataFromFile);
            } else {
                System.out.println("false " + normalizedPair.getRight());
            }
        }
    }

    private static HashMap<String, ClassificationItem> getStringClassificationItemHashMap(Date nextUpdate, int timeToNextUpdate) {
        HashMap<String, ClassificationItem> dataFromFile = fileDownloader.getDataFromFile();
        if (dataFromFile != null && !dataFromFile.isEmpty()) {
            nextUpdate.setTime(nextUpdate.getTime() + timeToNextUpdate);
            System.out.println("Получены данные из okved.json. Следующее обновление данных из репозитория будет " + nextUpdate);
        }
        return dataFromFile;
    }

    private static int getTimeToNextUpdate(String[] args) {
        if (args.length < 1) {
            return 1000 * 60 * 60 * 24; // 1 day
        } else {
            try {
                return Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                return 1000 * 60 * 60 * 24;
            }
        }
    }


}
