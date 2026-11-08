package com.mehmetsolak.social.infrastructure.persistence.user;

import com.mehmetsolak.social.domain.user.model.User;
import com.mehmetsolak.social.domain.user.repository.UserRepository;
import com.mehmetsolak.social.domain.user.vobj.Handle;
import com.mehmetsolak.social.domain.user.vobj.UserId;
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

    @Override
    public Optional<User> findById(UserId id) {
        return repository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public boolean existsByHandle(Handle handle) {
        return repository.existsByHandle(handle.value());
    }

    @Override
    public boolean existsById(UserId userId) {
        return repository.existsById(userId.value());
    }

    @Override
    public void save(User user) {
        repository.save(mapper.toEntity(user));
    }
}
