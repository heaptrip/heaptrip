package com.heaptrip.domain.repository.mail;

import com.heaptrip.domain.entity.mail.Mail;
import com.heaptrip.domain.repository.CrudRepository;

import java.util.List;

public interface MailRepository extends CrudRepository<Mail> {

    List<Mail> findByNode(String node);

}
