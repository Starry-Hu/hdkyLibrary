package cn.edu.hdky.library.dao;

import cn.edu.hdky.library.bean.News;
import cn.edu.hdky.library.bean.NewsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NewsMapper {
    long countByExample(NewsExample example);

    int deleteByExample(NewsExample example);

    int deleteByPrimaryKey(String id);

    int insert(News record);

    int insertSelective(News record);

    List<News> selectByExampleWithBLOBs(NewsExample example);

    List<News> selectByExample(NewsExample example);

    News selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") News record, @Param("example") NewsExample example);

    int updateByExampleWithBLOBs(@Param("record") News record, @Param("example") NewsExample example);

    int updateByExample(@Param("record") News record, @Param("example") NewsExample example);

    int updateByPrimaryKeySelective(News record);

    int updateByPrimaryKeyWithBLOBs(News record);

    int updateByPrimaryKey(News record);
}