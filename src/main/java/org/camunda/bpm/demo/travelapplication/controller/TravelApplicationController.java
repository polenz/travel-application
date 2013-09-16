package org.camunda.bpm.demo.travelapplication.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.inject.Instance;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.demo.travelapplication.TravelApplicationBean;
import org.camunda.bpm.demo.travelapplication.model.Project;
import org.camunda.bpm.demo.travelapplication.model.TravelApplication;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;

@Named
@javax.enterprise.context.RequestScoped
public class TravelApplicationController implements Serializable {

  private static final long serialVersionUID = 4207226058317208043L;

  private TravelApplication travelApplication = new TravelApplication();

  @Inject
  private RuntimeService runtimeService;

  @Inject
  private TaskService taskService;

  @Inject
  private Instance<Conversation> conversationInstance;

  @Inject
  @Named("camunda.taskForm")
  private TaskForm taskForm;

  @Inject
  private BusinessProcess businessProcess;

  @EJB
  private TravelApplicationBean travelApplicationBean;

  public void startProcess(String processDefinitionKey, String callbackUrl) throws IOException {
    if (conversationInstance.get().isTransient()) {
      conversationInstance.get().begin();
    }

    // set the process variable
    HashMap<String, Object> variables = new HashMap<String, Object>();
    variables.put("travelApplication", travelApplication);

    runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);

    // End the conversation
    conversationInstance.get().end();

    resetForm();

    // redirect
    FacesContext.getCurrentInstance().getExternalContext().redirect(callbackUrl);
  }

  public List<Project> getProjectList() {
    return travelApplicationBean.getProjects();
  }

  public void resetForm() {
    travelApplication = new TravelApplication();
  }

  public TravelApplication getTravelApplication() {
    return travelApplication;
  }

}
