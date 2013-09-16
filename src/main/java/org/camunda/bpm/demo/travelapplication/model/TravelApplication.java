package org.camunda.bpm.demo.travelapplication.model;

import java.io.Serializable;
import java.util.Date;

public class TravelApplication implements Serializable {

  private static final long serialVersionUID = 7965050832813717977L;

  /**
   * Vorname des Reisenden
   */
  protected String firstName;

  /**
   * Nachname des Reisenden
   */
  protected String lastName;

  /**
   * Abteilung des Reisenden
   */
  protected String department;

  /**
   * Projekt, auf das die Reise durchgeführt wird
   */
  protected Long projectNumber;

  /**
   * Zweck der Reise
   */
  protected String reason;

  /**
   * Ziel der Reise
   */
  protected String destination;

  /**
   * Abreisetag und ggf. gewünschte Uhrzeit
   */
  protected Date startDate;

  /**
   * Rückreisetag und ggf. gewünschte Uhrzeit
   */
  protected Date returnDate;

  /**
   * geschätzte Reisekosten
   */
  protected Double costs;

  /**
   * bevorzugte Verkehrsmittel
   */
  protected String preferredTransportationMeans;

  /**
   * bevorzugte Unterkunft
   */
  protected String preferredAccommodation;

  // empty constructor
  public TravelApplication() {
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public Long getProjectNumber() {
    return projectNumber;
  }

  public void setProjectNumber(Long projectNumber) {
    this.projectNumber = projectNumber;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getReturnDate() {
    return returnDate;
  }

  public void setReturnDate(Date returnDate) {
    this.returnDate = returnDate;
  }

  public Double getCosts() {
    return costs;
  }

  public void setCosts(Double costs) {
    this.costs = costs;
  }

  public String getPreferredTransportationMeans() {
    return preferredTransportationMeans;
  }

  public void setPreferredTransportationMeans(String preferredTransportationMeans) {
    this.preferredTransportationMeans = preferredTransportationMeans;
  }

  public String getPreferredAccommodation() {
    return preferredAccommodation;
  }

  public void setPreferredAccommodation(String preferredAccommodation) {
    this.preferredAccommodation = preferredAccommodation;
  }

}
