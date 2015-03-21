package com.alcedomoreno.sirme.business.data;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.alcedomoreno.sirme.business.transform.QuestionTransform;
import com.alcedomoreno.sirme.business.transform.Transformator;

public class Question implements BusinessObject,Cloneable{
	
	public static final int QU_EXT_1_REF = 1;
	public static final int QU_EXT_2_PLACA = 2;
	public static final int QU_EXT_3_UBICACION = 3;
	public static final int QU_EXT_4_FABRICACION = 4;
	public static final int QU_EXT_5_PH = 5;
	public static final int QU_EXT_6_CADUCIDAD = 6;
	public static final int QU_EXT_7_TIPO_EFICACIA = 7;
	public static final int QU_EXT_8_PESO = 8;
	public static final int QU_EXT_9_ACCESO = 9;
	public static final int QU_EXT_10_SENALIZACION = 10;
	public static final int QU_EXT_11_ESTADO_EXTERIOR = 11;
	public static final int QU_EXT_12_NECESARIO_PH = 12;
	public static final int QU_EXT_13_NECESARIO_RECARGA = 13;
	public static final int QU_EXT_14_DESAPARECIDO = 14;
	public static final int QU_EXT_15_CADUCADO = 15;
	public static final int QU_EXT_16_FUERA_NORMA = 16;
	public static final int QU_EXT_17_OPERACION = 17;
	public static final int QU_EXT_18_RETIMBRADO = 18;




	private int idQuestion;
	private String question;
	private Integer order;

	@JsonIgnore
	private Boolean totalize;
	private ReplyType replyType;

	private Boolean search;

	public Question() {
		super();
	}


	@Override
	public Class<? extends Transformator> getTransformator() {
		return QuestionTransform.class;
	}


	public int getIdQuestion() {
		return idQuestion;
	}
	public void setIdQuestion(int idQuestion) {
		this.idQuestion = idQuestion;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public ReplyType getReplyType() {
		return replyType;
	}
	public void setReplyType(ReplyType replyType) {
		this.replyType = replyType;
	}
	public Boolean getTotalize() {
		return totalize;
	}
	public void setTotalize(Boolean totalize) {
		this.totalize = totalize;
	}
	public Boolean getSearch() {
		return search;
	}
	public void setSearch(Boolean search) {
		this.search = search;
	}


	@Override
	public String toString() {
		return "Question [idQuestion=" + idQuestion + ", question=" + question
				+ ", order=" + order + ", totalize=" + totalize
				+ ", replyType=" + replyType + "]";
	}
	
}