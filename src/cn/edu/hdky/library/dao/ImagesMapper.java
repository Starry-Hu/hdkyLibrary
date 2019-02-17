package cn.edu.hdky.library.dao;

import cn.edu.hdky.library.bean.Images;
import cn.edu.hdky.library.bean.ImagesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImagesMapper {
    long countByExample(ImagesExample example);

    int deleteByExample(ImagesExample example);

    int deleteByPrimaryKey(String id);

    int insert(Images record);

    int insertSelective(Images record);

    List<Images> selectByExample(ImagesExample example);

    Images selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Images record, @Param("example") ImagesExample example);

    int updateByExample(@Param("record") Images record, @Param("example") ImagesExample example);

    int updateByPrimaryKeySelective(Images record);

    int updateByPrimaryKey(Images record);
}