package DAO;

import model.Car;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public void addCar(Car car) {
        final Transaction transaction = session.beginTransaction();
        try {
            session.save(car);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

    }

    public Car getCar(String brand, String model, String licensePlate) {
        Car cars;
        try {
            cars = session.createQuery(
                    "FROM Car WHERE brand = :brand AND licensePlate = :lp AND model = :model", Car.class)
                    .setParameter("brand", brand)
                    .setParameter("model", model)
                    .setParameter("lp", licensePlate)
                    .setMaxResults(1)
                    .uniqueResult();
        } finally {
            session.close();
        }
        return cars;
    }

    public boolean deleteCar(Long id) {
        final Transaction transaction = session.beginTransaction();
        int rows = 0;
        try {
            rows = session.createQuery("DELETE FROM Car с WHERE с.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return rows > 0;
    }

    public List<Car> getAllCars() {
        List<Car> allCars;
        try {
            allCars = session.createQuery("FROM Car", Car.class).list();
        } finally {
            session.close();
        }
        return allCars;
    }

    public int getCountCarsByModel(String model) {
        Long count;
        try {
            count = session.createQuery(
                    "SELECT COUNT(model) FROM Car WHERE model = :paramModel", Long.class)
                    .setParameter("paramModel", model)
                    .setMaxResults(1)
                    .uniqueResult();
        } finally {
            session.close();
        }
        return count == null ? 0 : count.intValue();
    }

    public void deleteCars() {
        final Transaction transaction = session.beginTransaction();
        try {
            session.createQuery("DELETE FROM Car").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }
}


