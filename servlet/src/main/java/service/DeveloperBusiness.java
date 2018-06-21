package service;

import db.DBHelper;
import model.DeveloperModel;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DeveloperBusiness {
    public List<DeveloperModel> getAllDevelopers(){
        List<DeveloperModel> modelList = new ArrayList<>();
        String sql = "select * from developer";
        DBHelper helper = new DBHelper(sql);
        ResultSet resultSet = null;
        try{
            resultSet = helper.preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String site = resultSet.getString(3);
                String avatar = resultSet.getString(4);
                DeveloperModel model = new DeveloperModel(id,name,site,avatar);

                modelList.add(model);
            }
            resultSet.close();
            helper.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return modelList;
    }
    public DeveloperModel getDeveloper(String developerId){
        String sql = "select * from developer where id="+developerId;
        DBHelper helper = new DBHelper(sql);
        DeveloperModel model = null;
        try{
            ResultSet resultSet = helper.preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String site = resultSet.getString(3);
                String avatar = resultSet.getString(4);
                model = new DeveloperModel(id,name,site,avatar);
            }
            resultSet.close();
            helper.close();
        }catch (Exception e){

        }
        return model;
    }
    public boolean addDeveloper(DeveloperModel model){
        String sql = "insert into developer(id,name,site,avatar) values("+model.getId()+",'"+model.getName()+"','"+model.getSite()+"','"+model.getAvatar()+"');";
        DBHelper helper = new DBHelper(sql);
        return execute(helper);
    }
    private boolean execute(DBHelper helper){
        try{
            helper.preparedStatement.execute();
            helper.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }
    public boolean updateDeveloper(String id,String name){
        String sql = "update developer set name='"+name+"' where id ="+id;
        DBHelper helper = new DBHelper(sql);
        try{
            helper.preparedStatement.executeUpdate();
            helper.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public boolean deleteDeveloper(String id){
        String sql = "delete from developer where id="+id;
        DBHelper helper = new DBHelper(sql);
        return execute(helper);
    }

}
