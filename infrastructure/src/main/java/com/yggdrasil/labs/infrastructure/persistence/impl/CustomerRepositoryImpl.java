package com.yggdrasil.labs.infrastructure.persistence.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yggdrasil.labs.domain.customer.model.Customer;
import com.yggdrasil.labs.domain.customer.repository.CustomerRepository;
import com.yggdrasil.labs.infrastructure.persistence.converter.CustomerConverter;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.CustomerDO;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.mapper.CustomerMapper;

/**
 * CustomerRepository 实现类
 *
 * <p>使用 @AutoMybatis 自动生成的 Mapper（CustomerMapper）
 *
 * <p>注意：@AutoMybatis 会在编译期生成 CustomerMapper 和 CustomerService
 */
@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    @Autowired private CustomerMapper customerMapper;

    @Autowired private CustomerConverter customerConverter;

    @Override
    public Customer findById(String customerId) {
        CustomerDO customerDO = customerMapper.selectById(customerId);
        return customerDO == null ? null : customerConverter.toEntity(customerDO);
    }

    @Override
    public List<Customer> findByNameLike(String name) {
        LambdaQueryWrapper<CustomerDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(CustomerDO::getCompanyName, name);
        List<CustomerDO> customerDOList = customerMapper.selectList(wrapper);
        return customerConverter.toEntityList(customerDOList);
    }

    @Override
    public void save(Customer customer) {
        CustomerDO customerDO = customerConverter.toDO(customer);
        customerMapper.insert(customerDO);
    }

    @Override
    public void update(Customer customer) {
        CustomerDO customerDO = customerConverter.toDO(customer);
        customerMapper.updateById(customerDO);
    }

    @Override
    public void delete(String customerId) {
        customerMapper.deleteById(customerId);
    }

    @Override
    public boolean existsByCompanyName(String companyName) {
        LambdaQueryWrapper<CustomerDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CustomerDO::getCompanyName, companyName);
        return customerMapper.selectCount(wrapper) > 0;
    }
}
