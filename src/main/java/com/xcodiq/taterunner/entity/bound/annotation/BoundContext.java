package com.xcodiq.taterunner.entity.bound.annotation;

import com.xcodiq.taterunner.entity.bound.type.BoundType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BoundContext {

	BoundType boundType();

	String boundPath() default "";
}
