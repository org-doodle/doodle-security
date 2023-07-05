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
package org.doodle.security.autoconfigure.client;

import com.vaadin.flow.router.RouterLink;
import org.doodle.boot.vaadin.views.ComponentSupplier;
import org.doodle.broker.autoconfigure.client.BrokerClientAutoConfiguration;
import org.doodle.broker.client.BrokerClientRSocketRequester;
import org.doodle.security.autoconfigure.broker.BrokerClientSecurityAutoConfiguration;
import org.doodle.security.client.*;
import org.doodle.security.vaadin.views.SecurityVaadinUsersView;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;

@AutoConfiguration(
    after = {BrokerClientAutoConfiguration.class, BrokerClientSecurityAutoConfiguration.class})
@ConditionalOnClass({SecurityClientProperties.class, RSocketSecurity.class})
@ConditionalOnBean(BrokerClientRSocketRequester.class)
@EnableConfigurationProperties(SecurityClientProperties.class)
@ConditionalOnProperty(prefix = SecurityClientProperties.PREFIX, name = "enabled")
public class SecurityClientAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public SecurityClientMapper securityClientMapper() {
    return new SecurityClientMapper();
  }

  @Bean
  @ConditionalOnMissingBean
  public SecurityClient securityClient(
      BrokerClientRSocketRequester requester, SecurityClientProperties properties) {
    return new BrokerSecurityClientImpl(requester, properties);
  }

  @Bean
  @ConditionalOnMissingBean
  public SecurityClientUserService securityClientUserService(SecurityClientMapper mapper) {
    return new SecurityClientUserService(mapper);
  }

  @Bean
  public ComponentSupplier securityComponentSupplier() {
    return (authenticationContext) -> new RouterLink("权限管理", SecurityVaadinUsersView.class);
  }
}
