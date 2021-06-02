package data;


import com.github.javafaker.Faker;
import lombok.Data;
import lombok.Value;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, shift);
        return dateFormat.format(calendar.getTime());
    }

    public static String generateCity(String locale) {
        // TODO: добавить логику для объявления переменной city и задания её значения, генерацию можно выполнить
        // с помощью Faker, либо используя массив валидных городов и класс Random
        Faker faker =new Faker(new Locale(locale));
        String city = faker.address().city();
        return city;
    }

    public static String generateName(String locale) {
        // TODO: добавить логику для объявления переменной name и задания её значения, для генерации можно
        // использовать Faker
        Faker faker =new Faker(new Locale(locale));
        String name = faker.name().fullName();
        return name;
    }

    public static String generatePhone(String locale) {
        // TODO: добавить логику для объявления переменной phone и задания её значения, для генерации можно
        // использовать Faker
        Faker faker =new Faker(new Locale(locale));
        String phone = faker.phoneNumber().cellPhone();
        return phone;
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            // TODO: добавить логику для создания пользователя user с использованием методов generateCity(locale),
            // generateName(locale), generatePhone(locale)
            int a = 0;
            System.out.println(a + 1);

             UserInfo user = new UserInfo(generateCity("ru-RU"),
                    generateName("ru-RU"),
                    generatePhone("ru-RU"));
            
            return user;
        }
    }
    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;

        public UserInfo(String city, String name, String phone) {
            this.city = city;
            this.name = name;
            this.phone = phone;
        }

        public String getCity() {
            return city;
        }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }
    }
}
