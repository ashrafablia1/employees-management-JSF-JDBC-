package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Role;
import model.UserBean;

/**
 *
 * @author aablia
 */
public class UserDao {

    private Connection establishConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/employee",
                    "postgres", "Aa1122");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int insert(UserBean userBean) {
        int num = 0;
        Connection conn = establishConnection();
        try {
            String sql = "INSERT INTO users(email, role, password, name, age, skill, postion, address) VALUES(?,?,?,?,?,?,?,?);";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, userBean.getUsername());
            statement.setString(2, userBean.getRole().name());
            statement.setString(3, userBean.getPassword());
            statement.setString(4, userBean.getName());
            statement.setInt(5, userBean.getAge());
            statement.setString(6, userBean.getSkill());
            statement.setString(7, userBean.getPostion());
            statement.setString(8, userBean.getAddress());

            num = statement.executeUpdate();
            statement.close();
            return num;

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return num;
    }

    public UserBean find(String email) {
        Connection conn = establishConnection();
        UserBean userBean = null;
        try {
            String sql = "SELECT * FROM users WHERE email=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                userBean = new UserBean();
                userBean.setId(result.getLong("id"));
                userBean.setUsername(result.getString("email"));
                userBean.setRole(Role.valueOf(result.getString("role")));
                userBean.setName(result.getString("name"));
                userBean.setAge(result.getInt("age"));
                userBean.setSkill(result.getString("skill"));
                userBean.setAddress(result.getString("address"));
                userBean.setPassword(result.getString("password"));

                statement.close();
                result.close();
                return userBean;
            }
            return userBean;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return userBean;
    }

    public List<UserBean> findAll() {
        List<UserBean> usersBeans = new ArrayList<>();
        Connection conn = establishConnection();
        UserBean userBean = null;

        try {
            String sql = "SELECT * FROM users";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                userBean = new UserBean();
                userBean.setId(result.getLong("id"));
                userBean.setUsername(result.getString("email"));
                userBean.setName(result.getString("name"));
                userBean.setAge(result.getInt("age"));
                userBean.setSkill(result.getString("skill"));
                userBean.setAddress(result.getString("address"));
                userBean.setPostion(result.getString("postion"));
                userBean.setPassword(result.getString("password"));
                userBean.setRole(Role.valueOf(result.getString("role")));

                usersBeans.add(userBean);
            }
            statement.close();
            result.close();
            return usersBeans;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return usersBeans;
    }

    public int update(UserBean userBean) {

        Connection conn = establishConnection();
        int num = 0;
        try {

            String sql = "UPDATE users SET email= ?, role= ?,password= ?, name= ?, age= ?, skill= ?, postion= ?, address= ? WHERE id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, userBean.getUsername());
            statement.setString(2, userBean.getRole().name());
            statement.setString(3, userBean.getPassword());
            statement.setString(4, userBean.getName());
            statement.setInt(5, userBean.getAge());
            statement.setString(6, userBean.getSkill());
            statement.setString(7, userBean.getPostion());
            statement.setString(8, userBean.getAddress());
            statement.setLong(9, userBean.getId());
            num = statement.executeUpdate();

            statement.close();
            return num;

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return num;

    }

    public UserBean findById(Long id) {
        Connection conn = establishConnection();
        UserBean userBean = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            String sql = "SELECT * FROM users WHERE id=?";
            statement = conn.prepareStatement(sql);
            statement.setLong(1, id);
            result = statement.executeQuery();
            if (result.next()) {
                userBean = new UserBean();
                userBean.setId(result.getLong("id"));
                userBean.setUsername(result.getString("email"));
                userBean.setRole(Role.valueOf(result.getString("role")));
                userBean.setName(result.getString("name"));
                userBean.setAge(result.getInt("age"));
                userBean.setSkill(result.getString("skill"));
                userBean.setAddress(result.getString("address"));
                userBean.setPassword(result.getString("password"));
                userBean.setPostion(result.getString("postion"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                result.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return userBean;
    }

    public int delete(Long id) {

        Connection conn = establishConnection();
        int num = 0;
        try {
            String sql = "DELETE FROM users WHERE id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, id);
            num = statement.executeUpdate();
            statement.close();
            return num;

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return num;
    }

    public List<UserBean> search(String column, String value) {
        List<UserBean> usersBeans = new ArrayList<>();
        Connection conn = establishConnection();
        UserBean userBean = null;

        try {
            String sql = "SELECT * FROM users WHERE " + column + " LIKE ?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, "%" + value + "%");
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                userBean = new UserBean();
                userBean.setId(result.getLong("id"));
                userBean.setUsername(result.getString("email"));
                userBean.setName(result.getString("name"));
                userBean.setAge(result.getInt("age"));
                userBean.setSkill(result.getString("skill"));
                userBean.setAddress(result.getString("address"));
                userBean.setPostion(result.getString("postion"));
                userBean.setPassword(result.getString("password"));
                userBean.setRole(Role.valueOf(result.getString("role")));

                usersBeans.add(userBean);
            }
            statement.close();
            result.close();
            return usersBeans;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return usersBeans;
    }
}
