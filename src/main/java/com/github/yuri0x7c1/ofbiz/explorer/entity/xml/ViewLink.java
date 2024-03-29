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
 *       &lt;sequence>
 *         &lt;element ref="{}description" minOccurs="0"/>
 *         &lt;element ref="{}key-map" maxOccurs="unbounded"/>
 *         &lt;element ref="{}entity-condition" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{}attlist.view-link"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "description",
    "keyMap",
    "entityCondition"
})
@XmlRootElement(name = "view-link")
public class ViewLink {

    protected String description;
    @XmlElement(name = "key-map", required = true)
    protected List<KeyMap> keyMap;
    @XmlElement(name = "entity-condition")
    protected EntityCondition entityCondition;
    @XmlAttribute(name = "entity-alias", required = true)
    protected String entityAlias;
    @XmlAttribute(name = "rel-entity-alias", required = true)
    protected String relEntityAlias;
    @XmlAttribute(name = "rel-optional")
    protected Boolean relOptional;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the keyMap property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the keyMap property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKeyMap().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KeyMap }
     * 
     * 
     */
    public List<KeyMap> getKeyMap() {
        if (keyMap == null) {
            keyMap = new ArrayList<KeyMap>();
        }
        return this.keyMap;
    }

    /**
     * Gets the value of the entityCondition property.
     * 
     * @return
     *     possible object is
     *     {@link EntityCondition }
     *     
     */
    public EntityCondition getEntityCondition() {
        return entityCondition;
    }

    /**
     * Sets the value of the entityCondition property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityCondition }
     *     
     */
    public void setEntityCondition(EntityCondition value) {
        this.entityCondition = value;
    }

    /**
     * Gets the value of the entityAlias property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntityAlias() {
        return entityAlias;
    }

    /**
     * Sets the value of the entityAlias property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntityAlias(String value) {
        this.entityAlias = value;
    }

    /**
     * Gets the value of the relEntityAlias property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelEntityAlias() {
        return relEntityAlias;
    }

    /**
     * Sets the value of the relEntityAlias property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelEntityAlias(String value) {
        this.relEntityAlias = value;
    }

    /**
     * Gets the value of the relOptional property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getRelOptional() {
        if (relOptional == null) {
            return Boolean.FALSE;
        } else {
            return relOptional;
        }
    }

    /**
     * Sets the value of the relOptional property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRelOptional(Boolean value) {
        this.relOptional = value;
    }

}
