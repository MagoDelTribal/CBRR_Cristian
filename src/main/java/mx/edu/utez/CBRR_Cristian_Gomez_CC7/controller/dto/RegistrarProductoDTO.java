package mx.edu.utez.CBRR_Cristian_Gomez_CC7.controller.dto;

import com.sun.source.doctree.SeeTree;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrarProductoDTO {
    @NotBlank(message = "el producto debe llecvar un nombre")
    private String nombreProducto;


    @NotNull(message = "error")
    private Long id;

    @NotBlank(message = "fbbdfsyy")
    private String categoria;

    @NotBlank(message = "error")
    private int stock;

    @NotNull(message = "el producto esta mal")
    private boolean activo;



}
