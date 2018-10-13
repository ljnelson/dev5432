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
package ljnelson.dev5432.configmap.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;

import javax.enterprise.event.Observes;

import io.fabric8.kubernetes.api.model.ConfigMap;

import ljnelson.dev5432.configmap.controller.annotation.AllDefaultConfigMaps;

import org.microbean.kubernetes.controller.cdi.annotation.Added;
import org.microbean.kubernetes.controller.cdi.annotation.KubernetesEventSelector; // for javadoc only

/**
 * A class housing <a
 * href="http://docs.jboss.org/cdi/spec/2.0/cdi-spec.html#observer_methods"
 * target="_parent">observer methods</a>.
 *
 * <h2>Learning Notes</h2>
 *
 * <p>This class houses two <a
 * href="http://docs.jboss.org/cdi/spec/2.0/cdi-spec.html#observer_methods"
 * target="_parent">observer methods</a>:</p>
 *
 * <ol>
 *
 * <li>{@link #onStartup(Object)}: This observer method {@linkplain
 * Observes observes} the {@linkplain Initialized initialization} of
 * the {@linkplain ApplicationScoped application scope}&mdash;that is,
 * it is notified when the CDI ecosystem has successfully started.
 * You can think of it as a kind of {@code main} method.  Its sole
 * parameter can be safely ignored.  It is included in this class
 * solely so that you can see exactly when the CDI ecosystem has come
 * up (it merely logs the fact that CDI has started).</li>
 *
 * <li>{@link #onConfigMapAddition(ConfigMap)}: This <em>Kubernetes
 * event observer method</em> is an observer method that will be
 * notified with {@link ConfigMap} "events" that describe the
 * {@linkplain Added addition} of a {@code ConfigMap} to a Kubernetes
 * cluster.  The production of these events is governed and filtered
 * by (in this application) the {@link
 * AllDefaultConfigMapsProducer#produceAllDefaultConfigMaps(KubernetesClient)}
 * producer method.  The events that result indirectly from this
 * Kubernetes event selector method are "linked" with the {@link
 * #onConfigMapAddition(ConfigMap)} Kubernetes event observer method
 * by way of the {@link AllDefaultConfigMaps} Kubernetes event
 * selector annotation.</li>
 *
 * </ol>
 *
 * <p>This class is not {@code public} and does not have to be.  None
 * of its methods are {@code public} either.  The CDI specification
 * guarantees that they will be called when appropriate CDI events are
 * fired, regardless of their accessibility settings.</p>
 *
 * @see #onStartup(Object)
 *
 * @see #onConfigMapAddition(ConfigMap)
 */
@ApplicationScoped
final class AllDefaultConfigMapsEventHandler {


  /*
   * Instance fields.
   */
  
  
  /**
   * A {@link Logger} used by this {@link
   * AllDefaultConfigMapsEventHandler}.
   *
   * <p>This field is never {@code null}.</p>
   *
   * @see #AllDefaultConfigMapsEventHandler()
   */
  private final Logger logger;


  /*
   * Constructors.
   */

  
  /**
   * Creates a new {@link AllDefaultConfigMapsEventHandler}.
   */
  private AllDefaultConfigMapsEventHandler() {
    super();
    this.logger = Logger.getLogger(this.getClass().getName());
  }

  /**
   * An <a
   * href="http://docs.jboss.org/cdi/spec/2.0/cdi-spec.html#observer_methods"
   * target="_parent">observer method</a> that {@linkplain Observes
   * observes} the {@linkplain Initialized initialization} of the
   * {@linkplain ApplicationScoped application scope}, and is thus
   * notified when the CDI container comes up successfully.
   *
   * @param event an {@link Object} describing the {@linkplain
   * Initialized initialization} of the {@linkplain ApplicationScoped
   * application scope}; ignored
   */
  private final void onStartup(@Observes @Initialized(ApplicationScoped.class) final Object event) {
    final String cn = this.getClass().getName();
    final String mn = "onStartup";
    if (this.logger.isLoggable(Level.FINE)) {
      logger.logp(Level.FINE, cn, mn, "CDI is up");
    }
  }

  /**
   * A <em>Kubernetes event observer method</em>
   * that will be notified with {@link ConfigMap} "events" that
   * describe the {@linkplain Added addition} of a {@code ConfigMap}
   * to a Kubernetes cluster.
   *
   * <p>The production of these events is governed and filtered by (in
   * this application) the {@link
   * AllDefaultConfigMapsProducer#produceAllDefaultConfigMaps(KubernetesClient)}
   * producer method.  The events that result indirectly from that
   * Kubernetes event selector method are "linked" with this
   * Kubernetes event observer method by way of the {@link
   * AllDefaultConfigMaps} Kubernetes event selector annotation.</p>
   *
   * @param newConfigMap the {@link ConfigMap} that was added; will
   * not be {@code null}
   *
   * @see AllDefaultConfigMaps
   *
   * @see
   * AllDefaultConfigMapsProducer#produceAllDefaultConfigMaps(KubernetesClient)
   *
   * @see KubernetesEventSelector
   *
   * @see Added
   */
  private final void onConfigMapAddition(@Observes @Added @AllDefaultConfigMaps final ConfigMap newConfigMap) {
    final String cn = this.getClass().getName();
    final String mn = "onConfigMapAddition";
    if (this.logger.isLoggable(Level.INFO)) {
      logger.logp(Level.INFO, cn, mn, "New ConfigMap added: {0}", newConfigMap);
    }
  }

}
