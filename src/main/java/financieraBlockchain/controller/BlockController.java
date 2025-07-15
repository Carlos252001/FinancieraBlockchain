package financieraBlockchain.controller;


import financieraBlockchain.model.Block;
import financieraBlockchain.service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
public class BlockController {

    @Autowired
    private BlockchainService blockchainService;

    @GetMapping("/nuevo-prestamo")
    public String mostrarFormulario() {
        return "nuevo_prestamo";
    }

    @PostMapping("/nuevo-prestamo")
    public String registrarPrestamo(
            @RequestParam String companyName,
            @RequestParam BigDecimal amount,
            @RequestParam Double interestRate,
            @RequestParam String dueDate,
            Model model
    ) {
        LocalDate fechaVencimiento = LocalDate.parse(dueDate);

        Block nuevo = blockchainService.crearNuevoBloque(companyName, amount, interestRate, fechaVencimiento);

        model.addAttribute("mensaje", "Préstamo registrado y bloque agregado con ID: " + nuevo.getId());
        return "nuevo_prestamo";
    }

    @GetMapping("/bloques")
public String listarBloques(Model model) {
    model.addAttribute("bloques", blockchainService.listarBloques());
    return "lista_bloques";
}

@GetMapping("/")
public String home() {
    return "home";
}

@GetMapping("/verificar-cadena")
public String verificarCadena(Model model) {
    boolean esValida = blockchainService.verificarCadena();
    model.addAttribute("esValida", esValida);
    return "verificar_cadena";
}

}

