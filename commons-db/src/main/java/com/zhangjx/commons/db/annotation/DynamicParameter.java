package com.zhangjx.commons.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zhangjx.commons.db.enumeration.Operation;

@Target(value={ElementType.FIELD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface DynamicParameter {

	Operation operation() default Operation.EQUAL;
	
}
