package com.raina.vehicle_inspection.exception;

public class VehicleInspectionException extends RuntimeException {

    public VehicleInspectionException(String message) {
        super(message);
    }

    public  VehicleInspectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
