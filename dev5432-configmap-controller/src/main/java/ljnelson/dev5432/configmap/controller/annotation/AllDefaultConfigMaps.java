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

import java.io.Closeable; // for javadoc only

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.AnnotationLiteral;

import javax.inject.Qualifier;

import io.fabric8.kubernetes.api.model.HasMetadata; // for javadoc only
import io.fabric8.kubernetes.api.model.KubernetesResourceList; // for javadoc only

import io.fabric8.kubernetes.client.KubernetesClient; // for javadoc only
import io.fabric8.kubernetes.client.Watcher; // for javadoc only

import io.fabric8.kubernetes.client.dsl.Listable; // for javadoc only
import io.fabric8.kubernetes.client.dsl.VersionWatchable; // for javadoc only
import io.fabric8.kubernetes.client.dsl.Watchable; // for javadoc only

import org.microbean.kubernetes.controller.cdi.annotation.Added; // for javadoc only
import org.microbean.kubernetes.controller.cdi.annotation.Deleted; // for javadoc only
import org.microbean.kubernetes.controller.cdi.annotation.KubernetesEventSelector;
import org.microbean.kubernetes.controller.cdi.annotation.Modified; // for javadoc only

/**
 * A {@linkplain Qualifier qualifier annotation} that itself is
 * qualified with {@link KubernetesEventSelector} allowing it to pair
 * relevant producer and observer methods so that certain {@code
 * ConfigMap} resources can be observed.
 *
 * <h2>Learning Notes</h2>
 *
 * <p>Note that this annotation is annotated with both {@link
 * Qualifier}&mdash;making it a <a
 * href="http://docs.jboss.org/cdi/spec/2.0/cdi-spec.html#qualifiers"
 * target="_parent">CDI qualifier</a>&mdash;and {@link
 * KubernetesEventSelector}&mdash;allowing it to be recognized by the
 * <a
 * href="https://microbean.github.io/microbean-kubernetes-controller-cdi/"
 * target="_parent">microBean Kubernetes Controller CDI</a>
 * framework.</p>
 *
 * <p>Because it "is a" {@link KubernetesEventSelector}, you apply
 * this annotation to two things:</p>
 *
 * <ol>
 *
 * <li>To an <a
 * href="http://docs.jboss.org/cdi/spec/2.0/cdi-spec.html#observer_methods"
 * target="_parent">observer method</a>'s <a
 * href="http://docs.jboss.org/cdi/spec/2.0/cdi-spec.html#observer_method_event_parameter"
 * target="_parent">event parameter</a> that is an instance of {@link
 * HasMetadata}.  This identifies your observer method to the <a
 * href="https://microbean.github.io/microbean-kubernetes-controller-cdi/"
 * target="_parent">microBean Kubernetes Controller CDI</a> framework
 * and allows it to deliver Kubernetes events to it.  An observer
 * method whose event parameter is annotated in this
 * way&mdash;together with {@link Added}, {@link Modified} or {@link
 * Deleted} qualifier annotations&mdash;constitutes a <em>Kubernetes
 * event observer method</em>.</li>
 *
 * <li>To a bean&mdash;most commonly to a <a
 * href="http://docs.jboss.org/cdi/spec/2.0/cdi-spec.html#producer_method"
 * target="_parent">producer method</a>&mdash;that can be assigned to
 * a reference that can be described by the following type
 * declaration: {@code <X extends }{@link Listable}{@code <?
 * extends}{@link KubernetesResourceList}{@code > & }{@link
 * VersionWatchable}{@code <? extends }{@link Closeable}{@code ,
 * }{@link Watcher}{@code <? extends }{@link HasMetadata}{@code >>>}.
 * This bean is known as a <em>Kubernetes event selector</em>: it is
 * something whose {@link Listable#list() list()} method and {@link
 * Watchable#watch(Object) watch(W)} method will be called to select what
 * Kubernetes events will get delivered to your observer method.
 * While that type declaration looks complicated, it conveniently
 * describes the return types of most of the {@linkplain
 * KubernetesClient methods on <code>KubernetesClient</code>}.</li>
 *
 * </ol>
 *
 * <p>When you "connect" a Kubernetes event selector with a Kubernetes
 * event observer method using this annotation, then events produced
 * by the Kubernetes event selector will be delivered to the
 * Kubernetes event observer method.  If you "connect" a different
 * Kubernetes event selector with a different Kubernetes event
 * observer method using another {@link
 * KubernetesEventSelector}-annotated qualifier annotation, then
 * <em>that</em> Kubernetes event selector will be used to deliver
 * Kubernetes events to <em>that</em> Kubernetes event observer
 * method.</p>
 *
 * @see KubernetesEventSelector
 */
@Documented
@KubernetesEventSelector
@Qualifier
@Retention(value = RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE })
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
