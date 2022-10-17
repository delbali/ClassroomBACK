package it.itresources.springtut.springtutorial.socket;


import it.itresources.springtut.springtutorial.model.UserDetailImpl;
import it.itresources.springtut.springtutorial.security.JwtUtils;
import it.itresources.springtut.springtutorial.services.impl.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.security.Principal;
import java.util.Objects;


@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)

public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);
    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl customUserServiceDetails;

    private static final String BEARER = "Bearer";


    @Autowired
    public WebSocketConfig(JwtUtils jwtUtils, UserDetailsServiceImpl customUserServiceDetails) {
        this.jwtUtils = jwtUtils;
        this.customUserServiceDetails = customUserServiceDetails;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/chat").setAllowedOrigins("*");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(new ChannelInterceptorAdapter() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {

                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if (StompCommand.CONNECT.equals(accessor.getCommand())) {

                    String header = accessor.getFirstNativeHeader("Authorization");

                    logger.info("Header auth token: " + header);

                    String jwt = header.substring(BEARER.length() + 1);

                    logger.info("Token only : " + jwt);

                    if (StringUtils.hasText(jwt) && jwtUtils.validateJwtToken(jwt)) {
                        String username = jwtUtils.getUsernameFromToken(jwt);

                        UserDetailImpl user = (UserDetailImpl) customUserServiceDetails.loadUserByUsername(username);

                        Principal principal = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                        if (Objects.isNull(principal))
                            return null;

                        accessor.setUser(principal);
                    } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
                        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                        if (Objects.nonNull(authentication))
                            logger.info("Disconnected Auth : " + authentication.getName());
                        else
                            logger.info("Disconnected Sess : " + accessor.getSessionId());
                    }
                }
                return message;
            }
        });
    }
}
