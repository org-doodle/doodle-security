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
package org.doodle.security.server;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SecurityServerUserService implements ReactiveUserDetailsService {

  private final SecurityServerUserRepo userRepo;

  @Override
  public Mono<UserDetails> findByUsername(String username) {
    return this.userRepo
        .findByUsername(username)
        .onErrorMap(error -> new UsernameNotFoundException(String.format("找不到用户: %s", username)))
        .cast(UserDetails.class);
  }
}