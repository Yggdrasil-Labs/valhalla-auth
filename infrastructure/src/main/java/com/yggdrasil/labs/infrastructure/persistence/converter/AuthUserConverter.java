package com.yggdrasil.labs.infrastructure.persistence.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.yggdrasil.labs.domain.auth.model.AuthUser;
import com.yggdrasil.labs.domain.auth.model.enums.UserStatus;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.AuthUserDO;

/**
 * 用户认证对象转换器
 *
 * <p>负责 AuthUserDO 与领域实体（Entity）之间的转换
 *
 * @author YoungerYang-Y
 */
@Mapper(componentModel = "spring")
public interface AuthUserConverter {

    AuthUserConverter INSTANCE = Mappers.getMapper(AuthUserConverter.class);

    @Mapping(target = "status", source = "status", qualifiedByName = "statusToEnum")
    AuthUser toEntity(AuthUserDO authUserDO);

    @Mapping(target = "status", source = "status", qualifiedByName = "statusToCode")
    @Mapping(target = "deletedAt", ignore = true)
    AuthUserDO toDO(AuthUser authUser);

    @Named("statusToEnum")
    default UserStatus statusToEnum(Integer code) {
        return UserStatus.fromCode(code);
    }

    @Named("statusToCode")
    default Integer statusToCode(UserStatus status) {
        return status != null ? status.getCode() : null;
    }
}
