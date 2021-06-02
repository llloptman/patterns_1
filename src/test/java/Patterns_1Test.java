import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.conditions.Text;
import com.codeborne.selenide.ex.ElementNotFound;
import data.DataGenerator;
import data.DataGenerator.Registration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

class Patterns_1Test {
    String city;
    String name;
    String phone;
    String date;
    String replanDate;
    DataGenerator.UserInfo user =  Registration.generateUser("ru-RU");

    public void fillInTheFormSeparatly(String city, String date, String name, String phone){
        $("[placeholder='Город']").setValue(city);
        $("[placeholder='Дата встречи']").sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(date);
        $("[name='name']").setValue(name);
        $("[name='phone']").setValue(phone);
        $(".checkbox__box").click();
    }
    public void checkCity(String city){

        int count = 1;
        for (int i = 0; i < count; i++) {
            try {
                $("[placeholder='Город']").setValue(city);
                $$(".menu-item__control").find(new Text(city)).click();
                $("[placeholder='Город']").shouldHave(Condition.value(city));
            }catch (ElementNotFound e){
                count++;
                $("[placeholder='Город']").sendKeys(Keys.CONTROL + "A", Keys.DELETE);
                city = DataGenerator.generateCity("ru-RU");
                this.city = city;
            }finally {
                $("[placeholder='Город']").sendKeys(Keys.CONTROL + "A", Keys.DELETE);
            }
        }
    }

@BeforeEach
    void init() {
        city = DataGenerator.generateCity("ru-RU");
        date = DataGenerator.generateDate(5);
        replanDate = DataGenerator.generateDate(3);
        name = DataGenerator.generateName("ru-RU");
        phone = DataGenerator.generatePhone("ru-RU");

        open("http://localhost:9999/");
        clearBrowserCookies();
        clearBrowserLocalStorage();

        // проверка, что генерируются только верные города (т.к. есть проблемы с некоторыми городами, например Сочи)
        checkCity(city);
        checkCity(user.getCity());
        //Вывод данных в консоль, чтоб было видно сгенерированные данные
        System.out.println("Город " + city + "\n" +
            "Имя " + name + "\n" +
            "Телефон " + phone);

        System.out.println("Город " + user.getCity() + "\n" +
            "Имя " + user.getName() + "\n" +
            "Телефон " + user.getPhone());
    }

    @Test
    void ShouldPassGenerateSeparately() {
        fillInTheFormSeparatly(city,date,name,phone);
        $(".button").click();
        $("[data-test-id='success-notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] button").click();
        Selenide.refresh();
        fillInTheFormSeparatly(city,replanDate,name,phone);
        $(".button").click();
        $("[data-test-id='replan-notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='replan-notification'] button").click();
        $(new Selectors.WithText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition
                .text(replanDate));
    }
    @Test
    void ShouldPassGenerateUser() {
        $("[placeholder='Город']").setValue(user.getCity());
        $("[placeholder='Дата встречи']").sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(date);
        $("[name='name']").setValue(user.getName());
        $("[name='phone']").setValue(user.getPhone());
        $(".checkbox__box").click();
        $(".button").click();
        $("[data-test-id='success-notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] button").click();
        Selenide.refresh();
        $("[placeholder='Город']").setValue(user.getCity());
        $("[placeholder='Дата встречи']").setValue(replanDate);
        $("[name='name']").setValue(user.getName());
        $("[name='phone']").setValue(user.getPhone());
        $(".checkbox__box").click();
        $(".button").click();
        $("[data-test-id='replan-notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='replan-notification'] button").click();
        $(new Selectors.WithText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition
                .text(replanDate));

    }
    @Test
    void ShouldFailByCity() {
        fillInTheFormSeparatly("Сочи",date,name,phone);
        $(".button").click();
        $("[data-test-id='success-notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] button").click();
        Selenide.refresh();
        fillInTheFormSeparatly(city,replanDate,name,phone);
        $(".button").click();
        $("[data-test-id='replan-notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='replan-notification'] button").click();
        $(new Selectors.WithText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition
                .text(replanDate));
    }


}