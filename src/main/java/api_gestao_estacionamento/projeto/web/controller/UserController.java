package api_gestao_estacionamento.projeto.web.controller;

import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.repository.projection.UserProjection;
import api_gestao_estacionamento.projeto.service.UserService;
import api_gestao_estacionamento.projeto.web.dto.pageable.PageableDto;
import api_gestao_estacionamento.projeto.web.dto.user.UserCreateDto;
import api_gestao_estacionamento.projeto.web.dto.user.UserResponseDto;
import api_gestao_estacionamento.projeto.web.mapper.PageableMapper;
import api_gestao_estacionamento.projeto.web.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id){
        User user = userService.findUserById(id);
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @GetMapping
    public ResponseEntity<PageableDto> getAll(Pageable pageable){
        Page<UserProjection> projection = userService.findAllUsers(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(projection));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
