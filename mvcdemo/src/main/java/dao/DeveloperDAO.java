package dao;

import model.DeveloperModel;

import java.util.List;

public interface DeveloperDAO {
    List<DeveloperModel> getAllDevelopers();

    DeveloperModel getDeveloper(String id);

    boolean addDeveloper(DeveloperModel model);

    boolean updateDeveloper(String id,String name);

    boolean deleteDeveloper(String id);
}
