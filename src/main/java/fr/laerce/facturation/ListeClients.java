package fr.laerce.facturation;

import fr.laerce.facturation.model.Client;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class ListeClients extends HttpServlet {
    Template listeClients;

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        Connection conn= ( Connection
                ) getServletContext().getAttribute("db");
        try {

            //
            Statement req = conn.createStatement();
            //String query = "SELECT clt_num, clt_nom, clt_pnom, clt_loc, clt_pays FROM clients";
            //ResultSet res = req.executeQuery(query);

            PreparedStatement prep = (PreparedStatement) getServletContext().getAttribute("requet1");
            ResultSet res = prep.executeQuery();
            PreparedStatement prep1 = (PreparedStatement) getServletContext().getAttribute("requet2");
            prep1.setString(1,"F05");
            ResultSet res1 = prep1.executeQuery();


            while (res1.next()){
                System.out.println(res1.getString("art_nom"));
                System.out.println(res1.getString("art_num"));
            }

            List<Client> clients = new ArrayList<Client>();
            while(res.next()){
                clients.add(new Client(res.getString("clt_num"),
                        res.getString("clt_nom"),
                        res.getString("clt_pnom"),
                        res.getString("clt_loc"),
                        res.getString("clt_pays")));
            }
            Map<String,Object> datas = new HashMap<>();
            datas.put("clients",clients);

            listeClients.process(datas, httpServletResponse.getWriter());

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (TemplateException e){

        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        listeClients = (Template) getServletContext().getAttribute("listeClients");

        /* try {
           String user = getInitParameter("user");
            String password = getInitParameter("password");
            String driver = getInitParameter("driver");

            Class.forName("org.postgresql.Driver");
            Properties props = new Properties();
            props.setProperty("user", user);
            props.setProperty("password", password);
            conn = DriverManager.getConnection(driver, props);
            //conn = DriverManager.getConnection("jdbc:postgresql://localhost/exemple", props);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException("Pas de Driver SQL");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Pas de connexion à la base");
        }*/

    }

}


