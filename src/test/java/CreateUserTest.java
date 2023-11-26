import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.user.Credentials;
import org.example.user.UserAction;
import org.example.user.UserAssertions;
import org.example.user.UserGenerator;
import org.junit.Test;

public class CreateUserTest {

    private final UserAssertions userCheck = new UserAssertions();
    protected String accessToken;
    UserAction userAction = new UserAction();

    @Test
    @DisplayName("Создать уникального пользователя")
    public void createUserTest() {
        var user = UserGenerator.random();

        ValidatableResponse authResponse = this.userAction.create(user);
        userCheck.createdSuccessfully(authResponse);

        var loginData = Credentials.from(user);

        ValidatableResponse loginResponse = this.userAction.login(loginData);
        accessToken = userCheck.logedInSuccessfully(loginResponse);

        ValidatableResponse delete = userAction.delete(accessToken);
        userCheck.deletedSuccessfully(delete);

    }

    @Test
    @DisplayName("Создать пользователя, который уже зарегистрирован")
    public void createTwiceUserTest() {
        var userFirst = UserGenerator.random();

        ValidatableResponse responseFirst = userAction.create(userFirst);
        userCheck.createdSuccessfully(responseFirst);

        var loginData = Credentials.from(userFirst);

        ValidatableResponse loginResponse = userAction.login(loginData);
        accessToken = userCheck.logedInSuccessfully(loginResponse);

        ValidatableResponse responseSecond = userAction.create(userFirst);
        userCheck.createdUserTwice(responseSecond);

        ValidatableResponse delete = userAction.delete(accessToken);
        userCheck.deletedSuccessfully(delete);

    }

    @Test
    @DisplayName("Создать пользователя и не заполнить одно из обязательных полей")
    public void createUserWithoutObligateParamTest() {

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