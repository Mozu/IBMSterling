package com.mozu.sterling.service;

import com.mozu.sterling.handler.ConfigHandler;

/**
 * Receives jms messages for processing. A new class is needed so that it can
 * create a unique durable subscription. Not thread safe as it is tied to a
 * tenant.
 *
 */
public class UpdateSterlingToMozuOrderMessageListener extends
		NewSterlingToMozuOrderMessageListener {
	public UpdateSterlingToMozuOrderMessageListener(Integer tenantId, Integer siteId,
			ConfigHandler configHandler, OrderService orderService) {
		super(tenantId, siteId, configHandler, orderService);
	}
}
