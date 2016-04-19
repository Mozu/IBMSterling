package com.mozu.sterling.jmsUtil;

import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jms.Destination;
import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.model.Setting;

/**
 * A simple concurrent hash map keeps track of jms resources per tenant.
 *
 */
@Component
public class JmsConnectionCache {
	@Autowired
	private ConfigHandler configHandler;

	@Autowired
	@Qualifier("directJmsStrategy")
	private JmsConnectionStrategy directConnectionStrategy;

	private ConcurrentHashMap<Integer, JmsResource> jmsResourceMap;

	public JmsTemplate getTemplate(Integer tenantId, Integer siteId) throws Exception {
		JmsResource resource = getResource(tenantId, siteId);

		if (resource != null) {
			return resource.getJmsTemplate();
		} else {
			throw new RuntimeException("No jms settings configured for tenant "
					+ tenantId);
		}
	}

	public Destination getDefaultDestination(Integer tenantId, Integer siteId) throws Exception {
		JmsResource resource = getResource(tenantId, siteId);

		if (resource != null) {
			return resource.getCreateOrderDestination();
		} else {
			throw new RuntimeException("No jms settings configured for tenant "
					+ tenantId);
		}
	}

	/**
	 *
	 * @param tenantId
	 * @param siteId
	 * @param listener
	 * @return True if the listener is off, otherwise false.
	 */
	public boolean turnOffListener(Integer tenantId, Integer siteId) throws Exception {
		JmsResource resource = getResource(tenantId, siteId);
        boolean isListening = false;

		if (resource != null) {
			if (resource.isListening()) {
				resource.stopListening();
				jmsResourceMap.remove(resource);
			} 
			isListening = resource.isListening();
		}

		return !isListening;
	}

    /**
    *
    * @param tenantId
    * @param siteId
    * @param listener
    * @return True if the listener is on, otherwise false.
    */
   public boolean turnOnListener(Integer tenantId, Integer siteId) throws Exception {
       JmsResource resource = getResource(tenantId, siteId);
       boolean isListening = false;
       
       if (resource != null) {
           if (!resource.isListening()) {
               resource.startListening();
           }
           isListening = resource.isListening();
       }

       return isListening;
   }

   @PostConstruct
	public void postConstruct() {
		jmsResourceMap = new ConcurrentHashMap<Integer, JmsResource>();
	}

	@PreDestroy
	public void preDestroy() {
		for (JmsResource resource : jmsResourceMap.values()) {
			resource.close();
		}
	}

	protected JmsResource getResource(Integer tenantId, Integer siteId) throws Exception {
		JmsResource resource = jmsResourceMap.get(tenantId);

		if (resource == null) {
			Setting setting = configHandler.getSetting(tenantId);
			resource = jmsResourceMap.putIfAbsent(tenantId,
					createResource(tenantId, siteId, setting));
		} else {
			resource.addSite(siteId);
		}

		return resource;
	}

	protected JmsResource createResource(Integer tenantId, Integer siteId, Setting setting)
			throws JMSException {
		JmsConnectionStrategyEnum connectionStrategyType = JmsConnectionStrategyEnum
				.from(setting.getConnectionStrategy());
		JmsResource jmsResource = null;

		switch (connectionStrategyType) {
		case DIRECT:

			jmsResource = new JmsResource(directConnectionStrategy.getJmsResourceSettings(setting, tenantId, siteId));
			break;
		case WEBSPHEREMQ:
			// TODO needs implementation
			break;
		case JNDI:
			// TODO needs implementation
			break;
		}

		return jmsResource;
	}
}
