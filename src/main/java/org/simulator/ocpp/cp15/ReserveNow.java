package org.simulator.ocpp.cp15;

import java.util.Map;

import org.simulator.common.Cache;
import org.simulator.ocpp.ChargeBoxStatus;
import org.simulator.ocpp.OcppOperation;
import org.springframework.context.annotation.Configuration;

import com.ocpp.cp201206.ReservationStatus;
import com.ocpp.cp201206.ReserveNowRequest;
import com.ocpp.cp201206.ReserveNowResponse;
import com.ocpp.cs201206.ChargePointStatus;

@Configuration
public class ReserveNow extends DownwardsOperation<ReserveNowRequest, ReserveNowResponse> {

	@Override
	public String getOperationName() {
		return ReserveNowRequest.class.getName();
	}

	@Override
	ReserveNowResponse createResponse(ReserveNowRequest request, Map<String, ?> parms) throws Exception {

		String chargeBoxIdentity = parms.get(OcppOperation.CHARGE_BOX_IDENTITY).toString();
		ChargeBoxStatus old = getDeviceStatus(chargeBoxIdentity, request.getConnectorId());
		if( old == null){
			return accept(request, chargeBoxIdentity);
		}
		
		// device is fault
		if (ChargePointStatus.FAULTED.equals(old.getStatus())) {
			return fault();
		}

		// device is in use
		if (ChargePointStatus.OCCUPIED.equals(old.getStatus())) {
			auditService.auditResponse(chargeBoxIdentity, old.toString());
			return reject();
		}

		// the same driver reserved again.
		if (old.getTransaction_Id() == request.getReservationId()) {
			return accept(request, chargeBoxIdentity);
		} else {
			auditService.auditResponse(chargeBoxIdentity, old.toString());
			return reject();
		}

	}

	private ReserveNowResponse accept(ReserveNowRequest request, String chargeBoxIdentity) {

		ChargeBoxStatus status = new ChargeBoxStatus(ChargePointStatus.RESERVED);
		status.setChargeBoxIdentity(chargeBoxIdentity);
		status.setConnector_Id(request.getConnectorId());
		status.setExpiryDate(request.getExpiryDate());
		status.setTransaction_Id(request.getReservationId());

		putReservation(status);
		ReserveNowResponse response = new ReserveNowResponse();
		response.setStatus(ReservationStatus.ACCEPTED);
		return response;
	}

	private void putReservation(ChargeBoxStatus status) {

		Cache.put(Cache.generateKey(status.getChargeBoxIdentity(), status.getConnector_Id()), status);
		Cache.put(status.getTransaction_Id(), status);

	}

	private ReserveNowResponse reject() {
		ReserveNowResponse response = new ReserveNowResponse();
		response.setStatus(ReservationStatus.REJECTED);
		return response;
	}

	private ReserveNowResponse fault() {
		ReserveNowResponse response = new ReserveNowResponse();
		response.setStatus(ReservationStatus.FAULTED);
		return response;
	}

}
