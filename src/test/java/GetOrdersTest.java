import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.order.OrderAction;
import org.example.order.OrderAssertions;
import org.example.user.UserAction;
import org.example.user.UserAssertions;
import org.example.user.UserGenerator;
import org.junit.Test;

public class GetOrdersTest {

    private final UserAssertions userCheck = new UserAssertions();
    private final OrderAssertions checkOrder = new OrderAssertions();
    protected String accessToken;
    UserAction userAction = new UserAction();
    OrderAction orderAction = new OrderAction();

    @Test
    @DisplayName("Получение заказа авторизованным пользователем")
    public void createOrderWithAuthTest() {
        var user = UserGenerator.random();

        ValidatableResponse response = this.userAction.create(user);
        accessToken = userCheck.createdSuccessfully(response);

        ValidatableResponse createOrderResponse = this.orderAction.createOrder(accessToken);
        checkOrder.createdOrderSuccessfully(createOrderResponse);

        ValidatableResponse getOrderResponse = this.orderAction.getOrdersWithAuth(accessToken);
        checkOrder.getOrderListWithAuthSuccessfully(getOrderResponse);

        ValidatableResponse delete = userAction.delete(accessToken);
        userCheck.deletedSuccessfully(delete);
    }

    @Test
    @DisplayName("Получение заказа неавторизованным пользователем")
    public void createOrderWithoutAuthTest() {

        ValidatableResponse getOrderResponse = this.orderAction.getOrdersWithoutAuth();
        checkOrder.getOrderListWithoutAuth(getOrderResponse);
    }

}