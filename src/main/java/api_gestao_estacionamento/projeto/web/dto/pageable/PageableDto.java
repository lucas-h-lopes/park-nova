package api_gestao_estacionamento.projeto.web.dto.pageable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PageableDto {

    private List content = new ArrayList<>();
    private boolean first;
    private boolean last;
    private Integer totalPages;
    private Integer size;
    @JsonProperty("page")
    private Integer number;
    @JsonProperty("pageElements")
    private Integer numberOfElements;
    private long totalElements;

    public PageableDto(Page page){
        this.content = page.getContent();
        this.first = page.isFirst();
        this.last = page.isLast();
        this.totalPages = page.getTotalPages();
        this.size = page.getSize();
        this.number = page.getNumber();
        this.numberOfElements = page.getNumberOfElements();
        this.totalElements = page.getTotalElements();
    }
}
