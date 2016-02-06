package org.simulator.ocpp;

import javax.xml.datatype.XMLGregorianCalendar;

import com.mongodb.util.JSON;
import com.ocpp.cs201206.ChargePointStatus;

public class ChargeBoxStatus {

	public static final ChargeBoxStatus FAULT = new ChargeBoxStatus(ChargePointStatus.FAULTED);

	public ChargeBoxStatus() {
		// TODO Auto-generated constructor stub
	}

	public ChargeBoxStatus(ChargePointStatus status) {
		this.status = status;
	}

	private ChargePointStatus status;

	private String id_tag;

	private String chargeBoxIdentity;

	private int connector_Id;

	private int transaction_Id;

	private XMLGregorianCalendar expiryDate;

	public ChargePointStatus getStatus() {
		return status;
	}

	public void setStatus(ChargePointStatus status) {
		this.status = status;
	}

	public String getId_tag() {
		return id_tag;
	}

	public void setId_tag(String id_tag) {
		this.id_tag = id_tag;
	}

	public String getChargeBoxIdentity() {
		return chargeBoxIdentity;
	}

	public void setChargeBoxIdentity(String chargeBoxIdentity) {
		this.chargeBoxIdentity = chargeBoxIdentity;
	}

	public int getConnector_Id() {
		return connector_Id;
	}

	public void setConnector_Id(int connector_Id) {
		this.connector_Id = connector_Id;
	}

	public int getTransaction_Id() {
		return transaction_Id;
	}

	public void setTransaction_Id(int transaction_Id) {
		this.transaction_Id = transaction_Id;
	}

	public XMLGregorianCalendar getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(XMLGregorianCalendar expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Override
	public String toString() {
		return  JSON.serialize(this);
	}

	
	
}
