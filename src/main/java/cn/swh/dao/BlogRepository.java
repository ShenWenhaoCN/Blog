package cn.swh.dao;

import cn.swh.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by limi on 2017/10/20.
 */
public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    //查找推荐文章
    @Query("select b from Blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);

    //查找已发布文章
    @Query("select b from Blog b where b.published = true ")
    Page<Blog> findPublished(Pageable pageable);

    //按条件查找文章
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query,Pageable pageable);

    //更新阅读量
    @Transactional
    @Modifying
    @Query("update Blog b set b.views = b.views+1 where b.id = ?1")
    int updateViews(Long id);

    //查找年份
    @Query("select function('date_format', b.createTime, '%Y') as year from Blog b group by function('date_format', b.createTime, '%Y') order by year desc ")
    List<String> findGroupYear();

    //查找某年的文章列表
    @Query("select b from Blog b where function('date_format', b.createTime, '%Y') = ?1")
    List<Blog> findByYear(String year);

}
