package org.simulator.ocpp.cp15;

import java.util.Map;

import org.springframework.context.annotation.Configuration;

import com.ocpp.cp201206.GetDiagnosticsRequest;
import com.ocpp.cp201206.GetDiagnosticsResponse;

@Configuration
public class GetDiagnostics extends DownwardsOperation<GetDiagnosticsRequest, GetDiagnosticsResponse> {

    @Override
    public String getOperationName() {
        return GetDiagnosticsRequest.class.getName();
    }

    @Override
    GetDiagnosticsResponse createResponse(GetDiagnosticsRequest request, Map<String, ?> parms) throws Exception {
        GetDiagnosticsResponse response = new GetDiagnosticsResponse();
        response.setFileName("http://ieqq.iteye.com/blog/2114077");
        return response;
    }

}
