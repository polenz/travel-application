package org.camunda.bpm.demo.travelapplication;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.inject.Named;

import org.camunda.bpm.demo.travelapplication.model.Absence;
import org.camunda.bpm.demo.travelapplication.model.Project;
import org.camunda.bpm.demo.travelapplication.model.TravelApplication;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Named("saveAbsence")
public class SaveAbsenceDelegate implements JavaDelegate {

  private final Logger LOGGER = Logger.getLogger(SaveAbsenceDelegate.class.getName());

  @EJB
  private TravelApplicationBean travelApplicationBean;

  @Override
  public void execute(DelegateExecution execution) throws Exception {

    TravelApplication travelApplication = (TravelApplication) execution.getVariable("travelApplication");

    Absence absence = new Absence();
    absence.setEmployee(travelApplication.getFirstName() + " " + travelApplication.getLastName());
    absence.setFrom(travelApplication.getStartDate());
    absence.setUntil(travelApplication.getReturnDate());

    Project project = travelApplicationBean.findById(Project.class, travelApplication.getProjectNumber());
    absence.setProject(project);

    travelApplicationBean.insert(absence);

    LOGGER.info("New absence for employee '" + travelApplication.getFirstName() + " " + travelApplication.getLastName() + "' saved.");

  }

}
