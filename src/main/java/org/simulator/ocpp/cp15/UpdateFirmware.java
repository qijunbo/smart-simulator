package org.simulator.ocpp.cp15;

import java.util.Map;

import org.springframework.context.annotation.Configuration;

import com.ocpp.cp201206.UpdateFirmwareRequest;
import com.ocpp.cp201206.UpdateFirmwareResponse;

@Configuration
public class UpdateFirmware extends DownwardsOperation<UpdateFirmwareRequest, UpdateFirmwareResponse> {

    @Override
    public String getOperationName() {
        return UpdateFirmwareRequest.class.getName();
    }

    @Override
    UpdateFirmwareResponse createResponse(UpdateFirmwareRequest request, Map<String, ?> parms) throws Exception {
        UpdateFirmwareResponse response = new UpdateFirmwareResponse();
        return response;
    }

}
