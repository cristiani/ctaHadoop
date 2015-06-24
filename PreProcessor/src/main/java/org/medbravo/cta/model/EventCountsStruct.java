//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.05.19 at 05:10:42 PM EEST 
//


package org.medbravo.cta.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for event_counts_struct complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="event_counts_struct">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="group_id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="subjects_affected" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="subjects_at_risk" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="events" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "event_counts_struct", propOrder = {
    "value"
})
public class EventCountsStruct {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "group_id")
    protected String groupId;
    @XmlAttribute(name = "subjects_affected")
    protected String subjectsAffected;
    @XmlAttribute(name = "subjects_at_risk")
    protected String subjectsAtRisk;
    @XmlAttribute(name = "events")
    protected String events;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the groupId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Sets the value of the groupId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupId(String value) {
        this.groupId = value;
    }

    /**
     * Gets the value of the subjectsAffected property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubjectsAffected() {
        return subjectsAffected;
    }

    /**
     * Sets the value of the subjectsAffected property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubjectsAffected(String value) {
        this.subjectsAffected = value;
    }

    /**
     * Gets the value of the subjectsAtRisk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubjectsAtRisk() {
        return subjectsAtRisk;
    }

    /**
     * Sets the value of the subjectsAtRisk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubjectsAtRisk(String value) {
        this.subjectsAtRisk = value;
    }

    /**
     * Gets the value of the events property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEvents() {
        return events;
    }

    /**
     * Sets the value of the events property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEvents(String value) {
        this.events = value;
    }

}