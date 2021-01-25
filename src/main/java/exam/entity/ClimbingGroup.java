package exam.entity;

// Группа для восхождения на гору:
//Гора;
//коллекция Альпинистов;
//идёт набор в группу или нет;
//дата восхождения;
//продолжительность восхождения;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "climbing_group")
public class ClimbingGroup extends BaseIdentify {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Mountain mountain;

    @ManyToMany
    @JoinTable(name = "climbers_groups",
    joinColumns = @JoinColumn(name = "climbing_group_id"),
    inverseJoinColumns = @JoinColumn(name = "climber_id"))
    private List<Climber> climberList = new ArrayList<>();

    @Column(nullable = false)
    private boolean groupSetOpen;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, precision = 2, scale = 1)
    private int duration;

    public ClimbingGroup() {}

    public ClimbingGroup(Mountain mountain, LocalDate date, int duration, boolean groupSetOpen) {
        this.mountain = mountain;
        this.date = date;
        this.duration = duration;
        this.groupSetOpen = groupSetOpen;
    }

    public Mountain getMountain() {
        return mountain;
    }

    public void setMountain(Mountain mountain) {
        this.mountain = Objects.requireNonNull(mountain);
    }

    public List<Climber> getClimberList() {
        return climberList;
    }

    public void setClimberList(List<Climber> climberList) {
        if (climberList == null) {
            throw new IllegalArgumentException("climberList не должен быть null");
        }
        for (Climber climber : climberList) {
            List<ClimbingGroup> groupList = climber.getGroupList();

            for (ClimbingGroup group : groupList) {
                if (!(group.getDate().plusDays((long) group.getDuration()).isBefore(this.date))
                && !(group.getDate().isAfter(this.date.plusDays((long) this.getDuration())))) {
                    throw new IllegalArgumentException("Даты восхождений пересекаются");
                }
            }
        }
        this.climberList = climberList;
    }

    public boolean isGroupSetOpen() {
        return groupSetOpen;
    }

    public void setGroupSetOpen(boolean groupSetOpen) {
        this.groupSetOpen = groupSetOpen;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = Objects.requireNonNull(date);
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if (duration < 1) {
            throw new RuntimeException("duration не должен быть меньше 1");
        }
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "ClimbingGroup{" +
                "mountain=" + mountain +
                ", groupSetOpen=" + groupSetOpen +
                ", date=" + date +
                ", duration=" + duration +
                '}';
    }
}
