package org.camunda.bpm.demo.travelapplication.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Absence implements Serializable {

  private static final long serialVersionUID = 5961834478392205859L;

  /**
   * primary key
   * auto increment
   */
  @Id
  @GeneratedValue
  protected Long number;

  /**
   * first and last name
   */
  @Column
  protected String employee;

  /**
   * start date of travel application
   * 'from' is a reserved word
   * change column name to start
   */
  @Column(name="start")
  protected Date from;

  /**
   * return date of travel application
   */
  @Column
  protected Date until;

  /**
   * project
   */
  @ManyToOne
  protected Project project;

  // empty constructor
  public Absence() {
  }

  public Long getNumber() {
    return number;
  }

  public void setNumber(Long number) {
    this.number = number;
  }

  public String getEmployee() {
    return employee;
  }

  public void setEmployee(String employee) {
    this.employee = employee;
  }

  public Date getFrom() {
    return from;
  }

  public void setFrom(Date from) {
    this.from = from;
  }

  public Date getUntil() {
    return until;
  }

  public void setUntil(Date until) {
    this.until = until;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

}
