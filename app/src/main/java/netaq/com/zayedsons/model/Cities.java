package netaq.com.zayedsons.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabih on 20-Feb-18.
 */

public class Cities {
    public ArrayList<City> cities;

    public ArrayList<City> getCities() {
        return cities;
    }

    public class City {

        int id;
        String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
