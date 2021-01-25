package exam.dao;

import exam.entity.Climber;
import exam.entity.Climber_;
import exam.entity.ClimbingGroup;
import exam.entity.ClimbingGroup_;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class ClimberDao implements Dao<Climber, Integer> {

    EntityManager manager;

    public ClimberDao(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void add(Climber climber) {
        manager.persist(climber);
    }

    @Override
    public void update(Climber climber) {
        manager.merge(climber);
    }

    @Override
    public Climber getByPK(Integer pK) {
        return manager.find(Climber.class, pK);
    }

    @Override
    public void delete(Climber climber) {
        manager.remove(climber);
    }

    @Override
    public void deleteByPK(Integer pK) {
        Climber climber = getByPK(pK);
        if (climber != null) {
            delete(climber);
        }
    }

    public List<Climber> climberListByAge(int from, int to) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Climber> criteriaQuery = builder.createQuery(Climber.class);
        Root<Climber> root = criteriaQuery.from(Climber.class);
        Predicate condition1 = builder.greaterThanOrEqualTo(root.get(Climber_.age), from);
        Predicate condition2 = builder.lessThan(root.get(Climber_.age), to);
        Predicate and = builder.and(condition1, condition2);
        criteriaQuery.select(root).where(and);
        TypedQuery<Climber> query = manager.createQuery(criteriaQuery);
        List<Climber> group = query.getResultList();
        return group;
    }

}
