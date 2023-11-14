/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backing;

import Service.UserService;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Role;
import model.UserBean;

/**
 *
 * @author aablia
 */
@ManagedBean(eager = false)
@SessionScoped
public class UserBacking {

    private UserService userService;
    private String error = "";
    private UserBean operationBean;
    private UserBean searchBean;
    private UserBean loginBean;
    private List<UserBean> usersBeans;
    private String action;

    public UserBacking() {
        userService = new UserService();
        operationBean = new UserBean();
        loginBean = new UserBean();
        searchBean = new UserBean();
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserBean getOperationBean() {
        return operationBean;
    }

    public void setOperationBean(UserBean operationBean) {
        this.operationBean = operationBean;
    }

    public UserBean getSearchBean() {
        return searchBean;
    }

    public void setSearchBean(UserBean serchBean) {
        this.searchBean = serchBean;
    }

    public UserBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(UserBean loginBean) {
        this.loginBean = loginBean;
    }

    public UserBean getUserBean() {
        return operationBean;
    }

    public void setUserBean(UserBean userBean) {
        this.operationBean = userBean;
    }

    public List<UserBean> getUsersBeans() {
        return usersBeans;
    }

    public void setUsersBeans(List<UserBean> usersBeans) {
        this.usersBeans = usersBeans;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String doLogin() {
        if (loginBean.getUsername() != null && loginBean.getPassword() != null) {
            loginBean = userService.loginValidate(loginBean.getUsername(), loginBean.getPassword());
            if (loginBean.getRole().equals(Role.CLIENT)) {
                usersBeans = null;
                usersBeans.add(loginBean);
                return "masterJsf";
            } else {
                return goToHomePge();
            }
        }
        error = "email or password is not correct";
        return "login";
    }

    public List<UserBean> retriveUsersBeans() {
        return usersBeans;
    }

    public String addrOrUpdate() {
        if (action.equals("add")) {
            userService.addNewUser(operationBean);
        } else {
            userService.updateUser(operationBean);
        }
        return goToHomePge();
    }

    public String goToOpertion() {
        if (action.equals("add")) {
            operationBean = new UserBean();
        }
        return "operation";
    }

    public String delete() {
        userService.delete(operationBean.getId());
        return goToHomePge();
    }

    public String clear() {
        long id = operationBean.getId();
        operationBean = new UserBean();
        operationBean.setId(id);
        return "operation";
    }

    public String goToHomePge() {
        usersBeans = userService.viewAllusers();
        return "masterJsf";
    }

    public String search() {
        if (!"".equals(searchBean.getName())) {
            usersBeans = userService.search("name", searchBean.getName());
        } else if (!"".equals(searchBean.getAddress())) {
            usersBeans = userService.search("address", searchBean.getAddress());
        } else if (!"".equals(searchBean.getPostion())) {
            usersBeans = userService.search("postion", searchBean.getPostion());
        }

        return "masterJsf";
    }

    public String clearSearch() {
        searchBean = new UserBean();

        return goToHomePge();
    }
}
