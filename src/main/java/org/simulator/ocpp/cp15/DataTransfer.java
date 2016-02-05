package org.simulator.ocpp.cp15;

import java.util.Map;

import org.springframework.context.annotation.Configuration;

import com.ocpp.cp201206.DataTransferRequest;
import com.ocpp.cp201206.DataTransferResponse;
import com.ocpp.cp201206.DataTransferStatus;

@Configuration
public class DataTransfer extends DownwardsOperation<DataTransferRequest, DataTransferResponse> {

    @Override
    public String getOperationName() {
        return DataTransferRequest.class.getName();
    }

    @Override
    DataTransferResponse createResponse(DataTransferRequest request, Map<String, ?> parms) throws Exception {
        DataTransferResponse response = new DataTransferResponse();
        response.setStatus(DataTransferStatus.ACCEPTED);
        return response;
    }

}
