package com.example.codetalker.model;
import javax.xml.bind.annotation.*;
import java.util.*;

@XmlRootElement(name="project")
@XmlAccessorType(XmlAccessType.FIELD)
public class PomProject {
    @XmlElementWrapper(name="dependencies")
    @XmlElement(name ="dependency")
    private List<Dependency> dependencies;   
}
