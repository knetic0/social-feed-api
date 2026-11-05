package com.mehmetsolak.social.infrastructure.persistence.user;

import com.mehmetsolak.social.application.features.follow.ports.UserLookupPort;
import com.mehmetsolak.social.domain.user.repository.UserRepository;
import com.mehmetsolak.social.domain.user.vobj.UserId;
import org.springframework.stereotype.Component;

@Component
public class UserLookupImpl implements UserLookupPort {

    private final UserRepository userRepository;

    public UserLookupImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean exists(UserId userId) {
        return userRepository.existsById(userId);
    }
}
