package com.alcedomoreno.sirme.core.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

public class ConfigDataTest {

	@Test
	public void testConstructor(){
		try {
			ReportData data0 = new ReportData();
			assertNotNull(data0);
			data0 = new ReportData(288);
			assertNotNull(data0);
			assertEquals(data0.getIdReport(), 288);
			
			QuestionData data1 = new QuestionData();
			assertNotNull(data1);
			data1 = new QuestionData(288);
			assertNotNull(data1);
			assertEquals(data1.getIdQuestion(), 288);
			
			QuestionGroupData data2 = new QuestionGroupData();
			assertNotNull(data2);
			data2 = new QuestionGroupData(288);
			assertNotNull(data2);
			assertEquals(data2.getIdQuestionGroup(), 288);
			
			ReplyData data3 = new ReplyData();
			assertNotNull(data3);
			data3 = new ReplyData(288);
			assertNotNull(data3);
			assertEquals(data3.getIdReply(), 288);
			
			ReplyGroupData data4 = new ReplyGroupData();
			assertNotNull(data4);
			data4 = new ReplyGroupData(288);
			assertNotNull(data4);
			assertEquals(data4.getIdReplyGroup(), 288);
			
			ReplyTypeData data5 = new ReplyTypeData();
			assertNotNull(data5);
			data5 = new ReplyTypeData(288);
			assertNotNull(data5);
			assertEquals(data5.getIdReplyType(), 288);

		} catch (Exception e){
			fail(e.getMessage());
		}
    }
	
	@Test
	public void testGetSetReportData(){
		try {
			ReportData data = new ReportData();

			data.setFileReport("fileReport");
			assertEquals(data.getFileReport(), "fileReport");
			
			data.setIdReport(288);
			assertEquals(data.getIdReport(), 288);
			
			data.setNameReport("nameReport");
			assertEquals(data.getNameReport(), "nameReport");
			
			data.setTitleReport("titleReport");
			assertEquals(data.getTitleReport(), "titleReport");

			assertNotNull(data.getQuestionGroups());
			assertTrue(data.getQuestionGroups().isEmpty());
			Set<QuestionGroupData> qgd = new LinkedHashSet<QuestionGroupData>();
			data.setQuestionGroups(qgd);
			assertNotNull(data.getQuestionGroups());
			assertEquals(data.getQuestionGroups(), qgd);
		} catch (Exception e){
			fail(e.getMessage());
		}
    }

	
	@Test
	public void testGetSetReplyTypeData(){
		try {
			ReplyTypeData data = new ReplyTypeData();

			data.setIdReplyType(2885);
			assertEquals(data.getIdReplyType(), 2885);

			data.setNameReplyType("nameReplyType");
			assertEquals(data.getNameReplyType(), "nameReplyType");

		} catch (Exception e){
			fail(e.getMessage());
		}
    }
	

	@Test
	public void testGetSetQuestionGruopData(){
		try {
			QuestionGroupData data = new QuestionGroupData();

			data.setIdQuestionGroup(2);
			assertEquals(data.getIdQuestionGroup(), 2);
			
			data.setNameQuestionGroup("nameQuestionGroup");
			assertEquals(data.getNameQuestionGroup(), "nameQuestionGroup");
			
			data.setTimes(9);
			assertEquals(data.getTimes(), new Integer(9));

			assertNotNull(data.getQuestions());
			Set<QuestionData> questions = new LinkedHashSet<QuestionData>();
			data.setQuestions(questions);
			assertEquals(data.getQuestions(), questions);
			
			assertNull(data.getReport());
			ReportData report = new ReportData();
			data.setReport(report);
			assertEquals(data.getReport(), report);

		} catch (Exception e){
			fail(e.getMessage());
		}
    }
	
	@Test
	public void testGetSetQuestionData(){
		try {
			QuestionData data = new QuestionData();

			data.setIdQuestion(288);
			assertEquals(data.getIdQuestion(), 288);

			data.setOrder(89);
			assertEquals(data.getOrder(), new Integer(89));

			data.setQuestion("question");
			assertEquals(data.getQuestion(), "question");

			assertEquals(data.getSearch(), null);
			data.setSearch(true);
			assertEquals(data.getSearch(), true);

			assertEquals(data.getTotalize(), null);
			data.setTotalize(false);
			assertEquals(data.getTotalize(), false);

			assertNull(data.getQuestionGroup());
			QuestionGroupData questionGroup = new QuestionGroupData();
			data.setQuestionGroup(questionGroup);
			assertEquals(data.getQuestionGroup(), questionGroup);
			
			assertNull(data.getReplyType());
			ReplyTypeData replyType = new ReplyTypeData();
			data.setReplyType(replyType);
			assertEquals(data.getReplyType(), replyType);

			assertNotNull(data.getReplies());
			Set<ReplyData> replies = new LinkedHashSet<ReplyData>();
			data.setReplies(replies);
			assertEquals(data.getReplies(), replies);

		} catch (Exception e){
			fail(e.getMessage());
		}
    }
	
	
	@Test
	public void testGetSetReplyData(){
		try {
			ReplyData data = new ReplyData();

			data.setIdReply(8);
			assertEquals(data.getIdReply(), 8);

			data.setReply("replyyyy");
			assertEquals(data.getReply(), "replyyyy");
			
			assertNull(data.getReplyGroup());
			ReplyGroupData replyGroup = new ReplyGroupData();
			data.setReplyGroup(replyGroup);
			assertEquals(data.getReplyGroup(), replyGroup);

			assertNull(data.getQuestion());
			QuestionData question = new QuestionData();
			data.setQuestion(question);
			assertEquals(data.getQuestion(), question);
		} catch (Exception e){
			fail(e.getMessage());
		}
    }
	

	@Test
	public void testGetSetReplyGroupData(){
		try {
			ReplyGroupData data = new ReplyGroupData();

			data.setIdReplyGroup(3);
			assertEquals(data.getIdReplyGroup(), 3);
			
			data.setNameReplyGroup("nameReplyGroup");
			assertEquals(data.getNameReplyGroup(), "nameReplyGroup");
			
			assertNull(data.getQuestionGroup());
			QuestionGroupData questionGroup = new QuestionGroupData();
			data.setQuestionGroup(questionGroup);
			assertEquals(data.getQuestionGroup(), questionGroup);
			
			assertNull(data.getReport());
			ReportData report = new ReportData();
			data.setReport(report);
			assertEquals(data.getReport(), report);
			
			assertNull(data.getWork());
			WorkData work = new WorkData();
			data.setWork(work);
			assertEquals(data.getWork(), work);
			
			assertNotNull(data.getReplies());
			Set<ReplyData> replies = new LinkedHashSet<ReplyData>();
			data.setReplies(replies);
			assertEquals(data.getReplies(), replies);
			
		} catch (Exception e){
			fail(e.getMessage());
		}
    }
}
