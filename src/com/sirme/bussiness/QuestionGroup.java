package com.sirme.bussiness;

import java.util.ArrayList;
import java.util.Collection;

import com.sirme.services.rest.dto.QuestionDTO;
import com.sirme.services.rest.dto.QuestionGroupDTO;
import com.sirme.transform.ITransformator;
import com.sirme.transform.QuestionGroupTransform;

public class QuestionGroup implements IBusinessObject,Cloneable{

	// Extintor
	public static final int QUG_1_1_EXTINTOR		= 1;

	// Elemento
	public static final int QUG_2_1_ELEMENTO		= 2;

	// Luz
	public static final int QUG_3_1_LUZ				= 3;

	// Elemnto
	public static final int QUG_4_1_ELEMENTO		= 4;
	
	// Características de la central, Sistema Distribución y Elementos
	public static final int QUG_5_1_CARACTERISTICAS = 5;
	public static final int QUG_5_2_SISTEMA 		= 6;
	public static final int QUG_5_3_ELEMENTOS		= 7;

	// Características de la central, Sistema Distribución y Elementos
	public static final int QUG_6_1_CARACTERISTICAS = 8;
	public static final int QUG_6_2_SISTEMA 		= 9;
	public static final int QUG_6_3_ELEMENTOS 		= 10;
	
	// Puestos de Control
	public static final int QUG_7_1_PUESTOS			= 11;
	
	// Hidrantes y Casetas
	public static final int QUG_8_1_HIDRANTES		= 12;
	public static final int QUG_8_2_CASETAS			= 13;

	// General y Elementos
	public static final int QUG_9_1_GENERAL			= 14;
	public static final int QUG_9_2_ELEMENTOS		= 15;

	// Carac, Element, Detectores, Válvulas y Cilindros
	public static final int QUG_10_1_CARACTERISTICAS= 16;
	public static final int QUG_10_2_ELEMENTOS		= 17;
	public static final int QUG_10_3_DETECTORES		= 18;
	public static final int QUG_10_4_VALVULAS		= 19;
	public static final int QUG_10_5_CILINDROS		= 20;
	
	// Campanas de cocina
	public static final int QUG_11_1_GENERAL		= 21;
	public static final int QUG_11_2_PREGUNTAS		= 22;
	
	// Grupo de Presión Sanitario
	public static final int QUG_12_1_GENERAL		= 23;

	private int idQuestionGroup;
	private String nameQuestionGroup;
	private Integer times;
	private Report report;
	private Collection<Question> questions;


	public QuestionGroup(QuestionGroupDTO questionGroup) {
		idQuestionGroup = questionGroup.getIdQuestionGroup();
		nameQuestionGroup = questionGroup.getNameQuestionGroup();
		times = questionGroup.getTimes();

		if ( questionGroup.getQuestions() != null ){
			questions = new ArrayList<Question>();
			for ( QuestionDTO q:questionGroup.getQuestions() )
				questions.add( new Question(q) );
		}
	}

	public QuestionGroup() {
		super();
	}

	@Override
	public Class<? extends ITransformator> getTransformator() {
		return QuestionGroupTransform.class;
	}
	
	public int getIdQuestionGroup() {
		return idQuestionGroup;
	}
	public void setIdQuestionGroup(int idQuestionGroup) {
		this.idQuestionGroup = idQuestionGroup;
	}
	public String getNameQuestionGroup() {
		return nameQuestionGroup;
	}
	public void setNameQuestionGroup(String nameQuestionGroup) {
		this.nameQuestionGroup = nameQuestionGroup;
	}
	public Report getReport() {
		return report;
	}
	public void setReport(Report report) {
		this.report = report;
	}
	public Collection<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(Collection<Question> questions) {
		this.questions = questions;
	}
	public Integer getTimes() {
		return times;
	}
	public void setTimes(Integer times) {
		this.times = times;
	}

}