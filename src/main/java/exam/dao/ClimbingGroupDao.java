package exam.dao;

import exam.entity.Climber;
import exam.entity.ClimbingGroup;
import exam.entity.ClimbingGroup_;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class ClimbingGroupDao implements Dao<ClimbingGroup, Integer> {

    EntityManager manager;

    public ClimbingGroupDao(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void add(ClimbingGroup climbingGroup) {
        manager.persist(climbingGroup);
    }

    @Override
    public void update(ClimbingGroup climbingGroup) {
        manager.merge(climbingGroup);
    }

    @Override
    public ClimbingGroup getByPK(Integer pK) {
        return manager.find(ClimbingGroup.class, pK);
    }

    @Override
    public void delete(ClimbingGroup climbingGroup) {
        manager.remove(climbingGroup);
    }

    @Override
    public void deleteByPK(Integer pK) {
        ClimbingGroup climbingGroup = getByPK(pK);
        if (climbingGroup != null) {
            delete(climbingGroup);
        }
    }

    public List<ClimbingGroup> groupListByMountainTitle(String mountainTitle) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<ClimbingGroup> criteriaQuery = builder.createQuery(ClimbingGroup.class);
        Root<ClimbingGroup> root = criteriaQuery.from(ClimbingGroup.class);
        Predicate condition = builder.equal(root.get(ClimbingGroup_.mountain).get("name"), mountainTitle);
        criteriaQuery.select(root).where(condition);
        TypedQuery<ClimbingGroup> query = manager.createQuery(criteriaQuery);
        List<ClimbingGroup> group = query.getResultList();
        return group;
    }

    public List<ClimbingGroup> groupListByGroupSetOpen(boolean flag) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<ClimbingGroup> criteriaQuery = builder.createQuery(ClimbingGroup.class);
        Root<ClimbingGroup> root = criteriaQuery.from(ClimbingGroup.class);
        Predicate condition = builder.equal(root.get(ClimbingGroup_.groupSetOpen), flag);
        criteriaQuery.select(root).where(condition);
        TypedQuery<ClimbingGroup> query = manager.createQuery(criteriaQuery);
        List<ClimbingGroup> group = query.getResultList();
        return group;
    }

}
