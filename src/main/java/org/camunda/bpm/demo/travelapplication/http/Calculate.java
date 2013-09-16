package org.camunda.bpm.demo.travelapplication.http;

import java.math.BigDecimal;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
//@Path("")
public class Calculate {

  @WebMethod
  public double sum(@WebParam(name="value1") double value1,
                    @WebParam(name="value2") double value2,
                    @WebParam(name="value3") double value3) {
    return (BigDecimal.valueOf(value1).add(BigDecimal.valueOf(value2)).add(BigDecimal.valueOf(value3))).doubleValue();
  }

//  @GET
//  @Path("/sum")
//  public double sum(@QueryParam("value1") double value1,
//                    @QueryParam("value2") double value2,
//                    @QueryParam("value3") double value3) {
//    return (BigDecimal.valueOf(value1).add(BigDecimal.valueOf(value2)).add(BigDecimal.valueOf(value3))).doubleValue();
//  }

}
