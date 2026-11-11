package mx.edu.utez.CBRR_Cristian_Gomez_CC7.model.pedido;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.CBRR_Cristian_Gomez_CC7.model.Producto;

@Entity
@Table(name="pedido")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String nombreCliente;

    private int cantidad;

    private double precioUnitario;

    private double total;

    @Enumerated
    private Estado estado=Estado.pendiente;

    @ManyToOne
    @JoinColumn(name="producto_id")
    private Producto producto;



}
