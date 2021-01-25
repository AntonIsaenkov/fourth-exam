package exam.entity;

// Гора:
//название (не менее 4 символов);
//страна (не менее 4 символов);
//высота (не менее 100 метров);

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mountain")
public class Mountain extends BaseIdentify {

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 45)
    private String country;

    @Column(nullable = false)
    private int high;

    public Mountain() {}

    public Mountain(String name, String country, int high) {
        setName(name);
        setCountry(country);
        setHigh(high);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().length() < 4) {
            throw new IllegalArgumentException("name не должен быть null и должен содержать не менее 4 символов");
        }
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        if (country == null || country.trim().length() < 4) {
            throw new IllegalArgumentException("country не должен быть null и должен содержать не менее 4 символов");
        }
        this.country = country;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        if (high < 100.0) {
            throw new IllegalArgumentException("high должен быть не менее 100");
        }
        this.high = high;
    }

    @Override
    public String toString() {
        return "Mountain{" +
                "name='" + name + '\'' +
                '}';
    }
}
