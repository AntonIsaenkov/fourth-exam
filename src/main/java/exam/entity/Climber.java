package exam.entity;

// Альпинист:
//имя (не менее 3 символов);
//адрес проживания (не менее 5 символов);
//возраст (не менее 18 лет);
//коллекция групп (при необходимости);

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "climber")
public class Climber extends BaseIdentify {

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 150)
    private String address;

    @Column(nullable = false)
    private int age;

    @ManyToMany(mappedBy = "climberList")
    private List<ClimbingGroup> groupList = new ArrayList<>();

    @Transient
    private ClimbingGroup climbingGroup;

    public Climber() {}

    public Climber(String name, String address, int age) {
        setName(name);
        setAddress(address);
        setAge(age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().length() < 3) {
            throw new IllegalArgumentException("name не должен быть null и должен содержать не менее 3 символов");
        }
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().length() < 5) {
            throw new IllegalArgumentException("address не должен быть null и должен содержать не менее 5 символов");
        }
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 18) {
            throw new IllegalArgumentException("age не должен быть меньше 18");
        }
        this.age = age;
    }

    public List<ClimbingGroup> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<ClimbingGroup> groupList) {
        if (groupList == null) {
            throw new IllegalArgumentException("groupList не должен быть null");
        }
        for (int i = 1; i <= groupList.size() - 1 ; i++) {
            if (!(groupList.get(i).getDate().plusDays((long)groupList.get(i).getDuration()).isBefore(groupList.get(i - 1).getDate()))
            && !(groupList.get(i).getDate().isAfter(groupList.get(i - 1).getDate().plusDays((long)groupList.get(i - 1).getDuration())))) {
                throw new IllegalArgumentException("Даты восхождений пересекаются");
            }
        }
        this.groupList = groupList;
    }

    @Override
    public String toString() {
        return "Climber{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
