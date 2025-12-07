package com.yggdrasil.labs.domain.customer.model;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.cola.domain.Entity;
import com.alibaba.cola.exception.BizException;

import lombok.Data;

/**
 * Customer 领域实体
 *
 * <p>封装客户相关的业务规则和行为
 *
 * <p><b>职责：</b>
 *
 * <ul>
 *   <li>维护客户的领域不变性
 *   <li>封装业务规则（如公司类型判断、冲突检查等）
 *   <li>保证实体状态的有效性
 * </ul>
 */
@Data
@Entity
public class Customer {

    private String customerId;
    private String memberId;
    private String globalId;
    private long registeredCapital;
    private String companyName;
    private SourceType sourceType;
    private CompanyType companyType;

    public Customer() {}

    /**
     * 判断是否为大企业
     *
     * @return 注册资金大于1000万返回 true
     */
    public boolean isBigCompany() {
        return registeredCapital > 10000000; // 注册资金大于1000万的是大企业
    }

    /**
     * 判断是否为中小企业
     *
     * @return 注册资金在10万到100万之间返回 true
     */
    public boolean isSME() {
        return registeredCapital > 10000 && registeredCapital < 1000000; // 注册资金大于10万小于100万的为中小企业
    }

    /**
     * 检查公司名称冲突
     *
     * <p>如果公司名称已经存在，抛出业务异常
     *
     * @throws BizException 公司名称冲突时抛出
     */
    public void checkConflict() {
        // Per different biz, the check policy could be different, if so, use ExtensionPoint
        if ("ConflictCompanyName".equals(this.companyName)) {
            throw new BizException(this.companyName + " has already existed, you can not add it");
        }
    }

    /**
     * 校验领域不变性
     *
     * <p>确保实体的核心属性满足业务规则
     *
     * @throws BizException 校验失败时抛出
     */
    public void validate() {
        if (StringUtils.isBlank(customerId)) {
            throw new BizException("客户ID不能为空");
        }
        if (StringUtils.isBlank(companyName)) {
            throw new BizException("公司名称不能为空");
        }
        if (registeredCapital < 0) {
            throw new BizException("注册资金不能为负数");
        }
        if (companyType == null) {
            throw new BizException("公司类型不能为空");
        }
    }
}
