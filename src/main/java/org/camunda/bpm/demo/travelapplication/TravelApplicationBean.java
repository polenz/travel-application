package org.camunda.bpm.demo.travelapplication;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.demo.travelapplication.model.Absence;
import org.camunda.bpm.demo.travelapplication.model.Project;

@Named
@Stateless
public class TravelApplicationBean {

  private final Logger LOGGER = Logger.getLogger(TravelApplicationBean.class.getName());

  @PersistenceContext
  private EntityManager entityManager;

  public <T> T findById(Class<T> clazz, Long id) {
    return entityManager.find(clazz, id);
  }

  @SuppressWarnings("unchecked")
  public List<Project> getProjects() {
    return entityManager.createQuery("select p from Project p").getResultList();
  }

  public void insert(Object object) {
    entityManager.persist(object);
  }

  public void saveAbsence(String firstName, String lastName, Date startDate, Date returnDate, Date startTime, Date returnTime, Long projectNumber) {
    Absence absence = new Absence();
    absence.setEmployee(firstName + " " + lastName);
    absence.setFrom(combineDateTime(startDate, startTime));
    absence.setUntil(combineDateTime(returnDate, returnTime));

    Project project = findById(Project.class, projectNumber);
    absence.setProject(project);

    entityManager.persist(absence);
  }

  private Date combineDateTime(Date date, Date time)
    {
    Calendar calendarDate = Calendar.getInstance();
    calendarDate.setTime(date);

    if (time != null) {
      Calendar calendarTime = Calendar.getInstance();
      calendarTime.setTime(time);

      calendarDate.set(Calendar.HOUR_OF_DAY, calendarTime.get(Calendar.HOUR_OF_DAY));
      calendarDate.set(Calendar.MINUTE, calendarTime.get(Calendar.MINUTE));
    }

    Date result = calendarDate.getTime();
    return result;
  }

}
