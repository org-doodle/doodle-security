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
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SecurityServerUserRepo
    extends UserRepoCustom, ReactiveMongoRepository<SecurityServerUserEntity, String> {}

interface UserRepoCustom {
  Mono<SecurityServerUserEntity> findByUsername(String username);
}

@RequiredArgsConstructor
class UserRepoCustomImpl implements UserRepoCustom {
  private final ReactiveMongoTemplate mongoTemplate;

  @Override
  public Mono<SecurityServerUserEntity> findByUsername(String username) {
    return Mono.just(Criteria.where("username").is(username))
        .map(new Query()::addCriteria)
        .flatMap(query -> this.mongoTemplate.findOne(query, SecurityServerUserEntity.class));
  }
}
