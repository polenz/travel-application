package org.camunda.bpm.demo.travelapplication.controller;

import java.io.Serializable;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.inject.Named;


@Named
public class TravelApplicationForm extends org.camunda.bpm.engine.cdi.jsf.TaskForm implements Serializable {

  private static final long serialVersionUID = 1L;

  public void startTask() {
    Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    String taskId = requestParameterMap.get("taskId");
    String callbackUrl = requestParameterMap.get("callbackUrl");
    super.startTask(taskId, callbackUrl);
  }

  public void startProcessInstanceByKeyForm() {
    Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    String processDefinitionKey = requestParameterMap.get("processDefinitionKey");
    String callbackUrl = requestParameterMap.get("callbackUrl");
    super.startProcessInstanceByKeyForm(processDefinitionKey, callbackUrl);
  }

}