package pl.uncleglass.cloudstorage.mapper;

import org.apache.ibatis.annotations.*;
import pl.uncleglass.cloudstorage.model.Credential;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
            "VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential> selectCredentialsByUserId(int userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credential selectCredentialById(int credentialId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password} " +
            "WHERE credentialid = #{credentialId}")
    int update(Credential credential);
}
