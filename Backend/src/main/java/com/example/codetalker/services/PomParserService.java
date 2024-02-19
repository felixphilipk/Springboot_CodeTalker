package com.example.codetalker.services;

import com.example.codetalker.model.PomProject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Service;

import java.io.StringReader;


@Service
public class PomParserService {

    public PomProject parsePOMXml(String pomContent) throws JAXBException{
         JAXBContext jaxbContext = JAXBContext.newInstance(PomProject.class);
         Unmarshaller jaxbUnmarshaller=  jaxbContext.createUnmarshaller();
         return (PomProject) jaxbUnmarshaller.unmarshal(new StringReader(pomContent));
    }
    
}
