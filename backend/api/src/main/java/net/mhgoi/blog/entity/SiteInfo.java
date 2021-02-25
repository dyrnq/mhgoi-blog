package net.mhgoi.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 网站信息表(SiteInfo)表实体类
 *
 * @author 少年
 * @since 2020-07-31 07:53:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SiteInfo implements Serializable {
    private static final long serialVersionUID = 8732307694396026687L;
    //ID
    private String id;
    //博主头像
    private String avatar;
    //网站标语
    private String slogan;
    //博主名
    private String name;
    //域名
    private String domain;
    //网站通知
    private String notice;
    //网站简述
    private String description;
    //网站底部信息
    private String footer;
    //网站备案
    private String beian;
    //banner
    private String banner;
    //扩展信息
    private String extend;

    @TableField(exist = false)
    @JsonInclude
    private Map extendData;


    public Map getExtendData() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        Map map = new HashMap();
        try {
            map = objectMapper.readValue(extend, new TypeReference<HashMap>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return map;
    }
}