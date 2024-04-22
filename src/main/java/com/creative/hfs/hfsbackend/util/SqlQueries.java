package com.creative.hfs.hfsbackend.util;

public class SqlQueries {
    public static final String INSERT_USER =  "INSERT INTO hrbp_user_secret (user_id, status, password, is_default_password) VALUES (?, ?, ?,?)";
    public static final String UPDATE_USER = "UPDATE hrbp_user_secret SET status = ? WHERE user_id = ?";
}
