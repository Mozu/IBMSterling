package com.mozu.sterling.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mozu.api.ApiContext;
import com.mozu.api.contracts.productadmin.LocationInventoryAdjustment;
import com.mozu.api.resources.commerce.catalog.admin.products.LocationInventoryResource;
import com.mozu.sterling.handler.SterlingInventoryToMozuMapper;
import com.mozu.sterling.model.Setting;

/**
 * Service for mapping, and sending inventory from sterling to mozu.
 *
 * @author bob_hewett
 *
 */
@Service
public class InventoryService extends SterlingClient {
	private static final Logger logger = LoggerFactory
			.getLogger(InventoryService.class);

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
			String productCode,
			com.mozu.sterling.model.inventory.AvailabilityChange availabilityChange
			) throws Exception {
		List<LocationInventoryAdjustment> locationInventoryAdjustments = null;
		if (availabilityChange != null) {

			locationInventoryAdjustments = sterlingInventoryToMozuMapper
					.availabilityChangeToLocationInventoryAdjustment(productCode,
							availabilityChange, apiContext, setting);

			LocationInventoryResource locationInventoryResource = new LocationInventoryResource(
					apiContext);

			locationInventoryResource.updateLocationInventory(
					locationInventoryAdjustments, productCode);

		} else {
			logger.info("AvailabilityChange cannot be null.");
		}
		return locationInventoryAdjustments != null;
	}
}
