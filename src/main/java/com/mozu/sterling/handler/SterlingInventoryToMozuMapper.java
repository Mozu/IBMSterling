package com.mozu.sterling.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mozu.api.ApiContext;
import com.mozu.api.contracts.productadmin.LocationInventoryAdjustment;
import com.mozu.sterling.model.Setting;

@Component
public class SterlingInventoryToMozuMapper {
    private static final Logger logger = LoggerFactory.getLogger(SterlingInventoryToMozuMapper.class);

    public static final String INVENTORY_ADJUSTMENT_TYPE = "Absolute";


    protected Integer getInventoryQuantity(com.mozu.sterling.model.inventory.AvailabilityChange availabilityChange) {
	return Integer.valueOf(availabilityChange.getOnhandAvailableQuantity());
    }

    protected String getLocationCode(com.mozu.sterling.model.inventory.AvailabilityChange availabilityChange, Setting setting) {
	String mozuLocation = null;
	String shipnode = availabilityChange.getNode();
	Map<String, String> locationMap = setting.getLocationMap();

        if (StringUtils.isNotBlank(shipnode)) {
            for (Entry<String, String> entry : locationMap.entrySet()) {
                if (shipnode.equals(entry.getValue())) {
                    mozuLocation = entry.getKey();
                    break;
                }
            }
        } else {
		mozuLocation = locationMap.keySet().iterator().next();
        }

        return mozuLocation;
    }

    /**
     * Map Sterling inventory to a Mozu adjustment.
     *
     * @param productCode
     * @param availabilityChange
     * @param apiContext
     * @param setting
     * @return
     * @throws Exception
     */
    public List<LocationInventoryAdjustment> availabilityChangeToLocationInventoryAdjustment(String productCode, com.mozu.sterling.model.inventory.AvailabilityChange availabilityChange, ApiContext apiContext, Setting setting)
            throws Exception {
	List<LocationInventoryAdjustment> locationInventoryAdjustments = new ArrayList<LocationInventoryAdjustment>();
		LocationInventoryAdjustment locationInventoryAdjustment = new LocationInventoryAdjustment();
		locationInventoryAdjustments.add(locationInventoryAdjustment);

		locationInventoryAdjustment.setLocationCode(getLocationCode(availabilityChange, setting));
		locationInventoryAdjustment.setProductCode(productCode);
		locationInventoryAdjustment.setType(INVENTORY_ADJUSTMENT_TYPE);
		locationInventoryAdjustment.setValue(getInventoryQuantity(availabilityChange));

	return locationInventoryAdjustments;
    }
}
