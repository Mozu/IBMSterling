package com.mozu.sterling.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.mozu.sterling.model.Setting;
import com.mozu.sterling.model.catalog.MasterCatalog;
import com.mozu.sterling.model.catalog.MasterCatalogList;

@Service("catalogService")
public class CatalogService extends SterlingOrganizationService {
    private static final Logger logger = LoggerFactory.getLogger(CatalogService.class);
    public final static String MASTER_CATALOG_SERVICE_NAME = "getMasterCatalogList";

    public CatalogService () throws Exception {
        super();
    }

    public List<MasterCatalog> getSterlingMasterCatalogs (Setting setting) throws Exception {
        MasterCatalogList masterCatalogList = null;
        if (StringUtils.isNotBlank(setting.getSterlingUrl())) {
            MasterCatalog inCatalog= new com.mozu.sterling.model.catalog.MasterCatalog();
            if (StringUtils.isNotBlank(setting.getSterlingEnterpriseCode())) {
                inCatalog.setOrganizationCode(setting.getSterlingEnterpriseCode());
            }

            Document inDoc = convertObjectToXml(inCatalog, MasterCatalog.class);
            Document outDoc = null;
            try {
                outDoc = this.invoke(MASTER_CATALOG_SERVICE_NAME, inDoc, setting);
                masterCatalogList = (MasterCatalogList) convertXmlToObject(outDoc, MasterCatalogList.class);
            } catch (Exception e) {
                logger.warn("Unable to get Master Catalog list from Sterling: " + e.getMessage());
            }
            
        } else {
            logger.warn ("Cannot get Sterling ship nodes because the settings aren't set.");
        }
        return masterCatalogList != null ? masterCatalogList.getMasterCatalog() : new ArrayList<MasterCatalog>(); 

    }
}
