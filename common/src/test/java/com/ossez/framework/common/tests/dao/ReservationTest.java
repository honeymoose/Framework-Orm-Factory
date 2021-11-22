package com.ossez.framework.common.tests.dao;


import com.ossez.framework.common.dao.Factory;
import com.ossez.framework.common.dao.factories.CarFactory;
import com.ossez.framework.common.models.CarType;
import com.ossez.framework.common.models.orm.CarInventory;
import com.ossez.framework.common.models.orm.CarReservation;
import com.ossez.framework.common.models.request.ReservationRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ValidationException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * ReservationTest Testing
 *
 * @author YuCheng Hu
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReservationTest {
    private static Logger logger = LoggerFactory.getLogger(ReservationTest.class);

    @BeforeEach
    protected void setUp() throws Exception {
        Factory.beginTransaction();
    }

    @AfterEach
    protected void tearDown() throws Exception {
        Factory.commitTransaction();
    }

    /**
     * Tests JPA and database connection
     */
    @Test
    public void testGetQIndex() throws ValidationException {
        CarInventory carInventory = CarFactory.getCarInventoryById(1L);
        logger.debug("carInventory Name for Id 1   - {}/{}", carInventory.getCarName(), carInventory.getCarType());

        // make sure the reportManufacturer  was found
        assertNotNull(carInventory);
    }

    /**
     * Tests to check the size of Inventory
     */
    @Test
    public void testSizeOfInventory() throws ValidationException {
        ReservationRequest reservationRequest = createReservationRequest();
        List<CarInventory> carInventoryList = CarFactory.getCarInventory(reservationRequest.getCarType());
        logger.debug("carInventory List   - {}/{}", reservationRequest.getCarType(), carInventoryList.size());
    }

    /**
     * Test Save database for Reservation
     *
     * @throws ValidationException
     */
    @Test
    public void testAddRecordToReservation() throws ValidationException {
        ReservationRequest reservationRequest = createReservationRequest();
        List<CarInventory> carInventoryList = CarFactory.getCarInventory(reservationRequest.getCarType());

        //
        CarReservation carReservation = CarFactory.createReservation(reservationRequest, carInventoryList.get(0));
        logger.debug("carReservation CN   - {}", carReservation.getId());
    }


    /**
     * MAIN TEST Function for Reservation logic
     *
     * @throws ValidationException
     */
    @Test
    public void testMakeReservation() throws ValidationException {
        CarInventory carInventory = null;
        String confirmationNumber = null;

        // Build Request
        ReservationRequest reservationRequest = createReservationRequest();

        // Check Inventory
        List<CarInventory> carInventoryList = checkInventoryAvailable(reservationRequest);
        if (CollectionUtils.isNotEmpty(carInventoryList)) {
            logger.debug("carInventory List   - {}/{}", reservationRequest.getCarType(), carInventoryList.size());
            carInventory = carInventoryList.get(RandomUtils.nextInt(0, carInventoryList.size()));
        }

        // Make Reservation
        if (ObjectUtils.isNotEmpty(carInventory)) {
            confirmationNumber = processReservation(reservationRequest, carInventory);
        } else {
            logger.debug("NO carInventory");
        }

        // UPDATE Inventory
        if (StringUtils.isNotEmpty(confirmationNumber))
            updateInventory(carInventory, confirmationNumber);


        logger.debug("confirmation Number - [{}]", carInventory.getConfirmationNumber());
        assertNotNull(carInventory.getConfirmationNumber());
    }

    /**
     * Build reservationRequest
     *
     * @return
     */
    private ReservationRequest createReservationRequest() {
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setDateReservationStart(new Date());
        reservationRequest.setDurationReservation(2);
        reservationRequest.setCarType(CarType.SUV);

        return reservationRequest;
    }

    /**
     * Check checkInventory Size
     *
     * @param reservationRequest
     * @return
     */
    private List<CarInventory> checkInventoryAvailable(ReservationRequest reservationRequest) {
        return CarFactory.getCarInventory(reservationRequest.getCarType());
    }


    /**
     * Process Reservation and get confirmationNumber
     *
     * @param reservationRequest
     * @param carInventory
     * @return
     */
    private String processReservation(ReservationRequest reservationRequest, CarInventory carInventory) {
        CarReservation carReservation = CarFactory.createReservation(reservationRequest, carInventory);
        logger.debug("carReservation CN   - {}", carReservation.getConfirmationNumber());
        return carReservation.getConfirmationNumber();
    }


    /**
     * Save CarInventory
     *
     * @param carInventory
     * @param confirmationNumber
     * @return
     */
    private CarInventory updateInventory(CarInventory carInventory, String confirmationNumber) {
        carInventory.setConfirmationNumber(confirmationNumber);

        CarFactory.saveCarInventory(carInventory);
        logger.debug("carInventory CN   - {}", carInventory.getConfirmationNumber());
        return carInventory;
    }
}


