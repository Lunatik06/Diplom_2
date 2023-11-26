import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.user.Credentials;
import org.example.user.UserAction;
import org.example.user.UserAssertions;
import org.example.user.UserGenerator;
import org.junit.Test;

public class LoginUserTest {

    private final UserAction userAction = new UserAction();
    private final UserAssertions userCheck = new UserAssertions();
    protected String accessToken;

    @Test
    @DisplayName("Логин под существующим пользователем,")
    public void loginUserTest() {
        var user = UserGenerator.random();

        ValidatableResponse response = this.userAction.create(user);
        userCheck.createdSuccessfully(response);

        var loginData = Credentials.from(user);

        ValidatableResponse loginResponse = this.userAction.login(loginData);
        accessToken = userCheck.logedInSuccessfully(loginResponse);

        ValidatableResponse delete = userAction.delete(accessToken);
        userCheck.deletedSuccessfully(delete);

    }

    @Test
    @DisplayName("Логин с неверным email")
    public void loginUserWithWrongEmailTest() {

        var userWithWrongEmail = UserGenerator.withWrongEmail();
        var loginDataWithWrongEmail = Credentials.from(userWithWrongEmail);

        ValidatableResponse loginResponseWithWrongParam = this.userAction.login(loginDataWithWrongEmail);
        userCheck.loginWithWrongParam(loginResponseWithWrongParam);

    }

    @Test
    @DisplayName("Логин с неверным паролем")
    public void loginUserWithWrongPasswordTest() {

        var userWithWrongPassword = UserGenerator.withWrongPassword();
        var loginDataWithWrongPassword = Credentials.from(userWithWrongPassword);

        ValidatableResponse loginResponseWithWrongParam = this.userAction.login(loginDataWithWrongPassword);
        userCheck.loginWithWrongParam(loginResponseWithWrongParam);

    }


}