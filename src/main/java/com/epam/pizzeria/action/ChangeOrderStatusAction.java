package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.OrderDAOImpl;
import com.epam.pizzeria.database.dao.interfaces.OrderDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.pizzeria.action.ActionConstants.*;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;

public class ChangeOrderStatusAction implements Action {
    private OrderDAO orderDAO = new OrderDAOImpl();
    private ActionFactory actionFactory = ActionFactory.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long statusId = Long.parseLong(request.getParameter(STATUS_ID));
        Long orderId = Long.parseLong(request.getParameter(ORDER_ID));
        orderDAO.updateOrderStatusByOrderId(statusId, orderId);
        Long userId = Long.parseLong(request.getParameter(USER_ID));
        request.setAttribute(USER_ID, userId);
        actionFactory.getAction(GET_ORDERS_ACTION).execute(request, response);
    }
}
