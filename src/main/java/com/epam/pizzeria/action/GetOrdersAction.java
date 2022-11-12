package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.*;
import com.epam.pizzeria.database.dao.interfaces.*;
import com.epam.pizzeria.entity.Order;
import com.epam.pizzeria.entity.OrderDetail;
import com.epam.pizzeria.entity.StatusLocale;
import com.epam.pizzeria.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.epam.pizzeria.action.ActionConstants.*;
import static com.epam.pizzeria.util.constants.PageNameConstants.*;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;

public class GetOrdersAction implements Action {
    private StatusLocaleDAO statusLocaleDAO = new StatusLocaleDAOImpl();
    private OrderDAO orderDAO = new OrderDAOImpl();
    private OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();
    private ProductDAO productDAO = new ProductDAOImpl();
    private ProductSizeDAO productSizeDAO = new ProductSizeDAOImpl();
    private OrderIngredientDetailDAO orderIngredientDetailDAO = new OrderIngredientDetailDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User checkIfUser = (User) httpSession.getAttribute(USER);
        if (checkIfUser != null) {
            Long localeId = (Long) httpSession.getAttribute(LOCALE_ID);
            Long userId;
            if (checkIfUser.getIsAdmin() && request.getParameter(USER_ID) != null) {
                userId = Long.parseLong(request.getParameter(USER_ID));
                List<StatusLocale> statusLocaleList = statusLocaleDAO.getAllStatusByLocaleId(localeId);
                request.setAttribute(USER_ID, userId);
                request.setAttribute(STATUS_LOCALE_LIST, statusLocaleList);
            } else {
                userId = checkIfUser.getId();
            }
            List<Order> orders = orderDAO.getAllOrdersByUserIdAndLocaleId(userId, localeId);
            if (!orders.isEmpty()) {
                setValuesIntoOrder(orders, localeId);
                request.setAttribute(ORDERS, orders);
                request.getRequestDispatcher(GET_ORDERS_JSP).forward(request, response);
            } else {
                response.sendRedirect(INDEX_JSP);
            }
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }

    private void setProductAndIngredientsIntoOrderDetail(List<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setProduct(productDAO.getProductById(orderDetail.getProductId()));
            if (orderDetail.getProduct().getIsPizza())
                orderDetail.setProductSize(productSizeDAO.getProductSizeBySizeIdAndProductId(orderDetail.getProductSizeId(), orderDetail.getProductId()));
            orderDetail.setOrderIngredientDetailList(orderIngredientDetailDAO.getAllOrderIngredientDetailByOrderDetailId(orderDetail.getId()));
        }
    }

    private void setValuesIntoOrder(List<Order> orders, Long localeId) {
        List<OrderDetail> orderDetails;
        for (Order order : orders) {
            orderDetails = orderDetailDAO.getAllOrderDetailByOrderId(order.getId());
            setProductAndIngredientsIntoOrderDetail(orderDetails);
            order.setOrderDetailList(orderDetails);
            order.setStatusLocale(statusLocaleDAO.getStatusLocaleByStatusIdAndLocaleId(order.getStatusId(), localeId));
        }
    }
}
