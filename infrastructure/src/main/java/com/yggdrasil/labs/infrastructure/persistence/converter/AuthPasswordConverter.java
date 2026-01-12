package com.yggdrasil.labs.infrastructure.persistence.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.yggdrasil.labs.domain.auth.model.AuthPassword;
import com.yggdrasil.labs.domain.auth.model.enums.PasswordAlgo;
import com.yggdrasil.labs.domain.auth.model.enums.PasswordStatus;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.AuthPasswordDO;

/**
 * 用户密码对象转换器
 *
 * <p>负责 AuthPasswordDO 与领域实体（Entity）之间的转换
 *
 * @author YoungerYang-Y
 */
@Mapper(componentModel = "spring")
public interface AuthPasswordConverter {

    @Mapping(
            target = "passwordAlgo",
            source = "passwordAlgo",
            qualifiedByName = "passwordAlgoToEnum")
    @Mapping(
            target = "passwordStatus",
            source = "passwordStatus",
            qualifiedByName = "passwordStatusToEnum")
    AuthPassword toEntity(AuthPasswordDO authPasswordDO);

    @Mapping(
            target = "passwordAlgo",
            source = "passwordAlgo",
            qualifiedByName = "passwordAlgoToCode")
    @Mapping(
            target = "passwordStatus",
            source = "passwordStatus",
            qualifiedByName = "passwordStatusToCode")
    AuthPasswordDO toDO(AuthPassword authPassword);

    @Named("passwordAlgoToEnum")
    default PasswordAlgo passwordAlgoToEnum(Integer code) {
        return PasswordAlgo.fromCode(code);
    }

    @Named("passwordAlgoToCode")
    default Integer passwordAlgoToCode(PasswordAlgo algo) {
        return algo != null ? algo.getCode() : null;
    }

    @Named("passwordStatusToEnum")
    default PasswordStatus passwordStatusToEnum(Integer code) {
        return PasswordStatus.fromCode(code);
    }

    @Named("passwordStatusToCode")
    default Integer passwordStatusToCode(PasswordStatus status) {
        return status != null ? status.getCode() : null;
    }
}
