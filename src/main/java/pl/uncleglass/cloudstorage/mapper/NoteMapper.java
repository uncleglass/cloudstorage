package pl.uncleglass.cloudstorage.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
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
}
