package com.yggdrasil.labs.infrastructure.persistence.dataobject;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yggdrasil.labs.mybatis.annotation.AutoMybatis;

import lombok.Data;

@Data
@AutoMybatis
@TableName("customer")
public class CustomerDO {
    @TableId private String customerId;
    private String memberId;
    private String globalId;
    private long registeredCapital;
    private String companyName;
}
