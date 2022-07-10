package cl.ucn.disc.dsm.pictwin.backend;

import cl.ucn.disc.dsm.pictwin.backend.dao.PicRepository;
import cl.ucn.disc.dsm.pictwin.backend.model.Pic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * The PicTwin Backend Application
 */
@SpringBootApplication
@Slf4j
public class BackendApplication {
	/**
	 * The Main.
	 * @param args to use.
	 */
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	/**
	 *
	 * @param repository to use.
	 * @return the runner.
	 */
	@Bean
	public CommandLineRunner seed(PicRepository repository) {
		return (args) -> {
			repository.save(new Pic());
		};
	}
}
