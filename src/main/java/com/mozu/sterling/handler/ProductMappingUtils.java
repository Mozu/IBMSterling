package com.mozu.sterling.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mozu.api.ApiContext;
import com.mozu.api.contracts.commerceruntime.commerce.PackageMeasurements;
import com.mozu.api.contracts.productadmin.ProductCollection;
import com.mozu.api.contracts.productruntime.BundledProduct;
import com.mozu.api.contracts.productruntime.Category;
import com.mozu.api.contracts.productruntime.Product;
import com.mozu.api.contracts.productruntime.ProductOption;
import com.mozu.api.contracts.productruntime.ProductOptionValue;
import com.mozu.api.contracts.productruntime.ProductPrice;
import com.mozu.api.contracts.productruntime.ProductProperty;
import com.mozu.api.contracts.productruntime.ProductPropertyValue;
import com.mozu.api.resources.commerce.catalog.storefront.ProductResource;

public class ProductMappingUtils {
    private static final Logger logger = LoggerFactory.getLogger(ProductMappingUtils.class);

    /**
     * Returns the Variation Product code if one exists in the commerce runtime product.  Otherwise the product code is returned.
     * @param product
     * @return Variation Product code if one exists, otherwise it returns the product code.
     */
    public static String getActualProductCode (com.mozu.api.contracts.commerceruntime.products.Product product) {
        String productCode = null;
        if (product != null) {
            if (StringUtils.isNotBlank(product.getVariationProductCode())) {
                productCode = product.getVariationProductCode() ;
            } else {
                productCode= product.getProductCode();
            }
        }
        return productCode;
    }
    
    /**
     * This method does all the work of getting a product from Mozu and handling Variations and products.
     * @param apiContext
     * @param productCode
     * @param isVariant
     * @return
     */
    public static Product getProductFromMozu (ApiContext apiContext, String productCode, boolean isVariant) {
        Product product =  null;
        try {
            ProductResource productResource = new ProductResource(apiContext);
            if (!isVariant) {
                product = productResource.getProduct(productCode);
            } else {
                com.mozu.api.resources.commerce.catalog.admin.ProductResource adminProductResource = new com.mozu.api.resources.commerce.catalog.admin.ProductResource(apiContext);
                StringBuilder filter = new StringBuilder("isVariation eq true and productCode eq ").append(productCode);  
                ProductCollection productCollection = adminProductResource.getProducts(0, 1, null, filter.toString(), null, null, false, "items(baseProductCode)");
                com.mozu.api.contracts.productadmin.Product adminProduct = null;
                        
                if (productCollection != null && productCollection.getTotalCount() > 0) {
                    adminProduct = productCollection.getItems().get(0);
                    product = productResource.getProduct(adminProduct.getBaseProductCode(), productCode, true, true, true, null);
                } else {
                    throw new Exception ("Product not found with Variant Product Code: " + productCode);
                }
            }
        } catch (Exception e) {
            // log the error and try and keep going without the product
            StringBuilder errMsg = new StringBuilder("Unable to find Mozu product with product code ")
                .append(productCode)
                .append(" Exception: ")
                .append(e.getMessage());
            logger.warn(errMsg.toString());
        }
        return product;
    }
    
