package api_gestao_estacionamento.projeto.web.controller;

import api_gestao_estacionamento.projeto.jwt.JwtUserDetails;
import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.repository.projection.UserProjection;
import api_gestao_estacionamento.projeto.service.UserService;
import api_gestao_estacionamento.projeto.web.dto.pageable.PageableDto;
import api_gestao_estacionamento.projeto.web.dto.user.UserCreateDto;
import api_gestao_estacionamento.projeto.web.dto.user.UserNewPasswordDto;
import api_gestao_estacionamento.projeto.web.dto.user.UserResponseDto;
import api_gestao_estacionamento.projeto.web.mapper.PageableMapper;
import api_gestao_estacionamento.projeto.web.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> insert(@RequestBody @Valid UserCreateDto dto) {
        User user = UserMapper.toUser(dto);
        userService.insert(user);
        UserResponseDto result = UserMapper.toDto(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(result);
    }

    @PreAuthorize("hasRole('ADMIN') || (hasRole('CLIENT') && #id == authentication.principal.id)")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id){
        User user = userService.findUserById(id);
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<PageableDto> getAll(Pageable pageable){
        Page<UserProjection> projection = userService.findAllUsers(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(projection));
    }

    @PreAuthorize("hasRole('ADMIN') || (hasRole('CLIENT') && #id == authentication.principal.id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    @PutMapping
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid UserNewPasswordDto dto, @AuthenticationPrincipal JwtUserDetails details){
        userService.updatePassword(dto.getCurrentPassword(), dto.getNewPassword(), dto.getConfirmationPassword(), details);
        return ResponseEntity.noContent().build();
    }
}
