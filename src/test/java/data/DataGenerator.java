package data;


import com.github.javafaker.Faker;
import lombok.Data;
import lombok.Value;

import java.text.SimpleDateFormat;
import java.util.*;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, shift);
        return dateFormat.format(calendar.getTime());
    }

    public static String generateCity() {
        String[] cities = {
             "Белгород",
             "Великий новгород",
             "Волгоград",
             "Воронеж",
             "Горно-Алтайск"
        };

        String randomCity = cities[(int)Math.round(Math.random() * (cities.length - 1))];

        return randomCity;
    }

    public static String generateName(String locale) {
        Faker faker =new Faker(new Locale(locale));
        String name = faker.name().fullName();
        return name;
    }

    public static String generatePhone(String locale) {
        Faker faker =new Faker(new Locale(locale));
        String phone = faker.phoneNumber().cellPhone();
        return phone;
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            int a = 0;
            System.out.println(a + 1);

             UserInfo user = new UserInfo(generateCity(),
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
    }
}
