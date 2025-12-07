package com.yggdrasil.labs.infrastructure.persistence.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.yggdrasil.labs.domain.auth.model.AuthLoginLog;
import com.yggdrasil.labs.domain.auth.model.enums.CredentialType;
import com.yggdrasil.labs.domain.auth.model.enums.LoginStatus;
import com.yggdrasil.labs.domain.auth.model.enums.LoginType;
import com.yggdrasil.labs.domain.auth.model.enums.MfaType;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.AuthLoginLogDO;

/**
 * 登录日志对象转换器
 *
 * <p>负责 AuthLoginLogDO 与领域实体（Entity）之间的转换
 *
 * @author YoungerYang-Y
 */
@Mapper(componentModel = "spring")
public interface AuthLoginLogConverter {

    AuthLoginLogConverter INSTANCE = Mappers.getMapper(AuthLoginLogConverter.class);

    @Mapping(
            target = "credentialType",
            source = "credentialType",
            qualifiedByName = "credentialTypeToEnum")
    @Mapping(target = "loginType", source = "loginType", qualifiedByName = "loginTypeToEnum")
    @Mapping(target = "loginStatus", source = "loginStatus", qualifiedByName = "loginStatusToEnum")
    @Mapping(target = "mfaType", source = "mfaType", qualifiedByName = "mfaTypeToEnum")
    AuthLoginLog toEntity(AuthLoginLogDO authLoginLogDO);

    @Mapping(
            target = "credentialType",
            source = "credentialType",
            qualifiedByName = "credentialTypeToCode")
    @Mapping(target = "loginType", source = "loginType", qualifiedByName = "loginTypeToCode")
    @Mapping(target = "loginStatus", source = "loginStatus", qualifiedByName = "loginStatusToCode")
    @Mapping(target = "mfaType", source = "mfaType", qualifiedByName = "mfaTypeToCode")
    AuthLoginLogDO toDO(AuthLoginLog authLoginLog);

    @Named("credentialTypeToEnum")
    default CredentialType credentialTypeToEnum(Integer code) {
        return CredentialType.fromCode(code);
    }

    @Named("credentialTypeToCode")
    default Integer credentialTypeToCode(CredentialType type) {
        return type != null ? type.getCode() : null;
    }

    @Named("loginTypeToEnum")
    default LoginType loginTypeToEnum(Integer code) {
        return LoginType.fromCode(code);
    }

    @Named("loginTypeToCode")
    default Integer loginTypeToCode(LoginType type) {
        return type != null ? type.getCode() : null;
    }

    @Named("loginStatusToEnum")
    default LoginStatus loginStatusToEnum(Integer code) {
        return LoginStatus.fromCode(code);
    }

    @Named("loginStatusToCode")
    default Integer loginStatusToCode(LoginStatus status) {
        return status != null ? status.getCode() : null;
    }

    @Named("mfaTypeToEnum")
    default MfaType mfaTypeToEnum(Integer code) {
        return MfaType.fromCode(code);
    }

    @Named("mfaTypeToCode")
    default Integer mfaTypeToCode(MfaType type) {
        return type != null ? type.getCode() : null;
    }
}
