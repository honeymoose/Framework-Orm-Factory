package com.ossez.framework.common.models.request;

import com.ossez.framework.common.models.CarType;

import java.io.Serializable;
import java.util.Date;


/**
 * SearchRequest Object, UI can send search String and related pagination
 *
 * @author YuCheng Hu
 */
public class ReservationRequest implements Serializable {
    private static final long serialVersionUID = 6474765081240948885L;


    private Date dateReservationStart;
    private Integer durationReservation;
    private CarType carType;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Date getDateReservationStart() {
        return dateReservationStart;
    }

    public void setDateReservationStart(Date dateReservationStart) {
        this.dateReservationStart = dateReservationStart;
    }

    public Integer getDurationReservation() {
        return durationReservation;
    }

    public void setDurationReservation(Integer durationReservation) {
        this.durationReservation = durationReservation;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }
}
