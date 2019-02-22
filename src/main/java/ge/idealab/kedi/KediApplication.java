package ge.idealab.kedi;

import ge.idealab.kedi.config.AppProperties;
import ge.idealab.kedi.config.FileStorageProperties;
import ge.idealab.kedi.model.Category;
import ge.idealab.kedi.model.enums.AuthProvider;
import ge.idealab.kedi.model.product.Color;
import ge.idealab.kedi.model.product.Manufacturer;
import ge.idealab.kedi.model.user.Authority;
import ge.idealab.kedi.model.user.User;
import ge.idealab.kedi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties({AppProperties.class, FileStorageProperties.class})
public class KediApplication {
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {

        SpringApplication.run(KediApplication.class, args);
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        if (authorityRepository.count() == 0){
            List<Authority> authorities = new ArrayList<>();
            authorities.add(new Authority("ROLE_ADMIN"));
            authorities.add(new Authority("ROLE_USER"));
            authorities = authorityRepository.saveAll(authorities);

            User admin = new User("admin", "admin@localhost", passwordEncoder.encode("admin1234"), AuthProvider.local, authorities);
            userRepository.save(admin);
            User geass = new User("geass", "okoridze@yahoo.com", passwordEncoder.encode("1234"), AuthProvider.local, authorities);
            userRepository.save(geass);
            for (int i = 0; i < 10; i++){
                if (i % 3 == 0){
                    Category category = new Category();
                    List<Category> subCategories = new ArrayList<>();
                    category.setName("Category ["+i+"]");
                    category = categoryRepository.save(category);
                    for (int j = 0; j < 3; j++){
                        Category subCategory = new Category();
                        subCategory.setName("Category ["+i+"] - Subcategory ["+j+"]");
                        subCategory.setParent(category);
                        subCategories.add(subCategory);
                    }
                    categoryRepository.saveAll(subCategories);
                }else{
                    Category category = new Category();
                    category.setName("Category ["+i+"]");
                    categoryRepository.save(category);
                }
            }

            for (int i = 0; i < 5; i++){
                colorRepository.save(new Color("["+i+"] Color"));
            }

            for (int i = 0; i < 5; i++){
                manufacturerRepository.save(new Manufacturer("["+i+"] Manufacturer"));
            }
        }
    }

}

