package org.simulator.ocpp.cp15;

import java.util.Map;

import org.springframework.context.annotation.Configuration;

import com.ocpp.cp201206.ClearCacheRequest;
import com.ocpp.cp201206.ClearCacheResponse;
import com.ocpp.cp201206.ClearCacheStatus;

@Configuration
public class ClearCache extends DownwardsOperation<ClearCacheRequest, ClearCacheResponse> {

    @Override
    public String getOperationName() {
        return ClearCacheRequest.class.getName();
    }

    @Override
    ClearCacheResponse createResponse(ClearCacheRequest request, Map<String, ?> parms) throws Exception {
        ClearCacheResponse response = new ClearCacheResponse();
        response.setStatus(ClearCacheStatus.ACCEPTED);
        return response;
    }

}
