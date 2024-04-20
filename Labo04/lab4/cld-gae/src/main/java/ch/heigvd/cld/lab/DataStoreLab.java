package ch.heigvd.cld.lab;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "DatastoreWrite", value = "/datastorewrite")
public class DataStoreLab extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/plain");
        PrintWriter pw = resp.getWriter();
        pw.println("Writing entity to datastore.");

        // Seems weird to me but maybe that's how it works
        if (req.getQueryString() != null) {

            Enumeration<String> names = req.getParameterNames();
            Map<String, String> properties = new HashMap<>();
            names.asIterator().forEachRemaining(name -> properties.put(name, req.getParameter(name)));

            String _kind = properties.get("_kind");
            String _key = "";
            if (properties.containsKey("_key")) {
                _key = properties.get("_key");
            }
            properties.remove("_kind");
            properties.remove("_key");

            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            Entity entity;
            if (!_key.isEmpty()) {
                entity = new Entity(_kind, _key);
            } else {
                entity = new Entity(_kind);
            }

            properties.forEach(entity::setProperty);
            datastore.put(entity);
        }
    }
}