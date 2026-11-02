package com.mehmetsolak.social.infrastructure.messaging;

public final class RabbitMqConstants {

    private RabbitMqConstants() {}

    public static final String SOCIAL_EXCHANGE = "social.events";

    public static final String POST_CREATED_QUEUE = "post.created";

    public static final String POST_CREATED_ROUTING_KEY = "post.created";
}
