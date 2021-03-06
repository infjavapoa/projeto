package br.edu.infnet.projeto.ejb.avaliacao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import br.edu.infnet.projeto.ejb.core.BaseEntity;

@Entity
@Table
public class Alternativa extends BaseEntity<Long> {
	private static final long serialVersionUID = 3281109084862496739L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_alternativa")
	private Long id;
	private Integer ordem;
	private String texto;
	@OneToMany(mappedBy="alternativa", targetEntity=RespostaQuestaoObjetiva.class)
	private List<RespostaQuestaoObjetiva> respostaObjetivas = new ArrayList<RespostaQuestaoObjetiva>();
	
	public Alternativa() {
		super();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getOrdem() {
		return ordem;
	}
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public List<RespostaQuestaoObjetiva> getRespostaObjetivas() {
		return respostaObjetivas;
	}
	public void setRespostaObjetivas(List<RespostaQuestaoObjetiva> respostaObjetivas) {
		this.respostaObjetivas = respostaObjetivas;
	}
}