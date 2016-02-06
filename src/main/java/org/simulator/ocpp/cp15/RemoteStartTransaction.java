package org.simulator.ocpp.cp15;

import java.io.IOException;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.soap.SOAPException;

import org.common.util.soap.XMLGregorianCalendarUtil;
import org.simulator.chargepoint.model.ChargePoint;
import org.simulator.chargepoint.model.ChargePointRepository;
import org.simulator.common.Cache;
import org.simulator.ocpp.BusinessException;
import org.simulator.ocpp.ChargeBoxStatus;
import org.simulator.ocpp.OcppOperation;
import org.simulator.ocpp.OperationLocator;
import org.simulator.ocpp.cs15.StartTransaction;
import org.simulator.ocpp.cs15.UpwardsOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.ocpp.cp201206.RemoteStartStopStatus;
import com.ocpp.cp201206.RemoteStartTransactionRequest;
import com.ocpp.cp201206.RemoteStartTransactionResponse;
import com.ocpp.cs201206.ChargePointStatus;
import com.ocpp.cs201206.StartTransactionResponse;

@Configuration
public class RemoteStartTransaction extends
		DownwardsOperation<RemoteStartTransactionRequest, RemoteStartTransactionResponse> {

	@Autowired
	private ChargePointRepository repository;

	@Autowired
	private OperationLocator locator;

	@Override
	public String getOperationName() {
		return RemoteStartTransactionRequest.class.getName();
	}

	@SuppressWarnings("unchecked")
	@Override
	RemoteStartTransactionResponse createResponse(RemoteStartTransactionRequest request,
			@SuppressWarnings("rawtypes") Map parms) throws Exception {

		String chargeBoxIdentity = parms.get(OcppOperation.CHARGE_BOX_IDENTITY).toString();

		parms.put(StartTransaction.ID_TAG, request.getIdTag());
		parms.put(StartTransaction.CONNECTORID, request.getConnectorId());
		parms.put(UpwardsOperation.CENTRAL_URL, getCentralURL(chargeBoxIdentity));

		ChargeBoxStatus old = getDeviceStatus(chargeBoxIdentity, request.getConnectorId());
		if (old == null) {
			// if no reservation
			return accept(parms, old);
		}
		// some body is using this connector.
		if (ChargePointStatus.OCCUPIED.equals(old.getStatus())) {
			auditService.auditRequest(chargeBoxIdentity, old.toString());
			return reject();
		}
		// device is faulted
		if (ChargePointStatus.FAULTED.equals(old.getStatus())) {
			auditService.auditRequest(chargeBoxIdentity, old.toString());
			return reject();
		}

		XMLGregorianCalendar calendar = XMLGregorianCalendarUtil.createXMLGregorianCalendar();
		if (old.getExpiryDate().compare(calendar) < 0) {
			// if reservation is expired
			return accept(parms, old);
		} else if (old.getId_tag().equals(request.getIdTag())) {
			// if it's my reservation
			return accept(parms, old);
		} else {
			auditService.auditRequest(chargeBoxIdentity, old.toString());
			return reject();
		}

	}

	private String getCentralURL(String chargeBoxIdentity) {
		ChargePoint cp = repository.findBySerial(chargeBoxIdentity);
		return cp.getCentralURL();
		// return "http://op.spie.ievep.net/ws/OcppGateway";
	}

	public RemoteStartTransactionResponse accept(Map<String, ?> parms, ChargeBoxStatus status) {

		RemoteStartTransactionResponse response = new RemoteStartTransactionResponse();
		response.setStatus(RemoteStartStopStatus.ACCEPTED);

		try {
			sendStartTransaction(parms, status);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return response;
	}

	private void setDeviceStatus(ChargeBoxStatus status, int newTransactionId) {
		Cache.remove(status.getTransaction_Id());
		status.setStatus(ChargePointStatus.OCCUPIED);
		status.setTransaction_Id(newTransactionId);
		Cache.put(newTransactionId, status);
	}

	public void sendStartTransaction(Map<String, ?> parms, ChargeBoxStatus status) throws Exception {

		StartTransaction startTransactionService = (StartTransaction) locator.get(new OcppOperation.Identifier(
				OCPP.ocpp15.name(), StartTransaction.class.getName()));
		String startResponse = startTransactionService.execute(null, parms);

		try {
			JAXBElement<?> jaxb = (JAXBElement<?>) marshaller.ummarshal(soapFactory.readSOAPMessage(startResponse),
					StartTransactionResponse.class.getPackage().getName());
			StartTransactionResponse transactionResponse = (StartTransactionResponse) jaxb.getValue();
			setDeviceStatus(status, transactionResponse.getTransactionId());
		} catch (JAXBException e) {
			throw new BusinessException(BusinessException.UNRECOGNISABLE_MESSAGE + startResponse, e);
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

	}

	public RemoteStartTransactionResponse reject() {
		RemoteStartTransactionResponse response = new RemoteStartTransactionResponse();
		response.setStatus(RemoteStartStopStatus.REJECTED);
		return response;
	}

}
