package com.leigq.shardingspherestudy.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.util.Date;

/**
 * The type Order.
 * @author leiguoqing
 */
@FieldNameConstants
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "t_order")
public class Order implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 1099157639421628833L;

    @TableId(value = "order_id", type = IdType.ASSIGN_ID)
    private Long orderId;

    private String name;

    /**消息唯一标识*/
    private String msgId;

    private Date createTime;

    private Date updateTime;

}
