package polytech.cli.ctrl;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Main {
    public static void main(String... args) {
        Quarkus.run(LoaderApp.class, args);
    }

    public static class LoaderApp implements QuarkusApplication {

        @Override
        public int run(String... args) throws Exception {
            LoadParking();
            Quarkus.waitForExit();
            return 0;
        }

        private void LoadParking() {
           /* Voiture voiture = new Voiture();*/
        }
    }
}