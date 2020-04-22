package service;

import DAO.DailyReportDao;
import model.DailyReport;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private static DailyReportService dailyReportService;

    private DailyReportDao dailyReportDao;

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
        getDao().addReport(earnings, soldCars);
    }

    public DailyReport getLastReport() {
        return getDao().getLastReport();
    }

    public List<DailyReport> getAllDailyReports() {
        return getDao().getAllDailyReport();
    }

    public void deleteAllReports() {
        getDao().deleteReports();
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

    private DailyReportDao getDao() {
        if (dailyReportDao == null) {
            dailyReportDao = new DailyReportDao(sessionFactory.openSession());
        } else {
            dailyReportDao.setSession(sessionFactory.openSession());
        }
        return dailyReportDao;
    }
}
