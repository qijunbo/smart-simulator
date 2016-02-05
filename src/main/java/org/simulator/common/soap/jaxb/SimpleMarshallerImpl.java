package org.simulator.common.soap.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.simulator.common.soap.SOAPFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.w3c.dom.Document;

@Configuration
public class SimpleMarshallerImpl implements SimpleMarshaller {

	@Autowired
	private SOAPFactory factory;
 

	@Override
	public Object ummarshal(Document document, String classPackage)
			throws JAXBException {
		Unmarshaller u = getJAXBContext(classPackage).createUnmarshaller();

		return u.unmarshal(document);

	}

	@Override
	public Document marshal(Object jaxb, String classPackage)
			throws ParserConfigurationException, JAXBException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		// dbf.setNamespaceAware(true);
		Document document = dbf.newDocumentBuilder().newDocument();

		Marshaller m = getJAXBContext(classPackage).createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(jaxb, document);
		return document;
	}

	private JAXBContext getJAXBContext(String classPackage) throws JAXBException {
		return JAXBContext.newInstance(classPackage);
	}

	@Override
	public Object ummarshal(SOAPMessage soapMessage, String classPackage)
			throws JAXBException, SOAPException {
		return ummarshal(soapMessage.getSOAPBody().extractContentAsDocument(),
				classPackage);

	}

	@Override
	public SOAPMessage marshalSOAP(Object jaxb, String classPackage)
			throws ParserConfigurationException, JAXBException, SOAPException {
		SOAPMessage message = factory.createSOAPMessage();
		message.getSOAPBody().addDocument(marshal(jaxb, classPackage));
		return message;
	}

}
