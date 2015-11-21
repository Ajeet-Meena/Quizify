-- Controller queries

-- ArrayList<User> getAllUser()
	SELECT * FROM mydb.users;

-- int insertNewUser(User user)
	INSERT INTO `mydb`.`users`
	(`facebook_id`,`user_name`,
	`gcm_id`,`email`)
	VALUES
	(1,2,4,5);
    
-- getFacebookIdByUserId(String facebokId)
	SELECT `user_id` FROM `mydb`.`users` where facebook_id = '1';

-- User getUserById(int userId)
    
    
-- isFacebookIdExist()
	SELECT count(*) from mydb.users where facebook_id = ?;

-- User getUserById(int userId)
	SELECT * FROM mydb.users where user_id = 8;

-- ArrayList<User> getUserByIds(int [id]);
	SELECT * FROM mydb.users where user_id IN (1,2);

-- boolean updateUser(User user)
	UPDATE `mydb`.`users`
SET
`correct_attempt` = correct_attempt + 1,
`score` = scorparent_topice + 1,
`wrong_attempt` = wrong_attempt + 1,
`un_attempt` = un_attempt +1 ,
`event_played` = event_played + 1
WHERE user_id = 8;

-- boolean updateUserGcmId(int userId, String gcmId)
	UPDATE `mydb`.`users`
SET
`gcm_id` = 'gcm_id'
WHERE user_id = 2;

-- Arraylist<Topic> selectAllTopic()
SELECT `parent_topic`.`parent_topic_id`,`topic`.`topic_id`,
    `parent_topic`.`parent_topic_name`, `topic`.`topic_name`
FROM `mydb`.`parent_topic` left join mydb.topic on mydb.topic.parent_topic_id = mydb.parent_topic.parent_topic_id ;

-- boolean insertTopic(String topicName, int parentTopicId)
INSERT INTO `mydb`.`topic`
(`topic_name`,
`parent_topic_id`)
VALUES
('Speed and Time',
1);

-- boolean insertParentTopic(String topicName)
INSERT INTO `mydb`.`parent_topic`
(`parent_topic_name`)
VALUES
('Aptitude');

-- boolean deleteAllParentTopic()
set FOREIGN_KEY_CHECKS = 0;
truncate table mydb.parent_topic;
set FOREIGN_KEY_CHECKS = 0;

-- insertQuestion(Question question)
INSERT INTO `mydb`.`questions`
(`question_text`,`explanation_text`,`topic_id`)
VALUES
('check',NULL,2);

-- boolean insertOptionWithQuestionId(int questionId)
INSERT INTO `mydb`.`options`
(`question_id`,
`option_text`,is_aswer)
VALUES
(2,
'option_text',1);

-- ArrayList<Question> selectAllQuestion()
	SELECT mydb.questions.*, mydb.topic.topic_id, mydb.topic.parent_topic_id , mydb.topic.topic_name, mydb.parent_topic.parent_topic_name
FROM `mydb`.`questions` inner join mydb.topic on topic.topic_id = questions.topic_id inner join mydb.parent_topic on topic.parent_topic_id = parent_topic.parent_topic_id;

-- ArrayList<Question> getQuestionByIds(int[] id)
	SELECT * FROM mydb.questions where question_id in (1,2);
	SELECT mydb.questions.question_id, mydb.options.option_id, mydb.options.option_text
FROM mydb.options left join mydb.questions on `mydb`.`options`.question_id = `mydb`.`questions`.question_id where mydb.questions.question_id in (1);

-- ArrayList<Option> selectAllOption()
   SELECT * FROM mydb.questions;
   SELECT mydb.questions.question_id, mydb.options.option_id, mydb.options.option_text
FROM mydb.options left join mydb.questions on `mydb`.`options`.question_id = `mydb`.`questions`.question_id;

-- ArrayList<Option> selectOptionByQuestionId(int questionId)
 SELECT * FROM mydb.questions;
   SELECT mydb.questions.question_id, mydb.options.option_id, mydb.options.option_text
FROM mydb.options left join mydb.questions on `mydb`.`options`.question_id = `mydb`.`questions`.question_id where options.question_id in (2);

-- boolean reportQuestion(int question_id)
UPDATE `mydb`.`questions`
SET
`no_of_times_reported` = questions.no_of_times_reported + 1
WHERE question_id = 1;

-- int[] getAllReportedQuestionIds()
SELECT `questions`.`question_id`, questions.no_of_times_reported
FROM `mydb`.`questions` where mydb.questions.no_of_times_reported>0;

-- insertQuiz(Quiz quiz)
INSERT INTO `mydb`.`quiz`
(`quiz_name`,
`topic_id`,
`is_active`)
VALUES
('quiz',
1,
1);

-- insertQuizQuestion(int quizId, int[] questionId)
INSERT INTO `mydb`.`quiz_questions`
(`quiz_id`,
`questions__id`)
VALUES
(1,8),(1,8);

-- updateQuizStatus(int quizId, int status)
UPDATE `mydb`.`quiz`
SET
`is_active` = 0
WHERE mydb.quiz.quiz_id = 1;

-- int insertUserInParticipateQuiz(int quizId, int[] userId)
INSERT INTO `mydb`.`user_participated_quiz`
(`user_id`,
`quiz_id`)
VALUES
(0,1);

-- updateQuizResultInUserParticipated(int participatedId, quizScore, quizCorrectAttempt, quizWrongAttempt, quizUnAttempted)
UPDATE `mydb`.`user_participated_quiz`
SET
`quiz_score` = 1,
`quiz_wrong_attempted` = 1,
`quiz_correct_attempted` = 1,
`quiz_unattempted` = 1,
`is_completed` = 1
WHERE mydb.user_participated_quiz.user_participated_quiz_id = 1;
