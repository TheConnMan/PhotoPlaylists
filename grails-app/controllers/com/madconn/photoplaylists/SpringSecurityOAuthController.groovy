/*
 * Copyright 2012 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.madconn.photoplaylists

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.oauth.OAuthLoginException
import grails.plugin.springsecurity.oauth.OAuthToken
import grails.plugin.springsecurity.userdetails.GrailsUser
import org.springframework.web.servlet.ModelAndView
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
/**
 * Simple controller for handling OAuth authentication and integrating it
 * into Spring Security.
 */
class SpringSecurityOAuthController {

    public static final String SPRING_SECURITY_OAUTH_TOKEN = 'springSecurityOAuthToken'

    def oauthService
    def springSecurityService
    def springSecurityOAuthService
    def authenticationManager

    /**
     * This can be used as a callback for a successful OAuth authentication
     * attempt.
     * It logs the associated user in if he or she has an internal
     * Spring Security account and redirects to <tt>targetUri</tt> (provided as a URL
     * parameter or in the session). Otherwise it redirects to a URL for
     * linking OAuth identities to Spring Security accounts. The application must implement
     * the page and provide the associated URL via the <tt>oauth.registration.askToLinkOrCreateAccountUri</tt>
     * configuration setting.
     *
     * "/oauth/$provider/success"(controller: "springSecurityOAuth", action: "onSuccess")
     */
    def onSuccess(String provider) {
        // Validate the 'provider' URL. Any errors here are either misconfiguration
        // or web crawlers (or malicious users).
        if (!provider) {
            log.warn "The Spring Security OAuth callback URL must include the 'provider' URL parameter"
            throw new OAuthLoginException("The Spring Security OAuth callback URL must include the 'provider' URL parameter")
        }

        def sessionKey = oauthService.findSessionKeyForAccessToken(provider)
        if (!session[sessionKey]) {
            log.warn "No OAuth token in the session for provider '${provider}'"
            throw new OAuthLoginException("Authentication error for provider '${provider}'")
        }
        // Create the relevant authentication token and attempt to log in.
        OAuthToken oAuthToken = springSecurityOAuthService.createAuthToken(provider, session[sessionKey])

        if (oAuthToken.principal instanceof GrailsUser) {
            authenticateAndRedirect(oAuthToken, getDefaultTargetUrl())
        } else {
            // This OAuth account hasn't been registered against an internal
            // account yet. Give the oAuthID the opportunity to create a new
            // internal account or link to an existing one.
            session[SPRING_SECURITY_OAUTH_TOKEN] = oAuthToken

            def redirectUrl = springSecurityOAuthService.getAskToLinkOrCreateAccountUri()
            if (!redirectUrl) {
                log.warn "grails.plugin.springsecurity.oauth.registration.askToCreateAccountUri configuration option must be set"
                throw new OAuthLoginException('Internal error')
            }
            log.debug "Redirecting to askToCreateAccountUri: ${redirectUrl}"
            redirect(redirectUrl instanceof Map ? redirectUrl : [uri: redirectUrl])
        }
    }

    def onFailure(String provider) {
        // TODO: put it in i18n messages file
        //flash.message = "book.delete.message"
        //flash.args = ["The Stand"]
        flash.default = "Error authenticating with ${provider}"
        log.warn "Error authenticating with external provider ${provider}"
        authenticateAndRedirect(null, getDefaultTargetUrl())
    }

    def askToCreateAccount() {
        if (springSecurityService.isLoggedIn()) {
            def currentUser = springSecurityService.getCurrentUser()
            OAuthToken oAuthToken = session[SPRING_SECURITY_OAUTH_TOKEN]
            if (!oAuthToken) {
                log.warn "askToCreateAccount: OAuthToken not found in session"
                throw new OAuthLoginException('Authentication error')
            }
            currentUser.addToOAuthIDs(provider: oAuthToken.providerName, accessToken: oAuthToken.socialId, user: currentUser)
            if (currentUser.validate() && currentUser.save()) {
                oAuthToken = springSecurityOAuthService.updateOAuthToken(oAuthToken, currentUser)
                authenticateAndRedirect(oAuthToken, getDefaultTargetUrl())
                return
            }
        }
        return new ModelAndView("/springSecurityOAuth/askToCreateAccount", [:])
    }

    private boolean authenticationIsValid(String username, String password) {
        boolean valid = true
        try {
           authenticationManager.authenticate new UsernamePasswordAuthenticationToken(username, password)
        } catch (AuthenticationException e) {
           valid = false
        } 
        return valid
    }

    def createAccount(OAuthCreateAccountCommand command) {
        OAuthToken oAuthToken = session[SPRING_SECURITY_OAUTH_TOKEN]
        if (!oAuthToken) {
            log.warn "createAccount: OAuthToken not found in session"
            throw new OAuthLoginException('Authentication error')
        }
        if (request.post) {
            if (!springSecurityService.loggedIn) {
                def config = SpringSecurityUtils.securityConfig
                def commandValid = command.validate()
                def User = springSecurityOAuthService.lookupUserClass()
                boolean created = commandValid && User.withTransaction { status ->
                    def user = springSecurityOAuthService.lookupUserClass().newInstance()
                    //User user = new User(username: command.username, password: command.password1, enabled: true)
                    user.username = command.username
                    user.enabled = true
					user.password = generator(25);
                    user.addToOAuthIDs(provider: oAuthToken.providerName, accessToken: oAuthToken.socialId, user: user)
                    // updateUser(user, oAuthToken)
                    if (!user.validate() || !user.save(flush: true)) {
                        status.setRollbackOnly()
                        false
                    }
                    def UserRole = springSecurityOAuthService.lookupUserRoleClass()
                    def Role = springSecurityOAuthService.lookupRoleClass()
                    def roles = springSecurityOAuthService.getRoleNames()
                    for (roleName in roles) {
                        UserRole.create user, Role.findByAuthority(roleName)
                    }
                    oAuthToken = springSecurityOAuthService.updateOAuthToken(oAuthToken, user)
                    true
                }
                if (created) {
                    authenticateAndRedirect(oAuthToken, getDefaultTargetUrl())
                    return
                }
            }
        }
        render view: 'askToCreateAccount', model: [createAccountCommand: command]
    }

    protected Map getDefaultTargetUrl() {
        def config = SpringSecurityUtils.securityConfig
        def savedRequest = SpringSecurityUtils.getSavedRequest(session)
        def defaultUrlOnNull = '/'
        if (savedRequest && !config.successHandler.alwaysUseDefault) {
            return [url: (savedRequest.redirectUrl ?: defaultUrlOnNull)]
        }
        return [uri: (config.successHandler.defaultTargetUrl ?: defaultUrlOnNull)]
    }

    protected void authenticateAndRedirect(OAuthToken oAuthToken, redirectUrl) {
        session.removeAttribute SPRING_SECURITY_OAUTH_TOKEN
        SecurityContextHolder.context.authentication = oAuthToken
        redirect(redirectUrl instanceof Map ? redirectUrl : [uri: redirectUrl])
    }

	String generator(int n) {
		String alphabet = (('A'..'Z') + ('a'..'z') + ('0'..'9')).join();
		new Random().with {
			(1..n).collect { alphabet[ nextInt( alphabet.length() ) ] }.join();
		}
	}
}

class OAuthCreateAccountCommand {

    def springSecurityOAuthService

    String username

    static constraints = {
        username blank: false, minSize: 3, validator: { String username, command ->
            if (command.springSecurityOAuthService.usernameTaken(username)) {
                return 'OAuthCreateAccountCommand.username.error.unique'
            }
        }
    }
}
