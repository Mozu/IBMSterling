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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.ibm.ws.sib.comms.common.DirectConnectionImpl;
import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.jmsUtil.DestinationTypeEnum;
import com.mozu.sterling.jmsUtil.DirectWebsphereJmsStrategy;
import com.mozu.sterling.jmsUtil.JmsConnectionStrategy;
import com.mozu.sterling.jmsUtil.JmsResource;
import com.mozu.sterling.model.Setting;
import com.mozu.sterling.model.inventory.AvailabilityChange;
import com.mozu.sterling.model.inventory.Item;
import com.mozu.sterling.service.MessageService;

import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/resources/servlet-context.xml" })
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
	private OrderService orderService;

	@Autowired
	private InventoryService inventoryService;

	@Mock
	private ConfigHandler configHandler;

	private JmsConnectionStrategy directConnectionStrategy;
	private Setting setting;
	private JmsResource jmsResource;

	@Before
	public void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		Integer tenantId = new Integer(15148);
		Integer siteId = new Integer(21456);

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
		setting.setInventoryDestinationName("q_inventorychange");
		setting.setDestinationType(DestinationTypeEnum.QUEUE.destinationName());

		Map<String, String> shipMethodMap = new HashMap<String, String>();
		shipMethodMap.put("something", "something");

		setting.setShipMethodMap(shipMethodMap);

		Map<String, String> locationMap = new HashMap<String, String>();
		locationMap.put("AUS", "Auro_Store_1");
		locationMap.put("WRH001", "Aurora_WH1");

		setting.setLocationMap(locationMap);

		Map<String, String> siteMap = new HashMap<String, String>();
		siteMap.put(String.valueOf(siteId), "Aurora-Corp");

		setting.setSiteMap(siteMap);

		when(configHandler.getSetting(tenantId)).thenReturn(setting);

		directConnectionStrategy = new DirectWebsphereJmsStrategy(orderService, inventoryService, configHandler);

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

		Thread.sleep(30000); // wait for the listener to process the message


	}
}
