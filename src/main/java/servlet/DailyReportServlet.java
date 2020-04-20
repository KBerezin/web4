package servlet;

import com.google.gson.Gson;
import model.DailyReport;
import service.CarService;
import service.DailyReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DailyReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        if (req.getPathInfo().contains("all")) {
            String json = gson.toJson(DailyReportService.getInstance().getAllDailyReports());
            resp.getWriter().println(json);
        } else if (req.getPathInfo().contains("last")) {
            String json = gson.toJson(DailyReportService.getInstance().getLastReport());
            resp.getWriter().println(json);
        }
        resp.setContentType("application/json;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CarService.getInstance().deleteAllCars();
        DailyReportService.getInstance().deleteAllReports();
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
