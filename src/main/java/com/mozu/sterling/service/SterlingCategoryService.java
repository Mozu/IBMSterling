package com.mozu.sterling.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.mozu.api.ApiContext;
import com.mozu.api.MozuApiContext;
import com.mozu.api.contracts.productadmin.CategoryLocalizedContent;
import com.mozu.api.resources.commerce.catalog.admin.CategoryResource;
import com.mozu.sterling.model.Setting;
import com.mozu.sterling.model.category.Category;
import com.mozu.sterling.model.category.CategoryList;

@Service("sterlingCategoryService")
public class SterlingCategoryService extends SterlingClient {
    private static final Logger logger = LoggerFactory.getLogger(SterlingCategoryService.class);
    private static final String CATEGORY_SERVICE = "getCategoryList";
    private static final String ROOT_CATEGORY_ID = "";

    public SterlingCategoryService() throws Exception {
        super();
    }

    List<Category> getCategoryList(Setting setting, Integer siteId) throws Exception {
        CategoryList categoryList = null;
        Category lookup = new Category();

        if (StringUtils.isNotBlank(setting.getSterlingUrl())) {
            lookup.setOrganizationCode(setting.getSterlingEnterpriseCode());
            Document inDoc = convertObjectToXml(lookup, Category.class);
            Document outDoc = null;

            try {
                outDoc = this.invoke(CATEGORY_SERVICE, inDoc, setting);
                categoryList = (CategoryList) convertXmlToObject(outDoc, CategoryList.class);
            } catch (Exception e) {

                logger.warn(String.format("Unable to get the category list for site id %d from Sterling : %s", siteId,
                        e.getMessage()));
                throw e;
            }

        } else {
            logger.warn("Cannot import Sterling categories because the settings aren't set.");
        }
        return categoryList != null ? categoryList.getCategory() : new ArrayList<Category>();
    }

    public com.mozu.api.contracts.productadmin.Category mapSterlingCategory(Category sterlingCategory) {
        com.mozu.api.contracts.productadmin.Category mozuCategory = new com.mozu.api.contracts.productadmin.Category();

        mozuCategory.setCategoryType("Static");
        mozuCategory.setCategoryCode(sterlingCategory.getCategoryKey());
        CategoryLocalizedContent localizedContent = new CategoryLocalizedContent();
        localizedContent.setName(sterlingCategory.getShortDescription());
        localizedContent.setDescription(sterlingCategory.getDescription());
        mozuCategory.setContent(localizedContent);

        mozuCategory.setParentCategoryId(0);

        return mozuCategory;
    }

    public Map<String, List<com.mozu.api.contracts.productadmin.Category>> mapCategoryHierarchy(Setting setting,
            Integer siteId) throws Exception {
        Map<String, List<com.mozu.api.contracts.productadmin.Category>> categoryMap = new HashMap<>();

        List<Category> sterlingCategories = getCategoryList(setting, siteId);
        List<com.mozu.api.contracts.productadmin.Category> childCategories = null;
        //ApiContext apiContext = new MozuApiContext (Integer.valueOf(setting.getId()), siteId);
        for (Category sterlingCategory : sterlingCategories) {
            com.mozu.api.contracts.productadmin.Category mozuCategory = mapSterlingCategory(sterlingCategory);
            String parentKey = StringUtils.isBlank(sterlingCategory.getParentCategoryKey()) ? ROOT_CATEGORY_ID : sterlingCategory.getParentCategoryKey();
            childCategories = categoryMap.get(parentKey);
            if (childCategories == null) {
                childCategories = new ArrayList<>();
                categoryMap.put(sterlingCategory.getParentCategoryKey(), childCategories);
            }
            childCategories.add(mozuCategory);
        }
        
        return categoryMap;
    }
    
    public void addAllCategories (Setting setting, Integer siteId) throws Exception {
        Map<String, List<com.mozu.api.contracts.productadmin.Category>> categoryMap = mapCategoryHierarchy(setting, siteId);
        ApiContext apiContext = new MozuApiContext(Integer.valueOf(setting.getId()), siteId);
        addChildCategories (null, categoryMap, categoryMap.get(ROOT_CATEGORY_ID), apiContext);
    }
    
    private void addChildCategories (Integer parentCategoryId, Map<String, 
            List<com.mozu.api.contracts.productadmin.Category>> categoryMap,
            List<com.mozu.api.contracts.productadmin.Category> childCategories, 
            ApiContext apiContext) 
                    throws  Exception {
        CategoryResource categoryResource = new CategoryResource(apiContext);
        if (childCategories != null) {
            for (com.mozu.api.contracts.productadmin.Category category : childCategories) {
                
                // get the category
                // categoryResource.getCategory()
                category.setParentCategoryId(parentCategoryId);
                if (category.getId() == null) {
                    category = categoryResource.addCategory(category);
                } else {
                    category = categoryResource.updateCategory(category, category.getId());
                }
                addChildCategories (category.getId(), categoryMap, categoryMap.get(category.getCategoryCode()), apiContext);
            }
        }
    } 
}
