package org.simulator.ocpp.cp15;

import java.util.Map;

import org.springframework.context.annotation.Configuration;

import com.ocpp.cp201206.UnlockConnectorRequest;
import com.ocpp.cp201206.UnlockConnectorResponse;
import com.ocpp.cp201206.UnlockStatus;

@Configuration
public class UnlockConnector extends DownwardsOperation<UnlockConnectorRequest, UnlockConnectorResponse> {

    @Override
    public String getOperationName() {
        return UnlockConnectorRequest.class.getName();
    }

    @Override
    UnlockConnectorResponse createResponse(UnlockConnectorRequest request, Map<String, ?> parms) throws Exception {
        UnlockConnectorResponse response = new UnlockConnectorResponse();
        response.setStatus(UnlockStatus.ACCEPTED);
        return response;
    }

}
