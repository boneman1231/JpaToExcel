package org.redcenter.export.annotation;

public @interface ExcelColumn {

    String name() default "";
    int order() default -1;
}
