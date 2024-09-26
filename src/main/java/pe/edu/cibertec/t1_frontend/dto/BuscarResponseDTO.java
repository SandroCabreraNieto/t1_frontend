package pe.edu.cibertec.t1_frontend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BuscarResponseDTO(String codigo, String mensaje, String marca, String modelo, String nroAsientos, String precio, String color) {
}
