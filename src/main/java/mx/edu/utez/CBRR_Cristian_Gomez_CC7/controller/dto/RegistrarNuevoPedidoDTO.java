package mx.edu.utez.CBRR_Cristian_Gomez_CC7.controller.dto;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.CBRR_Cristian_Gomez_CC7.model.pedido.Estado;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarNuevoPedidoDTO {

    @NotBlank(message = "error el nombre esta vacio")
    private String nombreCliente;

    @NotNull(message = "error")
    private int id;

    @NotBlank(message = "error")
    private double precioUnitario;

    @NotBlank(message = "error")
    private double total;

    @Enumerated
    @GeneratedValue(strategy = Estado.pendiente)
    if(Estado!="activo"){
        message("error el product no esta activo")



    }

}
