package com.hanweb.jmp.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口缓存的注解
 * @author denganming
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InterfaceCache {

	/**
	 * 缓存时间  不写默认为10分钟  配置文件可配置默认的缓存时间
	 */
	public int time() default -1;
}
