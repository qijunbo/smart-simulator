package org.simulator.ocpp.cs15;

import java.util.Map;

import org.common.util.soap.XMLGregorianCalendarUtil;
import org.springframework.context.annotation.Configuration;

import com.ocpp.cs201206.StartTransactionRequest;

@Configuration
public class StartTransaction extends UpwardsOperation<StartTransactionRequest> {

    public static final String ID_TAG = "ID_TAG";
    public static final String CONNECTORID = "CONNECTORID";
    public static final String RESERVATIONID = "RESERVATIONID";

    @Override
    public String getOperationName() {

        return StartTransaction.class.getName();
    }

    @Override
    StartTransactionRequest createRequest(Map<String, ?> parms) {
        StartTransactionRequest request = new StartTransactionRequest();
        request.setConnectorId(Integer.parseInt(parms.get(CONNECTORID).toString()));
        request.setIdTag(parms.get(ID_TAG).toString());
        request.setMeterStart(XMLGregorianCalendarUtil.getMeter());
        request.setTimestamp(XMLGregorianCalendarUtil.createXMLGregorianCalendar());
        
        Object reservationId = parms.get(RESERVATIONID);
        if (reservationId != null) {
            request.setReservationId(Integer.parseInt(reservationId.toString()));
        }

        return request;
    }

}
