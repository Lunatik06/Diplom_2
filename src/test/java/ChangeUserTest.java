import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.user.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ChangeUserTest {
    private final UserAction userAction = new UserAction();
    private final UserAssertions userCheck = new UserAssertions();
    protected String accessToken;
    User user;
    User userWithNewData;

    // Вынес общие действия в Before и After
    @Before
    public void beforeAction() {
        user = UserGenerator.random();
        ValidatableResponse authResponse = this.userAction.create(user);
        userCheck.createdSuccessfully(authResponse);
        userWithNewData = UserGenerator.withNewData();
    }

    @After
    public void afterAction() {
        ValidatableResponse delete = userAction.delete(accessToken);
        userCheck.deletedSuccessfully(delete);
    }

    @Test
    @DisplayName("Редактирование пользователя с авторизацией")
    public void changeUserWithAuthTest() {
        var loginData = Credentials.from(user);

        ValidatableResponse loginResponse = this.userAction.login(loginData);
        accessToken = userCheck.logedInSuccessfully(loginResponse);

        ValidatableResponse change = userAction.changeWithAuth(userWithNewData, accessToken);
        userCheck.changeDataSuccessfully(change);
    }

    @Test
    @DisplayName("Редактирование пользователя без авторизации")
    public void changeUserWithoutAuthTest() {
        var loginData = Credentials.from(user);

        ValidatableResponse loginResponse = this.userAction.login(loginData);
        accessToken = userCheck.logedInSuccessfully(loginResponse);

        ValidatableResponse change = userAction.changeWithoutAuth(userWithNewData);
        userCheck.changeDataWithoutAuth(change);
    }
}