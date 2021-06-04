package yourssu.team

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class TeamApplication

fun main(args: Array<String>) {
	runApplication<TeamApplication>(*args)
}
