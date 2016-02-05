package org.simulator.ocpp.cs15;

import java.util.Map;

import com.ocpp.cs201206.AuthorizeRequest;

public class Authorize extends UpwardsOperation<AuthorizeRequest> {

    public static final String ID_TAG = "ID_TAG";

    @Override
    public String getOperationName() {

        return AuthorizeRequest.class.getName();
    }

    @Override
    AuthorizeRequest createRequest(Map<String, ?> parms) {
        AuthorizeRequest request = new AuthorizeRequest();
        request.setIdTag(parms.get(ID_TAG).toString());
        return request;
    }

}
