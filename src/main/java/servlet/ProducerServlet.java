package servlet;

import service.CarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProducerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean addCar = CarService.getInstance().addNewCar(
                req.getParameter("brand"),
                req.getParameter("model"),
                req.getParameter("licensePlate"),
                Long.parseLong(req.getParameter("price")));
        if (addCar) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        resp.setContentType("application/json;charset=utf-8");
    }
}
