import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.user.Credentials;
import org.example.user.UserAction;
import org.example.user.UserAssertions;
import org.example.user.UserGenerator;
import org.junit.Test;

public class ChangeUserTest {

    private final UserAction userAction = new UserAction();
    private final UserAssertions userCheck = new UserAssertions();
    protected String accessToken;

    @Test
    @DisplayName("Редактирование пользователя с авторизацией")
    public void changeUserWithAuthTest() {
        var user = UserGenerator.random();
        var userWithNewData = UserGenerator.withNewData();

        ValidatableResponse authResponse = this.userAction.create(user);
        userCheck.createdSuccessfully(authResponse);

        var loginData = Credentials.from(user);

        ValidatableResponse loginResponse = this.userAction.login(loginData);
        accessToken = userCheck.logedInSuccessfully(loginResponse);

        ValidatableResponse change = userAction.changeWithAuth(userWithNewData, accessToken);
        userCheck.changeDataSuccessfully(change);

        ValidatableResponse delete = userAction.delete(accessToken);
        userCheck.deletedSuccessfully(delete);

    }

    @Test
    @DisplayName("Редактирование пользователя без авторизации")
    public void changeUserWithoutAuthTest() {
        var user = UserGenerator.random();
        var userWithNewData = UserGenerator.withNewData();

        ValidatableResponse authResponse = this.userAction.create(user);
        userCheck.createdSuccessfully(authResponse);

        var loginData = Credentials.from(user);

        ValidatableResponse loginResponse = this.userAction.login(loginData);
        accessToken = userCheck.logedInSuccessfully(loginResponse);

        ValidatableResponse change = userAction.changeWithoutAuth(userWithNewData);
        userCheck.changeDataWithoutAuth(change);

        ValidatableResponse delete = userAction.delete(accessToken);
        userCheck.deletedSuccessfully(delete);

    }

}