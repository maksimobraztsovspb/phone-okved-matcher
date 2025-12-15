package ru.maksim;

import java.util.HashMap;

public class PhoneOkvedMatcher {

    /**
     * Возвращает длину совпадений.
     *
     * @param phone Номер телефона.
     * @param okveds HashMap с String ОКВЭД без разделителей и элементом ClassificationItem.
     * @return Возвращает длину совпадений.
     */
    public int match(final String phone, HashMap<String, ClassificationItem> okveds) {
        String tempString = phone;
        int i = 0;
        while (tempString.length() > 1) {
            tempString = tempString.substring(1);
            if (okveds.containsKey(tempString)) {
                System.out.println("Нормализованный номер: "
                        + phone + ". Найденный ОКВЭД: "
                        + okveds.get(tempString).getCode()
                        + ". "
                        + okveds.get(tempString).getName()
                        + ". Длина совпадения: " + tempString.length());
                return tempString.length();
            }
            i++;
        }
        System.out.println("Нормализованный номер: " + phone + ". Совпадений не найдено");
        return 0;
    }
}
