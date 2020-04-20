package service;

import DAO.DailyReportDao;
import model.DailyReport;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private static DailyReportService dailyReportService;

    private DailyReport currentReport;

    private SessionFactory sessionFactory;

    private DailyReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.currentReport = new DailyReport();
        this.currentReport.setSoldCars(0L);
        this.currentReport.setEarnings(0L);
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public void addReport(Long earnings, Long soldCars) {
        new DailyReportDao(sessionFactory.openSession()).addReport(earnings, soldCars);
    }

    public DailyReport getLastReport() {
        return new DailyReportDao(sessionFactory.openSession()).getLastReport();
    }

    public List<DailyReport> getAllDailyReports() {
        return new DailyReportDao(sessionFactory.openSession()).getAllDailyReport();
    }

    public void deleteAllReports() {
        new DailyReportDao(sessionFactory.openSession()).deleteReports();
    }

    public void makeReportOnSale(Long price) {
        currentReport.setSoldCars(currentReport.getSoldCars() + 1);
        currentReport.setEarnings(currentReport.getEarnings() + price);
    }

    public void makeNewDayReport() {
        addReport(currentReport.getEarnings(), currentReport.getSoldCars());
        currentReport.setSoldCars(0L);
        currentReport.setEarnings(0L);
    }
}
