//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2017.05.17 at 02:28:17 PM EEST
//


package com.github.yuri0x7c1.ofbiz.explorer.entity.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 *         &lt;element ref="{}field" maxOccurs="unbounded"/>
 *         &lt;element ref="{}prim-key" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}relation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}index" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{}attlist.entity"/>
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
    "field",
    "primKey",
    "relation",
    "index"
})
@XmlRootElement(name = "entity")
public class Entity {

    protected String description;
    @XmlElement(required = true)
    protected List<Field> field;
    @XmlElement(name = "prim-key")
    protected List<PrimKey> primKey;
    protected List<Relation> relation;
    protected List<Index> index;
    @XmlAttribute(name = "entity-name", required = true)
    protected String entityName;
    @XmlAttribute(name = "table-name")
    protected String tableName;
    @XmlAttribute(name = "package-name", required = true)
    protected String packageName;
    @XmlAttribute(name = "default-resource-name")
    protected String defaultResourceName;
    @XmlAttribute(name = "dependent-on")
    protected String dependentOn;
    @XmlAttribute(name = "sequence-bank-size")
    protected String sequenceBankSize;
    @XmlAttribute(name = "enable-lock")
    protected Boolean enableLock;
    @XmlAttribute(name = "no-auto-stamp")
    protected Boolean noAutoStamp;
    @XmlAttribute(name = "never-cache")
    protected Boolean neverCache;
    @XmlAttribute(name = "never-check")
    protected Boolean neverCheck;
    @XmlAttribute(name = "auto-clear-cache")
    protected Boolean autoClearCache;
    @XmlAttribute(name = "redefinition")
    protected Boolean redefinition;
    @XmlAttribute(name = "title")
    protected String title;
    @XmlAttribute(name = "copyright")
    protected String copyright;
    @XmlAttribute(name = "author")
    protected String author;
    @XmlAttribute(name = "version")
    protected String version;

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
     * Gets the value of the field property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the field property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getField().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Field }
     *
     *
     */
    public List<Field> getField() {
        if (field == null) {
            field = new ArrayList<Field>();
        }
        return this.field;
    }

    /**
     * Gets the value of the primKey property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the primKey property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrimKey().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrimKey }
     *
     *
     */
    public List<PrimKey> getPrimKey() {
        if (primKey == null) {
            primKey = new ArrayList<PrimKey>();
        }
        return this.primKey;
    }

    /**
     * Gets the value of the relation property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relation property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelation().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Relation }
     *
     *
     */
    public List<Relation> getRelation() {
        if (relation == null) {
            relation = new ArrayList<Relation>();
        }
        return this.relation;
    }

    /**
     * Gets the value of the index property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the index property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndex().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Index }
     *
     *
     */
    public List<Index> getIndex() {
        if (index == null) {
            index = new ArrayList<Index>();
        }
        return this.index;
    }

    /**
     * Gets the value of the entityName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getEntityName() {
        return entityName;
    }

    /**
     * Sets the value of the entityName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setEntityName(String value) {
        this.entityName = value;
    }

    /**
     * Gets the value of the tableName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Sets the value of the tableName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTableName(String value) {
        this.tableName = value;
    }

    /**
     * Gets the value of the packageName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * Sets the value of the packageName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPackageName(String value) {
        this.packageName = value;
    }

    /**
     * Gets the value of the defaultResourceName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDefaultResourceName() {
        return defaultResourceName;
    }

    /**
     * Sets the value of the defaultResourceName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDefaultResourceName(String value) {
        this.defaultResourceName = value;
    }

    /**
     * Gets the value of the dependentOn property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDependentOn() {
        return dependentOn;
    }

    /**
     * Sets the value of the dependentOn property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDependentOn(String value) {
        this.dependentOn = value;
    }

    /**
     * Gets the value of the sequenceBankSize property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSequenceBankSize() {
        return sequenceBankSize;
    }

    /**
     * Sets the value of the sequenceBankSize property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSequenceBankSize(String value) {
        this.sequenceBankSize = value;
    }

    /**
     * Gets the value of the enableLock property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean getEnableLock() {
        if (enableLock == null) {
            return Boolean.FALSE;
        } else {
            return enableLock;
        }
    }

    /**
     * Sets the value of the enableLock property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setEnableLock(Boolean value) {
        this.enableLock = value;
    }

    /**
     * Gets the value of the noAutoStamp property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean getNoAutoStamp() {
        if (noAutoStamp == null) {
            return Boolean.FALSE;
        } else {
            return noAutoStamp;
        }
    }

    /**
     * Sets the value of the noAutoStamp property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setNoAutoStamp(Boolean value) {
        this.noAutoStamp = value;
    }

    /**
     * Gets the value of the neverCache property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean getNeverCache() {
        if (neverCache == null) {
            return Boolean.FALSE;
        } else {
            return neverCache;
        }
    }

    /**
     * Sets the value of the neverCache property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setNeverCache(Boolean value) {
        this.neverCache = value;
    }

    /**
     * Gets the value of the neverCheck property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean getNeverCheck() {
        if (neverCheck == null) {
            return Boolean.FALSE;
        } else {
            return neverCheck;
        }
    }

    /**
     * Sets the value of the neverCheck property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setNeverCheck(Boolean value) {
        this.neverCheck = value;
    }

    /**
     * Gets the value of the autoClearCache property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean getAutoClearCache() {
        if (autoClearCache == null) {
            return Boolean.TRUE;
        } else {
            return autoClearCache;
        }
    }

    /**
     * Sets the value of the autoClearCache property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setAutoClearCache(Boolean value) {
        this.autoClearCache = value;
    }

    /**
     * Gets the value of the redefinition property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean getRedefinition() {
        return redefinition;
    }

    /**
     * Sets the value of the redefinition property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setRedefinition(Boolean value) {
        this.redefinition = value;
    }

    /**
     * Gets the value of the title property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the copyright property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     * Sets the value of the copyright property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCopyright(String value) {
        this.copyright = value;
    }

    /**
     * Gets the value of the author property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the value of the author property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAuthor(String value) {
        this.author = value;
    }

    /**
     * Gets the value of the version property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Get map of fields
     */
    public Map<String, Field> getFieldMap() {
    	Map<String, Field> fieldMap = new HashMap<>();
    	for (Field field : getField()) {
    		fieldMap.put(field.getName(), field);
    	}
    	return fieldMap;
    }

    /**
     * Get primary key names
     */
    public List<String> getPrimaryKeyNames() {
    	List<String> primaryKeyNames = new ArrayList<>();
    	for (PrimKey primaryKey : getPrimKey()) {
    		primaryKeyNames.add(primaryKey.getField());
    	}
    	return primaryKeyNames;
    }

}
