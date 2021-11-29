package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "certificates")
public class CertificateDTO extends RepresentationModel<CertificateDTO> {

    private Long id;
    @Size(min = 2, max = 50, message = "{certificate.incorrectNameLength}")
    @NotBlank(message = "{certificate.incorrectName}")
    private String name;
    @NotBlank(message = "{certificate.incorrectDescription}")
    private String description;
    @Min(value = 1, message = "{certificate.incorrectPrice.less}")
    @Max(value = 999, message = "{certificate.incorrectPrice.more}")
    private BigDecimal price;
    @Min(value = 1, message = "{certificate.incorrectDuration.less}")
    @Max(value = 100, message = "{certificate.incorrectDuration.more}")
    private Integer duration;
    private List<TagDTO> tags;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime createDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime lastUpdateDate;

}
