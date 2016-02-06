package org.simulator.ocpp.cs15;

import java.io.IOException;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.simulator.common.soap.SOAPFactory;
import org.simulator.common.soap.jaxb.SimpleMarshaller;
import org.simulator.ocpp.AbstractOperatoin;
import org.simulator.ocpp.OcppOperation;
import org.simulator.ocpp.cp15.OCPP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

public abstract class UpwardsOperation<R> extends AbstractOperatoin implements ResponseHandler<String> {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public static final String CENTRAL_URL = "CENTRAL_URL";

	@Autowired
	protected SOAPFactory soapFactory;

	@Autowired
	protected SimpleMarshaller marshaller;

	@Override
	public String getProtocol() {
		return OCPP.ocpp15.name();
	}

	@Override
	public String execute(Object nullable, Map<String, ?> parms) throws Exception {

		R request = createRequest(parms);

		return processHttpRequest(request, parms);

	}

	private String processHttpRequest(R request, Map<String, ?> parms) throws Exception, IOException,
			ClientProtocolException {
		CloseableHttpClient httpClient = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost(parms.get(CENTRAL_URL).toString());
		httpPost.setHeader("Accept", MediaType.APPLICATION_XML_VALUE);
		httpPost.setHeader("Content-Type", "application/soap+xml");
		HttpEntity entity = createRequestHttpEntity(request, parms);
		httpPost.setEntity(entity);

		String response = httpClient.execute(httpPost, this);
		auditService.auditResponse(parms.get(OcppOperation.CHARGE_BOX_IDENTITY).toString(), response);
		
		if(log.isDebugEnabled()){
			log.debug("#Response to chargepoint: " + response);
		}
		
		httpClient.close();
		return response;
	}

	private HttpEntity createRequestHttpEntity(R request, Map<String, ?> parms) throws Exception {
		SOAPMessage soapMessage = marshaller.marshalSOAP(request, request.getClass().getPackage().getName());

		soapMessage.getSOAPHeader()
				.addChildElement(new QName("urn://Ocpp/Cs/2012/06/", OcppOperation.CHARGE_BOX_IDENTITY))
				.addTextNode(parms.get(OcppOperation.CHARGE_BOX_IDENTITY).toString());

		String soapXML = soapFactory.toString(soapMessage);

		auditService.auditRequest(parms.get(OcppOperation.CHARGE_BOX_IDENTITY).toString(), soapXML);

		if(log.isDebugEnabled()){
			log.debug("#Send from chargepoint: " + soapXML);
		}

		StringEntity input = new StringEntity(soapXML, Charsets.UTF_8);
		input.setContentType(MediaType.TEXT_XML_VALUE);
		input.setContentEncoding("UTF-8");
		return input;
	}

	@Override
	public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		return EntityUtils.toString(response.getEntity());
	}

	abstract R createRequest(Map<String, ?> parms);
}
