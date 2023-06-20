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
package org.doodle.security.client;

import java.util.function.Supplier;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;

public class SecurityClientUserService
    implements ReactiveUserDetailsService, UserDetailsService, ApplicationContextAware {
  private final SecurityClientMapper mapper;
  private Supplier<SecurityClientApi> clientApiSupplier;

  public SecurityClientUserService(SecurityClientMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public void setApplicationContext(@NonNull ApplicationContext context) throws BeansException {
    this.clientApiSupplier = () -> context.getBean(SecurityClientApi.class);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return findByUsername(username).block();
  }

  @Override
  public Mono<UserDetails> findByUsername(String username) {
    return this.clientApiSupplier
        .get()
        .pull(mapper.toRequest(username))
        .map(mapper::fromProtoOrThrow)
        .cast(UserDetails.class)
        .onErrorMap(e -> new UsernameNotFoundException(username, e));
  }
}
