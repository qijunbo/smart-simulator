package org.simulator.ocpp.cp15;

import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;

import org.simulator.common.Cache;
import org.simulator.common.soap.SOAPFactory;
import org.simulator.common.soap.jaxb.SimpleMarshaller;
import org.simulator.ocpp.AbstractOperatoin;
import org.simulator.ocpp.ChargeBoxStatus;
import org.simulator.ocpp.OcppOperation;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DownwardsOperation<R, F> extends AbstractOperatoin {

    @Autowired
    protected SOAPFactory soapFactory;

    @Autowired
    protected SimpleMarshaller marshaller;

    @Override
    public String getProtocol() {
        return OCPP.ocpp15.name();
    }

    @Override
    public String execute(Object request, Map<String, ?> parms) throws Exception {

        @SuppressWarnings("unchecked")
        F response = createResponse((R) request, parms);

        SOAPMessage soapMessage = marshaller.marshalSOAP(response, response.getClass().getPackage().getName());

        soapMessage.getSOAPHeader()
                .addChildElement(new QName("urn://Ocpp/Cp/2012/06/", OcppOperation.CHARGE_BOX_IDENTITY))
                .addTextNode(parms.get(OcppOperation.CHARGE_BOX_IDENTITY).toString());

        return soapFactory.toString(soapMessage);
    }

    abstract F createResponse(R request, Map<String, ?> parms) throws Exception;

	protected ChargeBoxStatus getDeviceStatus(String chargeBoxIdentity, int connector) {
		return (ChargeBoxStatus) Cache.get(Cache.generateKey(chargeBoxIdentity, connector));
	}

}
