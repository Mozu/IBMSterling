package com.mozu.sterling.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;

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
	ApplicationContext applicationContext;

	@Autowired
	private MessageService messageService;

	@Autowired
	@Qualifier("directJmsStrategy")
	private JmsConnectionStrategy directConnectionStrategy;

	private Setting setting;
	private JmsResource jmsResource;

	@Before
	public void setup() {
		setting = new Setting();
		setting.setProviderEndpoint("50.23.47.110:7276:BootstrapBasicMessaging");
		setting.setBusName("mozuJMS");
		setting.setDestinationName("t_ordercreate");
		setting.setInboundDestinationName("t_ordercreate");

		Integer tenantId = new Integer(15148);

		DefaultMessageListener listener = applicationContext
				.getBean(DefaultMessageListener.class);

		try {

			jmsResource = new JmsResource(
					directConnectionStrategy.getConnectionFactory(setting),
					directConnectionStrategy.getOutboundDestination(setting),
					directConnectionStrategy.getInboundDestination(setting),
					listener, DestinationTypeEnum.from(setting
							.getDestinationType()));
		} catch (JMSException e) {
			logger.error("Failed getting jms resources.", e);
		}
	}

	@Test
	public void readOrderFromQueue() throws Exception {

		 if (!jmsResource.isListening()) {
		 jmsResource.startListening();
		 }

		Thread.sleep(10000); // wait for the listener to process the message
	}
}
