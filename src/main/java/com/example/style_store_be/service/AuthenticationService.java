package com.example.style_store_be.service;

import com.example.style_store_be.dto.request.AuthenticationRequest;
import com.example.style_store_be.dto.request.IntrospectRequest;
import com.example.style_store_be.dto.request.LogoutRequest;
import com.example.style_store_be.dto.request.RefreshRequest;
import com.example.style_store_be.dto.response.AuthenticationResponse;
import com.example.style_store_be.dto.response.IntrospectResponse;
import com.example.style_store_be.entity.InvalidatedToken;
import com.example.style_store_be.entity.Role;
import com.example.style_store_be.entity.User;
import com.example.style_store_be.exception.AppException;
import com.example.style_store_be.exception.Errorcode;
import com.example.style_store_be.repository.website.InvalidatedTokenRepository;
import com.example.style_store_be.repository.website.UserRepoSitory;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationService {
    UserRepoSitory userRepoSitory;
    InvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isvalid =true;
        try {
            verifyToken(token,false);

        } catch (Exception e) {
            isvalid = false;
        }
        return IntrospectResponse.builder()
                .valid(isvalid)
                .build();

    }

    private SignedJWT verifyToken(String token,Boolean isRefresh) throws ParseException, JOSEException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT  signedJWT = SignedJWT.parse(token);
        Date expityTime = (isRefresh )? new Date (signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(REFRESHABLE_DURATION,ChronoUnit.SECONDS).toEpochMilli())
                :signedJWT.getJWTClaimsSet().getExpirationTime();
        var verifed = signedJWT.verify(verifier);
        if (!verifed && expityTime.after(new Date())) throw new AppException(Errorcode.UNAUTHENTICATED);
        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(Errorcode.UNAUTHENTICATED);

        return signedJWT;
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user =userRepoSitory.findByEmail(request.getEmail())
            .orElseThrow(() -> new AppException(Errorcode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getMatKhau());
        if (!authenticated) throw new AppException(Errorcode.UNAUTHENTICATED);
        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }
    private String generateToken(User user){
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.HOURS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope",buildScope(user))
                .claim("id",user.getId())
                .claim("hoTen",user.getHoTen())
                .claim("tenDangNhap",user.getTenDangNhap())
                .claim("soDienThoai",user.getSoDienThoai())
                .build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader(JWSAlgorithm.HS512),
                jwtClaimsSet
        );
        try {
            signedJWT.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return signedJWT.serialize();
        } catch (JOSEException e) {
            log.error("Khong the tao token", e);
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        Role role = user.getRole();
        if (role != null) {
            stringJoiner.add("ROLE_" + role.getTen());
            if (!CollectionUtils.isEmpty(role.getPermissions())) {
                role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
            }
        }
        return stringJoiner.toString();
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {
            var signToken = verifyToken(request.getToken(),true);
            String jwtId = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();
            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jwtId)
                    .expiryTime(expiryTime)
                    .build();
            invalidatedTokenRepository.save(invalidatedToken);

        }catch (AppException exception){
            log.info("Token da bi xoa hoac het han");
        }

    };

    public AuthenticationResponse refreshToken (RefreshRequest request) throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken(),false);
        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);
        var userEmail = signedJWT.getJWTClaimsSet().getSubject();
        var user = userRepoSitory.findByEmail(userEmail)
                .orElseThrow(() -> new AppException(Errorcode.USER_NOT_EXISTED));
        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }
}
