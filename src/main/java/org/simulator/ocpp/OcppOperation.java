package org.simulator.ocpp;

import java.util.Map;

public interface OcppOperation {

    public static final String CHARGE_BOX_IDENTITY = "chargeBoxIdentity";

    String getProtocol();

    String getOperationName();

    Identifier getIdentifier();

    String execute(Object request, Map<String, ?> parms) throws Exception;

    static class Identifier {
        String protocol;
        String operationName;

        public Identifier(String protocol, String operationName) {
            this.protocol = protocol;
            this.operationName = operationName;
        }

        public String getId() {
            return (protocol + ":" + operationName).toLowerCase();
        }

        @Override
        public String toString() {
            return getId();
        }
    }
}
