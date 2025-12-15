package ru.maksim;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PhoneOkvedMatcherTest {
    private final PhoneOkvedMatcher matcher = new PhoneOkvedMatcher();
    public static final HashMap<String, ClassificationItem> OKVEDS = new HashMap<>();
    static {
        OKVEDS.put("011139", new ClassificationItem("01.11.39", "Выращивание семян прочих масличных культур", new ArrayList<>()));
    }

    @Test
    void match() {
        assertEquals(6, matcher.match("+79011011139", OKVEDS));
        assertEquals(0, matcher.match("+79011012148", OKVEDS));
    }
}