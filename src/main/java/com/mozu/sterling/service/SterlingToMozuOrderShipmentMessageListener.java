package com.mozu.sterling.service;

import java.io.StringReader;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.mozu.api.MozuApiContext;
import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.jmsUtil.TenantSiteMessageListener;
import com.mozu.sterling.model.Setting;
import com.mozu.sterling.model.shipment.Shipment;

/**
 * Receives jms messages for processing. Not thread safe as it is tied to a
 * tenant.
 *
 */
public class SterlingToMozuOrderShipmentMessageListener implements TenantSiteMessageListener {
    private static final Logger logger = LoggerFactory
            .getLogger(SterlingToMozuOrderShipmentMessageListener.class);
    private static JAXBContext jaxbContext = null;

    private Integer tenantId;

    private Set<Integer> siteSet;

    private OrderService orderService;

    private ConfigHandler configHandler;

    static {
        try {
            jaxbContext = JAXBContext
                    .newInstance(Shipment.class);
        } catch (JAXBException jaxbEx) {
            logger.error("Error getting jaxb context.");
        }
    }

    public SterlingToMozuOrderShipmentMessageListener(Integer tenantId,
            Integer siteId, ConfigHandler configHandler,
            OrderService orderService) {
        this.tenantId = tenantId;
        this.siteSet = new HashSet<Integer>();
        this.siteSet.add(siteId);
        this.configHandler = configHandler;
        this.orderService = orderService;
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                Setting setting = configHandler.getSetting(tenantId);

                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

                DocumentBuilderFactory factory = DocumentBuilderFactory
                        .newInstance();
                DocumentBuilder builder;
                Document document = null;
                builder = factory.newDocumentBuilder();
                
                document = builder.parse(new InputSource(new StringReader(
                        ((TextMessage) message).getText())));
                Shipment shipment = (com.mozu.sterling.model.shipment.Shipment) unmarshaller
                        .unmarshal(document);
                logger.debug("Reading Shipment from the message queue.  Sterling Order No: "
                        + shipment.getOrderNo());

                if (setting.getSiteMap() != null
                        && !setting.getSiteMap().isEmpty()
                        && setting.getSiteMap().containsValue(
                        		shipment.getSellerOrganizationCode())) {

                    Set<Integer> theSiteId = new HashSet<Integer>();

                    for (Entry<String, String> entry : setting.getSiteMap().entrySet()) {
                        if (entry.getValue().equals(shipment.getSellerOrganizationCode())) {
                            theSiteId.add(Integer.valueOf(entry.getKey()));
                        }
                    }

                    theSiteId.retainAll(siteSet);

                    if (!theSiteId.isEmpty() && theSiteId.size() == 1) {
                    	 orderService.importSterlingShipment(new MozuApiContext(
                                 tenantId, theSiteId.iterator().next()), setting, shipment);
                    } else {
                        logger.warn("No sites or more than one site matched for the sterling order.");
                    }
                } else {
                    logger.warn(
                            "Mozu site not found for sterling order {} and seller organization {}",
                            shipment.getOrderNo(),
                            shipment.getSellerOrganizationCode());
                }

            } catch (JMSException e) {
                logger.error("Failed to read message.", e);
            } catch (JAXBException jaxbEx) {
                logger.error("Failed to unmarshall.", jaxbEx);
            } catch (Exception mozuEx) {
                logger.error("Failed to complete mozu call.", mozuEx);
            }
        } else {
            logger.info("I don't know what kind of jms message this is.");
            logger.info(message.getClass().getName());
        }
    }

    @Override
    public void addToSites(Integer siteId) {
        this.siteSet.add(siteId);
    }
}
