package org.simulator.ocpp.cp15;

import java.util.Map;

import org.simulator.common.Cache;
import org.simulator.ocpp.ChargeBoxStatus;
import org.springframework.context.annotation.Configuration;

import com.ocpp.cp201206.CancelReservationRequest;
import com.ocpp.cp201206.CancelReservationResponse;
import com.ocpp.cp201206.CancelReservationStatus;

@Configuration
public class CancelReservation extends DownwardsOperation<CancelReservationRequest, CancelReservationResponse> {

	@Override
	public String getOperationName() {
		return CancelReservationRequest.class.getName();
	}

	@Override
	CancelReservationResponse createResponse(CancelReservationRequest request, Map<String, ?> parms) throws Exception {

		ChargeBoxStatus old = (ChargeBoxStatus) Cache.get(request.getReservationId());

		if (old != null && (old.getTransaction_Id() == request.getReservationId())) {
			return accept(old);
		}
		
		auditService.auditResponse(String.valueOf(request.getReservationId()),
				"You haven't make a reservation with this id: " + request.getReservationId());
		return reject();

	}

	private CancelReservationResponse reject() {
		CancelReservationResponse response = new CancelReservationResponse();
		response.setStatus(CancelReservationStatus.REJECTED);
		return response;
	}

	private CancelReservationResponse accept(ChargeBoxStatus old) {
		Cache.remove(old.getTransaction_Id());
		Cache.remove(Cache.generateKey(old.getChargeBoxIdentity(), old.getConnector_Id()));

		CancelReservationResponse response = new CancelReservationResponse();
		response.setStatus(CancelReservationStatus.ACCEPTED);
		return response;
	}

}
