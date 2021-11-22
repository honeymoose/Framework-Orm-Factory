package com.ossez.framework.common.models.orm;

import com.ossez.framework.common.DataObject;
import com.ossez.framework.common.models.CarType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;


/**
 *
 */
@Entity
@Table(catalog = "CRD", name = "Car_Inventory")
public class CarInventory extends DataObject implements Serializable {
    private static final long serialVersionUID = 5530454436970805656L;

    private static Logger logger = LoggerFactory.getLogger(CarInventory.class);

    @Column(name = "CAR_NAME")
    private String carName;

    @Column(name = "CAR_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private CarType carType;

    @Column(name = "CONFIRMATION_NUMBER")
    private String confirmationNumber;

    /**
     * Constructor
     */
    public CarInventory() {

    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }


    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(String confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }
}
