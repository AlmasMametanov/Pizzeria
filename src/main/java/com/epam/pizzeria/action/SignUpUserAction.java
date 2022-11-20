package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.UserDAOImpl;
import com.epam.pizzeria.database.dao.interfaces.UserDAO;
import com.epam.pizzeria.entity.User;
import com.epam.pizzeria.util.encodePassword.Encoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.epam.pizzeria.action.ActionConstants.*;
import static com.epam.pizzeria.util.constants.ErrorConstants.*;
import static com.epam.pizzeria.util.constants.PageNameConstants.*;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;
import static com.epam.pizzeria.validator.Validator.*;

public class SignUpUserAction implements Action {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private User user = new User();
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User checkUser = (User) httpSession.getAttribute(USER);
        if (checkUser == null) {
            String firstName = request.getParameter(FIRST_NAME);
            if (firstName != null) {
                Boolean isValidatorPassed = true;
                user.setFirstName(firstName);
                user.setEmail(request.getParameter(EMAIL));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    user.setBirthday(dateFormat.parse(request.getParameter(BIRTHDAY)));
                } catch (ParseException e) {
                    logger.error(e);
                    throw new RuntimeException(e);
                }
                user.setPhoneNumber(request.getParameter(PHONE_NUMBER));
                user.setAddress(request.getParameter(ADDRESS));
                isValidatorPassed = validators(isValidatorPassed, request);
                if (isValidatorPassed){
                    user.setPassword(Encoder.encodePassword(request.getParameter(PASSWORD)));
                    userDAO.insertUser(user);
                    request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
                } else {
                    request.getRequestDispatcher(SIGNUP_JSP).forward(request, response);
                }
            } else {
                response.sendRedirect(PAGE_NOT_FOUND_ACTION);
            }
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }

    }

    private Boolean validators(Boolean isValidatorPassed, HttpServletRequest request) {
        if (!validateEmail(user.getEmail())) {
            request.setAttribute(EMAIL_FORMAT_INCORRECT, ERROR_EMAIL);
            isValidatorPassed = false;
        }
        if (!validatePhoneNumber(user.getPhoneNumber())) {
            request.setAttribute(PHONE_FORMAT_INCORRECT, ERROR_PHONE_NUMBER);
            isValidatorPassed = false;
        }
        if (!validatePassword(request.getParameter(PASSWORD))) {
            request.setAttribute(PASSWORD_FORMAT_INCORRECT, ERROR_PASSWORD);
            isValidatorPassed = false;
        }
        if (!isValidatorPassed)
            return false;
        return true;
    }
}
