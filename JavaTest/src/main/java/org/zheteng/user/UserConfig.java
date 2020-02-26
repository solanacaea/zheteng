package org.zheteng.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
public class UserConfig {

	@Autowired
	private AuthenticationManager authManager;

	@Bean
	public UserDetailService userDetailsService() {
		UserDetailService uerService = new UserDetailService(authManager);
		UserDetails sb = User.withUsername("aaa")
				.password("{noop}aaa")//{noop}意思是不加密
				.authorities(AuthorityUtils.NO_AUTHORITIES).build();
		uerService.createUser(sb);

//		UserDetails user = User.withDefaultPasswordEncoder()
//					.username("aaa")
//					.password("aaa")
//					.roles("USER")
//					.build();

		return uerService;
	}

	@Bean
	public UserDetailsManager userDetailsManager(UserDetailService uerService) {
		return new UserDetailsManager() {
			@Override
			public void createUser(UserDetails user) {
				uerService.createUser(user);
			}

			@Override
			public void updateUser(UserDetails user) {
				uerService.updateUser(user);
			}

			@Override
			public void deleteUser(String username) {
				uerService.deleteUser(username);
			}

			@Override
			public void changePassword(String oldPassword, String newPassword) {
				uerService.changePassword(oldPassword, newPassword);
			}

			@Override
			public boolean userExists(String username) {
				return uerService.userExists(username);
			}

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				return uerService.loadUserByUsername(username);
			}
		};
	}
}
