package pe.edu.cibertec.t1_frontend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import pe.edu.cibertec.t1_frontend.dto.BuscarRequestDTO;
import pe.edu.cibertec.t1_frontend.dto.BuscarResponseDTO;
import pe.edu.cibertec.t1_frontend.viewmodel.BusquedaModel;

@Controller
@RequestMapping("/validacion")
public class BuscarController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/inicio")
    public String inicio(Model model) {
        model.addAttribute("busquedaModel", new BusquedaModel("00", "", ""));
        return "inicio";
    }

    @PostMapping("/busqueda")
    public String busqueda(@RequestParam("placa") String placa, Model model) {
        // Validar campos de entrada

        if (placa == null || placa.trim().isEmpty()) {
            model.addAttribute("busquedaModel", new BusquedaModel("01", "Debe ingresar una placa correcta", ""));
            return "inicio";
        }

        try {
            // Invocar servicio de autenticación
            String endpoint = "http://localhost:8082/validacion/busqueda";
            BuscarRequestDTO buscarRequestDTO = new BuscarRequestDTO(placa);
            BuscarResponseDTO busquedaResponseDto = restTemplate.postForObject(endpoint, buscarRequestDTO, BuscarResponseDTO.class);

            if (busquedaResponseDto != null && "00".equals(busquedaResponseDto.codigo())) {
                model.addAttribute("busquedaDTO", busquedaResponseDto);
                return "principal";
            } else {
                model.addAttribute("busquedaModel", new BusquedaModel("02", "No se encontró un vehículo para la placa ingresada", ""));
                return "inicio";
            }

        } catch (Exception e) {
            model.addAttribute("busquedaModel", new BusquedaModel("99", "Error: Ocurrió un problema en la busqueda", ""));
            System.out.println(e.getMessage());
            return "inicio";
        }
    }
}
