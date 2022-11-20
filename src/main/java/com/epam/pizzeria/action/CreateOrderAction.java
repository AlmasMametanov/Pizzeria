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

import static com.epam.pizzeria.action.ActionConstants.*;
import static com.epam.pizzeria.util.constants.ErrorConstants.*;
import static com.epam.pizzeria.util.constants.PageNameConstants.*;
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
                List<Basket> basketList = (List<Basket>) httpSession.getAttribute(BASKETS_BY_USER);
                Order order = new Order();
                setParametersToOrderAndInsertIntoDatabase(order, httpSession);
                for (Basket basket : basketList) {
                    setParametersToOrderDetailAndInsertIntoDatabase(order.getId(), basket);
                    basketIngredientDetailDAO.deleteBasketIngredientDetailByBasketId(basket.getId());
                    basketDAO.deleteBasketById(basket.getId());
                }
                response.sendRedirect(INDEX_JSP);
            } else {
                request.getRequestDispatcher(CHECKOUT_ORDER_JSP).forward(request, response);
            }
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }

    private Boolean validators(HttpServletRequest request) {
        Boolean isValidatorPassed = true;
        if (!validateCardNumber(request.getParameter(CARD_NUMBER))) {
            request.setAttribute(CARD_NUMBER_FORMAT_INCORRECT, ERROR_CARD_NUMBER);
            isValidatorPassed = false;
        }
        if (!validateValidityOfBankCard(request.getParameter(VALIDITY))) {
            request.setAttribute(VALIDITY_CARD_FORMAT_INCORRECT, ERROR_VALIDITY_CARD);
            isValidatorPassed = false;
        }
        if (!validateCVCOfBankCard(request.getParameter(CVC))) {
            request.setAttribute(CVC_BANK_CARD_FORMAT_INCORRECT, ERROR_CVC_BANK_CARD);
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
        Integer totalPrice = (Integer) httpSession.getAttribute(TOTAL_PRICE);
        DeliveryMethodLocale deliveryMethodLocale = (DeliveryMethodLocale) httpSession.getAttribute(DELIVERY_METHOD_LOCALE);
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
