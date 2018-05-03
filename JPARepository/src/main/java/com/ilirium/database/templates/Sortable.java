package com.ilirium.database.templates;

import com.mysema.query.types.expr.ComparableExpressionBase;

public interface Sortable {

    ComparableExpressionBase getSortColumn();

    default String stringValue() {
        String stringValue = getSortColumn().toString().toLowerCase();
        return stringValue.substring(stringValue.indexOf(".")+1).replace(".", "");
    }
}
