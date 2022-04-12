package demo;

import org.apache.olingo.commons.api.edmx.EdmxReference;
import org.apache.olingo.commons.api.http.HttpMethod;
import org.apache.olingo.server.api.*;

import java.util.ArrayList;

public class Launcher {
    public static void main(String[] args) {

        // TODO too much hassle to set tomcat now
        //  check if it's possible to run locally without server and tomcat
        try {
            // create odata handler and configure it with CsdlEdmProvider and Processor
            OData odata = OData.newInstance();
            ServiceMetadata edm = odata.createServiceMetadata(new DemoEdmProvider(), new ArrayList<EdmxReference>());
            ODataHandler handler = odata.createRawHandler(edm);
            handler.register(new DemoEntityCollectionProcessor());

            ODataRequest oDataRequest=new ODataRequest();
            oDataRequest.setRawBaseUri("http://localhost:8080/DemoService/DemoService.svc/");
            oDataRequest.setMethod(HttpMethod.GET);
            ODataResponse process = handler.process(oDataRequest);
            System.out.println(process.getContent());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
