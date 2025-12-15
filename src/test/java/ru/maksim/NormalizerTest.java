package ru.maksim;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class NormalizerTest {
    private final Normalizer normalizer = new Normalizer();
    private final String[] positiveTests = {
            "+7 (916) 123-45-67",
            "8 916 123 45 67",
            "89161234567",
            "7(916)123-45-67",
            "9161234567",
            "79161234567",
            "+79161234567"
    };
    private final String[] negativeTests = {
            "8161234567",
            "abc +7916def",
            "",
            "61234567890"
    };
    private final String[] negativeResults = {
            "Номер начинается не с 9",
            "Длина не соответствует длине телефонного номера",
            "Input is empty",
            "Номер начинается не с 8 или 7, или код оператора начинается не на 9"
    };

    @Test
    void normalize() {
        int positiveCount = 0;
        for (String test : positiveTests) {
            Pair<Optional<String>, String> normalized = normalizer.normalize(test);
            if (normalized.getLeft().isPresent()) {
                assertEquals(normalized.getLeft().get(), "+79161234567");
                positiveCount++;
            }
        }
        assertEquals(positiveTests.length, positiveCount);
        for (int i = 0; i < negativeTests.length; i++) {
            Pair<Optional<String>, String> normalized = normalizer.normalize(negativeTests[i]);

            if (normalized.getRight() != null) {
                assertEquals(normalized.getRight(), negativeResults[i]);
                positiveCount++;
            }
        }
    }
}