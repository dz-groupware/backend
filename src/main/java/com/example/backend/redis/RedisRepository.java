package com.example.backend.redis;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<RedisToken,String> {
  // @Indexed 사용한 필드만 가능
  Optional<RedisToken> findByJwt(String username);
}