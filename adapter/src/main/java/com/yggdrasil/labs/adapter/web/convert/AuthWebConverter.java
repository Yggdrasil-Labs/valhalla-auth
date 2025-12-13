package com.yggdrasil.labs.adapter.web.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.yggdrasil.labs.adapter.web.request.CreateCredentialRequest;
import com.yggdrasil.labs.adapter.web.request.GetTokenRequest;
import com.yggdrasil.labs.adapter.web.request.GetUserRequest;
import com.yggdrasil.labs.adapter.web.request.ListCredentialsRequest;
import com.yggdrasil.labs.adapter.web.request.LoginRequest;
import com.yggdrasil.labs.adapter.web.request.LogoutRequest;
import com.yggdrasil.labs.adapter.web.request.RefreshTokenRequest;
import com.yggdrasil.labs.adapter.web.request.VerifyTokenRequest;
import com.yggdrasil.labs.client.dto.cmd.CreateCredentialCmd;
import com.yggdrasil.labs.client.dto.cmd.LoginCmd;
import com.yggdrasil.labs.client.dto.cmd.LogoutCmd;
import com.yggdrasil.labs.client.dto.cmd.RefreshTokenCmd;
import com.yggdrasil.labs.client.dto.cmd.VerifyTokenCmd;
import com.yggdrasil.labs.client.dto.query.GetTokenQuery;
import com.yggdrasil.labs.client.dto.query.GetUserQuery;
import com.yggdrasil.labs.client.dto.query.ListCredentialsQuery;

/**
 * 认证 Web 转换器
 *
 * <p>负责 Request DTO 与 Command/Query 之间的转换
 *
 * @author YoungerYang-Y
 */
@Mapper(componentModel = "spring")
public interface AuthWebConverter {

    AuthWebConverter INSTANCE = Mappers.getMapper(AuthWebConverter.class);

    /** LoginRequest → LoginCmd */
    LoginCmd toLoginCmd(LoginRequest request);

    /** RefreshTokenRequest → RefreshTokenCmd */
    RefreshTokenCmd toRefreshTokenCmd(RefreshTokenRequest request);

    /** LogoutRequest → LogoutCmd */
    LogoutCmd toLogoutCmd(LogoutRequest request);

    /** VerifyTokenRequest → VerifyTokenCmd */
    VerifyTokenCmd toVerifyTokenCmd(VerifyTokenRequest request);

    /** CreateCredentialRequest → CreateCredentialCmd */
    CreateCredentialCmd toCreateCredentialCmd(CreateCredentialRequest request);

    /** GetUserRequest → GetUserQuery */
    GetUserQuery toGetUserQuery(GetUserRequest request);

    /** ListCredentialsRequest → ListCredentialsQuery */
    ListCredentialsQuery toListCredentialsQuery(ListCredentialsRequest request);

    /** GetTokenRequest → GetTokenQuery */
    GetTokenQuery toGetTokenQuery(GetTokenRequest request);
}
