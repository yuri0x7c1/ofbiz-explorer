//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.06.03 at 04:24:07 PM EEST 
//


package com.github.yuri0x7c1.ofbiz.explorer.service.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="estimation-size" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="estimation-time" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="smoothing" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "metric")
public class Metric {

    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "estimation-size")
    protected String estimationSize;
    @XmlAttribute(name = "estimation-time")
    protected String estimationTime;
    @XmlAttribute(name = "smoothing")
    protected String smoothing;

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
     * Gets the value of the estimationSize property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstimationSize() {
        return estimationSize;
    }

    /**
     * Sets the value of the estimationSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstimationSize(String value) {
        this.estimationSize = value;
    }

    /**
     * Gets the value of the estimationTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstimationTime() {
        return estimationTime;
    }

    /**
     * Sets the value of the estimationTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstimationTime(String value) {
        this.estimationTime = value;
    }

    /**
     * Gets the value of the smoothing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmoothing() {
        return smoothing;
    }

    /**
     * Sets the value of the smoothing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmoothing(String value) {
        this.smoothing = value;
    }

}
