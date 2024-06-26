package io.paioneer.nain.interview.jpa.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.paioneer.nain.interview.jpa.entity.*;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class InterviewRepositoryImpl implements InterviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;
    private QInterviewEntity interviewEntity = QInterviewEntity.interviewEntity;
    private QQuestionEntity questionEntity = QQuestionEntity.questionEntity;

    @Override
    public ArrayList<QuestionEntity> selectRanQuestion(ArrayList typeList, String category) {

        ArrayList<QuestionEntity> questions = new ArrayList<>();

        for(Object type : typeList) {
            if(String.valueOf(type).equals(category)) {
                List<QuestionEntity> list = queryFactory
                        .selectFrom(questionEntity)
                        .where(questionEntity.qType.eq((String) type))
                        .fetch();
                Collections.shuffle(list);
                for(int i = 0; i < 4; i++) {
                    questions.add(list.get(i));
                }

            }else if(String.valueOf(type).equals("경험")){
                List<QuestionEntity> list = queryFactory
                        .selectFrom(questionEntity)
                        .where(questionEntity.qType.eq((String) type))
                        .fetch();
                Collections.shuffle(list);
                for(int i = 0; i < 2; i++) {
                    questions.add(list.get(i));
                }
            }else {
                List<QuestionEntity> list = queryFactory
                        .selectFrom(questionEntity)
                        .where(questionEntity.qType.eq((String) type))
                        .fetch();
                Collections.shuffle(list);
                questions.add(list.get(0));
            }
        }
        return questions;
    }

    @Override
    public InterviewEntity findByItvNo(Long itvNo) {
        return queryFactory.selectFrom(interviewEntity)
                .where(interviewEntity.itvNo.eq(itvNo))
                .fetchOne();
    }

    @Override
    public Double getVoiceScore(Long itvNo) {
        return queryFactory.select(interviewEntity.voiceScore)
                .from(interviewEntity)
                .where(interviewEntity.itvNo.eq(itvNo))
                .fetchOne();
    }
}
