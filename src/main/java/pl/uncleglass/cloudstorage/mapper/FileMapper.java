package pl.uncleglass.cloudstorage.mapper;

import org.apache.ibatis.annotations.*;
import pl.uncleglass.cloudstorage.model.File;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File select(int fileId);

    @Select("SELECT * FROM FILES")
    List<File> selectAll();

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES (#{filename}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void delete(int fileId);
}
