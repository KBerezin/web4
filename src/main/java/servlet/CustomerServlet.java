package servlet;

import com.google.gson.Gson;
import model.Car;
import service.CarService;
import service.DailyReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String json = gson.toJson(CarService.getInstance().getAllCars());
        resp.getWriter().println(json);
        resp.setContentType("application/json;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Car car = CarService.getInstance().getCar(
                req.getParameter("brand"),
                req.getParameter("model"),
                req.getParameter("licensePlate"));

        if (car != null) {
            CarService.getInstance().deleteCar(car.getId());
            DailyReportService.getInstance().makeReportOnSale(car.getPrice());
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
