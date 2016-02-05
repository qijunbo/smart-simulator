package org.simulator.ocpp;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractOperatoin implements OcppOperation {

    protected OperationLocator locator;

    @Autowired
    public void setLocator(OperationLocator locator) {
        this.locator = locator;
        this.locator.register(this);
    }

    @Override
    public OcppOperation.Identifier getIdentifier() {
        return new Identifier(getProtocol(), getOperationName());
    }

}
