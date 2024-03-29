//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.06.03 at 04:24:07 PM EEST 
//


package com.github.yuri0x7c1.ofbiz.explorer.service.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}invoke" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{}attlist.group"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "invoke"
})
@XmlRootElement(name = "group")
public class Group {

    @XmlElement(required = true)
    protected List<Invoke> invoke;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "send-mode")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String sendMode;

    /**
     * Gets the value of the invoke property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the invoke property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvoke().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Invoke }
     * 
     * 
     */
    public List<Invoke> getInvoke() {
        if (invoke == null) {
            invoke = new ArrayList<Invoke>();
        }
        return this.invoke;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the sendMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendMode() {
        if (sendMode == null) {
            return "all";
        } else {
            return sendMode;
        }
    }

    /**
     * Sets the value of the sendMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendMode(String value) {
        this.sendMode = value;
    }

}
