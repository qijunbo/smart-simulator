package org.simulator.ocpp.cp15;

import java.util.Map;

import org.springframework.context.annotation.Configuration;

import com.ocpp.cp201206.SendLocalListRequest;
import com.ocpp.cp201206.SendLocalListResponse;
import com.ocpp.cp201206.UpdateStatus;

@Configuration
public class SendLocalList extends DownwardsOperation<SendLocalListRequest, SendLocalListResponse> {

    @Override
    public String getOperationName() {
        return SendLocalListRequest.class.getName();
    }

    @Override
    SendLocalListResponse createResponse(SendLocalListRequest request, Map<String, ?> parms) throws Exception {
        SendLocalListResponse response = new SendLocalListResponse();
        response.setStatus(UpdateStatus.ACCEPTED);
        return response;
    }

}
