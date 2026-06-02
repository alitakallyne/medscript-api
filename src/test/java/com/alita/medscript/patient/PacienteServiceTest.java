package com.alita.medscript.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alita.medscript.patient.domain.Paciente;
import com.alita.medscript.patient.domain.Sexo;
import com.alita.medscript.patient.domain.enums.StatusPaciente;
import com.alita.medscript.patient.dto.ContatoDTO;
import com.alita.medscript.patient.dto.DadosPessoaisDTO;
import com.alita.medscript.patient.dto.DocumentoDTO;
import com.alita.medscript.patient.dto.EnderecoDTO;
import com.alita.medscript.patient.dto.PacienteRequestDTO;
import com.alita.medscript.patient.dto.PacienteResumoDTO;
import com.alita.medscript.patient.mapper.PacienteMapper;
import com.alita.medscript.patient.repository.PacienteRepository;
import com.alita.medscript.patient.service.PacienteService;
import com.alita.medscript.patient.service.validation.CpfValidator;
import com.alita.medscript.patient.service.validation.EmailValidator;
import com.alita.medscript.patient.service.validation.PacienteBusinessValidator;
import com.alita.medscript.patient.service.validation.PacienteDuplicidadeValidator;
import com.alita.medscript.shared.exception.BusinessException;
import com.alita.medscript.shared.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class PacienteServiceTest {

    @Mock
    private PacienteRepository repository;

    @Mock
    private CpfValidator cpfValidator;

    @Mock
    private EmailValidator emailValidator;

    @Mock
    private PacienteBusinessValidator businessValidator;

    @Mock
    private PacienteDuplicidadeValidator duplicidadeValidator;

    // Classe de teste para o serviço de paciente, utilizando Mockito para simular
    // as dependências e testar a lógica de criação de pacientes.
    @InjectMocks
    private PacienteService service;

    private PacienteRequestDTO request;
    private Paciente paciente;

    @BeforeEach
    void setUp() {
        request = new PacienteRequestDTO(
                "João Silva",
                "Joãozinho",
                LocalDate.of(1990, 5, 20),
                Sexo.MASCULINO,
                "123.456.789-00",
                "joao.silva@example.com",
                true,
                null, // LocalDate.now(),
                new ContatoDTO("086", "11987654321", "", ""),
                new EnderecoDTO("Rua A", "Bairro B", "Cidade C", "Estado D", "12345-678"),
                new DocumentoDTO("RG", "SSP", "SP", LocalDate.of(2010, 1, 15), "RG", "12.345.678-9", "123456789012345"),
                new DadosPessoaisDTO("Carlos Silva", "Maria Silva", "Ana Silva", 2, "Católica", "Casado",
                        "Ensino Médio Completo", "Branca", "Caucasiana", "Brasileira"));

        paciente = Paciente.builder()
                .id(1L)
                .nome("João Silva")
                .nomeSocial("Joãozinho")
                .dataNascimento(LocalDate.of(1990, 5, 20))
                .sexo(Sexo.MASCULINO)
                .cpf("12345678909")
                .email("joao@email.com")
                .status(StatusPaciente.ATIVO)
                .falecido(false)
                .build();
    }

    @Test
    @DisplayName("Deve criar um paciente com sucesso")
    void deveCriarPacienteComSucesso() {

        Paciente paciente = PacienteMapper.toEntity(request);
        when(repository.save(any(Paciente.class))).thenReturn(paciente);

        PacienteResumoDTO resultado = service.criar(request);

        assertNotNull(resultado);
        assertEquals(paciente.getNome(), resultado.nome());
        assertEquals(paciente.getCpf(), resultado.cpf());

        verify(cpfValidator)
                .validar(request.cpf());

        verify(emailValidator)
                .validar(request.email());

        verify(duplicidadeValidator)
                .validarCpfUnico(request.cpf());

        verify(businessValidator)
                .validar(any(Paciente.class));

        verify(repository).save(any(Paciente.class));

    }

    @Test
    @DisplayName("Deve lançar exceção ao criar paciente com CPF inválido")
    void deveLancarExcecaoCpfInvalido() {
        doThrow(new BusinessException("CPF inválido!"))
                .when(cpfValidator).validar(request.cpf());

        BusinessException exception =

                assertThrows(BusinessException.class,
                        () -> service.criar(request));
        assertEquals("CPF inválido!", exception.getMessage());

        verify(cpfValidator).validar(request.cpf());
        verify(repository, never())
                .save(any(Paciente.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar paciente com email inválido")
    void deveLancarExcecaoEmailInvalido() {
        doThrow(new BusinessException("Email inválido!"))
                .when(emailValidator).validar(request.email());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> service.criar(request));
        assertEquals("Email inválido!", exception.getMessage());

        verify(emailValidator).validar(request.email());
        verify(repository, never())
                .save(any(Paciente.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar paciente com data de nascimento futura")
    void deveLancarExcecaoDataNascimentoFutura() {
        request = new PacienteRequestDTO(
                "João Silva",
                "Joãozinho",
                LocalDate.now().plusDays(1),
                Sexo.MASCULINO,
                "123.456.789-00",
                "joao.silva@example.com", true,
                LocalDate.now(), null, null, null, null);
        doThrow(new BusinessException("Data de nascimento não pode ser futura!"))
                .when(businessValidator).validar(any(Paciente.class));

        BusinessException exception = assertThrows(BusinessException.class,
                () -> service.criar(request));
        assertEquals("Data de nascimento não pode ser futura!", exception.getMessage());

        verify(businessValidator).validar(any(Paciente.class));
        verify(repository, never())
                .save(any(Paciente.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar paciente falecido sem data de óbito")
    void deveLancarExcecaoPacienteFalecidoSemDataObito() {
        doThrow(new BusinessException("Data de óbito é obrigatória para pacientes falecidos!"))
                .when(businessValidator).validar(any(Paciente.class));

        BusinessException exception = assertThrows(BusinessException.class,
                () -> service.criar(request));
        assertEquals("Data de óbito é obrigatória para pacientes falecidos!", exception.getMessage());

        verify(businessValidator).validar(any(Paciente.class));
        verify(repository, never())
                .save(any(Paciente.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar paciente com CPF já cadastrado")
    void deveLancarExcecaoCpfDuplicado() {
        doThrow(new BusinessException("CPF já cadastrado"))
                .when(duplicidadeValidator).validarCpfUnico(request.cpf());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> service.criar(request));
        assertEquals("CPF já cadastrado", exception.getMessage());

        verify(duplicidadeValidator).validarCpfUnico(request.cpf());
        verify(repository, never())
                .save(any(Paciente.class));
    }

    @Test
    @DisplayName("Deve Buscar paciente por ID com sucesso")
    void deveBuscarPacientePorIdComSucesso() {
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(paciente));

        var resultado = service.buscarPacientePorId(1L);

        assertNotNull(resultado);
        // Verificar se os dados retornados correspondem ao paciente simulado
        assertEquals(paciente.getId(), resultado.id());
        assertEquals(paciente.getNome(), resultado.nome());
        assertEquals(paciente.getCpf(), resultado.cpf());

        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar paciente por ID inexistente")
    void deveLancarExcecaoQuandoPacienteNaoExiste() {
        when(repository.findById(1L)).thenReturn(java.util.Optional.empty());

        var exception = assertThrows(ResourceNotFoundException.class, () -> service.buscarPacientePorId(1L));
        assertEquals("Paciente não encontrado", exception.getMessage());

        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("Deve atualizar paciente com sucesso")
    void deveAtualizarPacienteComSucesso() {
        PacienteRequestDTO novoRequest = new PacienteRequestDTO(
                "João Silva Atualizado",
                "Joãozinho",
                LocalDate.of(1990, 5, 20),
                Sexo.MASCULINO,
                "123.456.789-00",
                "", true,
                null, null, null, null, null);

        when(repository.findById(1L)).thenReturn(java.util.Optional.of(paciente));
        when(repository.save(any(Paciente.class))).thenReturn(paciente);

        var resultado = service.atualizar(1L, novoRequest);

        assertNotNull(resultado);
        assertEquals(paciente.getId(), resultado.id());
        assertEquals(paciente.getNome(), resultado.nome());
        assertEquals(paciente.getCpf(), resultado.cpf());

        verify(repository).findById(1L);
        verify(repository).save(any(Paciente.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar paciente inexistente")
    void deveLancarExcecaoAoAtualizarPacienteInexistente() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.atualizar(1L, request));
    }

    @Test
    @DisplayName("Deve Inativar paciente com sucesso")
    void deveInativarPacienteComSucesso() {
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(paciente));
        when(repository.save(any(Paciente.class))).thenReturn(paciente);

        service.inativarPaciente(1L);
        assertEquals(StatusPaciente.INATIVO, paciente.getStatus());

        verify(repository).findById(1L);
        verify(repository).save(any(Paciente.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao inativar paciente já inativo")
    void deveLancarExcecaoAoInativarPacienteJaInativo() {
        paciente.setStatus(StatusPaciente.INATIVO);
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(paciente));

        var exception = assertThrows(BusinessException.class, () -> service.inativarPaciente(1L));
        assertEquals("Paciente já está inativo", exception.getMessage());

        verify(repository).findById(1L);
    }
}
