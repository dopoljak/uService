package com.ilirium.database.request;

import com.ilirium.database.templates.*;
import com.mysema.query.types.OrderSpecifier;

import java.util.*;

public abstract class OrderRequest<T> {

    //ASC or DESC
    protected Order order = Order.ASC;
    protected T sortColumn;

    public OrderRequest(Order order) {
        this.order = order;
    }

    public enum Order {
        ASC, DESC;

        public static OrderRequest.Order resolve(String value) {
            Optional<Order> order = Arrays.stream(Order.values()).filter(orderValue -> orderValue.name().equalsIgnoreCase(value)).findFirst();
            return order.orElse(Order.ASC);
        }
    }

    public final OrderSpecifier getSpecifier() {
        Sortable sortColumnValue = Arrays.stream(getSortColumnValues()).filter(scv -> scv == sortColumn).findFirst().orElse(getDefaultSortColumn());
        return getSpecifierFromSortColumn(sortColumnValue);
    }

    private OrderSpecifier getSpecifierFromSortColumn(Sortable sortColumn) {
        if (order.equals(Order.ASC)) {
            return sortColumn.getSortColumn().asc();
        }
        return sortColumn.getSortColumn().desc();
    }

    protected abstract Sortable[] getSortColumnValues();

    protected abstract Sortable getDefaultSortColumn();
}
