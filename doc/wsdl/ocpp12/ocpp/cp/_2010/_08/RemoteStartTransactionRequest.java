//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.01.27 at 12:59:08 PM CST 
//


package ocpp.cp._2010._08;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Defines the RemoteStartTransaction.req PDU
 * 
 * <p>Java class for RemoteStartTransactionRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RemoteStartTransactionRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idTag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RemoteStartTransactionRequest", propOrder = {
    "idTag"
})
public class RemoteStartTransactionRequest {

    @XmlElement(required = true)
    protected String idTag;

    /**
     * Gets the value of the idTag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdTag() {
        return idTag;
    }

    /**
     * Sets the value of the idTag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdTag(String value) {
        this.idTag = value;
    }

}
