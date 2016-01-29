package com.mozu.sterling.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mozu.api.ApiContext;
import com.mozu.api.ApiError;
import com.mozu.api.ApiException;
import com.mozu.api.MozuApiContext;
import com.mozu.api.contracts.location.Location;
import com.mozu.api.contracts.location.LocationCollection;
import com.mozu.api.contracts.mzdb.EntityList;
import com.mozu.api.contracts.mzdb.IndexedProperty;
import com.mozu.api.contracts.tenant.Site;
import com.mozu.api.contracts.tenant.Tenant;
import com.mozu.api.resources.commerce.admin.LocationResource;
import com.mozu.api.resources.platform.TenantResource;
import com.mozu.base.handlers.EntityHandler;
import com.mozu.base.handlers.EntitySchemaHandler;
import com.mozu.base.models.EntityDataTypes;
import com.mozu.base.models.EntityScope;
import com.mozu.base.utils.ApplicationUtils;
import com.mozu.sterling.jmsUtil.DestinationTypeEnum;
import com.mozu.sterling.jmsUtil.JmsConnectionStrategyEnum;
import com.mozu.sterling.model.CarrierMap;
import com.mozu.sterling.model.LocationMap;
import com.mozu.sterling.model.OptionUI;
import com.mozu.sterling.model.Setting;
import com.mozu.sterling.model.SettingUI;
import com.mozu.sterling.model.SiteMap;
import com.mozu.sterling.model.organization.Organization;
import com.mozu.sterling.model.organization.ShipNode;
import com.mozu.sterling.model.shipping.CarrierService;
import com.mozu.sterling.service.MozuShippingService;
import com.mozu.sterling.service.SterlingOrganizationService;

/**
 * Manage the Sterling configuration settings in the Mozu MZDB data store
 * 
 * @author bob_hewett
 *
 */
@Component
public class ConfigHandler {
    private static final Logger logger = LoggerFactory.getLogger(ConfigHandler.class);
    private final static String TENANT_MZDB_ENTITY = "mozu-sterling-settings";

    @Autowired
    EntitySchemaHandler entitySchemaHandler;

    EntityHandler<Setting> settingEntityHandler;

    @Autowired
    @Qualifier("sterlingOrganizationService")
    SterlingOrganizationService organizationService;

    @Autowired
    MozuShippingService mozuShippingService;
    public ConfigHandler() {
        settingEntityHandler = new EntityHandler<Setting>(Setting.class);
    }

    /**
     * Get the Sterling settings for the tenant
     * 
     * @param tenantId
     *            id of the tenant
     * @param contextPath
     *            context path of the request
     * @param serverPort
     *            port of the server
     * @param serverName
     *            server name
     * @return settings data
     * @throws Exception
     */
    public Setting getSetting(Integer tenantId) throws Exception {
        Setting setting = settingEntityHandler.getEntity(new MozuApiContext(tenantId), TENANT_MZDB_ENTITY,
                String.valueOf(tenantId));
        if (setting == null) {
            setting = new Setting();
        }
        return setting;
    }

    /**
     * Get the settings from MZDB as well as the possible values for sites,
     * locations, shipNodes and Sterling organizations.
     * 
     * @param tenantId
     * @return the object to be used by the web UI
     * @throws Exception
     */
    public SettingUI getSettingUI(Integer tenantId) throws Exception {
        SettingUI settingUI = new SettingUI();
        settingUI.setIsConnected(false);
        Setting setting = getSetting(tenantId);
        BeanUtils.copyProperties(setting, settingUI);
        // load sites options from Mozu
        settingUI.setSites(getSites(tenantId));

        // load location values from Mozu
        settingUI.setLocations(getLocations(tenantId));
        
        // load mozu shipping methods
        settingUI.setMozuCarriers(mozuShippingService.getMozuShippingCarrierOptions());

        if (StringUtils.isNotBlank(setting.getSterlingUrl()) 
                || StringUtils.isNotBlank(setting.getSterlingUserId())
                || StringUtils.isNotBlank(setting.getSterlingPassword())) {

            // set the organization lists
            try {
                settingUI.setEnterprises(getEnterpriseList(setting));
                settingUI.setSellerMap(getSellerList(setting));
                settingUI.setShipNodeMap(getShipNodeMap(setting));
                settingUI.setCarrierMap(getCarrierMapList(setting));
                settingUI.setIsConnected(true);
            } catch (Exception e) {
                settingUI.setIsConnected(false);
                settingUI.setErrorMsg(
                        "Unable to connect to Sterling.  Please check that your connection settings are correct.");
            }
        }

        // load jms connection strategies
        List<OptionUI> connectionStrategyList = new ArrayList<OptionUI>();
        connectionStrategyList.add(new OptionUI(JmsConnectionStrategyEnum.DIRECT.strategyName(),
			JmsConnectionStrategyEnum.DIRECT.strategyName()));

        settingUI.setConnectionStrategies(connectionStrategyList);

        // load jms destination types
        List<OptionUI> destinationTypeList = new ArrayList<OptionUI>();
        destinationTypeList.add(new OptionUI(DestinationTypeEnum.QUEUE.destinationName(),
			DestinationTypeEnum.TOPIC.destinationName()));

        settingUI.setDestinationTypes(destinationTypeList);

        return settingUI;
    }


