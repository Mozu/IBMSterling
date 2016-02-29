package com.mozu.sterling.service;


import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

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
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.mozu.sterling.jmsUtil.DestinationTypeEnum;
import com.mozu.sterling.jmsUtil.JmsConnectionStrategy;
import com.mozu.sterling.jmsUtil.JmsResource;
import com.mozu.sterling.model.Setting;
import com.mozu.sterling.model.inventory.AvailabilityChange;
import com.mozu.sterling.model.inventory.Item;
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
		setting.setInventoryDestinationName("t_inventory");
		setting.setDestinationType(DestinationTypeEnum.TOPIC.destinationName());
		setting.setSubscriptionHome("smcfs94Node01.server1-mozuJMS");
		*/

		//queue setup
		setting.setBusName("JMSSterlingBus");
		setting.setCreateOrderDestinationName("q_ordercreate");
		setting.setUpdateOrderDestinationName("q_orderupdate");
		setting.setInventoryDestinationName("q_agent");
		setting.setDestinationType(DestinationTypeEnum.QUEUE.destinationName());

		Map<String, String> locationMap = new HashMap<String, String>();
		locationMap.put("mozu_wh1", "sterling_node1");

		setting.setLocationMap(locationMap);

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

		//An order create/update is expected to be queued up form on sterling

		//Write an inventory message

		JmsTemplate template = jmsResource.getJmsTemplate();
		template.send(jmsResource.getInventoryDestination(), new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				AvailabilityChange availabilityChange = new AvailabilityChange();
				availabilityChange.setNode("sterling_node1");
				availabilityChange.setOnhandAvailableQuantity("101");

				Item item = new Item();
				item.setItemID("AuroraWMDRS-001");
				availabilityChange.setItem(item);

				String message = "";

				try {
					JAXBContext jaxbContext = JAXBContext
							.newInstance(com.mozu.sterling.model.inventory.AvailabilityChange.class);

					Marshaller marshaller = jaxbContext.createMarshaller();
					StringWriter stringWriter = new StringWriter();
					marshaller.marshal(availabilityChange, stringWriter);
					message = stringWriter.toString();

				} catch (JAXBException jaxbEx) {
					logger.error("Error getting jaxb context.");
				}

				return session.createTextMessage(message);
			}

		});

		Thread.sleep(30000); // wait for the listener to process the message


	}
}
