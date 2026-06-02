package com.alita.medscript.patient.mapper;

import com.alita.medscript.patient.domain.Contato;
import com.alita.medscript.patient.domain.DadosPessoais;
import com.alita.medscript.patient.domain.Documento;
import com.alita.medscript.patient.domain.Endereco;
import com.alita.medscript.patient.domain.Paciente;
import com.alita.medscript.patient.dto.ContatoDTO;
import com.alita.medscript.patient.dto.DadosClinicosDTO;
import com.alita.medscript.patient.dto.DadosPessoaisDTO;
import com.alita.medscript.patient.dto.DocumentoDTO;
import com.alita.medscript.patient.dto.EnderecoDTO;
import com.alita.medscript.patient.dto.PacienteRequestDTO;
import com.alita.medscript.patient.dto.PacienteResponseDTO;
import com.alita.medscript.patient.dto.PacienteResumoDTO;

public class PacienteMapper {

public static Paciente toEntity(PacienteRequestDTO dto) {
    if (dto == null) return null;

    return Paciente.builder()
            .nome(dto.nome())
            .nomeSocial(dto.nomeSocial())
            .dataNascimento(dto.dataNascimento())
            .sexo(dto.sexo())
            .cpf(dto.cpf())
            .email(dto.email())
            .contato(mapContato(dto.contato()))
            .endereco(mapEndereco(dto.endereco()))
            .documento(mapDocumento(dto.documento()))
            .dadosPessoais(mapDadosPessoais(dto.dadosPessoais()))
            .build();
}

private static Contato mapContato(ContatoDTO dto) {
    if (dto == null) return null;

    Contato c = new Contato();
    c.setDdd(dto.ddd());
    c.setTelefone(dto.telefone());
    c.setTelefone2(dto.telefone2());
    c.setTelefone3(dto.telefone3());
    return c;
}

private static Endereco mapEndereco(EnderecoDTO dto) {
    if (dto == null) return null;

    Endereco e = new Endereco();
    e.setLogradouro(dto.logradouro());
    e.setBairro(dto.bairro());
    e.setCidade(dto.cidade());
    e.setEstado(dto.estado());
    e.setCep(dto.cep());
    return e;
}

private static Documento mapDocumento(DocumentoDTO dto) {
    if (dto == null) return null;

    Documento d = new Documento();
    d.setRg(dto.rg());
    d.setOrgaoEmissor(dto.orgaoEmissor());
    d.setUfEmissor(dto.ufEmissor());
    d.setDataExpedicao(dto.dataExpedicao());
    d.setTipoDocumento(dto.tipoDocumento());
    d.setNumeroDocumento(dto.numeroDocumento());
    d.setCartaoSus(dto.cartaoSus());
    return d;
}
private static DadosPessoais mapDadosPessoais(DadosPessoaisDTO dto) {
    if (dto == null) return null;

    DadosPessoais d = new DadosPessoais();
    d.setNomePai(dto.nomePai());
    d.setNomeMae(dto.nomeMae());
    d.setNomeConjuge(dto.nomeConjuge());
    d.setNumeroFilhos(dto.numeroFilhos());
    d.setReligiao(dto.religiao());
    d.setEstadoCivil(dto.estadoCivil());
    d.setEscolaridade(dto.escolaridade());
    d.setCor(dto.cor());
    d.setEtnia(dto.etnia());
    d.setNacionalidade(dto.nacionalidade());
    return d;
}


    public static PacienteResponseDTO toDTO(Paciente entity) {
        return PacienteResponseDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .nomeSocial(entity.getNomeSocial())
                .dataNascimento(entity.getDataNascimento())
                .sexo(entity.getSexo())
                .cpf(entity.getCpf())
                .email(entity.getEmail())
                .contato(mapContatoDTO(entity.getContato()))
                .endereco(mapEnderecoDTO(entity.getEndereco()))
                .documento(mapDocumentoDTO(entity.getDocumento()))
                .dadosPessoais(mapDadosPessoaisDTO(entity.getDadosPessoais()))
                .build();
    }

    private static DadosPessoaisDTO mapDadosPessoaisDTO(DadosPessoais entity) {
        if (entity == null)
            return null;
        return new DadosPessoaisDTO(
                entity.getNomePai(),
                entity.getNomeMae(),
                entity.getNomeConjuge(),
                entity.getNumeroFilhos(),
                entity.getReligiao(),
                entity.getEstadoCivil(),
                entity.getEscolaridade(),
                entity.getCor(),
                entity.getEtnia(),
                entity.getNacionalidade());
    }

    private static DocumentoDTO mapDocumentoDTO(Documento entity) {
        if (entity == null)
            return null;
        return new DocumentoDTO(
                entity.getRg(),
                entity.getOrgaoEmissor(),
                entity.getUfEmissor(),
                entity.getDataExpedicao(),
                entity.getTipoDocumento(),
                entity.getNumeroDocumento(),
                entity.getCartaoSus());
    }

    private static ContatoDTO mapContatoDTO(Contato entity) {
        if (entity == null)
            return null;
        return new ContatoDTO(
                entity.getDdd(),
                entity.getTelefone(),
                entity.getTelefone2(),
                entity.getTelefone3());
    }

    private static EnderecoDTO mapEnderecoDTO(Endereco entity) {
        if (entity == null)
            return null;
        return new EnderecoDTO(
                entity.getLogradouro(),
                entity.getBairro(),
                entity.getCidade(),
                entity.getEstado(),
                entity.getCep());
    }

    
    public static PacienteResumoDTO toResumoDTO(Paciente entity) {
        return new PacienteResumoDTO(
                entity.getId(),
                entity.getNome(),
                entity.getCpf());
    }

    public static PacienteResponseDTO toDetalheDTO(Paciente entity) {
        return new PacienteResponseDTO(
                entity.getId(),
                entity.getNome(),
                entity.getNomeSocial(),
                entity.getDataNascimento(),
                entity.getSexo(),
                entity.getCpf(),
                entity.getEmail(),
                mapContatoDTO(entity.getContato()),
                mapEnderecoDTO(entity.getEndereco()),
                mapDocumentoDTO(entity.getDocumento()),
                mapDadosPessoaisDTO(entity.getDadosPessoais()));
    }


    public static void atualizarEntity(
            Paciente paciente,
            PacienteRequestDTO dto
    ) {

        paciente.setNome(dto.nome());
        paciente.setNomeSocial(dto.nomeSocial());
        paciente.setDataNascimento(dto.dataNascimento());
        paciente.setSexo(dto.sexo());
        paciente.setCpf(dto.cpf());
        paciente.setEmail(dto.email());

        paciente.setContato(
                mapContato(dto.contato())
        );

        paciente.setEndereco(
                mapEndereco(dto.endereco())
        );

        paciente.setDocumento(
                mapDocumento(dto.documento())
        );

        paciente.setDadosPessoais(
                mapDadosPessoais(dto.dadosPessoais())
        );

    }
}
