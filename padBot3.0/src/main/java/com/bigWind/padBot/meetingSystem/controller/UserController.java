package com.bigWind.padBot.meetingSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/getUsers")
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public List<Map<String, Object>> getUsers() {
        String sql = "select id,name from annex limit 10" ;
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }
}

