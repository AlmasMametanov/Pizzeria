package com.epam.pizzeria.action;

import java.util.HashMap;
import java.util.Map;

import static com.epam.pizzeria.action.ActionConstants.*;

public class ActionFactory {
    private static final Map<String, Action> ACTION_MAP = new HashMap<>();
    private static final ActionFactory ACTION_FACTORY = new ActionFactory();

    static {
        ACTION_MAP.put(SIGNUP_ACTION, new SignUpUserAction());
        ACTION_MAP.put(CHANGE_LOCALE_ACTION, new ChangeLocaleAction());
        ACTION_MAP.put(LOGIN_ACTION, new LogInUserAction());
        ACTION_MAP.put(LOGOUT_ACTION, new LogOutUserAction());
        ACTION_MAP.put(GET_BASKET_ACTION, new GetBasketAction());
        ACTION_MAP.put(CHANGE_PRODUCT_COUNT_IN_BASKET_ACTION, new ChangeProductCountInBasketAction());
        ACTION_MAP.put(DELETE_PRODUCT_FROM_BASKET_ACTION, new DeleteBasketAction());
        ACTION_MAP.put(CREATE_NEW_PRODUCT_ACTION, new CreateProductAction());
        ACTION_MAP.put(CREATE_ADDITIONAL_INGREDIENT_ACTION, new CreateAdditionalIngredientAction());
        ACTION_MAP.put(CREATE_BASKET_ACTION, new CreateBasketAction());
        ACTION_MAP.put(SELECT_PRODUCT_SIZE_ACTION, new SelectProductSizeAction());
        ACTION_MAP.put(CHECKOUT_ORDER_ACTION, new CheckoutOrderAction());
        ACTION_MAP.put(CHANGE_USER_ADDRESS_IN_CHECKOUT_PAGE_ACTION, new ChangeUserAddressInCheckoutPageAction());
        ACTION_MAP.put(CHANGE_USER_PHONE_NUMBER_IN_CHECKOUT_PAGE_ACTION, new ChangeUserPhoneNumberInCheckoutPageAction());
        ACTION_MAP.put(CREATE_ORDER_ACTION, new CreateOrderAction());
        ACTION_MAP.put(PAGE_NOT_FOUND_ACTION, new PageNotFoundAction());
        ACTION_MAP.put(GET_ALL_USER_ACTION, new GetAllUserAction());
        ACTION_MAP.put(GET_USER_BY_ID_ACTION, new GetUserByIdAction());
        ACTION_MAP.put(CREATE_NEW_CATEGORY_ACTION, new CreateCategoryAction());
        ACTION_MAP.put(CHANGE_USER_BAN_STATUS_ACTION, new ChangeUserBanStatusAction());
        ACTION_MAP.put(GET_ORDERS_ACTION, new GetOrdersAction());
        ACTION_MAP.put(CHANGE_ORDER_STATUS_ACTION, new ChangeOrderStatusAction());
        ACTION_MAP.put(DELETE_CATEGORY_ACTION, new DeleteCategoryAction());
        ACTION_MAP.put(CHANGE_PRODUCT_ACTIVE_STATUS_ACTION, new ChangeProductActiveStatusAction());
        ACTION_MAP.put(GET_ALL_PRODUCT_ACTION, new GetAllProductAction());
        ACTION_MAP.put(CHANGE_PRODUCT_ACTION, new ChangeProductAction());
    }

    public static ActionFactory getInstance() {
        return ACTION_FACTORY;
    }

    public Action getAction(String requestString) {
        Action action = null;

        for (Map.Entry<String, Action> pair : ACTION_MAP.entrySet()) {
            if (requestString.equalsIgnoreCase(pair.getKey())) {
                action = pair.getValue();
            }
        }

        if (action == null) {
            if (action == null) {
                action = ACTION_MAP.get(PAGE_NOT_FOUND_ACTION);
            }
        }

        return action;
    }

}
