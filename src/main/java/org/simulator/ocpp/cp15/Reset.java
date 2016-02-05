package org.simulator.ocpp.cp15;

import java.util.Map;

import org.simulator.chargepoint.model.ChargePoint;
import org.simulator.chargepoint.model.ChargePointRepository;
import org.simulator.common.Cache;
import org.simulator.ocpp.ChargeBoxStatus;
import org.simulator.ocpp.OcppOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.ocpp.cp201206.ResetRequest;
import com.ocpp.cp201206.ResetResponse;
import com.ocpp.cp201206.ResetStatus;

@Configuration
public class Reset extends DownwardsOperation<ResetRequest, ResetResponse> {

	@Autowired
	private ChargePointRepository repository;

	@Override
	public String getOperationName() {
		return ResetRequest.class.getName();
	}

	@Override
	ResetResponse createResponse(ResetRequest request, Map<String, ?> parms) throws Exception {

		String chargeBoxIdentity = parms.get(OcppOperation.CHARGE_BOX_IDENTITY).toString();

		ChargePoint chargePoint = repository.findBySerial(chargeBoxIdentity);

		for (int i = 1; i <= chargePoint.getConnectorNumber(); i++) {
			ChargeBoxStatus obj = getDeviceStatus(chargeBoxIdentity, i);
			if (obj.getTransaction_Id() != 0) {
				Cache.remove(obj.getTransaction_Id());
				Cache.remove(Cache.generateKey(chargeBoxIdentity, i));
			}
		}

		ResetResponse response = new ResetResponse();
		response.setStatus(ResetStatus.ACCEPTED);
		return response;
	}

}
