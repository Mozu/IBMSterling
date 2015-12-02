package com.mozu.sterling.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.model.MozuShippingCarrier;
import com.mozu.sterling.model.OptionUI;

@Service
public class MozuShippingService {
    private static final Logger logger = LoggerFactory.getLogger(ConfigHandler.class);
    private List<MozuShippingCarrier> mozuShippingCarriers = null;
    
    @PostConstruct
    public void intialize() {
        InputStream stream = null;
        try {
            logger.info("Loading attribute mapping definition");
            ObjectMapper mapper = new ObjectMapper();

            stream = this.getClass().getResourceAsStream("/mozu_shipping.json");
            mozuShippingCarriers = mapper.readValue(stream,new TypeReference<List<MozuShippingCarrier>>(){});
        } catch (Exception e) {
            logger.error("Unable to read mozu_shipping.json" + e.getMessage());
        } finally {
            try {
                if (stream != null)
                    stream.close();
            } catch (IOException e) {
            }
        }
    }
    
    public List<MozuShippingCarrier> getMozuShippingCarriers () {
        return mozuShippingCarriers;
    }

    public List<OptionUI> getMozuShippingCarrierOptions () {
        List<OptionUI> mozuShippingOptions = new ArrayList<>();
        
        for (MozuShippingCarrier carrier : mozuShippingCarriers) {
            mozuShippingOptions.add(new OptionUI(carrier.getCode(), carrier.getDescription()));
        }
        return mozuShippingOptions;
    }

}
