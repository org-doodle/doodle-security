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
package org.doodle.security.autoconfigure.server;

import org.doodle.broker.autoconfigure.client.BrokerClientAutoConfiguration;
import org.doodle.broker.client.BrokerClientRSocketRequester;
import org.doodle.security.autoconfigure.broker.BrokerClientSecurityAutoConfiguration;
import org.doodle.security.server.SecurityServerController;
import org.doodle.security.server.SecurityServerProperties;
import org.doodle.security.server.SecurityServerUserRepo;
import org.doodle.security.server.SecurityServerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;

@AutoConfiguration(
    after = {BrokerClientAutoConfiguration.class, BrokerClientSecurityAutoConfiguration.class})
@ConditionalOnClass({SecurityServerProperties.class, RSocketSecurity.class})
@ConditionalOnBean(BrokerClientRSocketRequester.class)
@EnableConfigurationProperties(SecurityServerProperties.class)
@EnableReactiveMongoAuditing
@EnableReactiveMongoRepositories(basePackageClasses = SecurityServerUserRepo.class)
public class SecurityServerAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public SecurityServerUserService securityServerUserService(
      @Autowired(required = false) SecurityServerUserRepo userRepo) {
    return new SecurityServerUserService(userRepo);
  }

  @Bean
  @ConditionalOnMissingBean
  public SecurityServerController securityServerController(SecurityServerUserService userService) {
    return new SecurityServerController();
  }
}
