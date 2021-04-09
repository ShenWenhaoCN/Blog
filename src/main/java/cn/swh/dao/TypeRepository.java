package cn.swh.dao;

import cn.swh.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by limi on 2017/10/16.
 */
public interface TypeRepository extends JpaRepository<Type,Long> {

    //按照名称查找类型
    Type findByName(String name);


    //查找博客数量最多的分类
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
