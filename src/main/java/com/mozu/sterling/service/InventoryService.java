package com.mozu.sterling.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mozu.api.ApiContext;
import com.mozu.api.contracts.productadmin.LocationInventoryAdjustment;
import com.mozu.api.resources.commerce.catalog.admin.products.LocationInventoryResource;
import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.handler.SterlingInventoryToMozuMapper;
import com.mozu.sterling.model.Setting;

/**
 * Service for reading, mapping, and sending Orders from to Mozu to Sterling.
 *
 * @author bob_hewett
 *
 */
@Service
public class InventoryService extends SterlingClient {
	private static final Logger logger = LoggerFactory
			.getLogger(InventoryService.class);

	public final static String ORDER_SERVICE_NAME = "createOrder";
	public final static String GET_ORDER_LIST_SERVICE_NAME = "getOrderList";
	public final static String GET_ORDER_SERVICE_NAME = "getOrderDetails";
	public final static String UPDATE_ORDER_SERVICE_NAME = "changeOrder";

	@Autowired
	SterlingClient sterlingClient;

	@Autowired
	ConfigHandler configHandler;

	@Autowired
	SterlingInventoryToMozuMapper sterlingInventoryToMozuMapper;

	public InventoryService() throws Exception {
		super();
	}

	/**
	 * Update inventory in Mozu from a Sterling inventory change event.
	 *
	 * @param sterlingOrder
	 * @return
	 */
	public boolean updateInventory(
			ApiContext apiContext,
			Setting setting,
			com.mozu.sterling.model.inventory.AvailabilityChange availabilityChange
			) throws Exception {
		List<LocationInventoryAdjustment> locationInventoryAdjustments = null;
		if (availabilityChange != null) {

			locationInventoryAdjustments = sterlingInventoryToMozuMapper
					.availabilityChangeToLocationInventoryAdjustment(
							availabilityChange, apiContext, setting);

			LocationInventoryResource locationInventoryResource = new LocationInventoryResource(
					apiContext);

			locationInventoryResource.updateLocationInventory(
					locationInventoryAdjustments, sterlingInventoryToMozuMapper
							.getProductCode(availabilityChange));

		} else {
			logger.info("AvailabilityChange cannot be null.");
		}
		return locationInventoryAdjustments != null;
	}

}
