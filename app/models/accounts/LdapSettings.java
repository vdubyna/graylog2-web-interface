/*
 * Copyright 2013 TORCH UG
 *
 * This file is part of Graylog2.
 *
 * Graylog2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Graylog2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Graylog2.  If not, see <http://www.gnu.org/licenses/>.
 */
package models.accounts;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import lib.APIException;
import lib.ApiClient;
import models.api.requests.accounts.LdapSettingsRequest;
import models.api.responses.accounts.LdapSettingsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.Http;

import java.io.IOException;
import java.net.URI;

public class LdapSettings {
    private static final Logger log = LoggerFactory.getLogger(LdapSettings.class);

    private final ApiClient api;
    private final LdapSettingsResponse response;

    @AssistedInject
    private LdapSettings(ApiClient api, @Assisted LdapSettingsResponse response) {
        this.api = api;
        this.response = response;
    }

    @AssistedInject
    private LdapSettings(ApiClient api, @Assisted LdapSettingsRequest request) {
        this.api = api;
        final LdapSettingsResponse response = new LdapSettingsResponse();
        response.setEnabled(request.enabled);
        response.setSystemUsername(request.systemUsername);
        response.setSystemPassword(request.systemPassword);
        response.setLdapUri(URI.create(request.ldapUri));
        response.setPrincipalSearchPattern(request.principalSearchPattern);
        response.setSearchBase(request.searchBase);
        response.setUsernameAttribute(request.usernameAttribute);
        this.response = response;
    }

    public boolean save() {
        LdapSettingsRequest request = toRequest();

        try {
            api.put().path("/system/ldap/settings").body(request).expect(Http.Status.NO_CONTENT).execute();
            return true;
        } catch (APIException e) {
            log.error("Unable to save LDAP settings.", e);
            return false;
        } catch (IOException e) {
            log.error("Unable to save LDAP settings.", e);
            return false;
        }
    }

    public LdapSettingsRequest toRequest() {
        LdapSettingsRequest request = new LdapSettingsRequest();
        request.enabled = isEnabled();
        request.systemUsername = getSystemUsername();
        request.systemPassword = getSystemPassword();
        request.ldapUri = getLdapUri().toString();
        request.principalSearchPattern = getPrincipalSearchPattern();
        request.searchBase = getSearchBase();
        request.usernameAttribute = getUsernameAttribute();
        return request;
    }

    public boolean delete() {
        try {
            api.delete().path("/system/ldap/settings").expect(Http.Status.NO_CONTENT).execute();
            return true;
        } catch (APIException e) {
            log.error("Unable to remove LDAP settings", e);
            return false;
        } catch (IOException e) {
            log.error("Unable to remove LDAP settings", e);
            return false;
        }
    }

    public interface Factory {
        LdapSettings fromSettingsResponse(LdapSettingsResponse response);
        LdapSettings fromSettingsRequest(LdapSettingsRequest request);
    }

    public boolean isEnabled() {
        return response.isEnabled();
    }

    public void setUsernameAttribute(String usernameAttribute) {
        response.setUsernameAttribute(usernameAttribute);
    }

    public String getSystemPassword() {
        return response.getSystemPassword();
    }

    public void setPrincipalSearchPattern(String principalSearchPattern) {
        response.setPrincipalSearchPattern(principalSearchPattern);
    }

    public String getUsernameAttribute() {
        return response.getUsernameAttribute();
    }

    public void setSearchBase(String searchBase) {
        response.setSearchBase(searchBase);
    }

    public String getPrincipalSearchPattern() {
        return response.getPrincipalSearchPattern();
    }

    public String getSystemUsername() {
        return response.getSystemUsername();
    }

    public void setSystemPassword(String systemPassword) {
        response.setSystemPassword(systemPassword);
    }

    public String getSearchBase() {
        return response.getSearchBase();
    }

    public URI getLdapUri() {
        return response.getLdapUri();
    }

    public void setLdapUri(URI ldapUri) {
        response.setLdapUri(ldapUri);
    }

    public void setEnabled(boolean enabled) {
        response.setEnabled(enabled);
    }

    public void setSystemUsername(String systemUsername) {
        response.setSystemUsername(systemUsername);
    }
}
