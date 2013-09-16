package org.camunda.bpm.demo.travelapplication;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}
