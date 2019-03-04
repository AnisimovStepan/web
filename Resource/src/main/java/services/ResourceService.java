package services;

import resources.TestResource;
import utils.sax.SaxHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ResourceService {
    private TestResource testResource;
    
    public ResourceService() {
        this.testResource = new TestResource();
    }
    
    public int getTestResourceAge () {
        return testResource.getAge();
    }
    
    public String getTestResourceName () {
        return testResource.getName();
    }
    
    public void readXMLResource(String path) throws Exception {
        // try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
    
            // Parse xml class file;
            SaxHandler handler = new SaxHandler();
            saxParser.parse(path, handler);
    
            testResource = (TestResource) handler.getObject();
        // }
        // catch (Exception e) {
        //     e.printStackTrace();
        // }
    }
}
