package com.mehmetsolak.social.domain.user.repository;

import com.mehmetsolak.social.domain.user.model.User;
import com.mehmetsolak.social.domain.user.vobj.Handle;
import com.mehmetsolak.social.domain.user.vobj.UserId;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByHandle(Handle handle);
    Optional<User> findById(UserId id);
    boolean existsByHandle(Handle handle);
    boolean existsById(UserId userId);
    void save(User user);
}
