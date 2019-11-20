package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by gyq on 2019/1/21.
 */
@Repository
public interface UserMapper {
    User Sel(int id);
}
