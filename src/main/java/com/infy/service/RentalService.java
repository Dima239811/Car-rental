package com.infy.service;

import com.infy.dto.CreateRentalRequest;
import com.infy.entity.*;
import com.infy.enums.RentalStatus;
import com.infy.exception.BadRequestException;
import com.infy.exception.ResourceNotFoundException;
import com.infy.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;

    private final ClientRepository clientRepository;

    private final EmployeeRepository employeeRepository;
    private final CarRepository carRepository;
    private final RentalCarRepository rentalCarRepository;
    private final RentalCarService rentalCarService;
    private final ClientService clientService;


    @Transactional(readOnly = true)
    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Rental> findAllWithClientAndEmployee() {
        return rentalRepository.findAllWithClientAndEmployee();
    }

    @Transactional(readOnly = true)
    public Optional<Rental> findById(Long id) {
        return rentalRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Rental> findByIdWithDetails(Long id) {
        List<Rental> rentals = rentalRepository.findByIdWithDetails(id);
        return rentals.isEmpty() ? Optional.empty() : Optional.of(rentals.get(0));
    }

    // для клиента
    @Transactional
    public Rental save(CreateRentalRequest rental) {
        Client client = clientRepository.findById(rental.getClientId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Клиент с ID " + rental.getClientId() + " не найден"
                        ));

        if (rental.getCarIds() == null || rental.getCarIds().isEmpty()) {
            throw new BadRequestException("Нужно выбрать хотя бы один автомобиль");
        }

        // Создаём сущность Rental
        Rental rentalEntity = new Rental();
        rentalEntity.setClient(client);
        rentalEntity.setEmployee(rentalRepository.findManagerWithLeastRentals());
        rentalEntity.setComment(rental.getComment());
        rentalEntity.setStatus(RentalStatus.PENDING);
        rentalEntity.setStartDate(rental.getStartDate());
        rentalEntity.setEndDate(rental.getEndDate());

        // Инициализируем коллекцию rentalCars
        rentalEntity.setRentalCars(new ArrayList<>());

        // Сохраняем Rental — получаем ID
        rentalEntity = rentalRepository.save(rentalEntity);

        for (Long carId : rental.getCarIds()) {
            Car car = carRepository.findById(carId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Автомобиль с ID " + carId + " не найден"
                            ));

            boolean available = rentalCarService.isAvailable(
                    carId,
                    rental.getStartDate(),
                    rental.getEndDate()
            );

            if (!available) {
                throw new BadRequestException(
                        "Автомобиль с ID " + carId + " недоступен на выбранный период"
                );
            }

            RentalCar rentalCar = new RentalCar();
            rentalCar.setId(new CarRentalId(car.getId(), rentalEntity.getId()));
            rentalCar.setCar(car);
            rentalCar.setRental(rentalEntity);
            rentalCar.setDiscount(rental.getDiscount());

            // Сохраняем RentalCar
            rentalCarRepository.save(rentalCar);

            // Добавляем в существующую коллекцию «на месте»
            rentalEntity.getRentalCars().add(rentalCar);
        }

        // Не заменяем коллекцию, а модифицируем существующую
        // rentalEntity.setRentalCars(savedRentalCars); — УБРАТЬ!

        // Обновляем сущность в БД
        rentalRepository.save(rentalEntity);

        // Загружаем полную сущность с деталями через JPQL-запрос
        Rental finalRentalEntity = rentalEntity;
        return rentalRepository.findByIdWithDetailsRental(rentalEntity.getId())
                .orElseThrow(() -> new RuntimeException("Saved rental with ID " + finalRentalEntity.getId() + " not found after creation"));
    }

    @Transactional(readOnly = true)
    public List<Rental> getRentalsByCurrentUser(Long userId) {
        Client client = clientService.findByUserId(userId);

        if (client == null) {
            return List.of();
            // throw new ClientNotFoundException("Client not found for user ID: " + userId);
        }
        return rentalRepository.findByClientId(client.getId());
    }



// TODO ИСПРАВИТЬ ПОТОМ
    // без статуса обновляет
//    @Transactional
//    public Rental update(Long id, CreateRentalRequest rental) {
//        Rental existing = rentalRepository.findById(id)
//                .orElseThrow(() -> new com.infy.exception.ResourceNotFoundException("Аренда с ID " + id + " не найдена"));
//
//        existing.setStartDate(rental.getStartDate());
//        existing.setEndDate(rental.getEndDate());
//        existing.setComment(rental.getComment());
//        existing.setClient(clientRepository.findById(rental.getClientId()).orElseThrow(
//                () -> new com.infy.exception.ResourceNotFoundException("Клиент с ID " + rental.getClientId() + " не найден")
//        ));
//        existing.setEmployee(employeeRepository.findById(rental.getEmployeeId()).orElseThrow(
//                () -> new com.infy.exception.ResourceNotFoundException("Сотрудник с ID " + rental.getEmployeeId() + " не найден")
//        ));
//
//        return rentalRepository.save(existing);
//    }

    @Transactional
    public void deleteById(Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Аренда с ID " + id + " не найдена"));

        if (rental.getStatus() == RentalStatus.ACTIVE) {
            throw new BadRequestException("Невозможно удалить аренду с ID " + id + ": аренда ещё активна");
        }

        rentalRepository.deleteById(id);
    }
}
