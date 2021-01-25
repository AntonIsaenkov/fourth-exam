package exam;

// Необходимо описать следующие сущности и связи между ними:
//Альпинист:
//имя (не менее 3 символов);
//адрес проживания (не менее 5 символов);
//возраст (не менее 18 лет);
//коллекция групп (при необходимости);
//
//Гора:
//название (не менее 4 символов);
//страна (не менее 4 символов);
//высота (не менее 100 метров);
//
//Группа для восхождения на гору:
//Гора;
//коллекция Альпинистов;
//идёт набор в группу или нет;
//дата восхождения;
//продолжительность восхождения;
//
//Зависимости прописывать в pom файле.
//Библиотеку lombok не использовать, в геттерах и сеттерах проверять все входящие данные.
//Для взаимодействия с БД использовать EntityManager.
//
//Описать следующие запросы:
//1. Получение всех участников в возрасте [from; to)
//2. Получение списка Групп по названию Горы
//3. Получение по Гор по названию страны
//4. Получение групп, набор в которые еще открыт

import exam.dao.ClimberDao;
import exam.dao.ClimbingGroupDao;
import exam.dao.MountainDao;
import exam.entity.Climber;
import exam.entity.ClimbingGroup;
import exam.entity.Mountain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HideApp {

    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("entityManager");
        EntityManager manager = factory.createEntityManager();

        Climber climber1 = new Climber("Tony", "St-P.", 35);
        Climber climber2 = new Climber("Max", "St-P.", 33);
        Climber climber3 = new Climber("John", "Amsterdam", 25);
        Climber climber4 = new Climber("John", "Amsterdam", 31);
        Climber climber5 = new Climber("John", "Amsterdam", 39);
        Climber climber6 = new Climber("John", "Amsterdam", 40);

        ClimberDao climberDao = new ClimberDao(manager);
        ClimbingGroupDao climbingGroupDao = new ClimbingGroupDao(manager);
        MountainDao mountainDao = new MountainDao(manager);

        Mountain mountain1 = new Mountain("Everest", "Alpi", 5_500);
        Mountain mountain2 = new Mountain("Everest", "Alpi", 5_500);
        Mountain mountain3 = new Mountain("Madagaskar", "Urugvai", 5_500);
        Mountain mountain4 = new Mountain("Nebraska", "Urugvai", 5_500);
        Mountain mountain5 = new Mountain("Limpopo", "Urugvai", 5_500);

        ClimbingGroup group1 = new ClimbingGroup(mountain1, LocalDate.of(2021, 01, 04), 15, true);
        ClimbingGroup group2 = new ClimbingGroup(mountain2, LocalDate.of(2021, 05, 04), 15, true);
        ClimbingGroup group3 = new ClimbingGroup(mountain3, LocalDate.of(2021, 02, 04), 15, true);
        ClimbingGroup group4 = new ClimbingGroup(mountain3, LocalDate.of(2022, 02, 04), 15, false);

        List<ClimbingGroup> groups1 = new ArrayList<>();
        groups1.add(group1);
        groups1.add(group2);
        groups1.add(group3);

        climber1.setGroupList(groups1);

        manager.getTransaction().begin();
        climberDao.add(climber1);
        climberDao.add(climber2);
        climberDao.add(climber3);
        climberDao.add(climber4);
        climberDao.add(climber5);
        climberDao.add(climber6);
        mountainDao.add(mountain1);
        mountainDao.add(mountain2);
        mountainDao.add(mountain3);
        mountainDao.add(mountain4);
        mountainDao.add(mountain5);
        climbingGroupDao.add(group1);
        climbingGroupDao.add(group2);
        climbingGroupDao.add(group3);
        climbingGroupDao.add(group4);
        manager.getTransaction().commit();

        System.out.println(climbingGroupDao.groupListByMountainTitle("Everest"));
        System.out.println(climbingGroupDao.groupListByGroupSetOpen(true));
        System.out.println(mountainDao.mountainListByCounty("Urugvai"));
        System.out.println(climberDao.climberListByAge(31, 40));


    }

}
