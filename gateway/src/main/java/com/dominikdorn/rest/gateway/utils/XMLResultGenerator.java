package com.dominikdorn.rest.gateway.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XMLResultGenerator {

    public Document createDocument() {
        Document document = DocumentHelper.createDocument();
        return document;
    }
    
    public Element createRoot(Document doc, String host, String port) {
        return doc.addElement("results").addAttribute("host", host).addAttribute("port", port);
    }
    
    public Element addStorage(Element res, String host, String port) {
        return res.addElement("storage").addAttribute("host", host).addAttribute("port", port);
    }
    
    public void addResultToStorage(Element storage, String xml) {
        try {
            Document doc = DocumentHelper.parseText(xml);
            storage.add(doc.getRootElement());
        } catch (DocumentException e) {
            
        }
    }
    
    public Element addErrorElement(Element e, String error) {
        return e.addElement("error").addText(error);
    }
}
