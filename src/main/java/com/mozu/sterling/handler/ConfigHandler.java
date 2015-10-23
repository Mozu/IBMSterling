package com.mozu.sterling.handler;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mozu.api.ApiContext;
import com.mozu.api.ApiError;
import com.mozu.api.ApiException;
import com.mozu.api.MozuApiContext;
import com.mozu.api.contracts.mzdb.EntityList;
import com.mozu.api.contracts.mzdb.IndexedProperty;
import com.mozu.base.handlers.EntityHandler;
import com.mozu.base.handlers.EntitySchemaHandler;
import com.mozu.base.models.EntityDataTypes;
import com.mozu.base.models.EntityScope;
import com.mozu.base.utils.ApplicationUtils;
import com.mozu.sterling.model.Setting;

/**
 * Manage the Sterling configuration setting in the database
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
    
    public ConfigHandler() { 
        settingEntityHandler = new EntityHandler<Setting>(Setting.class);
    }

    /**
     * Get the Sterling settings for the tenant
     * @param tenantId id of the tenant
     * @param contextPath context path of the request
     * @param serverPort port of the server
     * @param serverName server name
     * @return settings data
     * @throws Exception
     */
    public Setting getSetting(Integer tenantId) throws Exception {
        Setting setting = settingEntityHandler.getEntity(new MozuApiContext(tenantId), TENANT_MZDB_ENTITY, String.valueOf(tenantId));
        if (setting == null) {
            setting = new Setting();
        }
        return setting;
    }
    
    /**
     * Save Sterling configuration settings for the tenant
     * 
     * @param tenantId tenant id
     * @param setting the populated settings data
     * @throws Exception
     */
    public void saveSettings(Integer tenantId, Setting setting) throws Exception {
        
        logger.debug("Saving settings into MZDB for " + tenantId);
        MozuApiContext apiContext = new MozuApiContext(tenantId);
        setting.setId(String.valueOf(tenantId));

        try {
            settingEntityHandler.upsertEntity(apiContext, TENANT_MZDB_ENTITY, String.valueOf(tenantId), setting);
        
        } catch (Exception e) {
                if (e instanceof ApiException) {
                    ApiError apiError = ((ApiException)e).getApiError();
                    logger.warn("Exception updating application settings:" + 
                            " App name: " + ((apiError.getApplicationName()!=null)?apiError.getApplicationName():"") +
                            " Correlation ID: " + ((apiError.getCorrelationId()!=null)?apiError.getCorrelationId():"") + 
                            " Error Code " + apiError.getErrorCode() + 
                            " Message: " + ((apiError.getExceptionDetail().getMessage()!=null)?
                            		apiError.getExceptionDetail().getMessage():"")
                            );
                }
                throw e;
        }
        if (StringUtils.isNotEmpty(setting.getSterlingUrl()) &&
            StringUtils.isNotEmpty(setting.getSterlingUserId()) &&
            StringUtils.isNotEmpty(setting.getSterlingPassword())) {
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
        IndexedProperty idProperty = entitySchemaHandler.getIndexedProperty("id",
                EntityDataTypes.String);

        entitySchemaHandler.installSchema(apiContext, entityList, EntityScope.Tenant, idProperty, null);
    }
    
}
