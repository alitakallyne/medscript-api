package com.alita.medscript.atendimento.domain;

@Entity
@Table(name = "fila_atendimento")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilaAtendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Prioridade prioridade;

    @Column(name = "nivel_prioridade", nullable = false)
    private Integer nivelPrioridade;

    @Column(nullable = false)
    private LocalDateTime chegada;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusFila status;

   
    public FilaAtendimento(Paciente paciente, Prioridade prioridade) {
        this.paciente = paciente;
        this.prioridade = prioridade;
        this.nivelPrioridade = prioridade.getNivel();
        this.chegada = LocalDateTime.now();
        this.status = StatusFila.AGUARDANDO;
    }

    public void chamar() {
        this.status = StatusFila.CHAMADO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilaAtendimento outro = (FilaAtendimento) o;
        return id != null && id.equals(outro.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
