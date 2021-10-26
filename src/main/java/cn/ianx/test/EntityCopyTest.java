package cn.ianx.test;

import cn.ianx.util.BeanCopyUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityCopyTest {

    public static void main(String[] args) {

        List<User> users = new ArrayList<>(Arrays
                .asList(new User("ian", 20),
                        new User("哈哈", 31),
                        new User("嘿嘿", 43)));
        System.out.println(BeanCopyUtils.copyProperties(users.get(0), User.class));
        System.out.println(BeanCopyUtils.copyProperties(users.get(1), new User()));
        System.out.println(BeanCopyUtils.copyProperties(users, User.class));

    }


    public static class User {
        private String userName;
        private Integer age;

        public User() {
        }

        public User(String userName, Integer age) {
            this.userName = userName;
            this.age = age;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "userName='" + userName + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
