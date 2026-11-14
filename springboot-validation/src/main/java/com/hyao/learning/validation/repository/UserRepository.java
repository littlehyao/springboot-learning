package com.hyao.learning.validation.repository;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    public int selectCountByUsername(String username) {
        // 模拟数据库查询，就不连库了
        return "hyao".equals(username) ? 1 : 0;
    }
}
