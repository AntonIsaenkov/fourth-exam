package exam.dao;

public interface Dao<T, PK> {

    void add(T t);
    void update(T t);
    T getByPK(PK pK);
    void delete(T t);
    void deleteByPK(PK pk);

}
