package com.heaptrip.domain.exception;

/**
 *  Error list from:
 *
 *  errors_ru.properties
 *  errors_en.properties 
 */
public enum ErrorEnum {

    LOGIN_FAILURE("err.login.failure"),
    REGISTRATION_FAILURE("err.registration.failure"),

    REMOVE_TRIP_FAILURE("err.remove.trip.failure"),
    REMOVE_TRIP_LANGUAGE_FAILURE("err.remove.trip.language.failure"),

    REMOVE_EVENT_LANGUAGE_FAILURE("err.remove.event.language.failure"),

    ERROR_ADD_COMMENT("error.add.comment"),

    ERROR_ACCOUNT_NOT_FOUND("error.account.not.found"),
    ERROR_ACCOUNT_ALREADY_CONFIRM("error.account.already.confirm"),
    ERROR_ACCOUNT_WRONG_CONFIRM_VALUE("error.account.wrong.confirm.value"),
    ERROR_ACCOUNT_NOT_ACTIVE("error.account.not.active"),
    ERROR_ACCOUNT_CURRENT_EMAIL_NOT_EQUALS("error.account.current.email.not.equals"),

    ERROR_NOTIFICATION_NOT_FOUND("error.notification.not.found"),
    ERROR_NOTIFICATION_NOT_NEW("error.notification.not.new"),

    ERROR_USER_NOT_FOUND("error.user.not.found"),
    ERROR_USER_NOT_FOUND_BY_EMAIL("error.user.not.found.by.email"),
    ERROR_USER_NOT_ACTIVE("error.user.not.active"),
    ERROR_USER_CURRENT_PSWD_WRONG("error.user.current.pswd.wrong"),
    ERROR_USER_DONT_HAVE_SOCIAL_NET("error.user.dont.have.social.net"),
    ERROR_USER_HAVE_ONE_SOCIAL_NET_AND_EMPTY_PSWD("error.user.have.one.social.net.and.empty.pswd"),
    ERROR_USER_ALREADY_HAS_SOCIAL_NET("error.user.already.has.social.net"),
    ERROR_USER_PSWD_VALUE_IS_WRONG("error.user.pswd.value.is.wrong"),
    ERROR_USER_EMAIL_IS_NOT_CORRECT("error.user.email.is.not.correct"),
    ERROR_USER_PSWD_IS_NOT_CORRECT("error.user.pswd.is.not.correct"),
    ERROR_ACCOUNT_WITH_THE_EMAIL_ALREADY_EXISTS("error.account.with.the.email.already.exists"),

    ERROR_COMMUNITY_NOT_FOUND("error.community.not.found"),
    ERROR_COMMUNITY_NOT_ACTIVE("error.community.not.active"),

    ERR_SYSTEM_CREATE("err.system.create.exception"),
    ERR_SYSTEM_DB("err.system.bd"),
    ERR_SYSTEM_SOLR("err.system.solr"),
    ERR_SYSTEM_SEND_MAIL("err.system.send.mail"),

    ERROR_RATING_CALCULATION("error.rating.calculation");

    public String KEY;

    ErrorEnum(String KEY) {
        this.KEY = KEY;
    }
}
