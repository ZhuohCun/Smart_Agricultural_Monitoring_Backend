package edu.ynu.se.xiecheng.achitectureclass.common.dao;

import edu.ynu.se.xiecheng.achitectureclass.common.entity.LogicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@NoRepositoryBean
public interface LogicDAO<T extends LogicEntity, ID extends Serializable> extends JpaRepository<T, ID> {
    @Override
    @Query(value =
            "update #{#entityName} e " +
                    "set e.isDeleted =1, e.deletedTime = current_timestamp " +
                    "where e.id = ?1 and e.isDeleted = 0")
    @Transactional
    @Modifying
    void deleteById(ID id);;

    @Override
    @Transactional
    default void delete(T entity) {
        deleteById((ID)entity.getId());
    }


    @Override
    @Transactional
    default void deleteAll(Iterable<? extends T> entities){
        entities.forEach(entity -> deleteById((ID) entity.getId()));
    }

    @Override
    @Transactional
    default void deleteAllById(Iterable<? extends ID> ids){
        ids.forEach(id->deleteById(id));
    }

    @Override
    @Query(value = "update #{#entityName} e set e.isDeleted =1, e.deletedTime = current_timestamp where e.isDeleted = 0")
    @Transactional
    @Modifying
    void deleteAll();

    @Override
    @Transactional
    default void deleteAllByIdInBatch(Iterable<ID> ids){
        ids.forEach(id->deleteById(id));
    }

    @Override
    @Transactional
    default void deleteAllInBatch(Iterable<T> entities){
        entities.forEach(entity -> deleteById((ID) entity.getId()));
    }

    @Override
    @Transactional
    default void deleteAllInBatch(){
        deleteAll();
    }

    @Override
    @Transactional(readOnly = true)
    @Query("select count(e) from #{#entityName} e where e.isDeleted = 0")
    long count();

}
