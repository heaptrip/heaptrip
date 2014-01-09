package com.heaptrip.service.content.qa;

import com.heaptrip.domain.entity.content.qa.Question;
import com.heaptrip.domain.service.content.qa.QuestionService;
import com.heaptrip.service.content.ContentServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl extends ContentServiceImpl implements QuestionService {

    @Override
    public Question save(Question question) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(Question question) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
