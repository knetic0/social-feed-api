package com.mehmetsolak.social.infrastructure.persistence.user;

import com.mehmetsolak.social.domain.user.model.User;
import com.mehmetsolak.social.domain.user.vobj.Handle;
import com.mehmetsolak.social.domain.user.vobj.PasswordHash;
import com.mehmetsolak.social.domain.user.vobj.UserId;
import org.springframework.stereotype.Component;

@Component
public final class UserMapper {

    public User toDomain(UserJpaEntity entity) {
        UserId userId = UserId.of(entity.getId());
        Handle handle = Handle.of(entity.getHandle());
        PasswordHash passwordHash = PasswordHash.of(entity.getPasswordHash());
        return User.rehydrate(
                userId,
                handle,
                passwordHash,
                entity.isCelebrity(),
                entity.getCreatedAt()
        );
    }

    public UserJpaEntity toEntity(User user) {
        return new UserJpaEntity(
                user.getId().value(),
                user.getHandle().value(),
                user.getPasswordHash().value(),
                user.isCelebrity(),
                user.getCreatedAt()
        );
    }
}
