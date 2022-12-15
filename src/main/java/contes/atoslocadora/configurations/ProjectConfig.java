package contes.atoslocadora.configurations;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic();
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/").hasAuthority("write")
                .antMatchers(HttpMethod.GET, "/").hasAnyAuthority("read", "write");
    }

    @Bean
    public UserDetailsService UserDetailService(DataSource dataSource) {

        String usersByUsernameQuery = "select username, password, enabled from atos_locadora.users where username = ?";
        String authByUserQuery = "select username, authority from atos_locadora.authorities where username = ?";

        var userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery(usersByUsernameQuery);
        userDetailsManager.setAuthoritiesByUsernameQuery(authByUserQuery);

        return userDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return NoOpPasswordEncoder.getInstance();
    }

    // @Bean
    // public BCryptPasswordEncoder BCryptPasswordEncoder() {
    // return new BCryptPasswordEncoder();
    // }

    // @Bean
    // public SCryptPasswordEncoder sCryptPasswordEncoder() {
    // return new SCryptPasswordEncoder();
    // }
}
