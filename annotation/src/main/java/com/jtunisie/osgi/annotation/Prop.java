/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
@Target(ElementType.ANNOTATION_TYPE)
public @interface Prop {

    String key();

    String value();
}
