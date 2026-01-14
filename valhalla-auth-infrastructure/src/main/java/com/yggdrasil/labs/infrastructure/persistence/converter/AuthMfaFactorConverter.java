package com.yggdrasil.labs.infrastructure.persistence.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.yggdrasil.labs.domain.auth.model.AuthMfaFactor;
import com.yggdrasil.labs.domain.auth.model.enums.MfaFactorStatus;
import com.yggdrasil.labs.domain.auth.model.enums.MfaType;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.AuthMfaFactorDO;

/**
 * 多因子认证因子对象转换器
 *
 * <p>负责 AuthMfaFactorDO 与领域实体（Entity）之间的转换
 *
 * @author YoungerYang-Y
 */
@Mapper(componentModel = "spring")
public interface AuthMfaFactorConverter {

    @Mapping(target = "mfaType", source = "mfaType", qualifiedByName = "mfaTypeToEnum")
    @Mapping(target = "status", source = "status", qualifiedByName = "mfaFactorStatusToEnum")
    AuthMfaFactor toEntity(AuthMfaFactorDO authMfaFactorDO);

    @Mapping(target = "mfaType", source = "mfaType", qualifiedByName = "mfaTypeToCode")
    @Mapping(target = "status", source = "status", qualifiedByName = "mfaFactorStatusToCode")
    @Mapping(target = "deletedAt", ignore = true)
    AuthMfaFactorDO toDO(AuthMfaFactor authMfaFactor);

    @Named("mfaTypeToEnum")
    default MfaType mfaTypeToEnum(Integer code) {
        return MfaType.fromCode(code);
    }

    @Named("mfaTypeToCode")
    default Integer mfaTypeToCode(MfaType type) {
        return type != null ? type.getCode() : null;
    }

    @Named("mfaFactorStatusToEnum")
    default MfaFactorStatus mfaFactorStatusToEnum(Integer code) {
        return MfaFactorStatus.fromCode(code);
    }

    @Named("mfaFactorStatusToCode")
    default Integer mfaFactorStatusToCode(MfaFactorStatus status) {
        return status != null ? status.getCode() : null;
    }
}
