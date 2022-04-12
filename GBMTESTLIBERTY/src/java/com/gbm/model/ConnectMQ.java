/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbm.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author root
 */
public class ConnectMQ extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if (request.getParameter("mq") != null) {
                response.sendRedirect(request.getContextPath() + "/mq.jsp");
            }
            if (request.getParameter("putMessage") != null) {
                putMessage(request, response);
            }
            if (request.getParameter("getMessage") != null) {
                getMessages(request, response);
            }
        }
    }

    private void putMessage(HttpServletRequest request, HttpServletResponse response) {
        try {
            InitialContext ctx = new InitialContext();
            ConnectionFactory cf = (javax.jms.QueueConnectionFactory) ctx.lookup("java:comp/env/jms/myqcf");
            Queue q = (javax.jms.Queue) ctx.lookup("java:comp/env/jms/wmqQ1");
            Connection c = cf.createConnection();
            Session s = null;
            MessageProducer pr = null;
            Message m = null;
            for (int i = 0; i < 2500; i++) {
                c = cf.createConnection();
                c.start();
                System.out.println("Linea 1 -> " + i);
                s = c.createSession(false, Session.AUTO_ACKNOWLEDGE);
                pr = s.createProducer(q);
                m = s.createTextMessage("Valor " + i);
                pr.setDeliveryMode(DeliveryMode.PERSISTENT);
                //pr.setPriority(Integer.parseInt(request.getParameter("feedback")));
                pr.send(m);
                c.close();
            }
            for (int i = 0; i < 2500; i++) {
                c = cf.createConnection();
                c.start();
                System.out.println("Linea 2 -> " + i);
                s = c.createSession(false, Session.AUTO_ACKNOWLEDGE);
                pr = s.createProducer(q);
                m = s.createTextMessage("Valor " + i);
                pr.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                //pr.setPriority(Integer.parseInt(request.getParameter("feedback")));
                pr.send(m);
                c.close();
            }

            //Put con el api de IBM
//            Hashtable properties = new Hashtable<String, Object>();
//            properties.put(MQConstants.HOST_NAME_PROPERTY, "servidor");
//            properties.put(MQConstants.PORT_PROPERTY, 1414);
//            properties.put(MQConstants.CHANNEL_PROPERTY, "APP.SVRCONN");
//            properties.put(MQConstants.USE_MQCSP_AUTHENTICATION_PROPERTY, true);
//            properties.put(MQConstants.USER_ID_PROPERTY, "mqm");
//            properties.put(MQConstants.PASSWORD_PROPERTY, "mqm");
//            MQQueueManager qManager = (MQQueueManager) ctx.lookup("java:comp/env/jms/myqcf");
//            int openOptions = MQConstants.MQOO_OUTPUT;
//            MQQueue queue = qManager.accessQueue("COLA1", openOptions);
//            MQMessage msg = new MQMessage();
//            msg.writeString(request.getParameter("feedback"));
//            MQPutMessageOptions pmo = new MQPutMessageOptions();
//            queue.put(msg, pmo);
//            queue.closeOptions = MQConstants.MQCO_NONE;
//            queue.close();
//            qManager.disconnect();
            request.setAttribute("utilOutput", "Insertado Correctamente");
            request.getRequestDispatcher("/mq.jsp").forward(request, response);
        } catch (Exception e) {
            Writer error = new StringWriter();
            e.printStackTrace(new PrintWriter(error));
            //out.println("<h1>" + error.toString() + "</h1>");
            try {
                request.setAttribute("utilOutput", error.toString());
                request.getRequestDispatcher("/mq.jsp").forward(request, response);
            } catch (Exception ex) {
                e.printStackTrace();
            }
        }
    }

    private void getMessages(HttpServletRequest request, HttpServletResponse response) {
        //Recibir mensajes
        try {
            InitialContext ctx = new InitialContext();
            ConnectionFactory cf = (javax.jms.ConnectionFactory) ctx.lookup("java:comp/env/jms/myqcf");
            Queue q = (javax.jms.Queue) ctx.lookup("java:comp/env/jms/wmqQ1");
            Connection c = cf.createConnection();
            c.start();
            System.out.println(q.getQueueName());
            Session s = c.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer co = s.createConsumer(q);
            Message mr = co.receiveNoWait();
            String mensaje = mr.toString();
            c.close();
            // Get con el api de IBM
//            Hashtable properties = new Hashtable<String, Object>();
//            properties.put(MQConstants.HOST_NAME_PROPERTY, "servidor");
//            properties.put(MQConstants.PORT_PROPERTY, 1414);
//            properties.put(MQConstants.CHANNEL_PROPERTY, "APP.SVRCONN");
//            properties.put(MQConstants.USE_MQCSP_AUTHENTICATION_PROPERTY, true);
//            properties.put(MQConstants.USER_ID_PROPERTY, "mqm");
//            properties.put(MQConstants.PASSWORD_PROPERTY, "mqm");
//            int idMensaje = 0;
//            String mensaje = "";
//            InitialContext ctx = new InitialContext();
//            //MQConnectionManager ll= new  ctx.lookup("java:comp/env/jdbc/DB2CONN");
//            MQQueueManager qManager = new MQQueueManager("QM1", properties);
//            int openOptions = MQConstants.MQOO_INQUIRE + MQConstants.MQOO_INPUT_SHARED + MQConstants.MQOO_FAIL_IF_QUIESCING;
//            MQGetMessageOptions getOptions = new MQGetMessageOptions();
//            getOptions.options = MQConstants.MQGMO_NO_WAIT + MQConstants.MQGMO_FAIL_IF_QUIESCING;
//            boolean getMore = true;
//            MQMessage receiveMsg = null;
//            MQQueue queue = qManager.accessQueue("COLA1", openOptions);
//            try {
//                while (getMore) {
//                    idMensaje = idMensaje + 1;
//                    receiveMsg = new MQMessage();
//                    queue.get(receiveMsg, getOptions);
//                    byte[] b = new byte[receiveMsg.getMessageLength()];
//                    receiveMsg.readFully(b);
//                    mensaje = mensaje + " " + String.valueOf(idMensaje) + " " + new String(b) + "\n";
//                }
//            } catch (MQException e) {
//                if ((e.completionCode == MQConstants.MQCC_WARNING)
//                        && (e.reasonCode == MQConstants.MQRC_NO_MSG_AVAILABLE)) {
//                    System.out.println("Bottom of the queue reached.");
//                    getMore = false;
//                } else {
//                    System.err.println("MQRead CC=" + e.completionCode + " : RC=" + e.reasonCode);
//                    getMore = false;
//                }
//            }
//            queue.closeOptions = MQConstants.MQCO_NONE;
//            queue.close();
//            qManager.disconnect();
            request.setAttribute("utilOutput", mensaje);
            request.getRequestDispatcher("/mq.jsp").forward(request, response);
        } catch (Exception e) {
            Writer error = new StringWriter();
            e.printStackTrace(new PrintWriter(error));
            //out.println("<h1>" + error.toString() + "</h1>");
            try {
                request.setAttribute("utilOutput", error.toString());
                request.getRequestDispatcher("/mq.jsp").forward(request, response);
            } catch (Exception ex) {
                e.printStackTrace();
            }

        }
//            out.println("</body>");
//            out.println("</html>");
    }

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