    public static com.mozu.api.contracts.commerceruntime.products.Product convertProduct(Product inProduct) {
        com.mozu.api.contracts.commerceruntime.products.Product outProduct = 
                new com.mozu.api.contracts.commerceruntime.products.Product();
        
        if (inProduct.getBundledProducts()!=null) {
            List<com.mozu.api.contracts.commerceruntime.products.BundledProduct> outBundledProducts = new ArrayList<>();
            for (BundledProduct inBundledProduct: inProduct.getBundledProducts()) {
                outBundledProducts.add(convertBundledProduct(inBundledProduct));
            }
            outProduct.setBundledProducts(outBundledProducts);
        } else {
            outProduct.setBundledProducts(new ArrayList<com.mozu.api.contracts.commerceruntime.products.BundledProduct>());
        }

        if (inProduct.getCategories()!=null) {
            List<com.mozu.api.contracts.commerceruntime.products.Category> outCategories = new ArrayList<>();
            for (Category inCategory: inProduct.getCategories()) {
                outCategories.add(convertCategory(inCategory));
            }
            outProduct.setCategories(outCategories);
        } else {
            outProduct.setCategories(new ArrayList<com.mozu.api.contracts.commerceruntime.products.Category>());
        }
        
        outProduct.setDescription(inProduct.getContent()!=null?inProduct.getContent().getProductShortDescription():null);
        
//        outProduct.setDiscountsRestricted(discountsRestricted);
//        outProduct.setDiscountsRestrictedEndDate(discountsRestrictedEndDate);
//        outProduct.setDiscountsRestrictedStartDate(discountsRestrictedStartDate);
//        outProduct.setImageAlternateText(imageAlternateText);
//        outProduct.setImageUrl();
//        outProduct.setProductReservationId(productReservationId);
        
        outProduct.setFulfillmentTypesSupported(inProduct.getFulfillmentTypesSupported());
        outProduct.setIsPackagedStandAlone(inProduct.getIsPackagedStandAlone());
        outProduct.setIsRecurring(inProduct.getIsRecurring());
        outProduct.setIsTaxable(inProduct.getIsTaxable());
        outProduct.setMeasurements(convertMeasurements(inProduct.getMeasurements()));
        outProduct.setMfgPartNumber(inProduct.getMfgPartNumber());
        outProduct.setName(inProduct.getContent()!=null?inProduct.getContent().getProductName():null);
        
        if (inProduct.getOptions()!=null) {
            List<com.mozu.api.contracts.commerceruntime.products.ProductOption> options = new ArrayList<>();
            for (com.mozu.api.contracts.productruntime.ProductOption inOption: inProduct.getOptions()) {
                options.add(convertOptions(inOption));
            }
            outProduct.setOptions(options);
        }
        
        outProduct.setPrice(convertPrice(inProduct.getPrice()));
        
        outProduct.setProductCode(inProduct.getProductCode());
        outProduct.setProductType(inProduct.getProductType());
        outProduct.setProductUsage(inProduct.getProductUsage());

        if (inProduct.getProperties()!=null) {
            List<com.mozu.api.contracts.commerceruntime.products.ProductProperty> properties = new ArrayList<>();
            for (ProductProperty inProperty: inProduct.getProperties()) {
                properties.add(convertProperties(inProperty));
            }
            outProduct.setProperties(properties);
        }
        
        outProduct.setUpc(inProduct.getUpc());
        outProduct.setVariationProductCode(inProduct.getVariationProductCode());
        
        return outProduct;
    }

    public static com.mozu.api.contracts.commerceruntime.products.ProductProperty convertProperties(
            ProductProperty inProperty) {
        com.mozu.api.contracts.commerceruntime.products.ProductProperty outProperty = 
                new com.mozu.api.contracts.commerceruntime.products.ProductProperty();
        outProperty.setAttributeFQN(inProperty.getAttributeFQN());
        if (inProperty.getAttributeDetail()!=null) {
            outProperty.setDataType(inProperty.getAttributeDetail().getDataType());
            outProperty.setName(inProperty.getAttributeDetail().getName());
        }
        outProperty.setIsMultiValue(inProperty.getIsMultiValue());
        List<com.mozu.api.contracts.commerceruntime.products.ProductPropertyValue> values = new ArrayList<>();
        for (ProductPropertyValue inValue: inProperty.getValues()) {
            values.add(convertProductPropertyValue(inValue));
        }
        outProperty.setValues(values);
        return outProperty;
    }

