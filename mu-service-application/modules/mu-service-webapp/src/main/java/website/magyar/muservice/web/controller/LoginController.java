package website.magyar.muservice.web.controller;

import website.magyar.muservice.web.configuration.WebAppConfigurationAccess;
import website.magyar.muservice.web.provider.CurrentUserProvider;
import website.magyar.muservice.web.service.AuthenticatedUser;
import website.magyar.muservice.web.service.GoogleOauth2Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static website.magyar.muservice.web.service.GoogleOauth2Service.GOOGLE_TEXT;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

/**
 * Controller for handling requests for the application login page.
 *
 * @author Tamas Kohegyi
 */
@Controller
public class LoginController {
    private static final String LOGIN_PAGE = "login";
    private static final String HOME_PAGE = "home";

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private GoogleOauth2Service googleOauth2Service;
    @Autowired
    private CurrentUserProvider currentUserProvider;
    @Autowired
    private WebAppConfigurationAccess webAppConfigurationAccess;

    /**
     * Serves requests which arrive to home and sends back the home page.
     *
     * @return the name of the jsp to display as result
     */
    @GetMapping(value = "/app/login")
    public String showLoginPage(HttpSession httpSession, @RequestParam(value = "result", defaultValue = "") final String result) {
        currentUserProvider.getUserInformation(httpSession);
        if (result.length() == 0) {
            return LOGIN_PAGE;
        }
        //else make simple login page default
        logger.warn("Unhandled login page request, falling back to provide basic login page.");
        return LOGIN_PAGE;
    }

    /**
     * Serves requests to log in with Google.
     *
     * @return the name of the jsp to display as result
     */
    @GetMapping(value = "/app/loginGoogle")
    public String showGoogleLoginPage(HttpServletResponse httpServletResponse) {
        var loginUrl = googleOauth2Service.getLoginUrlInformation();
        try {
            httpServletResponse.sendRedirect(loginUrl);
            return null;
        } catch (IOException e) {
            logger.warn("Redirect to Google authentication does not work.", e);
        }
        return LOGIN_PAGE;
    }

    /**
     * Serves login requests in general, can deal with logins both from Google and Facebook.
     *
     * @param code                Google uses this
     * @param scope               unused
     * @param authuser            unused
     * @param state               facebok uses this
     * @param accessToken        is a token
     * @param httpSession         identifies the user
     * @param httpServletResponse is used to build the response
     * @param httpServletRequest  is used to work with the request
     * @return depends on the status of the login procedure
     */
    //https://fuf.me/adoration/loginResult?code=4%2F...eR8&scope=email+profile+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile
    // +https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&session_state=8ca7cab3c0dd23415b112fae84f84b1cb9957590..dd73&prompt=consent#
    @GetMapping(value = "/app/loginResult")
    public String showLoginResultPage(
            @RequestParam(value = "code", defaultValue = "") final String code,  //google uses this
            @RequestParam(value = "scope", defaultValue = "") final String scope,
            @RequestParam(value = "authuser", defaultValue = "") final String authuser,
            @RequestParam(value = "state", defaultValue = "") final String state,  //facebook uses this
            @RequestParam(value = "access_token", defaultValue = "") final String accessToken,
            HttpSession httpSession,
            HttpServletResponse httpServletResponse,
            HttpServletRequest httpServletRequest
    ) {
        currentUserProvider.getUserInformation(httpSession);
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if ((code.length() > 0) && (state.length() == 0) && ((auth == null) || auth instanceof AnonymousAuthenticationToken)) {  //if GOOGLE login can be performed and it is not yet authenticated for Ador App
            String nextPage;
            nextPage = authenticateWithGoogle(httpSession, httpServletResponse, code);
            return nextPage;
        }
        return HOME_PAGE;
    }

    private String commonAuthentication(HttpSession httpSession, HttpServletResponse httpServletResponse,
                                        Authentication authentication, String serviceName) {
        String followUpPage;
        var sc = SecurityContextHolder.getContext();
        sc.setAuthentication(authentication);
        httpSession.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
        String userName = currentUserProvider.getQuickUserName(authentication);
        var principal = authentication.getPrincipal();
        if (principal instanceof AuthenticatedUser) {
            var user = (AuthenticatedUser) principal;
            currentUserProvider.addSession(httpSession.getId(), user);
            logger.info("Session: {} connected to user:{}", httpSession.getId(), userName);
        } else {
            logger.warn("Session: {} is without proper user.", httpSession.getId());
        }
        logger.info("User logged in with {}: {}", serviceName, userName);
        try {
            httpServletResponse.sendRedirect(webAppConfigurationAccess.getProperties().getGoogleRedirectUrl());
            followUpPage = null;
        } catch (IOException e) {
            logger.warn("Redirect after {} authentication does not work.", serviceName, e);
            followUpPage = LOGIN_PAGE;
        }
        return followUpPage;
    }

    private String authenticateWithGoogle(HttpSession httpSession, HttpServletResponse httpServletResponse, String code) {
        String followUpPage;
        var authentication = googleOauth2Service.getGoogleUserInfoJson(code);
        if (authentication == null) { //was unable to get user info properly
            followUpPage = LOGIN_PAGE;
        } else {
            followUpPage = commonAuthentication(httpSession, httpServletResponse, authentication, GOOGLE_TEXT);
        }
        return followUpPage;
    }

    /**
     * Serves Logout requests.
     *
     * @param httpSession         identifies the user
     * @param httpServletResponse is used to build up the response
     * @return with a logged-out state and with the home page
     */
    @GetMapping(value = "/appSecure/exit")
    public String showExitPage(
            HttpSession httpSession,
            HttpServletResponse httpServletResponse
    ) {
        //clean up the session info
        currentUserProvider.getUserInformation(httpSession);
        var sc = (SecurityContext) httpSession.getAttribute(SPRING_SECURITY_CONTEXT_KEY);
        if (sc != null) {
            var authentication = sc.getAuthentication();
            if (authentication != null) {
                logger.info("User logout: {}", currentUserProvider.getQuickUserName(authentication));
            }
            currentUserProvider.removeSession(httpSession.getId());
            sc.setAuthentication(null); // this cleans up the authentication data technically
            httpSession.removeAttribute(SPRING_SECURITY_CONTEXT_KEY); // this clean up the session itself
        }
        httpSession.invalidate();
        try {
            httpServletResponse.sendRedirect(webAppConfigurationAccess.getProperties().getBaseUrl());
            return null;
        } catch (IOException e) {
            logger.warn("Redirect after logout does not work.", e);
        }
        return HOME_PAGE;
    }

}
