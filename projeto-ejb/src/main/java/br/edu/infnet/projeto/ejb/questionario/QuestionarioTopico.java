package br.edu.infnet.projeto.ejb.questionario;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import br.edu.infnet.projeto.ejb.core.BaseEntity;

@Entity
@Table(name="questionario_topico")
public class QuestionarioTopico extends BaseEntity<Long> implements Comparable<QuestionarioTopico> {
	private static final long serialVersionUID = 6365086666794727846L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_questionario_topico")
	private Long id;
    @ManyToOne
    @JoinColumn(name = "id_topico", referencedColumnName = "id_topico")
	private Topico topico;
    @ManyToOne
    @JoinColumn(name = "id_questionario", referencedColumnName = "id_questionario")    
	private Questionario questionario;
    private Integer ordem;
	@OneToMany(mappedBy="questionarioTopico", orphanRemoval=true, cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@OrderBy("ordem") 
	private List<QuestionarioTopicoQuestao> questionarioTopicoQuestoes = new ArrayList<QuestionarioTopicoQuestao>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Topico getTopico() {
		return topico;
	}
	public void setTopico(Topico topico) {
		this.topico = topico;
	}
	public Questionario getQuestionario() {
		return questionario;
	}
	public void setQuestionario(Questionario questionario) {
		this.questionario = questionario;
	}
	public Integer getOrdem() {
		return ordem;
	}
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
	public List<QuestionarioTopicoQuestao> getQuestionarioTopicoQuestoes() {
		return questionarioTopicoQuestoes;
	}
	public void setgetQuestionarioTopicoQuestoes(List<QuestionarioTopicoQuestao> questionarioTopicoQuestoes) {
		this.questionarioTopicoQuestoes = questionarioTopicoQuestoes;
	}
	public void adicionaQuestionarioTopicoQuestao(Questao questao){
		QuestionarioTopicoQuestao qtq = new QuestionarioTopicoQuestao();
		qtq.setQuestao(questao);
		qtq.setQuestionarioTopico(this);
		qtq.setOrdem(questionarioTopicoQuestoes.size()+1);
		this.questionarioTopicoQuestoes.add(qtq);
	}
	public void removeQuestionarioTopicoQuestao(QuestionarioTopicoQuestao qtq){
		this.questionarioTopicoQuestoes.remove(qtq);
	}
	
	@Override
	public int compareTo(QuestionarioTopico that) {
		Integer o1 = this.getOrdem();
		Integer o2 = that.getOrdem();
		
		if (o1 > o2 ){
		   return 1;
		}
		else if (o1 < o2){
		   return -1;
		}
		else
		   return 0;
	}
}