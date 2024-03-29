//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.06.03 at 04:24:07 PM EEST 
//


package com.github.yuri0x7c1.ofbiz.explorer.service.xml;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.github.yuri0x7c1.ofbiz.explorer.service.xml package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Vendor_QNAME = new QName("", "vendor");
    private final static QName _Created_QNAME = new QName("", "created");
    private final static QName _Namespace_QNAME = new QName("", "namespace");
    private final static QName _Description_QNAME = new QName("", "description");
    private final static QName _Version_QNAME = new QName("", "version");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.github.yuri0x7c1.ofbiz.explorer.service.xml
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RequiredPermissions }
     * 
     */
    public RequiredPermissions createRequiredPermissions() {
        return new RequiredPermissions();
    }

    /**
     * Create an instance of {@link CheckPermission }
     * 
     */
    public CheckPermission createCheckPermission() {
        return new CheckPermission();
    }

    /**
     * Create an instance of {@link CheckRoleMember }
     * 
     */
    public CheckRoleMember createCheckRoleMember() {
        return new CheckRoleMember();
    }

    /**
     * Create an instance of {@link PermissionService }
     * 
     */
    public PermissionService createPermissionService() {
        return new PermissionService();
    }

    /**
     * Create an instance of {@link Implements }
     * 
     */
    public Implements createImplements() {
        return new Implements();
    }

    /**
     * Create an instance of {@link FailProperty }
     * 
     */
    public FailProperty createFailProperty() {
        return new FailProperty();
    }

    /**
     * Create an instance of {@link Invoke }
     * 
     */
    public Invoke createInvoke() {
        return new Invoke();
    }

    /**
     * Create an instance of {@link Notification }
     * 
     */
    public Notification createNotification() {
        return new Notification();
    }

    /**
     * Create an instance of {@link Exclude }
     * 
     */
    public Exclude createExclude() {
        return new Exclude();
    }

    /**
     * Create an instance of {@link Attribute }
     * 
     */
    public Attribute createAttribute() {
        return new Attribute();
    }

    /**
     * Create an instance of {@link TypeValidate }
     * 
     */
    public TypeValidate createTypeValidate() {
        return new TypeValidate();
    }

    /**
     * Create an instance of {@link FailMessage }
     * 
     */
    public FailMessage createFailMessage() {
        return new FailMessage();
    }

    /**
     * Create an instance of {@link Override }
     * 
     */
    public Override createOverride() {
        return new Override();
    }

    /**
     * Create an instance of {@link Group }
     * 
     */
    public Group createGroup() {
        return new Group();
    }

    /**
     * Create an instance of {@link Services }
     * 
     */
    public Services createServices() {
        return new Services();
    }

    /**
     * Create an instance of {@link Service }
     * 
     */
    public Service createService() {
        return new Service();
    }

    /**
     * Create an instance of {@link Metric }
     * 
     */
    public Metric createMetric() {
        return new Metric();
    }

    /**
     * Create an instance of {@link AutoAttributes }
     * 
     */
    public AutoAttributes createAutoAttributes() {
        return new AutoAttributes();
    }

    /**
     * Create an instance of {@link ServiceGroup }
     * 
     */
    public ServiceGroup createServiceGroup() {
        return new ServiceGroup();
    }

    /**
     * Create an instance of {@link ServiceSecurity }
     * 
     */
    public ServiceSecurity createServiceSecurity() {
        return new ServiceSecurity();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "vendor")
    public JAXBElement<String> createVendor(String value) {
        return new JAXBElement<String>(_Vendor_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "created")
    public JAXBElement<String> createCreated(String value) {
        return new JAXBElement<String>(_Created_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "namespace")
    public JAXBElement<String> createNamespace(String value) {
        return new JAXBElement<String>(_Namespace_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "description")
    public JAXBElement<String> createDescription(String value) {
        return new JAXBElement<String>(_Description_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "version")
    public JAXBElement<String> createVersion(String value) {
        return new JAXBElement<String>(_Version_QNAME, String.class, null, value);
    }

}
