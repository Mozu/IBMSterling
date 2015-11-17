package com.mozu.sterling.service;

import static org.junit.Assert.assertEquals;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

//import com.ibm.websphere.sib.api.jms.JmsConnectionFactory;
//import com.ibm.websphere.sib.api.jms.JmsFactoryFactory;
/**
 * Unit tests for <code>MessageService</code>.
 *
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/servlet-context.xml"})
public class MessageSeviceTest
{
    @Autowired
    private MessageService messageService;
    private String message;
    
    /**
     * Test setup.
     */
    @Before
    public void setUp() {
        message = "TestSpringJMSMQ test message.";
    }
    
    /**
     * Test that sends a message to a queue.
     */
    @Test
    public void testSendMessage() {
//        messageService.sendMessage(message);
    }
    
    /**
     * Test that receives a message from a queue.
     * 
     * @throws JMSException
     */
    @Test
    public void testReadMessage() throws JMSException {
//        String readMessage = messageService.readMessage();
//        
//        assertEquals(readMessage, message);
    }
    
    @Test
    public void testNoSpringJMSConnection () throws Exception {
//        JmsFactoryFactory jmsFactoryFactory = JmsFactoryFactory.getInstance();
//        JmsConnectionFactory connectionFactory = jmsFactoryFactory.createConnectionFactory();
//        connectionFactory.setProviderEndpoints("iiop://50.23.47.110:2809");
//        connectionFactory.setBusName("mozuJMS");
//        Connection connection = connectionFactory.createConnection();
//        connection.start();
//        Destination destination = jmsFactoryFactory.createQueue("QUEUE1");
//        if (true) {
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//            MessageProducer producer = session.createProducer(destination);
//            TextMessage hello = session.createTextMessage("hello");
//            producer.send(hello);
//            session.close();
//        }
//        if (true) {
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//            MessageConsumer consumer = session.createConsumer(destination);
//            Message receive = consumer.receiveNoWait();
//            while (receive != null) {
//                if (receive instanceof TextMessage) {
//                    TextMessage textMessage = (TextMessage) receive;
//                    System.out.println("textMessage.getText() = " + textMessage.getText());
//                } else {
//                    System.out.println("message = " + receive.getClass().getName());
//                }
//                receive = consumer.receiveNoWait();
//            }
//            session.close();
//        }
//        connection.stop();
//        connection.close();
    }        
}