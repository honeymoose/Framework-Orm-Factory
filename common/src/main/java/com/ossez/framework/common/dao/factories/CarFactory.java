package com.ossez.framework.common.dao.factories;


import com.ossez.framework.common.dao.Factory;
import com.ossez.framework.common.models.CarType;
import com.ossez.framework.common.models.orm.CarInventory;
import com.ossez.framework.common.models.orm.CarReservation;
import com.ossez.framework.common.models.request.ReservationRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

/**
 * CarFactory to process database query
 *
 * @author YuCheng Hu
 */
public class CarFactory {
    private final static Logger logger = LoggerFactory.getLogger(CarFactory.class);

    /**
     * Search ReportManufacturer by ID
     *
     * @param id
     * @return
     */
    public static CarInventory getCarInventoryById(Long id) {
        logger.debug("Search Database to find ReportManufacturer by ID");

        Object obj = Factory.getSession().get(CarInventory.class, id);

        if (ObjectUtils.allNotNull(obj))
            return (CarInventory) obj;
        else
            return null;
    }

    /**
     * Search CarInventory by CarType
     *
     * @param carType
     * @return
     */
    public static List<CarInventory> getCarInventory(CarType carType) {
        logger.debug("Search Database to find ReportManufacturer by ID");

        Criteria cr = Factory.createCriteria(CarInventory.class);
        cr.add(Restrictions.eq("carType", carType));
        cr.add(Restrictions.isNull("confirmationNumber"));

        List results = cr.list();

        return results;
    }

    /**
     * Search CarInventory by CarType
     *
     * @param reservationRequest
     * @param carInventory
     * @return
     */
    public static CarReservation createReservation(ReservationRequest reservationRequest, CarInventory carInventory) {
        logger.debug("Search Database to find ReportManufacturer by ID");

        DateTime dateStart = new DateTime(reservationRequest.getDateReservationStart());

        CarReservation carReservation = new CarReservation();
        carReservation.setCarInventoryId(carInventory.getId());
        carReservation.setDateReservationsStart(dateStart.toDate());
        carReservation.setDateReservationsStart(dateStart.plusDays(reservationRequest.getDurationReservation()).toDate());
        carReservation.setConfirmationNumber(UUID.randomUUID().toString());

        Factory.save(carReservation);

        return carReservation;
    }

    /**
     * @param carInventory
     * @return
     */
    public static CarInventory saveCarInventory(CarInventory carInventory) {
        logger.debug("Save CarInventory");

        Factory.save(carInventory);
        return carInventory;
    }

}
