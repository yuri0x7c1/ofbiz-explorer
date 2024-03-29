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
 *         &lt;element ref="{}member-entity" maxOccurs="unbounded"/>
 *         &lt;element ref="{}alias-all" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}alias" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}view-link" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}relation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}entity-condition" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{}attlist.view-entity"/>
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
    "memberEntity",
    "aliasAll",
    "alias",
    "viewLink",
    "relation",
    "entityCondition"
})
@XmlRootElement(name = "view-entity")
public class ViewEntity {

    protected String description;
    @XmlElement(name = "member-entity", required = true)
    protected List<MemberEntity> memberEntity;
    @XmlElement(name = "alias-all")
    protected List<AliasAll> aliasAll;
    protected List<Alias> alias;
    @XmlElement(name = "view-link")
    protected List<ViewLink> viewLink;
    protected List<Relation> relation;
    @XmlElement(name = "entity-condition")
    protected EntityCondition entityCondition;
    @XmlAttribute(name = "entity-name", required = true)
    protected String entityName;
    @XmlAttribute(name = "package-name", required = true)
    protected String packageName;
    @XmlAttribute(name = "dependent-on")
    protected String dependentOn;
    @XmlAttribute(name = "default-resource-name")
    protected String defaultResourceName;
    @XmlAttribute(name = "never-cache")
    protected Boolean neverCache;
    @XmlAttribute(name = "auto-clear-cache")
    protected Boolean autoClearCache;
    @XmlAttribute(name = "title")
    protected String title;
    @XmlAttribute(name = "copyright")
    protected String copyright;
    @XmlAttribute(name = "author")
    protected String author;
    @XmlAttribute(name = "version")
    protected String version;
    @XmlAttribute(name = "redefinition")
    protected Boolean redefinition;

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
     * Gets the value of the memberEntity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the memberEntity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMemberEntity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MemberEntity }
     * 
     * 
     */
    public List<MemberEntity> getMemberEntity() {
        if (memberEntity == null) {
            memberEntity = new ArrayList<MemberEntity>();
        }
        return this.memberEntity;
    }

    /**
     * Gets the value of the aliasAll property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the aliasAll property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAliasAll().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AliasAll }
     * 
     * 
     */
    public List<AliasAll> getAliasAll() {
        if (aliasAll == null) {
            aliasAll = new ArrayList<AliasAll>();
        }
        return this.aliasAll;
    }

    /**
     * Gets the value of the alias property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the alias property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAlias().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Alias }
     * 
     * 
     */
    public List<Alias> getAlias() {
        if (alias == null) {
            alias = new ArrayList<Alias>();
        }
        return this.alias;
    }

    /**
     * Gets the value of the viewLink property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the viewLink property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getViewLink().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ViewLink }
     * 
     * 
     */
    public List<ViewLink> getViewLink() {
        if (viewLink == null) {
            viewLink = new ArrayList<ViewLink>();
        }
        return this.viewLink;
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

}
