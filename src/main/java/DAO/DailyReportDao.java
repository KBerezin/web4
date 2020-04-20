package DAO;

import model.DailyReport;
import org.hibernate.Session;

import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public void addReport(Long earnings, Long soldCars) {
        session.beginTransaction();
        session.save(new DailyReport(earnings, soldCars));
        session.getTransaction().commit();
        session.close();
    }

    public List<DailyReport> getAllDailyReport() {
        List<DailyReport> dailyReports = session.createQuery(
                "SELECT daily_reports FROM DailyReport daily_reports", DailyReport.class)
                .list();
        session.close();
        return dailyReports;
    }

    public DailyReport getLastReport() {
        DailyReport dailyReport = session.createQuery(
                "FROM DailyReport daily_reports ORDER BY id DESC", DailyReport.class)
                .setMaxResults(1)
                .uniqueResult();
        session.close();
        return dailyReport;
    }

    public void deleteReports() {
        session.beginTransaction();
        session.createQuery("DELETE FROM DailyReport")
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
