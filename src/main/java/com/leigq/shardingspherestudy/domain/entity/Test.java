package com.leigq.shardingspherestudy.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The type Test.
 * @author leiguoqing
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "test")
public class Test implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 1099157639421628833L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Integer age;

}
