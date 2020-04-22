package DAO;

import model.DailyReport;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public void addReport(Long earnings, Long soldCars) {
        final Transaction transaction = session.beginTransaction();
        try {
            session.save(new DailyReport(earnings, soldCars));
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public List<DailyReport> getAllDailyReport() {
        List<DailyReport> dailyReports;
        try {
            dailyReports = session.createQuery(
                    "SELECT daily_reports FROM DailyReport daily_reports", DailyReport.class)
                    .list();
        } finally {
            session.close();
        }
        return dailyReports;
    }

    public DailyReport getLastReport() {
        DailyReport dailyReport;
        try {
            dailyReport = session.createQuery(
                    "FROM DailyReport daily_reports ORDER BY id DESC", DailyReport.class)
                    .setMaxResults(1)
                    .uniqueResult();
        } finally {
            session.close();
        }
        return dailyReport;
    }

    public void deleteReports() {
        final Transaction transaction = session.beginTransaction();
        try {
            session.createQuery("DELETE FROM DailyReport")
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }

    }

    public void setSession(Session session) {
        this.session = session;
    }
}
