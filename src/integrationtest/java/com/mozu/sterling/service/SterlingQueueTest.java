package com.mozu.sterling.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;

import com.ibm.websphere.sib.api.jms.JmsConnectionFactory;
import com.ibm.websphere.sib.api.jms.JmsFactoryFactory;
import com.ibm.websphere.sib.api.jms.JmsTopic;
import com.mozu.sterling.jmsUtil.DestinationTypeEnum;
import com.mozu.sterling.jmsUtil.JmsConnectionStrategy;
import com.mozu.sterling.jmsUtil.JmsResource;
import com.mozu.sterling.model.Setting;
import com.mozu.sterling.service.MessageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/servlet-context.xml" })
public class SterlingQueueTest {
	private static final Logger logger = LoggerFactory
			.getLogger(SterlingQueueTest.class);

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private MessageService messageService;

	@Autowired
	private DefaultMessageListener defaultMessageListener;

	@Autowired
	@Qualifier("directJmsStrategy")
	private JmsConnectionStrategy directConnectionStrategy;

	private Setting setting;
	private JmsResource jmsResource;

	@Before
	public void setup() {
		setting = new Setting();
		setting.setProviderEndpoint("50.23.47.110:7276:BootstrapBasicMessaging");

		//topic setup
		/*
		setting.setBusName("mozuJMS");
		setting.setCreateOrderDestinationName("t_ordercreate");
		setting.setUpdateOrderDestinationName("t_orderupdate");
		setting.setDestinationType(DestinationTypeEnum.TOPIC.destinationName());
		setting.setSubscriptionHome("smcfs94Node01.server1-mozuJMS");
		*/

		//queue setup
		setting.setBusName("JMSSterlingBus");
		setting.setCreateOrderDestinationName("q_ordercreate");
		setting.setUpdateOrderDestinationName("q_orderupdate");
		setting.setDestinationType(DestinationTypeEnum.QUEUE.destinationName());

		Integer tenantId = new Integer(15148);

		try {
			jmsResource = new JmsResource(
					directConnectionStrategy.getJmsResourceSettings(setting,
							tenantId));
		} catch (JMSException e) {
			logger.error("Failed getting jms resources.", e);
		}
	}

	@Test
	public void readOrderFromQueue() throws Exception {

		if (!jmsResource.isListening()) {
			jmsResource.startListening();
		}

		//Write a message

//		JmsTemplate template = jmsResource.getJmsTemplate();
//		template.send(jmsResource.getUpdateOrderDestination(), new MessageCreator() {
//
//			@Override
//			public Message createMessage(Session session) throws JMSException {
//				return session.createTextMessage("New test message");
//			}
//
//		});

		Thread.sleep(30000); // wait for the listener to process the message

//		String clientId = null;
//		try {
//			clientId = InetAddress.getLocalHost().getHostName();
//		} catch (UnknownHostException e) {
//			clientId = "unknownhost";
//		}

//		JmsFactoryFactory jmsFactoryFactory = JmsFactoryFactory.getInstance();
//		JmsTopic topic = jmsFactoryFactory.createTopic("t_ordercreate");
//
//		ConnectionFactory connectionFactory = jmsResource.getConnectionFactory();
//		JmsConnectionFactory sterlingCF = (JmsConnectionFactory)((SingleConnectionFactory)connectionFactory).getTargetConnectionFactory();
//		sterlingCF.setClientID(clientId);
//		sterlingCF.setDurableSubscriptionHome("smcfs94Node01.server1-mozuJMS");
//
//		Connection connection = connectionFactory.createConnection();
//
//		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//
//		connection.start();
//
//
//		session.createDurableSubscriber(topic, "testSubscription");
//
//		JmsTemplate template = jmsResource.getJmsTemplate();
//
//		logger.info("Subscription established");
//
//		connection.stop();
//		session.close();
//		connection.close();
	}
}
