package org.rabbit.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EntryAction extends HttpServlet {

             private static final long serialVersionUID = -9092072935123090022L;

             public void doGet(HttpServletRequest req, HttpServletResponse resp)
                                             throws IOException, ServletException {
             }

             public void doPost(HttpServletRequest request, HttpServletResponse response)
                                             throws IOException {

             }
             
             /* (non-Javadoc)
            * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
            */
            @Override
            protected void service(HttpServletRequest arg0, HttpServletResponse arg1)
            throws ServletException, IOException {
            // TODO Auto-generated method stub
            super.service(arg0, arg1);
            }
}
