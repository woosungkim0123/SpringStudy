package com.example.aop.member.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 런타임까지 유지
@Target(ElementType.TYPE) // 타입(클래스, 인터페이스, Enum)
public @interface ClassAop {
}
