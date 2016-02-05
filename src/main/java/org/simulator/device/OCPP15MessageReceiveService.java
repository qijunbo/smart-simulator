package org.simulator.device;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.simulator.audit.model.AuditService;
import org.simulator.common.soap.SOAPFactory;
import org.simulator.common.soap.jaxb.SimpleMarshaller;
import org.simulator.ocpp.OcppOperation;
import org.simulator.ocpp.OperationLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.w3c.dom.Node;

import com.ocpp.cp201206.ObjectFactory;

@Configuration
public class OCPP15MessageReceiveService {

    @Autowired
    private AuditService auditService;

    @Autowired
    protected SOAPFactory soapFactory;

    @Autowired
    protected SimpleMarshaller marshaller;

    @Autowired
    private OperationLocator locator;

    public String dispatch(String protocol, String xml) {

        String deviceSerial = "";

        try {
            Map<String, Object> parms = new HashMap<String, Object>();

            SOAPMessage soapMessage = soapFactory.readSOAPMessage(xml);
            deviceSerial = getChargeBoxIdentity(soapMessage);
            parms.put(OcppOperation.CHARGE_BOX_IDENTITY, deviceSerial);
            auditService.auditRequest(deviceSerial, xml);

            JAXBElement<?> request = (JAXBElement<?>) marshaller.ummarshal(soapMessage, ObjectFactory.class
                    .getPackage().getName());
            String response = locator.get(
                    new OcppOperation.Identifier(protocol, request.getValue().getClass().getName())).execute(
                    request.getValue(), parms);
            auditService.auditRequest(deviceSerial, response);
            return response;

        } catch (SOAPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            auditService.auditRequest(deviceSerial, e.getMessage());
            return e.getMessage();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            auditService.auditRequest(deviceSerial, e.getMessage());
            return e.getMessage();
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            auditService.auditRequest(deviceSerial, e.getMessage());
            return e.getMessage();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            auditService.auditRequest(deviceSerial, e.getMessage());
            return e.getMessage();
        }

    }

    private String getChargeBoxIdentity(SOAPMessage soapMessage) throws SOAPException {
        @SuppressWarnings("unchecked")
        Iterator<Node> elements = soapMessage.getSOAPHeader().getChildElements();
        while (elements.hasNext()) {
            Node e = elements.next();
            if (OcppOperation.CHARGE_BOX_IDENTITY.equals(e.getLocalName())) {
                return e.getFirstChild().getNodeValue();
            }

        }
        return null;
    }
}
