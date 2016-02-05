package org.simulator.ocpp.cp15;

import java.util.Map;

import org.springframework.context.annotation.Configuration;

import com.ocpp.cp201206.GetLocalListVersionRequest;
import com.ocpp.cp201206.GetLocalListVersionResponse;

@Configuration
public class GetLocalListVersion extends DownwardsOperation<GetLocalListVersionRequest, GetLocalListVersionResponse> {

    @Override
    public String getOperationName() {
        return GetLocalListVersionRequest.class.getName();
    }

    @Override
    GetLocalListVersionResponse createResponse(GetLocalListVersionRequest request, Map<String, ?> parms)
            throws Exception {
        GetLocalListVersionResponse response = new GetLocalListVersionResponse();
        response.setListVersion(2);
        return response;
    }

}
