package api_gestao_estacionamento.projeto.web.controller;

import api_gestao_estacionamento.projeto.model.Spot;
import api_gestao_estacionamento.projeto.service.SpotService;
import api_gestao_estacionamento.projeto.web.dto.pageable.PageableDto;
import api_gestao_estacionamento.projeto.web.dto.spot.SpotCreateDto;
import api_gestao_estacionamento.projeto.web.dto.spot.SpotResponseDto;
import api_gestao_estacionamento.projeto.web.mapper.PageableMapper;
import api_gestao_estacionamento.projeto.web.mapper.SpotMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/spots")
@AllArgsConstructor
public class SpotController {

    private SpotService spotService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SpotResponseDto> insert(@RequestBody @Valid SpotCreateDto dto) {
        Spot spot = SpotMapper.toSpot(dto);
        spotService.insert(spot);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{code}")
                .buildAndExpand(spot.getCode())
                .toUri();
        return ResponseEntity.created(uri).body(SpotMapper.toResponseDto(spot));
    }

    @GetMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SpotResponseDto> getByCode(@PathVariable String code) {
        Spot spot = spotService.getByCode(code);
        return ResponseEntity.ok(SpotMapper.toResponseDto(spot));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDto> getAll(Pageable pageable, @RequestParam(name = "status", required = false) String status) {
        Page page;
        if(status == null){
            page = spotService.getAll(pageable);
        }else{
            page = spotService.getAllParameterized(pageable, status);
        }
        return ResponseEntity.ok(
                PageableMapper.toDto(page)
        );

    }
}
