package fr.laerce.facturation;
import freemarker.template.Configuration;
import freemarker.template.Template;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;






/**
 * Application Lifecycle Listener implementation class myServletListener
 *
 */
public class myServletListener implements ServletContextListener {

    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event) {
        Connection conn;
        Template listeClients;
        ServletContext sc = event.getServletContext();



        String user = sc.getInitParameter("user");
        String password = sc.getInitParameter("password");
        String driver = sc.getInitParameter("driver");


        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);



        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(driver, props);

            //conn = DriverManager.getConnection(driver, user, password);
            sc.setAttribute("db",conn);

            String sql = "SELECT clt_num, clt_nom, clt_pnom, clt_loc, clt_pays FROM clients";
            PreparedStatement req = conn.prepareStatement(sql);
            sc.setAttribute("requet1",req);

            String sql1 = "select art_num, art_nom FROM articles where art_frs= ?";
            PreparedStatement req1 = conn.prepareStatement(sql1);
            sc.setAttribute("requet2",req1);

        }  catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);
        cfg.setServletContextForTemplateLoading(sc,"/WEB-INF/templates");
        cfg.setDefaultEncoding("UTF8");
        try {
            listeClients = cfg.getTemplate("clients.ftl");
            sc.setAttribute("listeClients",listeClients);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {

    }

}
