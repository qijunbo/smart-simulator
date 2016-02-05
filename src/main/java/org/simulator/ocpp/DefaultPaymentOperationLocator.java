package org.simulator.ocpp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultPaymentOperationLocator implements OperationLocator {

    @Override
    public void register(OcppOperation operation) {

        OcppOperation old = beans.put(operation.getIdentifier().getId(), operation);

        if (old != null) {
            String error = "Two PaymentOperation are registered with the same identifer, the old one is:[%s], the new one is [%s].";
            throw new RuntimeException(String.format(error, old.toString(), operation.toString()));
        }
    }

    Map<String, OcppOperation> beans = new HashMap<String, OcppOperation>();

    @Override
    public OcppOperation get(OcppOperation.Identifier identifier) {

        return beans.get(identifier.getId());
    }

}
