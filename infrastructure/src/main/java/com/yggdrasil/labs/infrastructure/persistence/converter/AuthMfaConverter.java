package com.yggdrasil.labs.infrastructure.persistence.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.yggdrasil.labs.domain.auth.model.AuthMfa;
import com.yggdrasil.labs.domain.auth.model.enums.MfaType;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.AuthMfaDO;

/**
 * 多因子认证对象转换器
 *
 * <p>负责 AuthMfaDO 与领域实体（Entity）之间的转换
 *
 * @author YoungerYang-Y
 */
@Mapper(componentModel = "spring")
public interface AuthMfaConverter {

    AuthMfaConverter INSTANCE = Mappers.getMapper(AuthMfaConverter.class);

    @Mapping(target = "mfaType", source = "mfaType", qualifiedByName = "mfaTypeToEnum")
    AuthMfa toEntity(AuthMfaDO authMfaDO);

    @Mapping(target = "mfaType", source = "mfaType", qualifiedByName = "mfaTypeToCode")
    @Mapping(target = "deletedAt", ignore = true)
    AuthMfaDO toDO(AuthMfa authMfa);

    @Named("mfaTypeToEnum")
    default MfaType mfaTypeToEnum(Integer code) {
        return MfaType.fromCode(code);
    }

    @Named("mfaTypeToCode")
    default Integer mfaTypeToCode(MfaType type) {
        return type != null ? type.getCode() : null;
    }
}
