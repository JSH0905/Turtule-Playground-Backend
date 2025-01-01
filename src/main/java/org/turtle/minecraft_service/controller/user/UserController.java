package org.turtle.minecraft_service.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.turtle.minecraft_service.config.HttpErrorCode;
import org.turtle.minecraft_service.domain.primary.User;
import org.turtle.minecraft_service.dto.user.inquiry.UserInfoInquiryDto;
import org.turtle.minecraft_service.dto.user.inquiry.UserInfoInquiryResponseDto;
import org.turtle.minecraft_service.service.user.UserService;
import org.turtle.minecraft_service.swagger.ApiErrorCodeExample;
import org.turtle.minecraft_service.swagger.ApiErrorCodeExamples;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "회원 정보")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원 정보 조회")
    @ApiErrorCodeExamples(value = {
            @ApiErrorCodeExample(value = HttpErrorCode.AccessDeniedError),
            @ApiErrorCodeExample(value = HttpErrorCode.NotValidAccessTokenError),
            @ApiErrorCodeExample(value = HttpErrorCode.ExpiredAccessTokenError),
            @ApiErrorCodeExample(value = HttpErrorCode.UserNotFoundError)
    })
    @GetMapping()
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserInfoInquiryResponseDto.class )))
    public ResponseEntity<UserInfoInquiryResponseDto> getUserInfo(@AuthenticationPrincipal User user) {

        UserInfoInquiryDto userInfoInquiryDto = userService.getUserInfo(user);

        return new ResponseEntity<>(UserInfoInquiryResponseDto.fromDto(userInfoInquiryDto), HttpStatus.OK);

    }
}