package controller;

import dao.DeveloperDAO;
import model.CommonModel;
import model.DeveloperModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 首先在web.xml中我们用servlet拦截了全部请求交给Spring MVC的Dispatcher，
 * 然后它去找对应文件夹下的mvc-dispatcher-servlet.xml文件
 * （也能够不设置，会在默认位置载入文件，
 * 代码中有说明，这里仅仅是帮助养成良好的文件归档习惯）。
 * DeveloperController.java中加上的@Controller
 */
@Controller
@RequestMapping("/developer")
public class DeveloperController {
    @RequestMapping(value = "api/hello", method = RequestMethod.GET)
    public String hello() {
        return "hello";
    }

    private DeveloperDAO developerDAO;

    @Autowired
    DeveloperController(DeveloperDAO developerDAO) {
        this.developerDAO = developerDAO;
    }

    @RequestMapping(value = "/api/developers", method = RequestMethod.GET)
    @ResponseBody //通过@ResponseBody返回Json数据，通过@RequestBody解析数据
    public CommonModel getAllDevelopers() {
        List<DeveloperModel> developerList = developerDAO.getAllDevelopers();
        CommonModel model = new CommonModel();
        if (developerList.size() > 0) {
            model.setSuccess();
            model.setData(developerList);
        } else {
            model.setFail();
        }
        return model;
    }

    @RequestMapping(value = "/api/developer", method = RequestMethod.GET)
    @ResponseBody
    public CommonModel getDeveloper(String id) {
        //自动匹配参数
        DeveloperModel developerModel = developerDAO.getDeveloper(id);
        CommonModel model = new CommonModel();
        if (developerModel != null) {
            model.setSuccess();
            model.setData(developerModel);
        } else {
            model.setFail();
        }
        return model;
    }

    @RequestMapping(value = "/api/developer/add", method = RequestMethod.POST)
    @ResponseBody
    public CommonModel addDeveloper(String id, String name, String site, String avatar) {
        //自动匹配参数
        DeveloperModel developerModel = new DeveloperModel(Integer.parseInt(id), name, site, avatar);
        CommonModel model = new CommonModel();
        if (developerDAO.addDeveloper(developerModel)) {
            model.setSuccess();
        } else {
            model.setFail();
        }
        return model;
    }

    @RequestMapping(value = "/api/developer/update", method = RequestMethod.POST)
    @ResponseBody
    public CommonModel updateDeveloper(String id, String name) {
        //自动匹配参数
        CommonModel model = new CommonModel();
        if (developerDAO.updateDeveloper(id, name)) {
            model.setSuccess();
        } else {
            model.setFail();
        }
        return model;
    }

    //spring mvc 不支持put、delete方法实现传参
    @RequestMapping(value = "/api/developer/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public CommonModel deleteDeveloper(@PathVariable("id") String id) {
        CommonModel model = new CommonModel();
        if(developerDAO.deleteDeveloper(id)){
            model.setSuccess();
        }else{
            model.setFail();
        }
        return model;
    }

}
