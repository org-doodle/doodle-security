/*
 * Copyright (c) 2022-present Doodle. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.doodle.security.autoconfigure.broker;

import org.doodle.broker.autoconfigure.client.BrokerClientAutoConfiguration;
import org.doodle.broker.client.BrokerClientRSocketConnectorCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.rsocket.RSocketSecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.rsocket.core.PayloadSocketAcceptorInterceptor;
import org.springframework.security.rsocket.core.SecuritySocketAcceptorInterceptor;

@AutoConfiguration(
    before = BrokerClientAutoConfiguration.class,
    after = RSocketSecurityAutoConfiguration.class)
@ConditionalOnClass(RSocketSecurity.class)
public class BrokerClientSecurityAutoConfiguration {

  @Bean
  public BrokerClientRSocketConnectorCustomizer brokerClientSecuritySocketAcceptorInterceptor(
      SecuritySocketAcceptorInterceptor interceptor) {
    return connector -> connector.interceptors(registry -> registry.forSocketAcceptor(interceptor));
  }

  @Bean
  @ConditionalOnMissingBean
  public PayloadSocketAcceptorInterceptor brokerClientPayloadSocketAcceptorInterceptor(
      RSocketSecurity rSocketSecurity) {
    return rSocketSecurity
        .authorizePayload(authorize -> authorize.setup().permitAll().anyRequest().permitAll())
        .build();
  }
}
