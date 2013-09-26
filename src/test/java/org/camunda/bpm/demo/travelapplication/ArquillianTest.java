package org.camunda.bpm.demo.travelapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.xml.namespace.QName;

import org.camunda.bpm.demo.travelapplication.model.Project;
import org.camunda.bpm.demo.travelapplication.ws.Calculate;
import org.camunda.bpm.demo.travelapplication.ws.CalculateService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ArquillianTest {

  private static final String PROCESS_DEFINITION_KEY = "travel-application";
  private static final String WSDL_URL = "http://192.168.17.1:8080/travel-application-ws/Calculate?wsdl"; //"http://localhost:8080/travel-application/Calculate?wsdl";
  private static final String HTTP_SERVICE_URL = "http://localhost:8080/travel-application/sum?value1=1&value2=1&value3=1";

  private CalculateService calculateService;

  @Deployment
  public static WebArchive createDeployment() {
    MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class)
      .goOffline()
      .loadMetadataFromPom("pom.xml");

    return ShrinkWrap
            .create(WebArchive.class, "travel-application.war")
            // prepare as process application archive for camunda BPM Platform
            .addAsLibraries(resolver.artifact("org.camunda.bpm.javaee:camunda-ejb-client").resolveAsFiles())
            .addAsLibraries(resolver.artifact("org.camunda.bpm:camunda-engine-cdi").resolveAsFiles())
            .addAsWebResource("META-INF/processes.xml", "WEB-INF/classes/META-INF/processes.xml")
            .addAsWebResource("WEB-INF/beans.xml", "WEB-INF/beans.xml")
            // boot persistence unit
            .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
            // add your own classes (could be done one by one as well)
            .addPackages(false, "org.camunda.bpm.demo.travelapplication", // not recursive to skip package 'nonarquillian'
                                "org.camunda.bpm.demo.travelapplication.controller",
                                "org.camunda.bpm.demo.travelapplication.http",
                                "org.camunda.bpm.demo.travelapplication.model",
                                "org.camunda.bpm.demo.travelapplication.ws")
            // add process definition
            .addAsResource("Dienstreiseprozess.bpmn")
            // add process image for visualizations
            .addAsResource("Dienstreiseprozess.png")
            // now you can add additional stuff required for your test case
            .addAsLibraries(resolver.artifact("org.apache.commons:commons-email").resolveAsFiles())
    ;
  }

  @Inject
  private ProcessEngine processEngine;

  @Inject
  private TravelApplicationBean travelApplicationBean;

  /**
   * Tests that the process is executable and reaches its end.
   */
  @Test
  public void testProcessHappyPath() throws Exception {
    // get existing project
    Project project = travelApplicationBean.getProjects().get(0);
    if (project == null) {
      // create and persist project
      project = new Project();
      project.setNumber(4711L);
      project.setProjectName("A-Projekt");
      project.setProjectLeader("Christian Klein");
      travelApplicationBean.insert(project);
    }

    Calendar calendar = new GregorianCalendar().getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.DAY_OF_MONTH, 3);

    Map<String, Object> variables = new HashMap<String, Object>();
    variables.put("firstName", "Max");
    variables.put("lastName", "Mustermann");
    variables.put("department", "camunda BPM");
    variables.put("projectNumber", project.getNumber());
    variables.put("destination", "Berlin");
    variables.put("reason", "Schulung");
    variables.put("costs", 320.00);
    variables.put("startDate", new Date());
    variables.put("startTime", null);
    variables.put("returnDate", calendar.getTime());
    variables.put("returnTime", null);
    variables.put("preferredAccommodation", "Hotel");
    variables.put("preferredTransportationMeans", "Bahn");
    variables.put("userId", "demo");
    ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY, variables);

    // complete task 'Reisebudget genehmigen'
    Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
    assertNotNull(task);
    variables = new HashMap<String, Object>();
    variables.put("approvedBudget", true);
    processEngine.getTaskService().complete(task.getId(), variables);

    // complete task 'Reise genehmigen'
    task = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
    assertNotNull(task);
    variables = new HashMap<String, Object>();
    variables.put("approvedTravel", true);
    processEngine.getTaskService().complete(task.getId(), variables);

    // complete task 'Reise buchen'
    task = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
    assertNotNull(task);
    processEngine.getTaskService().complete(task.getId());

    // check process instance is ended
    assertEquals(1, processEngine.getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(processInstance.getId()).finished().count());
  }

  /**
   * Test for the sum method in the calculate service
   * @throws Exception
   */
  @Test
  public void testCalculateService() throws Exception {
//    // Restservice call to test method sum(double, double, double)
//    // -> uncommented more code in class Calculate to get a Restservice
//    ClientExecutor clientExecutor = new ApacheHttpClient4Executor();
//    ClientRequestFactory cf = new ClientRequestFactory(clientExecutor, new URI(HTTP_SERVICE_URL));
//    ClientRequest request = cf.createRequest(HTTP_SERVICE_URL);
//    ClientResponse<String> response = request.get(String.class);
//
//    if (response.getStatus() != 200) {
//      Assert.fail("Failed : HTTP error code : " + response.getStatus());
//    }
//    assertEquals(new Double(3), Double.valueOf(response.getEntity()));

    // Webservice call to test method sum(double, double, double)
    calculateService = new CalculateService(new URL(WSDL_URL), new QName( "http://http.travelapplication.demo.bpm.camunda.org/", "CalculateService" ));
    Calculate calculatePort = calculateService.getPort(Calculate.class);
    double sum = calculatePort.sum(1, 1, 1);
    assertEquals(new Double(3), (Double)sum);
  }
  
  @Test
  public void testMailServer() throws Exception {
	  new EmailDelegate().execute(null);
  }

}
