package ge.idealab.kedi;

import ge.idealab.kedi.config.AppProperties;
import ge.idealab.kedi.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({AppProperties.class, FileStorageProperties.class})
public class KediApplication {

    public static void main(String[] args) {

        SpringApplication.run(KediApplication.class, args);
    }

}