    public static com.mozu.api.contracts.commerceruntime.products.ProductPropertyValue convertProductPropertyValue(
            ProductPropertyValue inProperty) {
        com.mozu.api.contracts.commerceruntime.products.ProductPropertyValue outProperty = 
                new com.mozu.api.contracts.commerceruntime.products.ProductPropertyValue();
        outProperty.setStringValue(inProperty.getStringValue());
        outProperty.setValue(inProperty.getValue());
        return outProperty;
    }

    public static com.mozu.api.contracts.commerceruntime.products.ProductPrice convertPrice(
            ProductPrice inPrice) {
        com.mozu.api.contracts.commerceruntime.products.ProductPrice outPrice = 
                new com.mozu.api.contracts.commerceruntime.products.ProductPrice();
        if (inPrice!=null) {
            outPrice.setMsrp(inPrice.getMsrp());
            outPrice.setPrice(inPrice.getPrice());
            outPrice.setSalePrice(inPrice.getSalePrice());
        }        
        return outPrice;
    }

    public static com.mozu.api.contracts.commerceruntime.products.ProductOption convertOptions(
            ProductOption inOption) {
        com.mozu.api.contracts.commerceruntime.products.ProductOption outOption = 
                new com.mozu.api.contracts.commerceruntime.products.ProductOption();
        outOption.setAttributeFQN(inOption.getAttributeFQN());
        if (inOption.getAttributeDetail()!=null) {
            outOption.setDataType(inOption.getAttributeDetail().getDataType());
            outOption.setName(inOption.getAttributeDetail().getName());
        }
        for (ProductOptionValue productOptionValue : inOption.getValues()) {
            if (productOptionValue.getIsSelected()) {
                outOption.setShopperEnteredValue(productOptionValue.getShopperEnteredValue());
                outOption.setValue(productOptionValue.getValue());
            }
        }
        
        return outOption;
    }

    public static com.mozu.api.contracts.commerceruntime.products.Category convertCategory(
            Category inCategory) {
        if (inCategory==null)
            return null;
        
        com.mozu.api.contracts.commerceruntime.products.Category outCategory = 
                new com.mozu.api.contracts.commerceruntime.products.Category();
        outCategory.setId(inCategory.getCategoryId());
        outCategory.setParent(convertCategory(inCategory.getParentCategory()));
        return outCategory;
    }

    public static com.mozu.api.contracts.commerceruntime.products.BundledProduct convertBundledProduct(
            BundledProduct inBundledProduct) {
        com.mozu.api.contracts.commerceruntime.products.BundledProduct outBundledProduct = 
                new com.mozu.api.contracts.commerceruntime.products.BundledProduct();
        outBundledProduct.setIsPackagedStandAlone(inBundledProduct.getIsPackagedStandAlone());
        outBundledProduct.setMeasurements(convertMeasurements(inBundledProduct.getMeasurements()));
        if (inBundledProduct.getContent()!=null) {
            outBundledProduct.setDescription(inBundledProduct.getContent().getProductShortDescription());
            outBundledProduct.setName(inBundledProduct.getContent().getProductName());
        }

        outBundledProduct.setProductCode(inBundledProduct.getProductCode());
        outBundledProduct.setQuantity(inBundledProduct.getQuantity());
        return outBundledProduct;
    }

    public static PackageMeasurements convertMeasurements(
            com.mozu.api.contracts.productruntime.PackageMeasurements measurements) {
        com.mozu.api.contracts.commerceruntime.commerce.PackageMeasurements pkgMeasurements = 
                new com.mozu.api.contracts.commerceruntime.commerce.PackageMeasurements();
        if (measurements!=null) {
            pkgMeasurements.setHeight(measurements.getPackageHeight());
            pkgMeasurements.setLength(measurements.getPackageLength());
            pkgMeasurements.setWeight(measurements.getPackageWeight());
            pkgMeasurements.setWidth(measurements.getPackageWidth());
        }
        return pkgMeasurements;
    }
}
