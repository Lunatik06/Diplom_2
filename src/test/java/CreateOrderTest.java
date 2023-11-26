import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.order.OrderAction;
import org.example.order.OrderAssertions;
import org.example.user.UserAction;
import org.example.user.UserAssertions;
import org.example.user.UserGenerator;
import org.junit.Test;


public class CreateOrderTest {

    private final UserAssertions userCheck = new UserAssertions();
    private final OrderAssertions checkOrder = new OrderAssertions();
    protected String accessToken;
    UserAction userAction = new UserAction();
    OrderAction orderAction = new OrderAction();

    @Test
    @DisplayName("Создание заказа с авторизацией")
    public void createOrderWithAuthTest() {
        var user = UserGenerator.random();

        ValidatableResponse response = this.userAction.create(user);
        accessToken = userCheck.createdSuccessfully(response);

        ValidatableResponse createOrderResponse = this.orderAction.createOrder(accessToken);
        checkOrder.createdOrderSuccessfully(createOrderResponse);

        ValidatableResponse delete = userAction.delete(accessToken);
        userCheck.deletedSuccessfully(delete);

    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithoutAuthTest() {
        var user = UserGenerator.random();

        ValidatableResponse response = this.userAction.create(user);
        accessToken = userCheck.createdSuccessfully(response);

        ValidatableResponse createOrderResponse = this.orderAction.createOrder();
        checkOrder.createdOrderSuccessfully(createOrderResponse);

        ValidatableResponse delete = userAction.delete(accessToken);
        userCheck.deletedSuccessfully(delete);

    }

    @Test
    @DisplayName("Создание заказа без авторизации и без ингредиентов")
    public void createOrderWithoutAuthAndWithoutIngredientTest() {
        var user = UserGenerator.random();

        ValidatableResponse response = this.userAction.create(user);
        accessToken = userCheck.createdSuccessfully(response);

        ValidatableResponse createOrderResponse = this.orderAction.createOrderWithoutIngredients();
        checkOrder.createdOrderWithoutIngredients(createOrderResponse);

        ValidatableResponse delete = userAction.delete(accessToken);
        userCheck.deletedSuccessfully(delete);

    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void createOrderWithWrongDataTest() {
        var user = UserGenerator.random();

        ValidatableResponse response = this.userAction.create(user);
        accessToken = userCheck.createdSuccessfully(response);

        ValidatableResponse createOrderResponse = this.orderAction.createOrderWithWrongData();
        checkOrder.createdOrderWithWrongData(createOrderResponse);

        ValidatableResponse delete = userAction.delete(accessToken);
        userCheck.deletedSuccessfully(delete);

    }

}