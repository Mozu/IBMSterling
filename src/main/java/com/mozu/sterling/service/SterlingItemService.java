package com.mozu.sterling.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.mozu.api.MozuApiContext;
import com.mozu.api.contracts.core.Measurement;
import com.mozu.api.contracts.productadmin.Product;
import com.mozu.api.contracts.productadmin.ProductLocalizedContent;
import com.mozu.api.contracts.productadmin.ProductPrice;
import com.mozu.api.contracts.productadmin.ProductType;
import com.mozu.api.contracts.productadmin.ProductTypeCollection;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.ProductTypeResource;
import com.mozu.sterling.model.Setting;
import com.mozu.sterling.model.item.Item;
import com.mozu.sterling.model.item.ItemList;
import com.mozu.sterling.model.item.PrimaryInformation;
import com.mozu.sterling.model.page.API;
import com.mozu.sterling.model.page.Input;
import com.mozu.sterling.model.page.Page;
import com.mozu.sterling.model.page.output.Output;

@Service("sterlingItemService")
public class SterlingItemService extends SterlingClient {
    private static final Logger logger = LoggerFactory.getLogger(SterlingItemService.class);
    String PAGE_SERVICE = "getPage";
    String ITEM_SERVICE = "getItemList";

    public SterlingItemService() throws Exception {
        super();
    }

    List<Item> getAllItems(Setting setting, Integer siteId) throws Exception {
        ItemList itemList = null;
        Item lookup = new Item();

        if (StringUtils.isNotBlank(setting.getSterlingUrl())) {
            lookup.setCallingOrganizationCode(setting.getSiteMap().get(siteId.toString()));
            Document inDoc = convertObjectToXml(lookup, Item.class);
            Document outDoc = null;

            try {
                outDoc = this.invoke(ITEM_SERVICE, inDoc, setting);
                itemList = (ItemList) convertXmlToObject(outDoc, ItemList.class);
            } catch (Exception e) {

                logger.warn(String.format("Unable to get the item list for site id %d from Sterling : %s", siteId,
                        e.getMessage()));
                throw e;
            }

        } else {
            logger.warn("Cannot import Sterling items because the settings aren't set.");
        }
        return itemList != null ? itemList.getItem() : new ArrayList<Item>();
    }

    List<Item> getItemsPaged(Setting setting, Integer siteId, Integer pageNo, Integer pageSize) throws Exception {
        com.mozu.sterling.model.page.output.Page pageOutput;
        ItemList itemList = null;
        Item itemLookup = new Item();

        if (StringUtils.isNotBlank(setting.getSterlingUrl())) {
            itemLookup.setCallingOrganizationCode(setting.getSiteMap().get(siteId.toString()));
            Input input = new Input();
            input.getContent().add(itemLookup);
            API api = new API();
            api.setInput(input);
            api.setName(ITEM_SERVICE);

            Page page = new Page();
            page.setPageNumber(pageNo.toString());
            page.setPageSize(pageSize.toString());
            page.setAPI(api);
            String refresh = "N";
            if (pageNo == 1) {
                refresh = "Y";
            }
            page.setRefresh(refresh);

            Document inDoc = convertObjectToXml(page, Page.class);
            Document outDoc = null;

            try {
                outDoc = this.invoke(PAGE_SERVICE, inDoc, setting);
                pageOutput = (com.mozu.sterling.model.page.output.Page) convertXmlToObject(outDoc,
                        com.mozu.sterling.model.page.output.Page.class);
                Output output = pageOutput.getOutput();
                if (output != null & output.getContent() != null && output.getContent().size() > 0) {
                    JAXBContext payloadContext = JAXBContext.newInstance(ItemList.class);
                    itemList = (ItemList) payloadContext.createUnmarshaller()
                            .unmarshal((Node) output.getContent().get(0));
                }

            } catch (Exception e) {

                logger.warn(String.format("Unable to get the item list for site id %d from Sterling : %s", siteId,
                        e.getMessage()));
                throw e;
            }

        } else {
            logger.warn("Cannot import Sterling items because the settings aren't set.");
        }
        return itemList != null ? itemList.getItem() : new ArrayList<Item>();
    }

    Product mapItemToProduct(Item item, Integer tenantId) throws Exception {
        Product product = new Product();
        product.setProductCode(item.getItemID());

        ProductLocalizedContent content = new ProductLocalizedContent();
        content.setLocaleCode("en_US");
        PrimaryInformation productInfo = item.getPrimaryInformation();
        content.setProductName(productInfo.getShortDescription());
        content.setProductShortDescription(productInfo.getDescription());
        content.setProductFullDescription(productInfo.getExtendedDescription());
        product.setContent(content);
        // get height
        product.setPackageHeight(createMeasurement(productInfo.getUnitHeight(), productInfo.getUnitHeightUOM()));
        // get weight
        product.setPackageWeight(createMeasurement(productInfo.getUnitWeight(), productInfo.getUnitWeightUOM()));
        // get length
        product.setPackageHeight(createMeasurement(productInfo.getUnitLength(), productInfo.getUnitLengthUOM()));
        // get width
        product.setPackageHeight(createMeasurement(productInfo.getUnitWidth(), productInfo.getUnitWidthUOM()));
        ProductPrice price = new ProductPrice();
        price.setIsoCurrencyCode(
                StringUtils.isNotBlank(productInfo.getCostCurrency()) ? productInfo.getCostCurrency() : "USD");
        price.setPrice(StringUtils.isNotBlank(productInfo.getFixedPricingQtyPerLine())
                ? Double.valueOf(productInfo.getFixedPricingQtyPerLine()) : 0.00);
        product.setProductTypeId(getProductTypeId(productInfo.getItemType(), tenantId));
        return product;
    }

    private Integer getProductTypeId (String productTypeCode, Integer tenantId) throws Exception {
        Integer productTypeId = null;
        ProductTypeResource productTypeResource = new ProductTypeResource (new MozuApiContext(tenantId));
        ProductTypeCollection productTypes = productTypeResource.getProductTypes(0, 1, null, "name eq " + productTypeCode, "id");
        
        if (productTypes.getTotalCount() == 0) {
            productTypes = productTypeResource.getProductTypes(0, 1, null, "name eq Base", "id");
        }
        
        if(productTypes != null && productTypes.getTotalCount() > 0) {
            ProductType productType = productTypes.getItems().get(0);
            productTypeId = productType.getId();
        }
        return productTypeId;
    }
    private Measurement createMeasurement(String value, String unitOfMeasure) {
        Measurement measurement = new Measurement();
        if (value != null) {
            measurement.setUnit(unitOfMeasure);
            measurement.setValue(Double.valueOf(value));
        }
        return measurement;
    }
}
