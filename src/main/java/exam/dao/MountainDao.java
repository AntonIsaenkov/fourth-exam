package exam.dao;

import exam.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class MountainDao implements Dao<Mountain, Integer> {

    EntityManager manager;

    public MountainDao(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void add(Mountain mountain) {
        manager.persist(mountain);
    }

    @Override
    public void update(Mountain mountain) {
        manager.merge(mountain);
    }

    @Override
    public Mountain getByPK(Integer pK) {
        return manager.find(Mountain.class, pK);
    }

    @Override
    public void delete(Mountain mountain) {
        manager.remove(mountain);
    }

    @Override
    public void deleteByPK(Integer pK) {
        Mountain mountain = getByPK(pK);
        if (mountain != null) {
            delete(mountain);
        }
    }

    public List<Mountain> mountainListByCounty(String county) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Mountain> criteriaQuery = builder.createQuery(Mountain.class);
        Root<Mountain> root = criteriaQuery.from(Mountain.class);
        Predicate condition = builder.equal(root.get(Mountain_.country), county);
        criteriaQuery.select(root).where(condition);
        TypedQuery<Mountain> query = manager.createQuery(criteriaQuery);
        List<Mountain> group = query.getResultList();
        return group;
    }

}
