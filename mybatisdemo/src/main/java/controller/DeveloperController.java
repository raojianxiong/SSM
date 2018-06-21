package controller;

import model.CommonModel;
import model.DeveloperModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.DeveloperService;

import javax.swing.*;
import java.util.List;

@Controller
@RequestMapping("/developer")
public class DeveloperController {
    private DeveloperService service;
    @Autowired
    DeveloperController(DeveloperService service){
        this.service = service;
    }
    @RequestMapping(value = "/api/hello",method = RequestMethod.GET)
    public String hello(){
        return "hello";
    }

    @RequestMapping(value="/api/developers",method = RequestMethod.GET)
    @ResponseBody
    public CommonModel getAllDevelopers(){
        List<DeveloperModel> modelList = service.getAllDevelopers();
        CommonModel model = new CommonModel();
        if(modelList.size()>1){
            model.setSuccess();
            model.setData(modelList);
        }else{
            model.setFail();
        }
        return model;
    }

    @RequestMapping(value = "/api/developer",method = RequestMethod.GET)
    @ResponseBody
    public CommonModel getDeveloper(String id){
        //自动匹配参数
        DeveloperModel developerModel = service.getDeveloper(id);
        CommonModel model = new CommonModel();
        if(developerModel != null){
            model.setSuccess();
            model.setData(developerModel);
        }else{
            model.setFail();
        }
        return model;
    }

    @RequestMapping(value = "/api/developer/add",method = RequestMethod.POST)
    @ResponseBody
    public CommonModel addDeveloper(String id,String name,String site,String avatar){
        DeveloperModel developerModel = new DeveloperModel();
        developerModel.setId(Integer.parseInt(id));
        developerModel.setName(name);
        developerModel.setSite(site);
        developerModel.setAvatar(avatar);
        CommonModel model = new CommonModel();
        if(service.addDeveloper(developerModel)){
            model.setSuccess();
        }else{
            model.setFail();
        }
        return model;
    }
    @RequestMapping(value = "/api/developer/update",method = RequestMethod.POST)
    @ResponseBody
    public CommonModel updateDeveloper(String id,String name){
        //自动匹配
        CommonModel model = new CommonModel();
        if(service.updateDeveloper(id,name)){
            model.setSuccess();
        }else{
            model.setFail();
        }
        return model;
    }

    //Spring MVC 不支持put delete 方法实现传参
    @RequestMapping(value = "/api/developer/delete/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public CommonModel deleteDeveloper(@PathVariable("id") String id){
        CommonModel model = new CommonModel();
        if(service.deleteDeveloper(id)){
            model.setSuccess();
        }else{
            model.setFail();
        }
        return model;
    }


}
