package io.github.niulaniobm.clientes.resource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.niulaniobm.clientes.DTO.ServicoPrestadoDTO;
import io.github.niulaniobm.clientes.model.Cliente;
import io.github.niulaniobm.clientes.model.ServicoPrestado;
import io.github.niulaniobm.clientes.repository.ClienteRepository;
import io.github.niulaniobm.clientes.repository.ServicoPrestadoRepository;
import io.github.niulaniobm.clientes.util.BigDecimalConverter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/servicos-prestados")
@RequiredArgsConstructor
public class ServicoPrestadoResource {

	private final ClienteRepository clienteRepository;
	private final ServicoPrestadoRepository servicoPrestadoRepository;
	private final BigDecimalConverter bdc;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ServicoPrestado salvar(@RequestBody @Valid ServicoPrestadoDTO dto) {

		LocalDate data = LocalDate.parse(dto.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Integer idCliente = dto.getIdCliente();
		Cliente cliente = clienteRepository.findById(idCliente)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente inexistente"));

		ServicoPrestado sp = new ServicoPrestado();
		sp.setDescricao(dto.getDescricao());
		sp.setData(data);
		sp.setCliente(cliente);
		sp.setValor(bdc.conveter(dto.getPreco()));

		return servicoPrestadoRepository.save(sp);

	}

	@GetMapping
	public List<ServicoPrestado> pesquisar(@RequestParam(value = "nome", required = false, defaultValue = "") String nome,
			@RequestParam(value = "mes", required = false) Integer mes) {
		
		
		
		return servicoPrestadoRepository.findByNomeClienteAndMes("%"+nome+"%", mes);
	}

}
