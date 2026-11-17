 package com.alita.medscript.patient.mapper;

 import com.alita.medscript.patient.domain.Paciente;
 import com.alita.medscript.patient.domain.enums.StatusPaciente;
 import com.alita.medscript.patient.dto.PacienteRequestDTO;
 import com.alita.medscript.patient.dto.PacienteResumoDTO;

 public final class PacienteMapper {

    private PacienteMapper() {
        // classe utilitária: só métodos estáticos, ninguém instancia
    }

    public static Paciente toEntity(PacienteRequestDTO dto) {
        Paciente paciente = new Paciente();
        paciente.setNomeCompleto(normalizarNome(dto.nomeCompleto()));
        paciente.setCpf(normalizarCpf(dto.cpf()));
        paciente.setDataNascimento(dto.dataNascimento());
        paciente.setNomeMae(normalizarNome(dto.nomeMae()));
        paciente.setSexo(dto.sexo());
        paciente.setTelefoneContato(dto.telefoneContato());
        paciente.setContatoEmergenciaNome(dto.contatoEmergenciaNome());
        paciente.setContatoEmergenciaTelefone(dto.contatoEmergenciaTelefone());
        paciente.setAlergias(dto.alergias());
        paciente.setStatus(StatusPaciente.ATIVO); // coluna NOT NULL: todo paciente novo nasce ativo
        return paciente;
    }

    public static PacienteResumoDTO toResumoDTO(Paciente paciente) {
        return new PacienteResumoDTO(
                paciente.getId(),
                paciente.getNomeCompleto(),
                paciente.getCpf(),
                paciente.getDataNascimento(),
                paciente.getStatus());
    }

    private static String normalizarCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            return null; // "" viraria valor no UNIQUE; só null pode se repetir
        }
        return cpf.replaceAll("\\D", "");
    }

    private static String normalizarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return null;
        }
        return nome.trim().replaceAll("\\s+", " ").toUpperCase();
    }
}
