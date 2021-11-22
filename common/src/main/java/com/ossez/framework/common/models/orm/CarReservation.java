package com.ossez.framework.common.models.orm;

import com.ossez.framework.common.DataObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * ORM For Table: CARS
 */
@Entity
@Table(catalog = "CRD", name = "Car_Reservation")
public class CarReservation extends DataObject implements Serializable {

    @Column(name = "CAR_INVENTORY_ID")
    private Long carInventoryId;

    @Column(name = "CONFIRMATION_NUMBER")
    private String confirmationNumber;

    @Column(name = "DATE_RESERVATION_START")
    private Date dateReservationsStart;

    @Column(name = "DATE_RESERVATION_END")
    private Date dateReservationEnd;

    @Column(name = "DATE_ACTUAL_START")
    private Date dateActualStart;

    @Column(name = "DATE_ACTUAL_END")
    private Date dateActualEnd;

    /**
     * Constructor
     */
    public CarReservation() {
        this.setDateC(new Date());
        this.setDateM(new Date());
        this.setUuid(UUID.randomUUID().toString());
    }

    public Long getCarInventoryId() {
        return carInventoryId;
    }

    public void setCarInventoryId(Long carInventoryId) {
        this.carInventoryId = carInventoryId;
    }

    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(String confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public Date getDateReservationsStart() {
        return dateReservationsStart;
    }

    public void setDateReservationsStart(Date dateReservationsStart) {
        this.dateReservationsStart = dateReservationsStart;
    }

    public Date getDateReservationEnd() {
        return dateReservationEnd;
    }

    public void setDateReservationEnd(Date dateReservationEnd) {
        this.dateReservationEnd = dateReservationEnd;
    }

    public Date getDateActualStart() {
        return dateActualStart;
    }

    public void setDateActualStart(Date dateActualStart) {
        this.dateActualStart = dateActualStart;
    }

    public Date getDateActualEnd() {
        return dateActualEnd;
    }

    public void setDateActualEnd(Date dateActualEnd) {
        this.dateActualEnd = dateActualEnd;
    }
}
