package Service;

import dao.UserDao;
import java.util.List;
import model.UserBean;

/**
 *
 * @author aablia
 */
public class UserService {

    private final UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    public UserBean loginValidate(String username, String password) {
        UserBean userBean = userDao.find(username);
        if (userBean != null && userBean.getPassword().equals(password)) {
            return userBean;
        }
        return null;
    }

    public UserBean viewuserInfo(String username) {
        UserBean userBean = null;
        if (username != null) {
            userBean = userDao.find(username);
        }
        return userBean;
    }

    public UserBean viewuserInfo(Long id) {
        UserBean userBean = null;
        if (id != null) {
            userBean = userDao.findById(id);
        }
        return userBean;
    }

    public List<UserBean> viewAllusers() {
        List<UserBean> usersBeans = userDao.findAll();
        if (!usersBeans.isEmpty()) {
            return usersBeans;
        }
        return null;
    }

    public boolean addNewUser(UserBean userBean) {
        if (userBean != null && userDao.find(userBean.getUsername()) == null) {
            int num = userDao.insert(userBean);
            return num != 0;
        }
        return false;
    }

    public void updateUser(UserBean userBean) {
        if ((userBean.getId() != null && userBean.getUsername() != null && userBean.getPassword() != null && userBean.getRole() != null)
                && (userBean.getName() != null || userBean.getAddress() != null || userBean.getPostion() != null || userBean.getSkill() != null)) {
            userDao.update(userBean);
        }
    }

    public UserBean updateView(Long id) {
        UserBean userBean = null;
        if (id != null) {
            userBean = userDao.findById(id);
        }
        return userBean;
    }

    public boolean delete(Long id) {
        if (id != null) {
            return userDao.delete(id) != 0;
        }
        return false;
    }

    public List<UserBean> search(String column, String value) {
        return userDao.search(column, value);
    }

}
