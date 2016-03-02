package com.mozu.sterling.jmsUtil;

import javax.jms.MessageListener;

/**
 * Adds visibility to tenant/site settings of the listener beyond consuming jms messages.

 *
 */
public interface TenantSiteMessageListener extends MessageListener{
	void addToSites(Integer siteId);
}
