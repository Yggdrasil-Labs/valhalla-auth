package com.yggdrasil.labs.infrastructure.persistence.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.yggdrasil.labs.domain.auth.model.AuthFailedAttempt;
import com.yggdrasil.labs.domain.auth.model.enums.CredentialType;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.AuthFailedAttemptDO;

/**
 * 失败尝试对象转换器
 *
 * <p>负责 AuthFailedAttemptDO 与领域实体（Entity）之间的转换
 *
 * @author YoungerYang-Y
 */
@Mapper(componentModel = "spring")
public interface AuthFailedAttemptConverter {

    AuthFailedAttemptConverter INSTANCE = Mappers.getMapper(AuthFailedAttemptConverter.class);

    @Mapping(
            target = "credentialType",
            source = "credentialType",
            qualifiedByName = "credentialTypeToEnum")
    AuthFailedAttempt toEntity(AuthFailedAttemptDO authFailedAttemptDO);

    @Mapping(
            target = "credentialType",
            source = "credentialType",
            qualifiedByName = "credentialTypeToCode")
    AuthFailedAttemptDO toDO(AuthFailedAttempt authFailedAttempt);

    @Named("credentialTypeToEnum")
    default CredentialType credentialTypeToEnum(Integer code) {
        return CredentialType.fromCode(code);
    }

    @Named("credentialTypeToCode")
    default Integer credentialTypeToCode(CredentialType type) {
        return type != null ? type.getCode() : null;
    }
}
