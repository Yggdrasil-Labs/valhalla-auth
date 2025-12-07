package com.yggdrasil.labs.infrastructure.persistence.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.yggdrasil.labs.domain.auth.model.AuthCredential;
import com.yggdrasil.labs.domain.auth.model.enums.CredentialType;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.AuthCredentialDO;

/**
 * 登录凭证对象转换器
 *
 * <p>负责 AuthCredentialDO 与领域实体（Entity）之间的转换
 *
 * @author YoungerYang-Y
 */
@Mapper(componentModel = "spring")
public interface AuthCredentialConverter {

    AuthCredentialConverter INSTANCE = Mappers.getMapper(AuthCredentialConverter.class);

    @Mapping(
            target = "credentialType",
            source = "credentialType",
            qualifiedByName = "credentialTypeToEnum")
    AuthCredential toEntity(AuthCredentialDO authCredentialDO);

    @Mapping(
            target = "credentialType",
            source = "credentialType",
            qualifiedByName = "credentialTypeToCode")
    @Mapping(target = "deletedAt", ignore = true)
    AuthCredentialDO toDO(AuthCredential authCredential);

    @Named("credentialTypeToEnum")
    default CredentialType credentialTypeToEnum(Integer code) {
        return CredentialType.fromCode(code);
    }

    @Named("credentialTypeToCode")
    default Integer credentialTypeToCode(CredentialType type) {
        return type != null ? type.getCode() : null;
    }
}
