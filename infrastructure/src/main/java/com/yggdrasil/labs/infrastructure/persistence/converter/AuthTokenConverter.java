package com.yggdrasil.labs.infrastructure.persistence.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.yggdrasil.labs.domain.auth.model.AuthToken;
import com.yggdrasil.labs.domain.auth.model.enums.TokenType;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.AuthTokenDO;

/**
 * Token对象转换器
 *
 * <p>负责 AuthTokenDO 与领域实体（Entity）之间的转换
 *
 * @author YoungerYang-Y
 */
@Mapper(componentModel = "spring")
public interface AuthTokenConverter {

    AuthTokenConverter INSTANCE = Mappers.getMapper(AuthTokenConverter.class);

    @Mapping(target = "tokenType", source = "tokenType", qualifiedByName = "tokenTypeToEnum")
    AuthToken toEntity(AuthTokenDO authTokenDO);

    @Mapping(target = "tokenType", source = "tokenType", qualifiedByName = "tokenTypeToCode")
    AuthTokenDO toDO(AuthToken authToken);

    @Named("tokenTypeToEnum")
    default TokenType tokenTypeToEnum(Integer code) {
        return TokenType.fromCode(code);
    }

    @Named("tokenTypeToCode")
    default Integer tokenTypeToCode(TokenType type) {
        return type != null ? type.getCode() : null;
    }
}
