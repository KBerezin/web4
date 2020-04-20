import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlet.CustomerServlet;
import servlet.DailyReportServlet;
import servlet.NewDayServlet;
import servlet.ProducerServlet;

public class Main {

    public static void main(String[] args) throws Exception {
        CustomerServlet customerServlet = new CustomerServlet();
        DailyReportServlet dailyReportServlet = new DailyReportServlet();
        NewDayServlet newDayServlet = new NewDayServlet();
        ProducerServlet producerServlet = new ProducerServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(customerServlet), "/customer");
        context.addServlet(new ServletHolder(dailyReportServlet), "/report/*");
        context.addServlet(new ServletHolder(newDayServlet), "/newday");
        context.addServlet(new ServletHolder(producerServlet), "/producer");

        Server server = new Server(8079);
        server.setHandler(context);

        server.start();
        server.join();
    }
}
/*
*         CarService carService = CarService.getInstance();
        DailyReportService dailyReportService = DailyReportService.getInstance();
        carService.addNewCar("Жигули", "Калина", "228", 1000L);
        carService.addNewCar("BMW", "X5", "777", 10000L);
        carService.addNewCar("BMW", "7", "777", 10000L);
        carService.addNewCar("Traktor", "Belorus", "999", 1500L);
        carService.addNewCar("Lada", "Prior", "2228", 1000L);
        carService.addNewCar("KIA", "RIO", "228", 5000L);
        carService.addNewCar("Hyndai", "Solaris", "338", 4000L);
        carService.deleteCar("KIA", "RIO", "228");
        System.out.println(carService.getAllCars());
        dailyReportService.addReport(5L, 50000L);
        dailyReportService.addReport(4L, 40000L);
        dailyReportService.addReport(3L, 30000L);
        System.out.println(dailyReportService.getLastReport());
        dailyReportService.getLastReport();
        carService.deleteAllCars();
*
* */