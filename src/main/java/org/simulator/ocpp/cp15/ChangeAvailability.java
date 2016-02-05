package org.simulator.ocpp.cp15;

import java.util.Map;

import org.simulator.common.Cache;
import org.simulator.ocpp.ChargeBoxStatus;
import org.simulator.ocpp.OcppOperation;
import org.springframework.context.annotation.Configuration;

import com.ocpp.cp201206.AvailabilityStatus;
import com.ocpp.cp201206.AvailabilityType;
import com.ocpp.cp201206.ChangeAvailabilityRequest;
import com.ocpp.cp201206.ChangeAvailabilityResponse;

@Configuration
public class ChangeAvailability
		extends
		DownwardsOperation<ChangeAvailabilityRequest, ChangeAvailabilityResponse> {

	@Override
	public String getOperationName() {
		return ChangeAvailabilityRequest.class.getName();
	}

	@Override
	ChangeAvailabilityResponse createResponse(
			ChangeAvailabilityRequest request, Map<String, ?> parms)
			throws Exception {

		AvailabilityType type = request.getType();
		String chargeBoxIdentity = parms.get(OcppOperation.CHARGE_BOX_IDENTITY)
				.toString();
		int connectorId = request.getConnectorId();

		if (AvailabilityType.OPERATIVE.equals(type)) {
			enableChargePoint(chargeBoxIdentity, connectorId);
		} else {
			disableChargePoint(chargeBoxIdentity, connectorId);
		}

		ChangeAvailabilityResponse response = new ChangeAvailabilityResponse();
		response.setStatus(AvailabilityStatus.ACCEPTED);
		return response;
	}

	//set the device to broken-down.
	private void disableChargePoint(String chargeBoxIdentity, int connectorId) {
		Cache.put(Cache.generateKey(chargeBoxIdentity, connectorId), ChargeBoxStatus.FAULT);
	}

	private void enableChargePoint(String chargeBoxIdentity, int connectorId) {
		Cache.remove(Cache.generateKey(chargeBoxIdentity, connectorId));
	}

}
