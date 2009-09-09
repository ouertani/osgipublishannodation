/*
 * Publish.java
 * 
 * Date    :       Sep 8, 2009
 * Time    :       11:28:24 PM
 * Autor   :       Slim OUERTANI
 * Project :       JTUNISIE
 * Revision:       1
 * 
 * 
 * ---------+-------+-----------+-----------------------------------------
 * #        | Autor | Date      |Info
 * ---------+-------+-----------+-----------------------------------------
 * Creation |  SO   |08/09/2009 |  -  |
 * ---------+-------+-----------+-----------------------------------------
 */
package com.jtunisie.osgi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author slim ouertani
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Publish {

    String id();

    NamedInterface[] NamedInterfaces();

    Prop[] Props() default {};
}
