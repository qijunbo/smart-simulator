package org.simulator.ocpp.cp15;

import java.util.Map;

import org.springframework.context.annotation.Configuration;

import com.ocpp.cp201206.GetConfigurationRequest;
import com.ocpp.cp201206.GetConfigurationResponse;
import com.ocpp.cp201206.KeyValue;

@Configuration
public class GetConfiguration extends DownwardsOperation<GetConfigurationRequest, GetConfigurationResponse> {

    @Override
    public String getOperationName() {
        return GetConfigurationRequest.class.getName();
    }

    @Override
    GetConfigurationResponse createResponse(GetConfigurationRequest request, Map<String, ?> parms) throws Exception {
        GetConfigurationResponse response = new GetConfigurationResponse();
        KeyValue keyValue = new KeyValue();
        keyValue.setKey("How much shoud I pay?");
        keyValue.setValue("10000");
        response.getConfigurationKey().add(keyValue);
        return response;
    }

}
