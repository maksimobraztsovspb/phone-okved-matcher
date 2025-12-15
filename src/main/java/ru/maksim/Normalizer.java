package ru.maksim;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;

public class Normalizer {

    /**
     * Нормализует телефонный номер России в формат +7XXXXXXXXXX.
     *
     * @param input входная строка (может содержать буквы, пробелы, +)
     * @return Pair с Optional нормализованным номером и сообщением об ошибке
     */
    public Pair<Optional<String>, String> normalize(String input) {
        if (input == null || input.isEmpty()) {
            return Pair.of(Optional.empty(), "Input is empty");
        }
        final String digitsOnly = input.replaceAll("[^0-9]", "");
        if (!digitsOnly.matches("[0-9]+")) {
            return Pair.of(Optional.empty(), "Посторонние символы");
        }
        final int length = digitsOnly.length();
        if (length == 10) {
            if (digitsOnly.startsWith("9")) {
                return Pair.of(Optional.of("+7" + digitsOnly), "");
            }
            return Pair.of(Optional.empty(), "Номер начинается не с 9");

        } else if (length == 11) {
            if (digitsOnly.startsWith("89")) {
                return Pair.of(Optional.of("+7" + digitsOnly.substring(1)), "");
            } else if (digitsOnly.startsWith("79")) {
                return Pair.of(Optional.of("+" + digitsOnly), "");
            }
            return Pair.of(Optional.empty(), "Номер начинается не с 8 или 7, или код оператора начинается не на 9");
        }
        return Pair.of(Optional.empty(), "Длина не соответствует длине телефонного номера");
    }
}

