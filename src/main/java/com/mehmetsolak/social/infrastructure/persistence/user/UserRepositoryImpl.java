package com.mehmetsolak.social.infrastructure.persistence.user;

import com.mehmetsolak.social.domain.user.model.User;
import com.mehmetsolak.social.domain.user.repository.UserRepository;
import com.mehmetsolak.social.domain.user.vobj.Handle;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository repository;
    private final UserMapper mapper;

    public UserRepositoryImpl(UserJpaRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<User> findByHandle(Handle handle) {
        return repository.findByHandle(handle.value()).map(mapper::toDomain);
    }
}
