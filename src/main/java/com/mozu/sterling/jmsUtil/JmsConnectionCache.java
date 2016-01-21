package com.mozu.sterling.jmsUtil;

import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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

	public JmsTemplate getTemplate(Integer tenantId) throws Exception {
		JmsResource resource = getResource(tenantId);

		if (resource != null) {
			return resource.getJmsTemplate();
		} else {
			throw new RuntimeException("No jms settings configured for tenant "
					+ tenantId);
		}
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

	protected JmsResource getResource(Integer tenantId) throws Exception {
		JmsResource resource = jmsResourceMap.get(tenantId);

		if (resource == null) {
			Setting setting = configHandler.getSetting(tenantId);
			resource = jmsResourceMap.putIfAbsent(tenantId,
					createResource(setting));
		}

		return resource;
	}

	protected JmsResource createResource(Setting setting) throws JMSException {
		JmsConnectionStrategyEnum connectionStrategyType = JmsConnectionStrategyEnum
				.from(setting.getConnectionStrategy());
		JmsResource jmsResource = null;

		switch (connectionStrategyType) {
		case DIRECT:
			jmsResource = new JmsResource(
					directConnectionStrategy.getConnectionFactory(setting),
					directConnectionStrategy.getDestination(setting));
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
