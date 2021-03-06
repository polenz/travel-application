package org.camunda.bpm.demo.travelapplication.controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Instance;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

import org.camunda.bpm.demo.travelapplication.TravelApplicationBean;
import org.camunda.bpm.demo.travelapplication.model.Project;
import org.camunda.bpm.demo.travelapplication.model.TravelApplication;
import org.camunda.bpm.demo.travelapplication.ws.Calculate;
import org.camunda.bpm.demo.travelapplication.ws.CalculateService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.impl.ProcessVariableMap;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;

@Named
@ConversationScoped
public class TravelApplicationController implements Serializable {

	private static final String WSDL_URL = "http://192.168.17.1:8080/travel-application-ws/Calculate?wsdl"; // "http://localhost:8080/travel-application/Calculate?wsdl";

	private static final long serialVersionUID = 4207226058317208043L;

  private static Logger log = Logger.getLogger(TravelApplication.class.getName());

  private TravelApplication travelApplication = new TravelApplication();

  private String userId;

  @Inject
  private RuntimeService runtimeService;

  @Inject
  private IdentityService identityService;

  @Inject
  private Instance<Conversation> conversationInstance;

  @EJB
  private TravelApplicationBean travelApplicationBean;
  
  @Inject
  private BusinessProcess businessProcess;

  public void startProcess(String processDefinitionKey, String callbackUrl) throws IOException {
    // set the process variable
    HashMap<String, Object> variables = new HashMap<String, Object>();
    variables.put("firstName", travelApplication.getFirstName());
    variables.put("lastName", travelApplication.getLastName());
    variables.put("department", travelApplication.getDepartment());
    variables.put("projectNumber", travelApplication.getProjectNumber());
    variables.put("destination", travelApplication.getDestination());
    variables.put("reason", travelApplication.getReason());
    variables.put("costs", travelApplication.getCosts());
    variables.put("startDate", travelApplication.getStartDate());
    variables.put("startTime", travelApplication.getStartTime());
    variables.put("returnDate", travelApplication.getReturnDate());
    variables.put("returnTime", travelApplication.getReturnTime());
    variables.put("preferredAccommodation", travelApplication.getPreferredAccommodation());
    variables.put("preferredTransportationMeans", travelApplication.getPreferredTransportationMeans());

    if (userId == null) {
      getUser();
    }
    variables.put("userId", userId);

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

  // TODO @Produces @Named @ConversationScoped + shorten references in start-form.xhtml
  public TravelApplication getTravelApplication() {
    if (userId == null) {
      getUser();
    }
    User user = identityService.createUserQuery().userId(userId).singleResult();
    travelApplication.setFirstName(user.getFirstName());
    travelApplication.setLastName(user.getLastName());
    List<Group> departmentList = identityService.createGroupQuery().groupMember(user.getId()).list();
    if (departmentList != null && !departmentList.isEmpty()) {
      for (Group group : departmentList) {
        String groupId = group.getId();
		if ((groupId.contains("Mitarbeiter") && groupId.contains("_")) || (groupId.equals("Geschaeftsfuehrer")) || (groupId.equals("Sekretaer"))) {
          travelApplication.setDepartment(group.getName());
        }
      }
    }
    return travelApplication;
  }

  public String getProjectLeader(Long projectNumber) {
    Project project = travelApplicationBean.findById(Project.class, projectNumber);
    String[] names = project.getProjectLeader().split(" ");
    User projectLeader = identityService.createUserQuery().userFirstName(names[0]).userLastName(names[1]).singleResult();
    if (projectLeader != null) {
      return projectLeader.getId();
    }
    return null;
  }

  public String getDepartmentLeader(String userId) {
    String departmentLeaders = "";
    List<String> candidateGroupList = new ArrayList<String>();
    List<Group> groupList = identityService.createGroupQuery().groupMember(userId).list();
    if (groupList != null && !groupList.isEmpty()) {
      for (Group group : groupList) {
        if (group.getId().contains("_Mitarbeiter") && !group.getId().contains("IT")) {
          String groupId = group.getId().replace("Mitarbeiter", "Abteilungsleiter");
          candidateGroupList.add(groupId);
          groupId = group.getId().replace("Mitarbeiter", "Stellvertreter");
          candidateGroupList.add(groupId);
        }
        if (group.getId().contains("_Mitarbeiter") && group.getId().contains("IT")) {
          String groupId = group.getId().replace("Mitarbeiter", "Abteilungsleiter");
          candidateGroupList.add(groupId);
        }
        if (group.getId().contains("Geschaeftsfuehrung")) {
          String groupId = group.getId().concat("_Stellvertreter");
          candidateGroupList.add(groupId);
        }
      }
    }
    if (candidateGroupList != null && !candidateGroupList.isEmpty()) {
      for (String groupId : candidateGroupList) {
        if (departmentLeaders != null && !departmentLeaders.isEmpty()) {
          departmentLeaders = departmentLeaders + ", ";
        }
        departmentLeaders = departmentLeaders + groupId;
      }
    }
    return departmentLeaders;
  }

  public String getUser() {
    if (userId == null) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
              for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-id")) {
                  userId = cookie.getValue();
                }
              }
            }
        } catch (Exception ex) {
            log.log(Level.WARNING, "An exception occured during get the login information of the request.", ex);
        }
    }
    return userId;
  }
  
  public double sum(double a, double b, double c) throws Exception {
		CalculateService calculateService = new CalculateService(new URL(WSDL_URL), new QName(
				"http://http.travelapplication.demo.bpm.camunda.org/",
				"CalculateService"));
		Calculate calculatePort = calculateService.getPort(Calculate.class);
		double sum = calculatePort.sum(a, b, c);
		businessProcess.setVariable("sum", sum);
	return sum;
	  
  }

}
