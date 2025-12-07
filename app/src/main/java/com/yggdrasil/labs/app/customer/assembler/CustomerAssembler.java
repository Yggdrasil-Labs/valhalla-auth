package com.yggdrasil.labs.app.customer.assembler;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.yggdrasil.labs.client.dto.co.CustomerCO;
import com.yggdrasil.labs.domain.customer.model.CompanyType;
import com.yggdrasil.labs.domain.customer.model.Customer;
import com.yggdrasil.labs.domain.customer.model.SourceType;

/**
 * CustomerAssembler：Entity → CO 组装器
 *
 * <p>负责将 Domain 层的实体组装为 Client 层的 CO
 */
@Mapper(componentModel = "spring")
public interface CustomerAssembler {

    /**
     * Customer Entity → CustomerCO
     *
     * @param customer 客户实体
     * @return 客户 CO
     */
    @Mapping(target = "customerId", source = "customerId")
    @Mapping(target = "memberId", source = "memberId")
    @Mapping(target = "customerName", source = "companyName")
    @Mapping(
            target = "customerType",
            expression = "java(mapCustomerType(customer.getCompanyType()))")
    @Mapping(target = "companyName", source = "companyName")
    @Mapping(target = "source", expression = "java(mapSource(customer.getSourceType()))")
    CustomerCO toCustomerCO(Customer customer);

    /**
     * Customer Entity List → CustomerCO List
     *
     * @param customerList 客户实体列表
     * @return 客户 CO 列表
     */
    List<CustomerCO> toCustomerCOList(List<Customer> customerList);

    /** 将 SourceType 枚举映射为 source 字符串 */
    default String mapSource(SourceType sourceType) {
        return sourceType == null ? null : sourceType.name();
    }

    /** 将 CompanyType 枚举映射为 customerType 字符串 */
    default String mapCustomerType(CompanyType companyType) {
        if (companyType == null) {
            return "PERSONAL";
        }
        // 简单映射：IMPORTANT/VIP -> ENTERPRISE, 其他 -> PERSONAL
        if (companyType == CompanyType.IMPORTANT || companyType == CompanyType.VIP) {
            return "ENTERPRISE";
        }
        return "PERSONAL";
    }
}
