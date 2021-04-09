package cn.swh.dao;

import cn.swh.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by limi on 2017/10/16.
 */
public interface TagRepository extends JpaRepository<Tag,Long> {

    //按名称查找标签
    Tag findByName(String name);

    //查找博客数量最多的标签
    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
