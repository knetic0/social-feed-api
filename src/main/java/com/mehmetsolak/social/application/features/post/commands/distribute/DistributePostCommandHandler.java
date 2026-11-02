package com.mehmetsolak.social.application.features.post.commands.distribute;

import com.mehmetsolak.social.application.cqrs.CommandHandler;

public class DistributePostCommandHandler implements CommandHandler<DistributePostCommand, Void> {

    @Override
    public Void execute(DistributePostCommand command) {
        return null;
    }
}
