package service;

import DAO.CarDao;
import model.Car;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class CarService {

    private static CarService carService;

    private CarDao carDao;

    private SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

    public boolean addNewCar(String brand, String model, String licensePlate, Long price) {
        if (getDao().getCountCarsByModel(model) <= 10) {
            getDao().addCar(new Car(brand, model, licensePlate, price));
            return true;
        }
        return false;
    }

    public Car getCar(String brand, String model, String licensePlate) {
        return getDao().getCar(brand, model, licensePlate);
    }

    public boolean deleteCar(Long id) {
        return getDao().deleteCar(id);
    }

    public List<Car> getAllCars() {
        return getDao().getAllCars();
    }

    public void deleteAllCars() {
        getDao().deleteCars();
    }

    private CarDao getDao() {
        if (carDao == null) {
            carDao = new CarDao(sessionFactory.openSession());
        } else {
            carDao.setSession(sessionFactory.openSession());
        }
        return carDao;
    }
}
