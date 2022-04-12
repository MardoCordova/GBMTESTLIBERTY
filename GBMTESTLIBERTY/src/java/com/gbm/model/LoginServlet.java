/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbm.model;

import com.gbm.data.dbConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author root
 */
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //Descomentar el método para ante de desplegar en was
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String user = request.getParameter("username");
        String pwd = request.getParameter("userpass");

        try {
            if (new dbConnection().cargaUsuario(user, pwd)) {
                response.sendRedirect(request.getContextPath() + "/opciones.jsp");
            } else {
                out.print("Sorry username or password error");
                //RequestDispatcher rd = request.getRequestDispatcher("index.html");
                //rd.include(request, response);
            }
        } catch (SQLException ex) {
            Writer error = new StringWriter();
            ex.printStackTrace(new PrintWriter(error));
            out.print("Error al cargar el usuario y contraseña \n" + error.toString());

        } finally {
            out.close();
        }

    }

//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//
//        String user = request.getParameter("username");
//        String pwd = request.getParameter("userpass");
//
//        try {
//            //if (new dbConnection().cargaUsuario(user, pwd)) { -- Descomentar cuando se despliega en el docker
//            if (user.equals("123")) {
//                response.sendRedirect(request.getContextPath() + "/opciones.jsp");
//            } else {
//                out.print("Sorry username or password error");
//                //RequestDispatcher rd = request.getRequestDispatcher("index.html");
//                //rd.include(request, response);
//            }
//        } catch (Exception ex) {
//            //} catch (SQLException ex) { -- Decomentarlo cuando se despliega en el docker
//            Writer error = new StringWriter();
//            ex.printStackTrace(new PrintWriter(error));
//            out.print("Error al cargar el usuario y contraseña \n" + error.toString());
//
//        } finally {
//            out.close();
//        }
//
//    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
