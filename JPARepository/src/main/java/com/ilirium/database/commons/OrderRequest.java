package com.ilirium.database.commons;

import com.mysema.query.types.OrderSpecifier;

/**
 *
 * @author dpoljak
 * @param <T>
 */
public abstract class OrderRequest<T> {

    //ASC or DESC
    protected Order order = Order.ASC;
    protected T sortColumn;

    public enum Order {
        ASC, DESC;

        public static OrderRequest.Order resolve(String value) {
            for (Order orderValue : Order.values()) {
                if (orderValue.name().equalsIgnoreCase(value)) {
                    return orderValue;
                }
            }
            return Order.ASC;
        }
    }

    public final OrderSpecifier getSpecifier() {
        for (Sortable sortColumnValue : getSortColumnValues()) {
            if (sortColumn == sortColumnValue) {
                return getSpecifierFromSortColumn(sortColumnValue);
            }
        }
        return getSpecifierFromSortColumn(getDefaultSortColumn());
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
