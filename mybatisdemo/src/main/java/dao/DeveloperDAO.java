package dao;

import model.DeveloperModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeveloperDAO {
    List<DeveloperModel> getAllDevelopers();

    DeveloperModel getDeveloper(String developerId);

    boolean addDeveloper(DeveloperModel developerModel);

    //如果想传入多个参数，则需要在接口的参数上添加@Param注解
    boolean updateDeveloper(@Param("id") String id,@Param("name") String name);

    boolean deleteDeveloper(String id);
}
