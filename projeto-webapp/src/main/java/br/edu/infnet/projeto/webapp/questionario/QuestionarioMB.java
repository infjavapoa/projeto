package br.edu.infnet.projeto.webapp.questionario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.ReorderEvent;

import br.edu.infnet.projeto.ejb.core.InfnetException;
import br.edu.infnet.projeto.ejb.core.Repositorio;
import br.edu.infnet.projeto.ejb.questionario.Questao;
import br.edu.infnet.projeto.ejb.questionario.QuestaoObjetiva;
import br.edu.infnet.projeto.ejb.questionario.Questionario;
import br.edu.infnet.projeto.ejb.questionario.QuestionarioEJB;
import br.edu.infnet.projeto.ejb.questionario.QuestionarioTopico;
import br.edu.infnet.projeto.ejb.questionario.QuestionarioTopicoQuestao;
import br.edu.infnet.projeto.ejb.questionario.Topico;

@ManagedBean
@ViewScoped
public class QuestionarioMB {
	@EJB
    private Repositorio repositorio;
	@EJB
    private QuestionarioEJB questionarioEJB;	
    private Questionario questionario;
    private List<Questionario> listaQuestionarios;
    private Topico topico;
    private Questao questao;
    
    @PostConstruct
    public void init() {
    	atualizaView();
    }
    
    public Topico getTopico() {
		return topico;
	}

	public void setTopico(Topico topico) {
		this.topico = topico;
	}
	
    public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public Questionario getQuestionario() {
		return questionario;
	}
    
	public void setQuestionario(Questionario questionario) {
		this.questionario = questionario;
	}
	
	public void atualizaView(){
		topico = new Topico();
		questao = new QuestaoObjetiva();
		questionario = new Questionario();
		listaQuestionarios = repositorio.listar(Questionario.class);
	}
    
    public List<Questionario> getListaQuestionarios() {
		return listaQuestionarios;
	}

	public void salvar() {		
    	try {
    		questionarioEJB.salvarQuestionario(questionario);
    		atualizaView();
    	}
    	catch (InfnetException ex){
    		ex.printStackTrace();
        	FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", ex.getMessage()));
    	}
    }
	
	public void remover(Questionario q) {
    	try {
    		questionarioEJB.removerQuestionario(q);
    		atualizaView();
    	}
    	catch (InfnetException ex){
    		ex.printStackTrace();
        	FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", ex.getMessage()));
    	}
    }
	
	public List<Topico> listarTopicos(String texto){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("texto","%"+texto+"%");
		return repositorio.listarWithNamedQuery(Topico.class, "Topico.pesquisarPorTexto", parametros);
	}
	
	public List<Questao> listarQuestoes(String texto){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("texto","%"+texto+"%");
		return repositorio.listarWithNamedQuery(Questao.class, "Questao.pesquisarPorTexto", parametros);
	}
	
	public void adicionarQuestionarioTopico(){
		questionario.adicionaQuestionarioTopico(topico);
		topico = new Topico();
	}
	
	public void adicionarQuestionarioTopicoQuestao(QuestionarioTopico qt){
		qt.adicionaQuestionarioTopicoQuestao(questao);
		questao = new QuestaoObjetiva();
	}
	
	public void removerTopico(QuestionarioTopico qt) {
		questionario.removeQuestionarioTopico(qt);
		atualizaOrdem();
    }
	
	public void removerQuestao(QuestionarioTopico qt, QuestionarioTopicoQuestao qtq) {
		qt.removeQuestionarioTopicoQuestao(qtq);
		atualizaOrdem();
    }
	
    public void onTopicoRowReorder(ReorderEvent event) {
    	atualizaOrdem();
    }
    
    public void onQuestaoRowReorder(ReorderEvent event) {
    	atualizaOrdem();
    }
    
    private void atualizaOrdem(){
		//atualiza ordem dos tópicos e questões
		Integer i = 1;
		Integer j = 1;
		for (QuestionarioTopico qt : questionario.getQuestionarioTopicos()) {
			qt.setOrdem(i);
			for (QuestionarioTopicoQuestao qtq : qt.getQuestionarioTopicoQuestoes()) {
				qtq.setOrdem(j);
				j++;
			}
			i++;
			j=1;
		}
    }
}
