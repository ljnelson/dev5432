/* -*- mode: Java; c-basic-offset: 2; indent-tabs-mode: nil; coding: utf-8-unix -*-
 *
 * Copyright © 2018 Laird Nelson.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package ljnelson.dev5432.configmap.controller.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.AnnotationLiteral;

import javax.inject.Qualifier;

import org.microbean.kubernetes.controller.cdi.annotation.KubernetesEventSelector;

/**
 * A {@linkplain Qualifier qualifier annotation} that itself is
 * qualified with {@link KubernetesEventSelector} allowing it to pair
 * relevant producer and observer methods so that certain {@code
 * ConfigMap} resources can be observed.
 *
 * @see KubernetesEventSelector
 */
@Documented
@KubernetesEventSelector
@Qualifier
@Retention(value = RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.PARAMETER })
public @interface AllDefaultConfigMaps {


  /*
   * Inner and nested classes.
   */
  

  /**
   * An {@link AnnotationLiteral} that implements {@link AllDefaultConfigMaps}.
   */
  public static class Literal extends AnnotationLiteral<AllDefaultConfigMaps> implements AllDefaultConfigMaps {

    private static final long serialVersionUID = 1L;

    /**
     * A non-{@code null} {@link AllDefaultConfigMaps} literal.
     */
    public static final AllDefaultConfigMaps INSTANCE = new Literal();

    private Literal() {
      super();
    }
    
  }

}
