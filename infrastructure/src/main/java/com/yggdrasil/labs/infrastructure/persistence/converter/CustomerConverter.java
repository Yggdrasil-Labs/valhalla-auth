package com.yggdrasil.labs.infrastructure.persistence.converter;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.yggdrasil.labs.domain.customer.model.Customer;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.CustomerDO;

/**
 * Customer DO ↔ Entity Converter
 *
 * <p>负责 CustomerDO 与 Customer 实体之间的转换
 *
 * <p>使用 MapStruct 自动生成转换代码
 *
 * <p>注意：CustomerDO 中没有 sourceType 和 companyType 字段，这些字段仅存在于 Customer 实体中
 */
@Mapper(componentModel = "spring")
public interface CustomerConverter {

    /**
     * DO → Entity
     *
     * @param customerDO 数据库对象
     * @return 领域实体
     */
    @Mapping(target = "sourceType", ignore = true)
    @Mapping(target = "companyType", ignore = true)
    Customer toEntity(CustomerDO customerDO);

    /**
     * Entity → DO
     *
     * @param customer 领域实体
     * @return 数据库对象
     */
    CustomerDO toDO(Customer customer);

    /**
     * DO List → Entity List
     *
     * @param customerDOList DO 列表
     * @return 实体列表
     */
    List<Customer> toEntityList(List<CustomerDO> customerDOList);

    /**
     * Entity List → DO List
     *
     * @param customerList 实体列表
     * @return DO 列表
     */
    List<CustomerDO> toDOList(List<Customer> customerList);
}
