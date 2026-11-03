package com.mehmetsolak.social.infrastructure.messaging.consumers;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.features.feed.commands.distribute.DistributePostCommand;
import com.mehmetsolak.social.domain.post.events.PostCreatedEvent;
import com.mehmetsolak.social.infrastructure.messaging.RabbitMqConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class PostCreatedConsumer {

    private final ObjectMapper objectMapper;
    private final CommandHandler<DistributePostCommand, Void> distributePostCommandHandler;

    public PostCreatedConsumer(
            ObjectMapper objectMapper,
            CommandHandler<DistributePostCommand, Void> distributePostCommandHandler) {
        this.objectMapper = objectMapper;
        this.distributePostCommandHandler = distributePostCommandHandler;
    }

    @RabbitListener(
            queues = RabbitMqConstants.POST_CREATED_QUEUE
    )
    public void consume(String message) {
        PostCreatedEvent event = objectMapper.readValue(message, PostCreatedEvent.class);

        distributePostCommandHandler.execute(new DistributePostCommand(
                event.eventId(),
                event.postId(),
                event.authorId(),
                event.occurredAt()
        ));
    }
}
