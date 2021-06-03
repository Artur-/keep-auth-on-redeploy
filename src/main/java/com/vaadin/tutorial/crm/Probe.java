package com.vaadin.tutorial.crm;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;

public class Probe implements HttpSessionActivationListener, Serializable {

    @Override
    public void sessionDidActivate(HttpSessionEvent se) {
    }

    @Override
    public void sessionWillPassivate(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        // System.out.println("!!! PASSIVATE " + getNames(session));
        for (String name : getNames(session)) {
            if (name.startsWith("com.vaadin.flow.server.VaadinSession.springServlet")
                    || name.startsWith("springServlet.lock")) {
                session.removeAttribute(name);
                // System.out.println("DROP: " + name);
            } else {
                // System.out.println("RETAIN: " + name);
            }
        }
    }

    private Set<String> getNames(HttpSession session) {
        Enumeration<String> attributeNames = session.getAttributeNames();

        Set<String> result = new HashSet<>();
        while (attributeNames.hasMoreElements()) {
            result.add(attributeNames.nextElement());
        }
        return result;
    }

}
