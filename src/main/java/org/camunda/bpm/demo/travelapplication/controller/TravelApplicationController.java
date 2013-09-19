package org.camunda.bpm.demo.travelapplication.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Instance;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.demo.travelapplication.TravelApplicationBean;
import org.camunda.bpm.demo.travelapplication.model.Project;
import org.camunda.bpm.demo.travelapplication.model.TravelApplication;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;

@Named
@ConversationScoped
public class TravelApplicationController implements Serializable {

  private static final long serialVersionUID = 4207226058317208043L;

  private static Logger log = Logger.getLogger(TravelApplication.class.getName());

  private TravelApplication travelApplication = new TravelApplication();

  private User user;

  @Inject
  private RuntimeService runtimeService;

  @Inject
  private IdentityService identityService;

  @Inject
  private Instance<Conversation> conversationInstance;

  @EJB
  private TravelApplicationBean travelApplicationBean;

  public void startProcess(String processDefinitionKey, String callbackUrl) throws IOException {
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

  public String getProjectLeader() {
    Project project = travelApplicationBean.findById(Project.class, travelApplication.getProjectNumber());
    String[] names = project.getProjectLeader().split(" ");
    user = identityService.createUserQuery().userFirstName(names[0]).userLastName(names[1]).singleResult();
    return user.getId();
  }

  public List<String> getDepartmentLeader() {
    List<String> candidateGroupList = new ArrayList<String>();
    List<Group> groupList = identityService.createGroupQuery().groupMember(user.getId()).list();
    if (groupList != null && !groupList.isEmpty()) {
      for (Group group : groupList) {
        if (group.getId().contains("_") && !group.getId().contains("IT")) {
          String groupId = group.getId().replace("Mitarbeiter", "Abteilungsleiter");
          candidateGroupList.add(groupId);
          groupId = group.getId().replace("Mitarbeiter", "Stellvertreter");
          candidateGroupList.add(groupId);
        }
        if (group.getId().contains("_") && group.getId().contains("IT")) {
          String groupId = group.getId().replace("Mitarbeiter", "Abteilungsleiter");
          candidateGroupList.add(groupId);
        }
        if (group.getId().contains("Geschaeftsfuehrung")) {
          String groupId = group.getId().concat("_Stellvertreter");
          candidateGroupList.add(groupId);
        }
      }
    }
    return candidateGroupList;
  }

//  public String getUser() {
//    String user = null;
//    if (user == null) {
//        FacesContext context = FacesContext.getCurrentInstance();
//        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
//        try {
//            Principal userPrincipal = request.getUserPrincipal();
//            if (userPrincipal != null) {
//                user = userPrincipal.getName();
//            }
//        } catch (Exception ex) {
//            log.log(Level.WARNING, "An exception occured during get the login information of the request.", ex);
//        }
//    }
//    return user;
//  }

}
