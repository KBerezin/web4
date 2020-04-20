package service;

import DAO.CarDao;
import model.Car;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class CarService {

    private static CarService carService;

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
        if (new CarDao(sessionFactory.openSession()).getCountCarsByModel(model) <= 10) {
            new CarDao(sessionFactory.openSession()).addCar(new Car(brand, model, licensePlate, price));
            return true;
        }
        return false;
    }

    public Car getCar(String brand, String model, String licensePlate) {
        return new CarDao(sessionFactory.openSession()).getCar(brand, model, licensePlate);
    }

    public boolean deleteCar(Long id) {
        return new CarDao(sessionFactory.openSession()).deleteCar(id);
    }

    public List<Car> getAllCars() {
        return new CarDao(sessionFactory.openSession()).getAllCars();
    }

    public void deleteAllCars() {
        new CarDao(sessionFactory.openSession()).deleteCars();
    }
}
