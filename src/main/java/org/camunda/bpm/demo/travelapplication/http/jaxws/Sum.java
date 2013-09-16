
package org.camunda.bpm.demo.travelapplication.http.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "sum", namespace = "http://http.travelapplication.demo.bpm.camunda.org/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sum", namespace = "http://http.travelapplication.demo.bpm.camunda.org/", propOrder = {
    "value1",
    "value2",
    "value3"
})
public class Sum {

    @XmlElement(name = "value1", namespace = "")
    private double value1;
    @XmlElement(name = "value2", namespace = "")
    private double value2;
    @XmlElement(name = "value3", namespace = "")
    private double value3;

    /**
     * 
     * @return
     *     returns double
     */
    public double getValue1() {
        return this.value1;
    }

    /**
     * 
     * @param value1
     *     the value for the value1 property
     */
    public void setValue1(double value1) {
        this.value1 = value1;
    }

    /**
     * 
     * @return
     *     returns double
     */
    public double getValue2() {
        return this.value2;
    }

    /**
     * 
     * @param value2
     *     the value for the value2 property
     */
    public void setValue2(double value2) {
        this.value2 = value2;
    }

    /**
     * 
     * @return
     *     returns double
     */
    public double getValue3() {
        return this.value3;
    }

    /**
     * 
     * @param value3
     *     the value for the value3 property
     */
    public void setValue3(double value3) {
        this.value3 = value3;
    }

}
