package com.mozu.sterling.service;

import org.springframework.stereotype.Component;

/**
 * A service that sends and receives JMS messages. 
 * 
 */
@Component
public class MessageService 
{
//    @Autowired
//    private JmsTemplate jmsTemplate;
//    @Value("${queue}")
//    private String destination;
//
//    /**
//     * Sends a message to a queue.
//     * 
//     * @param text Message text.
//     */
//    public void sendMessage(final String text) {
//        jmsTemplate.send(destination, new MessageCreator() {
//            public Message createMessage(Session session) throws JMSException {
//                return session.createTextMessage(text);
//            }
//        });
//    }
//    
//    /**
//     * Receives a message from a queue.
//     * 
//     * @return Message text.
//     * @throws JMSException
//     */
//    public String readMessage() throws JMSException {
//        String message = null;
//        
//        Message msg = jmsTemplate.receive(destination);
//        if(msg instanceof TextMessage) {
//            message = ((TextMessage) msg).getText();
//        }
//        
//        return message;
//    }
}