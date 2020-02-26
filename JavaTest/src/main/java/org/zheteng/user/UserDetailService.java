package org.zheteng.user;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * 参考 InMemoryUserDetailsManager
 * @author zheteng
 *
 */
@CommonsLog
public class UserDetailService {

	private AuthenticationManager authenticationManager;

	public UserDetailService(AuthenticationManager auth) {
		this.authenticationManager = auth;
	}

    private Map<String, UserDetails> users = new HashMap<>();

    public void createUser(UserDetails user) {
        users.putIfAbsent(user.getUsername(), user);
    }

    public void updateUser(UserDetails user) {
        users.put(user.getUsername(), user);
    }

    public void deleteUser(String username) {
        users.remove(username);
    }

    public void changePassword(String oldPassword, String newPassword) {
        Authentication currentUser = SecurityContextHolder.getContext()
                .getAuthentication();
        if (currentUser == null) {
            // This would indicate bad coding somewhere
            throw new AccessDeniedException(
                    "Can't change password as no Authentication object found in context "
                            + "for current user.");
        }
        String username = currentUser.getName();
        UserDetails user = users.get(username);
        if (user == null) {
            throw new IllegalStateException("Current user doesn't exist in database.");
        }

        log.debug("Changing password for user '" + username + "'");

 		if (authenticationManager != null) {
 			log.debug("Reauthenticating user '" + username
 					+ "' for password change request.");

 			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
 					username, oldPassword));
 		}else {
			log.debug("No authentication manager set. Password won't be re-checked.");
		}

 		//update password to database.

    }

    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.get(username);
    }
}