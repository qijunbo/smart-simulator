package org.simulator.ocpp.cs15;

import java.util.Map;

import org.common.util.soap.XMLGregorianCalendarUtil;
import org.springframework.context.annotation.Configuration;

import com.ocpp.cs201206.StopTransactionRequest;

@Configuration
public class StopTransaction extends UpwardsOperation<StopTransactionRequest> {

    public static final String ID_TAG = "ID_TAG";
    public static final String TRANSACTIONID = "TRANSACTIONID";

    @Override
    public String getOperationName() {

        return StopTransaction.class.getName();
    }

    @Override
    StopTransactionRequest createRequest(Map<String, ?> parms) {
        StopTransactionRequest request = new StopTransactionRequest();

        request.setIdTag(parms.get(ID_TAG).toString());
        request.setMeterStop(XMLGregorianCalendarUtil.getMeter());
        request.setTimestamp(XMLGregorianCalendarUtil.createXMLGregorianCalendar());
        request.setTransactionId(Integer.parseInt(parms.get(TRANSACTIONID).toString()));
        return request;

    }

}
