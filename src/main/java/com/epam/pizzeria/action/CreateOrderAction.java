package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.*;
import com.epam.pizzeria.database.dao.interfaces.*;
import com.epam.pizzeria.entity.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static com.epam.pizzeria.action.ActionConstants.PAGE_NOT_FOUND_ACTION;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;
import static com.epam.pizzeria.validator.Validator.*;

public class CreateOrderAction implements Action {
    private OrderDAO orderDAO = new OrderDAOImpl();
    private OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();
    private BasketIngredientDetailDAO basketIngredientDetailDAO = new BasketIngredientDetailDAOImpl();
    private BasketDAO basketDAO = new BasketDAOImpl();
    private OrderIngredientDetailDAO orderIngredientDetailDAO = new OrderIngredientDetailDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User user = (User) httpSession.getAttribute(USER);
        if (user != null) {
            Boolean isValidatorPassed = validators(request);
            if (isValidatorPassed) {
                List<Basket> basketList = (List<Basket>) httpSession.getAttribute("basketsByUser");
                Order order = new Order();
                setParametersToOrderAndInsertIntoDatabase(order, httpSession);
                for (Basket basket : basketList) {
                    setParametersToOrderDetailAndInsertIntoDatabase(order.getId(), basket);

                    basketIngredientDetailDAO.deleteBasketIngredientDetailByBasketId(basket.getId());
                    basketDAO.deleteBasketById(basket.getId());
                }
                response.sendRedirect("index.jsp");
            } else {
                request.getRequestDispatcher("checkoutOrder.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }

    private Boolean validators(HttpServletRequest request) {
        Boolean isValidatorPassed = true;
        if (!validateCardNumber(request.getParameter("cardNumber"))) {
            request.setAttribute("cardFormatIncorrect", "Bank card number has to contain 16 digits from 0 to 9");
            isValidatorPassed = false;
        }
        if (!validateValidityOfBankCard(request.getParameter("validity"))) {
            request.setAttribute("validityCardFormatIncorrect", "Bank card validity has to be MM/yy");
            isValidatorPassed = false;
        }
        if (!validateCVCOfBankCard(request.getParameter("cvc"))) {
            request.setAttribute("cvcOfBankCardIncorrect", "Bank card CVC has to contain 3 digits from 0 to 9");
            isValidatorPassed = false;
        }
        if (isValidatorPassed)
            return true;
        return false;
    }

    private void setParametersToOrderDetailAndInsertIntoDatabase(Long orderId, Basket basket) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(orderId);
        orderDetail.setProductId(basket.getProductId());
        orderDetail.setCount(basket.getCount());
        orderDetail.setPrice(basket.getProduct().getPrice() / basket.getCount());
        if (basket.getProductSizeId() != 0) {
            orderDetail.setProductSizeId(basket.getProductSizeId());
            orderDetailDAO.insertOrderDetail(orderDetail);
            if (!basket.getProduct().getBasketIngredientDetailList().isEmpty())
                setParametersToOrderIngredientDetailAndInsertIntoDatabase(basket, orderDetail.getId());
        } else {
            orderDetailDAO.insertOrderDetailIfSizeIsNull(orderDetail);
        }

    }

    private void setParametersToOrderIngredientDetailAndInsertIntoDatabase(Basket basket, Long orderDetailId) {
        for (BasketIngredientDetail basketIngredientDetail : basket.getProduct().getBasketIngredientDetailList()) {
            OrderIngredientDetail orderIngredientDetail = new OrderIngredientDetail();
            orderIngredientDetail.setOrderDetailId(orderDetailId);
            orderIngredientDetail.setAdditionalIngredientDetailId(basketIngredientDetail.getAdditionalIngredientDetailId());
            orderIngredientDetailDAO.insertOrderIngredientDetail(orderIngredientDetail);
        }
    }

    private void setParametersToOrderAndInsertIntoDatabase(Order order, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(USER);
        Integer totalPrice = (Integer) httpSession.getAttribute("totalPrice");
        DeliveryMethodLocale deliveryMethodLocale = (DeliveryMethodLocale) httpSession.getAttribute("deliveryMethodLocale");
        LocalDateTime orderTime = LocalDateTime.now();
        Timestamp orderTimeForDB = Timestamp.valueOf(orderTime);
        order.setUserId(user.getId());
        order.setStatusId(1L);
        order.setDateStart(orderTimeForDB);
        order.setReadyIn("30 минут");
        order.setTotalPrice(totalPrice);
        order.setDeliveryMethodId(deliveryMethodLocale.getDeliveryMethodId());
        orderDAO.insertOrder(order);
    }
}
