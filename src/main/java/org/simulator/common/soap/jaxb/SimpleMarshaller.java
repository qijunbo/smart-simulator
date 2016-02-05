package org.simulator.common.soap.jaxb;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.w3c.dom.Document;

public interface SimpleMarshaller {

	Object ummarshal(Document document, String classPackage) throws JAXBException;

	Object ummarshal(SOAPMessage document, String classPackage) throws JAXBException, SOAPException;

	Document marshal(Object jaxb, String classPackage) throws ParserConfigurationException,
			JAXBException;

	SOAPMessage marshalSOAP(Object jaxb, String classPackage) throws ParserConfigurationException,
			JAXBException, SOAPException;

}
