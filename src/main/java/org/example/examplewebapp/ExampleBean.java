/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.example.examplewebapp;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.security.jacc.PolicyContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author anicholson
 */
@RequestScoped
@Named
public class ExampleBean implements Serializable {
    
    public String getBusinessLink() {
        return "/ExampleWebApp/administration/business/business.xhtml";
    }
    
    public String getSuperAdminLink() {
        return "/ExampleWebApp/administration/super-admin-only/super-admin.xhtml";
    }
    
    public void logout() {
        String logoutRedirect = "/index.html";

        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            if (facesContext != null) {
                ExternalContext context = facesContext.getExternalContext();
                ((HttpSession) context.getSession(false)).invalidate();
                context.redirect(context.getRequestContextPath() + logoutRedirect);
            }
            else {
                HttpServletRequest httpRequest = (HttpServletRequest) PolicyContext
                        .getContext("javax.servlet.http.HttpServletRequest");
                HttpServletResponse httpResponse = (HttpServletResponse) PolicyContext
                        .getContext("javax.servlet.http.HttpServletResponse");
                httpRequest.getSession().invalidate();
                httpResponse.sendRedirect(httpRequest.getContextPath() + logoutRedirect);
            }
        }
        catch (Exception e) {
            e.printStackTrace(); //NEVER DO THIS IN A PRODUCTION APP; USE A LOGGING FRAMEWORK
        }
    }
    
}
