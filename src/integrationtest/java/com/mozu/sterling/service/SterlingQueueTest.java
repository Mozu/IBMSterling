package com.mozu.sterling.service;


import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

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
		setting.setInventoryDestinationName("q_agent");
		setting.setDestinationType(DestinationTypeEnum.QUEUE.destinationName());

		Integer tenantId = new Integer(15148);
		Integer siteId = new Integer(21456);

		try {
			jmsResource = new JmsResource(
					directConnectionStrategy.getJmsResourceSettings(setting,
							tenantId, siteId));
		} catch (JMSException e) {
			logger.error("Failed getting jms resources.", e);
		}
	}

	@Test
	public void readMessagesFromQueue() throws Exception {

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


	}
}
