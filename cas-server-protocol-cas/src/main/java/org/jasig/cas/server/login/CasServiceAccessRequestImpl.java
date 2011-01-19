/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jasig.cas.server.login;

import org.jasig.cas.server.session.Access;
import org.jasig.cas.server.session.InvalidCasRequestProtocolAccessImpl;
import org.jasig.cas.server.session.InvalidSessionCasProtocolAccessImpl;

/**
 * Implementation of the {@link org.jasig.cas.server.login.ServiceAccessRequest} that implements the CAS protocol.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 3.5
 */
public final class CasServiceAccessRequestImpl extends DefaultLoginRequestImpl implements ServiceAccessRequest {

    private final String serviceId;

    private final boolean postRequest;

    private boolean passiveAuthentication;

    public CasServiceAccessRequestImpl(final String sessionId, final String remoteIpAddress, final boolean forceAuthentication, final boolean passiveAuthentication, final String serviceId, final boolean postRequest) {
        super(sessionId, remoteIpAddress, forceAuthentication, null);
        this.serviceId = serviceId;
        this.postRequest = postRequest;
        this.passiveAuthentication = passiveAuthentication;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public String getPassiveAuthenticationRedirectUrl() {
        return this.passiveAuthentication ? this.serviceId : null;
    }

    public boolean isPostRequest() {
        return this.postRequest;
    }

    public boolean isPassiveAuthentication() {
        return this.passiveAuthentication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CasServiceAccessRequestImpl)) return false;
        if (!super.equals(o)) return false;

        CasServiceAccessRequestImpl that = (CasServiceAccessRequestImpl) o;

        if (postRequest != that.postRequest) return false;
        if (serviceId != null ? !serviceId.equals(that.serviceId) : that.serviceId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (serviceId != null ? serviceId.hashCode() : 0);
        result = 31 * result + (postRequest ? 1 : 0);
        return result;
    }

    public Access generateInvalidRequestAccess() {
        return new InvalidCasRequestProtocolAccessImpl(this);
    }

    public Access generateInvalidSessionAccess() {
        return new InvalidSessionCasProtocolAccessImpl(this);
    }

    // TODO we need to actually figure this stuff out
    public boolean validate() {
        return true;
    }
}