package website.magyar.muservice.web.provider;

import website.magyar.muservice.database.tables.Person;
import website.magyar.muservice.database.tables.Social;
import website.magyar.muservice.web.json.CurrentUserInformationJson;
import website.magyar.muservice.web.service.AuthenticatedUser;
import website.magyar.muservice.web.service.GoogleUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

/**
 * Class to provide information about the actual user.
 */
@Component
public class CurrentUserProvider {

    private static final Map<String, AuthenticatedUser> sessionCache = new HashMap<>();
    private static final Object o = new Object();

    public void addSession(String id, AuthenticatedUser user) {
        synchronized (o) {
            if (sessionCache.containsKey(id)) {
                sessionCache.replace(id, user);
            } else {
                sessionCache.put(id, user);
            }
        }
    }

    /**
     * Checks if the actual session-user pair is the originally registered session-user pair or not.
     * @param httpSession is the session to be checked
     * @param authenticatedUser is the user that tries to use the session
     * @return false if something is not correct
     */
    private boolean checkSession(HttpSession httpSession, AuthenticatedUser authenticatedUser) {
        boolean result = false;
        synchronized (o) {
            String key = httpSession.getId();
            if (sessionCache.containsKey(key)) {
                AuthenticatedUser user = sessionCache.get(key);
                result = user.equals(authenticatedUser);
            } //else unregistered session, which is wrong too
        }
        return result;
    }

    /**
     * Remove the session from the list of active sessions.
     * Must be called on session expiration and on logouts.
     *
     * @param sessionId is the id of the session to be removed
     */
    public void removeSession(String sessionId) {
        synchronized (o) {
            if (sessionCache.containsKey(sessionId)) {
                sessionCache.remove(sessionId);
            }
        }
    }

    /**
     * Get information about the actual user.
     *
     * @param httpSession that the user have
     * @return with current user information in json
     */
    public CurrentUserInformationJson getUserInformation(HttpSession httpSession) {
        var currentUserInformationJson = new CurrentUserInformationJson(); //default info - user not logged in
        Authentication authentication = null;
        var securityContext = (SecurityContext) httpSession.getAttribute(SPRING_SECURITY_CONTEXT_KEY);
        if (securityContext != null) {
            authentication = securityContext.getAuthentication();
        }
        if (authentication != null) {
            var principal = authentication.getPrincipal();
            if (principal instanceof AuthenticatedUser) {
                var user = (AuthenticatedUser) principal;
                if (user.isSessionValid() && checkSession(httpSession, user)) {
                    user.extendSessionTimeout();
                    currentUserInformationJson = getCurrentUserInformation(user);
                } else { //session expired!
                    String sessionID = httpSession.getId();
                    //logger.info("Session expired/invalidated: {}", sessionID);
                    removeSession(sessionID);
                    securityContext.setAuthentication(null); // this cleans up the authentication data technically
                    httpSession.removeAttribute(SPRING_SECURITY_CONTEXT_KEY); // this clean up the session itself
                    httpSession.invalidate(); //and finally truly invalidates the session
                }
            }
        }
        return currentUserInformationJson;
    }

    private CurrentUserInformationJson getCurrentUserInformation(AuthenticatedUser user) {
        String loggedInUserName;
        String userName;
        Person person;
        Social social;
        var currentUserInformationJson = new CurrentUserInformationJson();
        currentUserInformationJson.isLoggedIn = true;  // if authentication is not null then the person is logged in
        person = user.getPerson();
        if (person != null) {
            currentUserInformationJson.fillIdentifiedPersonFields(person);
            loggedInUserName = person.getName();
            userName = loggedInUserName;
        } else { //only Social info we have - person is not identified
            userName = "Anonymous";
            String guestNameIntro = "GUEST";
            loggedInUserName = guestNameIntro + userName;
            if (user instanceof GoogleUser) {
                userName = user.getSocial().getGoogleUserName();
                loggedInUserName = guestNameIntro + userName;
            }
        }
        currentUserInformationJson.socialServiceUsed = user.getServiceName();
        currentUserInformationJson.loggedInUserName = loggedInUserName; //user who logged in via social
        currentUserInformationJson.userName = userName; //user who registered as adorator (his/her name may differ from the username used in Social)
        social = user.getSocial();
        if (social != null) {
            currentUserInformationJson.fillIdentifiedSocialFields(social);
        }
        return currentUserInformationJson;
    }

    /**
     * Gets the name of the actual user.
     *
     * @param authentication is the authentication object
     * @return with the name
     */
    public String getQuickUserName(Authentication authentication) {
        var principal = authentication.getPrincipal();
        String loggedInUserName = "";
        Person person;
        if (principal instanceof AuthenticatedUser) {
            var user = (AuthenticatedUser) principal;
            person = user.getPerson();
            if (person != null) {
                loggedInUserName = person.getName();
            } else {
                loggedInUserName = "Guest - Anonymous";
                if (principal instanceof GoogleUser) {
                    loggedInUserName = user.getSocial().getGoogleUserName();
                }
            }
        }
        return loggedInUserName;
    }

}
