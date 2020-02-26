package com.bridgelabz.fundoonotes.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

import com.bridgelabz.fundoonotes.utility.Util;

@Documented
@Pattern(regexp = Util.EMAIL_REGEX_PATTERN)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface ValidEmailId {
	String message()

	default "Please provide a valid email address";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}