    /**
     * Save Sterling configuration settings for the tenant
     *
     * @param tenantId
     *            tenant id
     * @param setting
     *            the populated settings data
     * @throws Exception
     */
    public void saveSettingUI(Integer tenantId, SettingUI settingUI) throws Exception {
        Setting setting = new Setting();
        BeanUtils.copyProperties(settingUI, setting);
        
        List<SiteMap> siteMapList = settingUI.getSellerMap();
        List<LocationMap> locationMapList = settingUI.getShipNodeMap();
        List<CarrierMap> carrierMapList = settingUI.getCarrierMap();
        Map <String, String> configMap = null;
        if (siteMapList != null) {
            configMap = new HashMap<>();

            for (SiteMap siteMap : siteMapList) {
                if (StringUtils.isNotBlank(siteMap.getMozuSiteId())) {
                    configMap.put(siteMap.getMozuSiteId(), siteMap.getSterlingOrgCode());
                } 
            }
            setting.setSiteMap(configMap);
        }
        if (locationMapList != null) {
            configMap = new HashMap<>();
            for (LocationMap locationMap : locationMapList) {
                if (StringUtils.isNotBlank(locationMap.getLocationCode())) {
                    configMap.put(locationMap.getLocationCode(), locationMap.getShippingNodeCode());
                }
            }
            setting.setLocationMap(configMap);
        }
        if (carrierMapList != null) {
            configMap = new HashMap<>();
            for (CarrierMap carrierMap : carrierMapList) {
                if (StringUtils.isNotBlank(carrierMap.getMozuShipCode())) {
                    configMap.put(carrierMap.getMozuShipCode(), carrierMap.getSterlingScacAndService());
                }
            }
            setting.setShipMethodMap(configMap);
        }
        saveSettings(tenantId, setting);
    }
    public void saveSettings(Integer tenantId, Setting setting) throws Exception {

        logger.debug("Saving settings into MZDB for " + tenantId);
        MozuApiContext apiContext = new MozuApiContext(tenantId);
        setting.setId(String.valueOf(tenantId));

        try {
            settingEntityHandler.upsertEntity(apiContext, TENANT_MZDB_ENTITY, String.valueOf(tenantId), setting);

        } catch (Exception e) {
            if (e instanceof ApiException) {
                ApiError apiError = ((ApiException) e).getApiError();
                logger.warn("Exception updating application settings:" + " App name: "
                        + ((apiError.getApplicationName() != null) ? apiError.getApplicationName() : "")
                        + " Correlation ID: "
                        + ((apiError.getCorrelationId() != null) ? apiError.getCorrelationId() : "") + " Error Code "
                        + apiError.getErrorCode() + " Message: " + ((apiError.getExceptionDetail().getMessage() != null)
                                ? apiError.getExceptionDetail().getMessage() : ""));
            }
            throw e;
        }
        if (StringUtils.isNotEmpty(setting.getSterlingUrl()) && StringUtils.isNotEmpty(setting.getSterlingUserId())
                && StringUtils.isNotEmpty(setting.getSterlingPassword())) {
            ApplicationUtils.setApplicationToInitialized(apiContext);
        }
    }

    /**
     * Install Schema with indexed properties for storing the Sterling settings
     * 
     * @param tenantId
     * @throws Exception
     */
    public void installSchema(Integer tenantId) throws Exception {
        EntityList entityList = new EntityList();
        entityList.setNameSpace(ApplicationUtils.getAppInfo().getNameSpace());
        entityList.setName(TENANT_MZDB_ENTITY);
        entityList.setIsLocaleSpecific(false);
        entityList.setIsSandboxDataCloningSupported(false);
        entityList.setIsShopperSpecific(false);
        entityList.setIsVisibleInStorefront(false);

        ApiContext apiContext = new MozuApiContext(tenantId);
        IndexedProperty idProperty = entitySchemaHandler.getIndexedProperty("id", EntityDataTypes.String);

        entitySchemaHandler.installSchema(apiContext, entityList, EntityScope.Tenant, idProperty, null);
    }

