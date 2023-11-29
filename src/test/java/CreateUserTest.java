import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.user.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateUserTest {
    private final UserAssertions userCheck = new UserAssertions();
    protected String accessToken;
    UserAction userAction = new UserAction();
    User user;

    // Вынес общие действия в Before и After
    @Before
    public void beforeAction() {
        user = UserGenerator.random();
        ValidatableResponse authResponse = this.userAction.create(user);
        userCheck.createdSuccessfully(authResponse);
    }

    @After
    public void afterAction() {
        ValidatableResponse delete = userAction.delete(accessToken);
        userCheck.deletedSuccessfully(delete);
    }

    @Test
    @DisplayName("Создать уникального пользователя")
    public void createUserTest() {
        var loginData = Credentials.from(user);

        ValidatableResponse loginResponse = this.userAction.login(loginData);
        accessToken = userCheck.logedInSuccessfully(loginResponse);
    }

    @Test
    @DisplayName("Создать пользователя, который уже зарегистрирован")
    public void createTwiceUserTest() {
        var loginData = Credentials.from(user);

        ValidatableResponse loginResponse = userAction.login(loginData);
        accessToken = userCheck.logedInSuccessfully(loginResponse);

        ValidatableResponse responseSecond = userAction.create(user);
        userCheck.createdUserTwice(responseSecond);
    }

    @Test
    @DisplayName("Создать пользователя и не заполнить одно из обязательных полей")
    public void createUserWithoutObligateParamTest() {
        var loginData = Credentials.from(user);

        ValidatableResponse loginResponse = this.userAction.login(loginData);
        accessToken = userCheck.logedInSuccessfully(loginResponse);

        var userWithoutEmail = UserGenerator.withoutEmail();

        ValidatableResponse responseWithoutEmail = userAction.create(userWithoutEmail);
        userCheck.createdWithoutObligateParam(responseWithoutEmail);

        var userWithoutPassword = UserGenerator.withoutPassword();

        ValidatableResponse responseWithoutPassword = userAction.create(userWithoutPassword);
        userCheck.createdWithoutObligateParam(responseWithoutPassword);

        var userWithoutName = UserGenerator.withoutName();

        ValidatableResponse responseWithoutName = userAction.create(userWithoutName);
        userCheck.createdWithoutObligateParam(responseWithoutName);
    }
}