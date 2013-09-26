package org.camunda.bpm.demo.travelapplication;

import java.net.URL;

import javax.xml.namespace.QName;

import org.camunda.bpm.demo.travelapplication.ws.Calculate;
import org.camunda.bpm.demo.travelapplication.ws.CalculateService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class WebServiceDelegate implements JavaDelegate {

	private static final String WSDL_URL = "http://192.168.17.1:8080/travel-application-ws/Calculate?wsdl"; // "http://localhost:8080/travel-application/Calculate?wsdl";
	private static final String HTTP_SERVICE_URL = "http://localhost:8080/travel-application/sum?value1=1&value2=1&value3=1";

	@Override
	public void execute(DelegateExecution arg0) throws Exception {
//	    // Restservice call to test method sum(double, double, double)
//	    // -> uncommented more code in class Calculate to get a Restservice
//	    ClientExecutor clientExecutor = new ApacheHttpClient4Executor();
//	    ClientRequestFactory cf = new ClientRequestFactory(clientExecutor, new URI(HTTP_SERVICE_URL));
//	    ClientRequest request = cf.createRequest(HTTP_SERVICE_URL);
//	    ClientResponse<String> response = request.get(String.class);
	//
//	    if (response.getStatus() != 200) {
//	      Assert.fail("Failed : HTTP error code : " + response.getStatus());
//	    }
//	    assertEquals(new Double(3), Double.valueOf(response.getEntity()));

	    // Webservice call to test method sum(double, double, double)
		CalculateService calculateService = new CalculateService(new URL(WSDL_URL), new QName(
				"http://http.travelapplication.demo.bpm.camunda.org/",
				"CalculateService"));
		Calculate calculatePort = calculateService.getPort(Calculate.class);
		double sum = calculatePort.sum(1, 1, 1);
	}

}
