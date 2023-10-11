package cal.projeteq3.glucose.security;

import cal.projeteq3.glucose.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthProvider implements AuthenticationProvider{
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	public AuthProvider(PasswordEncoder passwordEncoder, UserRepository userRepository){
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException{
		Operator user = loadUserByUsername(authentication.getPrincipal().toString());
		validateAuthentication(authentication, user);
		return new UsernamePasswordAuthenticationToken(
			user.getUsername(),
			user.getPassword(),
			user.getAuthorities()
		);
	}

	@Override
	public boolean supports(Class<?> authentication){
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

	//TODO: code duplication with JwtAuthenticationFilter: create AuthenticationManager
	private Operator loadUserByUsername(String username) throws UsernameNotFoundException{
		return userRepository.findOperatorByEmail(username)
			.orElseThrow(UserNotFoundException::new);
	}

	private void validateAuthentication(Authentication authentication, Operator user){
		if(!passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword()))
			throw new BadCredentialsException("Incorrect password");
		if(!user.isAccountEnabled())
			throw new BadCredentialsException("Account is disabled");
		if(!user.isAccountNonExpired())
			throw new BadCredentialsException("Account has expired");
		if(!user.isAccountNonLocked())
			throw new BadCredentialsException("Account is locked");
	}

}
