import constant.ConstantUtil;
import model.CommonModel;
import model.DeveloperModel;
import service.DeveloperBusiness;
import util.GsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "DeveloperServlet", urlPatterns = {
        ConstantUtil.ALL_DEVELOPERS_URL, ConstantUtil.ADD_DEVELOPER_RUL, ConstantUtil.DELETE_DEVELOPER_URL, ConstantUtil.UPDATE_DEVELOPER_URL, ConstantUtil.QUERY_DEVELOPERS_URL
})
public class DeveloperServlet extends HttpServlet {
    DeveloperBusiness developerBusiness = new DeveloperBusiness();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
//        super.doGet(req, resp);
//        resp.setContentType("text/html");
//        PrintWriter out = resp.getWriter();
//        out.println("<h3>Hello World</h3>");
        try {
            req.setCharacterEncoding("UTF-8");
            //设置响应类型
            resp.setContentType("text/json;charset=UTF-8");
            String url = req.getRequestURI();
            System.out.println("==========" + url);
            if (ConstantUtil.ALL_DEVELOPERS_URL.equals(url)) {
                System.out.println("select all developers");
                getAllDevelopers(req, resp);
            } else if (ConstantUtil.QUERY_DEVELOPERS_URL.equals(url)) {
                getDeveloper(req, resp);
            } else if (ConstantUtil.ADD_DEVELOPER_RUL.equals(url)) {
                addDeveloper(req, resp);
            } else if (ConstantUtil.UPDATE_DEVELOPER_URL.equals(url)) {
                updateDeveloper(req, resp);
            } else if (ConstantUtil.DELETE_DEVELOPER_URL.equals(url)) {
                deleteDeveloper(req, resp);
            }

        }catch (Exception e){
            System.out.println("出错了"+e.getMessage());
            e.printStackTrace();
        }
    }

    private void getAllDevelopers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        List<DeveloperModel> developerList = developerBusiness.getAllDevelopers();
        CommonModel commonModel = new CommonModel();
        if (developerList.size() > 0) {
            commonModel.setSuccess();
            commonModel.setData(developerList);
        } else {
            commonModel.setFail();
        }
        writer.println(GsonUtil.bean2Json(commonModel));
        writer.flush();
        writer.close();
    }
    private void getDeveloper(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{
        PrintWriter writer = response.getWriter();
        String id = request.getParameter("id");
        DeveloperModel model =  developerBusiness.getDeveloper(id);
        CommonModel commonModel = new CommonModel();
        if(model == null){
            commonModel.setFail();
        }else{
            commonModel.setSuccess();
            commonModel.setData(model);
        }
        writer.print(GsonUtil.bean2Json(commonModel));
        writer.flush();
        writer.close();

    }
    private void addDeveloper(HttpServletRequest request,HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String site = request.getParameter("site");
        String avatar = request.getParameter("avatar");

        CommonModel commonModel = new CommonModel();
        DeveloperModel developerModel = new DeveloperModel();
        developerModel.setId(Integer.parseInt(id));
        developerModel.setName(name);
        developerModel.setSite(site);
        developerModel.setAvatar(avatar);
        if(developerBusiness.addDeveloper(developerModel)){
            commonModel.setSuccess();
        }else{
            commonModel.setFail();
        }
        writer.println(GsonUtil.bean2Json(commonModel));
        writer.flush();
        writer.close();
    }
    private void updateDeveloper(HttpServletRequest request,HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        String id = request.getParameter("id");
        String name  = request.getParameter("name");
        CommonModel commonModel = new CommonModel();
        if(developerBusiness.updateDeveloper(id,name)){
            commonModel.setSuccess();
        }else{
            commonModel.setFail();
        }
        writer.println(GsonUtil.bean2Json(commonModel));
        writer.flush();
        writer.close();
    }
    private void deleteDeveloper(HttpServletRequest request,HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        String id = request.getParameter("id");
        CommonModel commonModel = new CommonModel();
        if(developerBusiness.deleteDeveloper(id)){
            commonModel.setSuccess();
        }else{
            commonModel.setFail();
        }
        writer.println(GsonUtil.bean2Json(commonModel));
        writer.flush();
        writer.close();
    }
}
