package com.alcedomoreno.sirme.core.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.alcedomoreno.sirme.core.AppTestConfig;
import com.alcedomoreno.sirme.core.data.QuestionData;
import com.alcedomoreno.sirme.core.data.QuestionGroupData;
import com.alcedomoreno.sirme.core.data.ReplyData;
import com.alcedomoreno.sirme.core.data.ReplyGroupData;
import com.alcedomoreno.sirme.core.data.ReportData;
import com.alcedomoreno.sirme.core.data.WorkData;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes=AppTestConfig.class)
public class WorkDaoTest {

	@Autowired
	WorkDao workDao;

	 
	@Test
	@Transactional
	public void saveTest() {

		try {
			Collection<WorkData> works = workDao.getAll();
			
			WorkData work = workDao.get(1);
			work = generateMock(work);
			workDao.update(work);
			
			work = workDao.get(1);
			
			assertEquals(work.getAddress().getPostalCode(), new Integer(28850));
			assertEquals(work.getAddress().getPobl(), "Torrejon de Ardoz");
			assertEquals(work.getReplyGroups().size(), 2);
			
			for (ReplyGroupData rgd : work.getReplyGroups()) {
				assertEquals(rgd.getReplies().size(), 10);
			}
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	private WorkData generateMock(WorkData work){
		
		Set<ReplyGroupData> replyGroups = new HashSet<ReplyGroupData>();

			ReplyGroupData replyGroup1 = new ReplyGroupData();
			replyGroup1.setIdReplyGroup(1);
			replyGroup1.setNameReplyGroup("Extintor 1");
			replyGroup1.setReplies(generateRepliesMock(1, 10, "Extintores 1 - ", replyGroup1));
			
			QuestionGroupData questionGroup = new QuestionGroupData();
			questionGroup.setIdQuestionGroup(1);
			replyGroup1.setQuestionGroup(questionGroup);
			
			ReportData report = new ReportData();
			report.setIdReport(1);
			replyGroup1.setReport(report);
			
			replyGroup1.setWork(work);
			
			replyGroups.add(replyGroup1);
			
			
			ReplyGroupData replyGroup2 = new ReplyGroupData();
			replyGroup2.setIdReplyGroup(2);
			replyGroup2.setNameReplyGroup("Extintor 2");
			replyGroup2.setReplies(generateRepliesMock(11, 20, "Extintores 1 - ", replyGroup2));
			
			QuestionGroupData questionGroup2 = new QuestionGroupData();
			questionGroup2.setIdQuestionGroup(1);
			replyGroup2.setQuestionGroup(questionGroup2);
			
			replyGroup2.setReport(report);
			
			replyGroup2.setWork(work);
			
			replyGroups.add(replyGroup2);


			work.setReplyGroups(replyGroups);

		return work;
	}
	
	private Set<ReplyData> generateRepliesMock(int init, int times, String r, ReplyGroupData rgd){
		Set<ReplyData> replies = new HashSet<ReplyData>();
		
		for (int i = init ; i <= times ; i++){
			ReplyData reply = new ReplyData();
			
			reply.setIdReply(i);
			reply.setReply(r + i);
			reply.setReplyGroup(rgd);
			
			QuestionData question = new QuestionData();
			question.setIdQuestion(i);
			reply.setQuestion(question);
			
			replies.add(reply);
		}
		
		return replies;
	}

}
