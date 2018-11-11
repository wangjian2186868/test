package com.wj.mybits.po;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class MybitsDemo {
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws Exception {
        //指定全局配置文件路径
        String resource = "SqlMapConfig.xml";
        //加载资源文件（全局配置文件和映射文件）
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //通过构建者模式获取sqlSessionFactory对象
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    @Ignore
    public void testSelect() {
        //由工厂创建SqlSession会话
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //调用sqlsession接口，去实现数据库的增删改查操作
        User user = sqlSession.selectOne("test.findUserById", 1);
        System.out.println(user);
        //释放资源
        sqlSession.close();
    }

    @Test
    @Ignore
    public void testSelectByName() {
        //数据库会话实例
        SqlSession sqlSession = null;
        try {
            //创建数据库会话实例sqlSession
            sqlSession = sqlSessionFactory.openSession();
            //根据用户姓名模糊查询多个用户
            List<User> list = sqlSession.selectList("test.findUserByUserName", "j");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Test
    @Ignore
    public void insertUser() {
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();
            User user = new User();
            user.setUserName("张小明");
            user.setAddress("定慧东里");
            user.setGender(1);
            user.setPhone(123);
            user.setEmail("aaaa@123.com");
            int uid = sqlSession.insert("test.insertUser", user);
            //提交事务
            sqlSession.commit();
            System.out.println(uid);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Test
    @Ignore
    public void testDelete() {
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();
            sqlSession.delete("test.deleteUserById", 2);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Test
    public void updateUser() {
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();
            User user = new User();
            user.setUid(1);
            user.setUserName("张小明");
            user.setAddress("定慧东里");
            user.setGender(1);
            user.setPhone(123);
            user.setEmail("aaaa@123.com");

            sqlSession.update("test.updateUserById", user);
            sqlSession.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

}
