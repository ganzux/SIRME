package com.alcedomoreno.sirme.core.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.alcedomoreno.sirme.core.AppTestConfig;
import com.alcedomoreno.sirme.core.data.AddressData;
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

	@Autowired
	CustomersDao customerDao;

	 
	@Test
	@Transactional
	public void updateTest() {

		try {
			WorkDaoImpl impl = new WorkDaoImpl();
			assertNotNull(impl);

			List<Integer> badYears = new ArrayList<Integer>();
			Collection<WorkData> works = workDao.getAll(badYears);
			assertEquals(works.size(), 2);
			
			badYears.add(2014);
			works = workDao.getAll(badYears);
			assertEquals(works.size(), 0);

			works = workDao.getAll();
			assertEquals(works.size(), 2);
			
			WorkData work = workDao.get(1);
			work = generateMock(work);
			workDao.update(work);
			
			WorkData newWork = workDao.get(1);
			
			assertEquals(newWork.getAddress().getPostalCode(), new Integer(28850));
			assertEquals(newWork.getAddress().getPobl(), "Torrejon de Ardoz");
			assertEquals(newWork.getReplyGroups().size(), 2);
			
			for (ReplyGroupData rgd : newWork.getReplyGroups()) {
				assertEquals(rgd.getReplies().size(), 10);
			}
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	@Test
	@Transactional
	public void updateStatusTest() {

		try {
			workDao.updateStatus(1, 25);
			
			WorkData work = workDao.get(1);

			assertEquals(work.getAddress().getPostalCode(), new Integer(28850));
			assertEquals(work.getAddress().getPobl(), "Torrejon de Ardoz");
			assertEquals(work.getReplyGroups().size(), 0);
			assertEquals(work.getStatus(), 25);
			
			for (ReplyGroupData rgd : work.getReplyGroups()) {
				assertEquals(rgd.getReplies().size(), 10);
			}
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	@Test
	@Transactional
	public void updateSignTest() {

		try {
			WorkData oldWork = workDao.get(1);

			workDao.updateSign(1, "newPath", "newSigner");

			WorkData work = workDao.get(1);

			assertEquals(work.getSignName(), "newSigner");
			assertEquals(work.getSignpath(), "newPath");
			assertEquals(work.getIdWork(), oldWork.getIdWork());
			assertEquals(work.getAlbaran(), oldWork.getAlbaran());
			assertEquals(work.getTypeWork(), oldWork.getTypeWork());
			assertEquals(work.getMemo(), oldWork.getMemo());
			
			for (ReplyGroupData rgd : work.getReplyGroups()) {
				assertEquals(rgd.getReplies().size(), 10);
			}
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	@Test
	@Transactional
	public void saveTest() {

		try {

			WorkData work = new WorkData(0);
			work = generateMock(work);
			
			work.setDate(new Date());
			work.setDateCreated(new Date());
			work.setSignName("iTo");
			work.setSignpath("/photos/signs/");
			work.setStatus(2);
			work.setYear(2015);
			work.setTypeWork(1);
			work.setMemo("memo");

			AddressData address = customerDao.getAddressById(1);
			work.setAddress(address);
			
			int id = workDao.save(work);
			
			work = workDao.get(id);
			
			assertEquals(work.getAddress().getPostalCode(), new Integer(28850));
			assertEquals(work.getAddress().getPobl(), "Torrejon de Ardoz");
			assertEquals(work.getReplyGroups().size(), 2);
			assertEquals(work.getIdWork(), 4);
			
			for (ReplyGroupData rgd : work.getReplyGroups()) {
				assertEquals(rgd.getReplies().size(), 10);
			}
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	@Test
	@Transactional
	public void albaranNumberTest() {

		try {
			int next14 = workDao.getMaxAlbaranByYear(2014);
			int next15 = workDao.getMaxAlbaranByYear(2015);

			assertEquals(next14, 0);
			assertEquals(next15, 3);

			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	@Test
	@Transactional
	public void reportsTypeTest() {

		try {
			Collection<ReportData> reports = workDao.getAllReportsType();

			assertEquals(reports.size(), 12);

			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	@Test
	@Transactional
	public void deleteTest() {

		try {

			WorkData work = new WorkData(1);
			workDao.delete(work);
			
			Collection<WorkData> works = workDao.getAll();
			
			assertEquals(works.size(), 1);
			

		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	@Test
	@Transactional
	public void getWorksFromAddressTest() {

		try {

			Collection<WorkData> works = workDao.getFromAddress(1);
			assertEquals(works.size(), 2);
			
			works = workDao.getFromAddress(0);
			assertEquals(works.size(), 0);

		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}

	@Test
	@Transactional
	public void getWorksFromTeamTest() {

		try {

			Collection<WorkData> works = workDao.getOpenAdvicesOrWorksFromTeam(1, new Date(), false);
			Collection<WorkData> works2 = workDao.getOpenAdvicesOrWorksFromTeam(1, new Date(), true);
			
			Collection<WorkData> works3 = workDao.getOpenAdvicesOrWorksFromTeam(1, null, false);
			//assertEquals(works.size(), 1);
			
			//works = workDao.getFromAddress(0);
			//assertEquals(works.size(), 0);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	

	private WorkData generateMock(WorkData work){
		
		Set<ReplyGroupData> replyGroups = new HashSet<ReplyGroupData>();

			ReplyGroupData replyGroup1 = new ReplyGroupData();
			replyGroup1.setIdReplyGroup(0);
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
			replyGroup2.setIdReplyGroup(0);
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
			
			reply.setIdReply(0);
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
