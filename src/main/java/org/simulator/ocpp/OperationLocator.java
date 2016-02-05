package org.simulator.ocpp;

public interface OperationLocator {

    void register(OcppOperation operation);

    OcppOperation get(OcppOperation.Identifier identifier);

}
