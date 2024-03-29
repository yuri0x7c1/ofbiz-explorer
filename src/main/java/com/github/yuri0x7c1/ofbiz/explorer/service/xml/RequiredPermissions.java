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
 *         &lt;element ref="{}check-permission" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}check-role-member" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}permission-service" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{}attlist.required-permissions"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "checkPermission",
    "checkRoleMember",
    "permissionService"
})
@XmlRootElement(name = "required-permissions")
public class RequiredPermissions {

    @XmlElement(name = "check-permission")
    protected List<CheckPermission> checkPermission;
    @XmlElement(name = "check-role-member")
    protected List<CheckRoleMember> checkRoleMember;
    @XmlElement(name = "permission-service")
    protected List<PermissionService> permissionService;
    @XmlAttribute(name = "join-type", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String joinType;

    /**
     * Gets the value of the checkPermission property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the checkPermission property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCheckPermission().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CheckPermission }
     * 
     * 
     */
    public List<CheckPermission> getCheckPermission() {
        if (checkPermission == null) {
            checkPermission = new ArrayList<CheckPermission>();
        }
        return this.checkPermission;
    }

    /**
     * Gets the value of the checkRoleMember property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the checkRoleMember property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCheckRoleMember().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CheckRoleMember }
     * 
     * 
     */
    public List<CheckRoleMember> getCheckRoleMember() {
        if (checkRoleMember == null) {
            checkRoleMember = new ArrayList<CheckRoleMember>();
        }
        return this.checkRoleMember;
    }

    /**
     * Gets the value of the permissionService property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the permissionService property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPermissionService().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PermissionService }
     * 
     * 
     */
    public List<PermissionService> getPermissionService() {
        if (permissionService == null) {
            permissionService = new ArrayList<PermissionService>();
        }
        return this.permissionService;
    }

    /**
     * Gets the value of the joinType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJoinType() {
        return joinType;
    }

    /**
     * Sets the value of the joinType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJoinType(String value) {
        this.joinType = value;
    }

}
