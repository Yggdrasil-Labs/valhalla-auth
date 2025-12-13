package com.yggdrasil.labs.app.auth.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.yggdrasil.labs.client.dto.enums.CredentialTypeEnum;
import com.yggdrasil.labs.client.dto.enums.TokenTypeEnum;
import com.yggdrasil.labs.client.dto.enums.UserStatusEnum;
import com.yggdrasil.labs.domain.auth.model.enums.CredentialType;
import com.yggdrasil.labs.domain.auth.model.enums.TokenType;
import com.yggdrasil.labs.domain.auth.model.enums.UserStatus;

/**
 * 认证对象转换器
 *
 * <p>负责 Client DTO 与 Domain Entity 之间的转换
 *
 * @author YoungerYang-Y
 */
@Mapper(componentModel = "spring")
public interface AuthConverter {

    AuthConverter INSTANCE = Mappers.getMapper(AuthConverter.class);

    /** CredentialTypeEnum 转 CredentialType */
    default CredentialType toDomainCredentialType(CredentialTypeEnum credentialTypeEnum) {
        if (credentialTypeEnum == null) {
            return null;
        }
        return CredentialType.fromCode(credentialTypeEnum.getCode());
    }

    /** CredentialType 转 CredentialTypeEnum */
    default CredentialTypeEnum toClientCredentialType(CredentialType credentialType) {
        if (credentialType == null) {
            return null;
        }
        return CredentialTypeEnum.fromCode(credentialType.getCode());
    }

    /** UserStatusEnum 转 UserStatus */
    default UserStatus toDomainUserStatus(UserStatusEnum userStatusEnum) {
        if (userStatusEnum == null) {
            return null;
        }
        return UserStatus.fromCode(userStatusEnum.getCode());
    }

    /** UserStatus 转 UserStatusEnum */
    default UserStatusEnum toClientUserStatus(UserStatus userStatus) {
        if (userStatus == null) {
            return null;
        }
        return UserStatusEnum.fromCode(userStatus.getCode());
    }

    /** TokenTypeEnum 转 TokenType */
    default TokenType toDomainTokenType(TokenTypeEnum tokenTypeEnum) {
        if (tokenTypeEnum == null) {
            return null;
        }
        return TokenType.fromCode(tokenTypeEnum.getCode());
    }

    /** TokenType 转 TokenTypeEnum */
    default TokenTypeEnum toClientTokenType(TokenType tokenType) {
        if (tokenType == null) {
            return null;
        }
        return TokenTypeEnum.fromCode(tokenType.getCode());
    }
}
