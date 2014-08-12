SELECT report.nameReport, 
		 questiongroup.nameQuestionGroup,
		 question.question,
		 replytype.nameReplyType
FROM report
LEFT OUTER JOIN questiongroup ON report.idReport = questiongroup.idReport
LEFT OUTER JOIN question ON question.idQuestionGroup = questiongroup.idQuestionGroup
LEFT OUTER JOIN replytype ON replytype.idReplyType = question.idReplyType