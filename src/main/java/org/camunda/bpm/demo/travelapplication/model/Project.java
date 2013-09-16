package org.camunda.bpm.demo.travelapplication.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Project implements Serializable {

  private static final long serialVersionUID = 3135989126908792567L;

  @Id
  protected Long number;

  @Column
  protected String projectName;

  @Column
  protected String projectLeader;

  // empty constructor
  public Project() {
  }

  public Long getNumber() {
    return number;
  }

  public void setNumber(Long number) {
    this.number = number;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getProjectLeader() {
    return projectLeader;
  }

  public void setProjectLeader(String projectLeader) {
    this.projectLeader = projectLeader;
  }

}
