package pl.uncleglass.cloudstorage.mapper;

import org.apache.ibatis.annotations.*;
import pl.uncleglass.cloudstorage.model.Note;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO NOTES ( notetitle, notedescription, userid) " +
            "VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> selectNotes(int userId);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}")
    int update(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    void delete(Integer noteId);
}
