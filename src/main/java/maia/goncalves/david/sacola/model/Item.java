package maia.goncalves.david.sacola.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@AllArgsConstructor //Coloca o Construtor "automaticamente"
@Builder // Design Patterns
@Data  // Getters e Setters são colocados no código "automaticamente"
@Entity //Diz que desejo que a classe seja convertida para uma tabela lá no BD
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) //Evita os erros do Json no Hibernate
@NoArgsConstructor //Construtor Vazio

public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Produto produto;
    private int quantidade;
    @ManyToOne
    @JsonIgnore
    private Sacola sacola;
}