package com.mehmetsolak.social.domain.user.repository;

import com.mehmetsolak.social.domain.user.model.User;
import com.mehmetsolak.social.domain.user.vobj.Handle;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByHandle(Handle handle);
    boolean existsByHandle(Handle handle);
    void save(User user);
}
