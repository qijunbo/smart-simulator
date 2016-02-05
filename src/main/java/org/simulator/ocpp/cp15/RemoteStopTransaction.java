package org.simulator.ocpp.cp15;

import java.util.Map;

import org.simulator.chargepoint.model.ChargePoint;
import org.simulator.chargepoint.model.ChargePointRepository;
import org.simulator.common.Cache;
import org.simulator.ocpp.ChargeBoxStatus;
import org.simulator.ocpp.OcppOperation;
import org.simulator.ocpp.OperationLocator;
import org.simulator.ocpp.cs15.StopTransaction;
import org.simulator.ocpp.cs15.UpwardsOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.ocpp.cp201206.RemoteStartStopStatus;
import com.ocpp.cp201206.RemoteStopTransactionRequest;
import com.ocpp.cp201206.RemoteStopTransactionResponse;

@Configuration
public class RemoteStopTransaction extends
		DownwardsOperation<RemoteStopTransactionRequest, RemoteStopTransactionResponse> {

	@Override
	public String getOperationName() {
		return RemoteStopTransactionRequest.class.getName();
	}

	@SuppressWarnings("unchecked")
	@Override
	RemoteStopTransactionResponse createResponse(RemoteStopTransactionRequest request,
			@SuppressWarnings("rawtypes") Map parms) throws Exception {

		String chargeBoxIdentity = parms.get(OcppOperation.CHARGE_BOX_IDENTITY).toString();

		parms.put(UpwardsOperation.CENTRAL_URL, getCentralURL(chargeBoxIdentity));
		parms.put(StopTransaction.TRANSACTIONID, request.getTransactionId());
		// parms.put(StartTransaction.ID_TAG, request.getIdTag());

		ChargeBoxStatus obj = (ChargeBoxStatus) Cache.get(request.getTransactionId());

		if (obj.getTransaction_Id() == request.getTransactionId()) {
			parms.put(StopTransaction.ID_TAG, obj.getId_tag());
			return accept(parms, request.getTransactionId());
		}

		return reject();

	}

	private String getCentralURL(String chargeBoxIdentity) {
		ChargePoint cp = repository.findBySerial(chargeBoxIdentity);
		return cp.getCentralURL();
		// return "http://test.op.spie.ievep.net/ws/OcppGateway";
	}

	public RemoteStopTransactionResponse accept(Map<String, ?> parms, int transactionId) {

		RemoteStopTransactionResponse response = new RemoteStopTransactionResponse();
		response.setStatus(RemoteStartStopStatus.ACCEPTED);

		try {
			sendStopTransaction(parms);

			cleanDeviceStatus(transactionId);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return response;
	}

	private void cleanDeviceStatus(int transactionId) {
		// clean the flag in cache.
		Cache.remove(transactionId);
		ChargeBoxStatus status = (ChargeBoxStatus) Cache.get(transactionId);

		Cache.remove(Cache.generateKey(status.getChargeBoxIdentity(), status.getConnector_Id()));
		// cleanDeviceStatus();
	}

	public void sendStopTransaction(Map<String, ?> parms) throws Exception {

		StopTransaction stopTransactionService = (StopTransaction) locator.get(new OcppOperation.Identifier(OCPP.ocpp15
				.name(), StopTransaction.class.getName()));

		System.out.println(stopTransactionService.execute(null, parms));

	}

	public RemoteStopTransactionResponse reject() {
		RemoteStopTransactionResponse response = new RemoteStopTransactionResponse();
		response.setStatus(RemoteStartStopStatus.REJECTED);
		return response;
	}

	@Autowired
	private ChargePointRepository repository;

	@Autowired
	private OperationLocator locator;

}
