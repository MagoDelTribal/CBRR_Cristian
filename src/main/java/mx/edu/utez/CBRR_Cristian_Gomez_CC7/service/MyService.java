package mx.edu.utez.CBRR_Cristian_Gomez_CC7.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import mx.edu.utez.CBRR_Cristian_Gomez_CC7.controller.dto.RegistrarNuevoPedidoDTO;
import mx.edu.utez.CBRR_Cristian_Gomez_CC7.model.Producto;
import mx.edu.utez.CBRR_Cristian_Gomez_CC7.model.pedido.Pedido;
import mx.edu.utez.CBRR_Cristian_Gomez_CC7.repository.PedidoRepository;
import mx.edu.utez.CBRR_Cristian_Gomez_CC7.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class MyService {

    private final ProductoRepository productoRepository;
    private final PedidoRepository pedidoRepository;

    @Transactional
    public Pedido hacerPedido(long pedidoId, long productoId) {
        Prducto producto=productoRepository.findBy(productoId).orElseThrow(() -> new IllegalAccessException("el producto no existe "));
        if(!Pedido.isActivo()){
            throw new IllegalAccessException("El producto no esta activo");
        }
        Producto producto
    }




    @Transactional
    public Prestamo solicitarPrestamo(long lectorId, long libroId) {
        Lector lector = lectorRepository.findById(lectorId)
                .orElseThrow(() -> new IllegalArgumentException("El lector no existe"));

        if (!lector.isActivo()) {
            throw new IllegalStateException("El lector no está activo");
        }

        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new IllegalArgumentException("El libro no existe"));

        if (libro.getEjemplaresDisponibles() < 1) {
            throw new IllegalStateException("No hay ejemplares disponibles para este libro");
        }

        List<EstadoPrestamo> estadosActivos = List.of(EstadoPrestamo.PRESTADO, EstadoPrestamo.VENCIDO);
        long prestamosActivos = prestamoRepository.countByLectorIdAndEstadoIn(lectorId, estadosActivos);

        int limiteMaximo = lector.isMembresiaPre() ? 5 : 3;
        if (prestamosActivos >= limiteMaximo) {
            throw new IllegalStateException("El lector excedió su límite permitido de préstamos (" + limiteMaximo + ")");
        }

        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() - 1);
        libroRepository.save(libro);

        Prestamo prestamo = new Prestamo();
        prestamo.setLector(lector);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucionMaxima(LocalDate.now().plusDays(14));
        prestamo.setEstado(EstadoPrestamo.PRESTADO);

        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public Prestamo devolverLibro(long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new IllegalArgumentException("El préstamo no existe"));

        if (prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new IllegalStateException("El préstamo ya fue devuelto");
        }

        prestamo.setEstado(EstadoPrestamo.DEVUELTO);

        Libro libro = prestamo.getLibro();
        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() + 1);
        libroRepository.save(libro);

        if (LocalDate.now().isAfter(prestamo.getFechaDevolucionMaxima())) {
            Lector lector = prestamo.getLector();
            lector.setActivo(false);
            lectorRepository.save(lector);
        }

        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public Prestamo renovarPrestamo(long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new IllegalArgumentException("El préstamo no existe"));

        if (prestamo.getEstado() == EstadoPrestamo.VENCIDO || prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new IllegalStateException("No se puede renovar un préstamo en estado VENCIDO o DEVUELTO");
        }

        prestamo.setFechaDevolucionMaxima(prestamo.getFechaDevolucionMaxima().plusDays(7));
        return prestamoRepository.save(prestamo);
    }


    @Transactional
    public Lector rehabilitarLector(long lectorId) {
        Lector lector = lectorRepository.findById(lectorId)
                .orElseThrow(() -> new IllegalArgumentException("El lector no existe"));

        lector.setActivo(true);
        return lectorRepository.save(lector);
    }

    @Transactional(readOnly = true)
    public List<Prestamo> listarPrestamosActivosLector(long lectorId) {
        List<EstadoPrestamo> estadosActivos = List.of(EstadoPrestamo.PRESTADO, EstadoPrestamo.VENCIDO);
        return prestamoRepository.findByLectorIdAndEstadoIn(lectorId, estadosActivos);
    }
}
