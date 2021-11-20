package com.zjm.springframework.springaop;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class BaseEntity {
    /**
     * 主键
     */
    private String id;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 最后一次更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 最后一次更新人
     */
    @TableField(value = "update_user",fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

}
