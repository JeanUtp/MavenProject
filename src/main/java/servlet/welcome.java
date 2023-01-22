/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.ConectaBD;
import util.Usuario;

/**
 *
 * @author ga
 */
@WebServlet(name = "WelcomeServlet", urlPatterns = {"/Welcome"})
public class welcome extends HttpServlet {

    private ConectaBD conectaDb;
    
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
        String usuarioNombre = "Nadie";
        
        this.conectaDb = new ConectaBD();
        
        Usuario usuario = new Usuario();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("*")
                .append(" FROM usuarios")
                .append(" WHERE idusuario = ?");
        try (Connection cn = conectaDb.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setInt(1, 1);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                usuario.setId(rs.getInt(1));
                usuario.setDni(rs.getString(2));
                usuario.setApellido(rs.getString(3));
                usuario.setNombre(rs.getString(4));
                usuario.setNumero(rs.getString(5));
                usuario.setDireccion(rs.getString(6));
                usuario.setTipo(rs.getString(7));
                usuario.setCorreo(rs.getString(8));
            }
        } catch (Exception e) {
            usuarioNombre = "error: "+e.getMessage();
        }
        usuarioNombre = usuario.getNombre()+" "+usuario.getApellido();
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet welcome</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Bienvenido " +usuarioNombre + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
