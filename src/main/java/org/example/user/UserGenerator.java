package org.example.user;

import com.github.javafaker.Faker;

public class UserGenerator {
    static Faker faker = new Faker();

    public static User generic() {
        return new User("raikov5@ya.ru", "1234", "saske");
    }

    public static User random() {
        return new User(faker.internet().emailAddress(), faker.number().digits(6), faker.name().firstName());
    }

    public static User withoutEmail() {
        return new User(null, "1234", "saske");
    }

    public static User withoutPassword() {
        return new User("raikov@ya.ru", null, "saske");
    }

    public static User withoutName() {
        return new User("raikov@ya.ru", "1234", null);
    }

    public static User withWrongEmail() {
        return new User("WrongEmail@ya.ru", "1234", null);
    }

    public static User withWrongPassword() {
        return new User("raikov@ya.ru", "WrongPassword", null);
    }

    public static User withNewData() {
        return new User("raikovNew@ya.ru", "01234", "saskeNew");
    }
}
