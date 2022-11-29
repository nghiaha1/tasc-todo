package tasc.ass1.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationSeeder implements CommandLineRunner {

    @Autowired
    TodoSeeder todoSeeder;

    private boolean isSeeding = true;

    @Override
    public void run(String... args) throws Exception {
        if (isSeeding) {
            todoSeeder.generate();
        }
    }
}
