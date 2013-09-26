package org.camunda.bpm.demo.travelapplication;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class EmailDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		Email email = new SimpleEmail();
		email.setHostName("192.168.17.1");
		email.setAuthentication("test@camunda-t61.local", "cam123");

		email.setFrom("test@camunda-t61.local");
		email.setSubject("Betreff");
		email.setMsg("Text.");

		email.addTo("test@camunda-t61.local");

		email.send();
	}

}
