package io.paioneer.nain.interview.jpa.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.paioneer.nain.interview.jpa.entity.InterviewEntity;
import io.paioneer.nain.interview.jpa.entity.QInterviewEntity;
import io.paioneer.nain.interview.jpa.entity.QQuestionEntity;
import io.paioneer.nain.interview.jpa.entity.QuestionEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class InterviewRepositoryImpl implements InterviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;
    private QInterviewEntity interviewEntity = QInterviewEntity.interviewEntity;
    private QQuestionEntity questionEntity = QQuestionEntity.questionEntity;

    @Override
    public ArrayList<QuestionEntity> selectRanQuestion(ArrayList typeList) {

        ArrayList<QuestionEntity> questions = new ArrayList<>();
        for(Object type : typeList) {
            Long count = queryFactory
                    .select(questionEntity.count())
                    .from(questionEntity)
                    .where(questionEntity.qType.eq(String.valueOf(type)))
                    .fetchOne();
            ArrayList numList = new ArrayList();
            for(int i = 0; i < 4; i++){
                int num = (int) ((Math.random() * count) + 1);
                if (i == 0) {
                    numList.add(num);
                } else {
                    if (numList.get(i - 1) != numList.get(i)) {
                        numList.add(num);
                    }
                }
            }


            if(String.valueOf(type) == "기술") {
                for (int i = 0; i < 4; i++) {
                    questions.add(queryFactory
                            .selectFrom(questionEntity)
                            .where(questionEntity.qType.eq((String) type)
                                    .and(Expressions.booleanTemplate("ROWNUM = {0}", numList.get(i))))
                            .fetchOne());
                }
            }else if(String.valueOf(type) == "경험"){
                    for(int i = 0; i < 2; i++){
                        questions.add(queryFactory
                                .selectFrom(questionEntity)
                                .where(questionEntity.qType.eq((String) type)
                                        .and(Expressions.booleanTemplate("ROWNUM = {0}", numList.get(i))))
                                .fetchOne());
                    }
            }else {
                questions.add(queryFactory
                        .selectFrom(questionEntity)
                        .where(questionEntity.qType.eq((String) type)
                                .and(Expressions.booleanTemplate("ROWNUM = {0}", numList.get(1))))
                        .fetchOne());
            }
        }
        return questions;
    }
}
