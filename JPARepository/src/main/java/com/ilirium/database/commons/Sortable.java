package com.ilirium.database.commons;

import com.mysema.query.types.expr.ComparableExpressionBase;

/**
 *
 * @author dpoljak
 */
public interface Sortable {

    ComparableExpressionBase getSortColumn();

    String stringValue();
}
