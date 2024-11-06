package api_gestao_estacionamento.projeto.web.mapper;

import api_gestao_estacionamento.projeto.web.dto.pageable.PageableDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableMapper {

    public static PageableDto toDto(Page page){
        return new PageableDto(page);
    }
}
