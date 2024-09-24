package com.bd;

import com.bd.model.constant.Patterns;
import com.bd.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class BuildingDreamApplicationTests {
    @Autowired
    private UsersService usersService;

    @Test
    void contextLoads() {
        String token = usersService.loginByUserAccount("740969457", "abc12345678");
        System.out.println(token);
        String s = usersService.loginByMobile("18649729785", "18649729785");
        System.out.println(s);
    }

}
