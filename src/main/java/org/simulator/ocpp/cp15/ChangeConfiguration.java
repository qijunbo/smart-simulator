package org.simulator.ocpp.cp15;

import java.util.Map;

import org.springframework.context.annotation.Configuration;

import com.ocpp.cp201206.ChangeConfigurationRequest;
import com.ocpp.cp201206.ChangeConfigurationResponse;
import com.ocpp.cp201206.ConfigurationStatus;

@Configuration
public class ChangeConfiguration extends DownwardsOperation<ChangeConfigurationRequest, ChangeConfigurationResponse> {

    @Override
    public String getOperationName() {
        return ChangeConfigurationRequest.class.getName();
    }

    @Override
    ChangeConfigurationResponse createResponse(ChangeConfigurationRequest request, Map<String, ?> parms)
            throws Exception {
        ChangeConfigurationResponse response = new ChangeConfigurationResponse();
        response.setStatus(ConfigurationStatus.ACCEPTED);
        return response;
    }

}
