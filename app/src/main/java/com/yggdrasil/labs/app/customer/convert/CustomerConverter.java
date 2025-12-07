package com.yggdrasil.labs.app.customer.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.yggdrasil.labs.client.dto.cmd.CreateCustomerCmd;
import com.yggdrasil.labs.domain.customer.model.CompanyType;
import com.yggdrasil.labs.domain.customer.model.Customer;
import com.yggdrasil.labs.domain.customer.model.SourceType;

/**
 * CustomerConverter：App 层 Cmd → Entity 转换器
 *
 * <p>负责将 Client 层的 Command 转换为 Domain 层的实体
 */
@Mapper(componentModel = "spring")
public interface CustomerConverter {

    /**
     * CreateCustomerCmd → Customer Entity
     *
     * @param cmd 创建客户命令
     * @return 客户实体
     */
    @Mapping(target = "customerId", source = "customerId")
    @Mapping(target = "memberId", source = "memberId")
    @Mapping(target = "companyName", source = "companyName")
    @Mapping(target = "globalId", ignore = true)
    @Mapping(target = "registeredCapital", constant = "0L")
    @Mapping(target = "sourceType", expression = "java(mapSourceType(cmd.getSource()))")
    @Mapping(target = "companyType", expression = "java(mapCompanyType(cmd.getCustomerType()))")
    Customer toEntity(CreateCustomerCmd cmd);

    /** 将 source 字符串映射为 SourceType 枚举 */
    default SourceType mapSourceType(String source) {
        if (source == null) {
            return null;
        }
        try {
            return SourceType.valueOf(source);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /** 将 customerType 字符串映射为 CompanyType 枚举 */
    default CompanyType mapCompanyType(String customerType) {
        if (customerType == null) {
            return CompanyType.POTENTIAL;
        }
        // 简单映射：ENTERPRISE -> IMPORTANT, PERSONAL -> POTENTIAL
        if ("ENTERPRISE".equals(customerType)) {
            return CompanyType.IMPORTANT;
        } else if ("PERSONAL".equals(customerType)) {
            return CompanyType.POTENTIAL;
        }
        return CompanyType.POTENTIAL;
    }
}
