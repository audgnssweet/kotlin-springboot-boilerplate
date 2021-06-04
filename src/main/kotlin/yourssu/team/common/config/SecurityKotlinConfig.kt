package yourssu.team.common.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import yourssu.team.common.jwt.CustomAccessDeniedHandler
import yourssu.team.common.jwt.JwtAuthenticationFilter
import yourssu.team.common.jwt.JwtTokenResolver


@Configuration
@EnableWebSecurity
class SecurityKotlinConfig(
    private val jwtTokenResolver: JwtTokenResolver,
    private val customAccessDeniedHandler: CustomAccessDeniedHandler
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun bCryptPasswordEncoder() = BCryptPasswordEncoder()

    override fun configure(web: WebSecurity) {
        web.ignoring()
            .antMatchers(
                "/v2/api-docs",
                "/favicon.ico",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
            )
    }

    override fun configure(http: HttpSecurity) {
        http
            .httpBasic().disable()
            .csrf().disable()
            .formLogin().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeRequests()
            .antMatchers("/auth/**").permitAll()
            .antMatchers(HttpMethod.GET, "/**").permitAll()
            .anyRequest().hasRole("USER")

            .and()
            .exceptionHandling()
            .accessDeniedHandler(customAccessDeniedHandler)

            .and()
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenResolver, ObjectMapper()),
                UsernamePasswordAuthenticationFilter::class.java
            )
    }
}