    /**
     * Get the locations for the give Mozu tenant.
     * 
     * @param tenantId
     * @return list of locations in the Mozu system for the tenant.
     * @throws Exception
     */
    protected List<OptionUI> getLocations(Integer tenantId) throws Exception {
        List<OptionUI> locationOptions = new ArrayList<>();

        LocationResource locationResource = new LocationResource(new MozuApiContext(tenantId));

        LocationCollection locations = locationResource.getLocations(0, 200, "name", null, "items(code,name)");
        for (Location location : locations.getItems()) {
            locationOptions.add(new OptionUI(location.getCode(), location.getName()));
        }

        return locationOptions;
    }

    /**
     * Get the sites for the tenant ID from Mozu
     * 
     * @param tenantId
     *            the mozu tenant id
     * @return the list of mozu sites.
     * @throws Exception
     */
    protected List<OptionUI> getSites(Integer tenantId) throws Exception {
        List<OptionUI> siteOptions = new ArrayList<>();

        TenantResource tenantResource = new TenantResource(new MozuApiContext(tenantId));
        Tenant tenant = tenantResource.getTenant(tenantId, "sites");

        List<Site> sites = tenant.getSites();
        if (sites != null && sites.size() > 0) {
            for (Site site : sites) {
                siteOptions.add(new OptionUI(String.valueOf(site.getId()), site.getName()));
            }
        }

        return siteOptions;
    }

    protected List<LocationMap> getShipNodeMap(Setting setting) throws Exception {
        List<LocationMap> shipNodeMap = new ArrayList<>();

        List<ShipNode> shipNodes = organizationService.getShipNodes(setting);

        Map <String, String> locationMap = setting.getLocationMap();
        for (ShipNode shipNode : shipNodes) {
            String mozuSiteId = getMappedMozuCode (shipNode.getShipnodeKey(), locationMap);
            shipNodeMap.add(new LocationMap(shipNode.getShipnodeKey(), shipNode.getDescription(), mozuSiteId));
        }

        return shipNodeMap;
    }

    protected List<OptionUI> getEnterpriseList(Setting setting) throws Exception {
        List<OptionUI> shipNodeOptions = new ArrayList<>();

        List<Organization> organizations = organizationService.getEnterpriseOrganizations(setting);

        for (Organization org : organizations) {
            shipNodeOptions.add(new OptionUI(org.getOrganizationCode(), org.getOrganizationName()));
        }

        return shipNodeOptions;
    }

    protected List<SiteMap> getSellerList(Setting setting) throws Exception {
        List<SiteMap> siteMappings = new ArrayList<>();

        List<Organization> organizations = organizationService.getSellerOrganizations(setting);

        Map <String, String> shipMap = setting.getSiteMap();
        for (Organization org : organizations) {
            String mozuSiteId = getMappedMozuCode (org.getOrganizationCode(), shipMap);
            siteMappings.add(new SiteMap(org.getOrganizationCode(), org.getOrganizationName(), mozuSiteId));
        }

        return siteMappings;
    }
    
    /**
     * 
     * @param setting
     * @return
     * @throws Exception
     */
    private List<CarrierMap> getCarrierMapList(Setting setting) throws Exception {
        List <CarrierService> sterlingCarriers = organizationService.getCarrierList(setting);
        List <CarrierMap> carrierMapList = new ArrayList<>();
        Map<String, String> shipMethodMap = setting.getShipMethodMap();
        for (CarrierService carrier : sterlingCarriers) {
        	String mozuCarrierCode = getMappedMozuCode (carrier.getSCACAndService(), shipMethodMap);
            carrierMapList.add(new CarrierMap(carrier.getSCACAndServiceDesc(), carrier.getSCACAndService(), mozuCarrierCode));
        }
        
        return carrierMapList;
    }


    /**
     * 
     * @param sterlingCode
     * @param mozuMap
     * @return
     */
    private String getMappedMozuCode(String sterlingCode, Map<String, String> mozuMap) {
        String mozuCarrierCode = null;
        if (mozuMap != null && mozuMap.size() > 0) {
            for (Map.Entry<String,String> shipMethod : mozuMap.entrySet()) {
                if (shipMethod.getValue().equals(sterlingCode)) {
                    mozuCarrierCode =shipMethod.getKey();
                    break;
                }
            }
        }
        return mozuCarrierCode;
    }
}
