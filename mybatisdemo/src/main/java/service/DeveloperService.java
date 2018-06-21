package service;


import dao.DeveloperDAO;
import model.DeveloperModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeveloperService {

    @Autowired
    private DeveloperDAO developerDAO;

    public List<DeveloperModel> getAllDevelopers(){
        return developerDAO.getAllDevelopers();
    }
    public DeveloperModel getDeveloper(String id){
        return developerDAO.getDeveloper(id);
    }
    public boolean addDeveloper(DeveloperModel model){
        return developerDAO.addDeveloper(model);
    }
    public boolean updateDeveloper(String id,String name){
        return developerDAO.updateDeveloper(id,name);
    }
    public boolean deleteDeveloper(String id){
        return developerDAO.deleteDeveloper(id);
    }
}
