//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.05.17 at 02:28:17 PM EEST 
//


package com.github.yuri0x7c1.ofbiz.explorer.entity.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
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
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{}complex-alias"/>
 *         &lt;element ref="{}complex-alias-field"/>
 *       &lt;/choice>
 *       &lt;attGroup ref="{}attlist.complex-alias"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "complexAliasOrComplexAliasField"
})
@XmlRootElement(name = "complex-alias")
public class ComplexAlias {

    @XmlElements({
        @XmlElement(name = "complex-alias", type = ComplexAlias.class),
        @XmlElement(name = "complex-alias-field", type = ComplexAliasField.class)
    })
    protected List<Object> complexAliasOrComplexAliasField;
    @XmlAttribute(name = "operator", required = true)
    protected String operator;

    /**
     * Gets the value of the complexAliasOrComplexAliasField property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the complexAliasOrComplexAliasField property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComplexAliasOrComplexAliasField().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ComplexAlias }
     * {@link ComplexAliasField }
     * 
     * 
     */
    public List<Object> getComplexAliasOrComplexAliasField() {
        if (complexAliasOrComplexAliasField == null) {
            complexAliasOrComplexAliasField = new ArrayList<Object>();
        }
        return this.complexAliasOrComplexAliasField;
    }

    /**
     * Gets the value of the operator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Sets the value of the operator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperator(String value) {
        this.operator = value;
    }

}
