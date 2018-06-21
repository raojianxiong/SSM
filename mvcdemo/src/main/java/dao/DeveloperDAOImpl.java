package dao;

import model.DeveloperModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("developerDAOImpl")
public class DeveloperDAOImpl implements DeveloperDAO {
    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<DeveloperModel> getAllDevelopers() {
        String sql = "select * from developer";
        return query(sql);
    }

    @Override
    public DeveloperModel getDeveloper(String id) {
        String sql = "select * from developer where id=" + id;
        List<DeveloperModel> developerList = query(sql);
        if (developerList.size() > 0) {
            return developerList.get(0);
        } else {
            return null;
        }
    }

    private List<DeveloperModel> query(String sql) {
        final List<DeveloperModel> developerList = new ArrayList<>();
        jdbcTemplate.query(sql, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String site = rs.getString(3);
                String avatar = rs.getString(4);
                DeveloperModel model = new DeveloperModel(id,name,site,avatar);
                developerList.add(model);
            }
        });
        return developerList;
    }

    @Override
    public boolean addDeveloper(DeveloperModel model) {
        String sql = "insert into developer(id,name,site,avatar) values("+
                model.getId()+",'"+model.getName()+"','"+model.getSite()+"','"+
                model.getAvatar()+"');";
        try{
            jdbcTemplate.execute(sql);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateDeveloper(String id, String name) {
        String sql = "update developer set name = '"+name+"' where id="+id;
        try{
            jdbcTemplate.update(sql);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteDeveloper(String id) {
        String sql = "delete from developer where id="+id;
        try{
            jdbcTemplate.execute(sql);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
