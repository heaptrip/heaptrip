package com.heaptrip.domain.exception;

/**
 * Error list from:
 * <p/>
 * errors_ru.properties
 * errors_en.properties
 */
public enum ErrorEnum {

    UNAUTHORIZED("err.unauthorized"),
    LOGIN_FAILURE("err.login.failure"),
    REGISTRATION_FAILURE("err.registration.failure"),

    REMOVE_TRIP_FAILURE("err.remove.trip.failure"),
    REMOVE_TRIP_LANGUAGE_FAILURE("err.remove.trip.language.failure"),
    ERROR_TRIP_USER_ALREADY_ADDED("err.trip.user.already.added"),

    REMOVE_EVENT_LANGUAGE_FAILURE("err.remove.event.language.failure"),

    ERROR_ADD_COMMENT("error.add.comment"),

    ERROR_ACCOUNT_NOT_FOUND("error.account.not.found"),
    ERROR_ACCOUNT_ALREADY_CONFIRM("error.account.already.confirm"),
    ERROR_ACCOUNT_WRONG_CONFIRM_VALUE("error.account.wrong.confirm.value"),
    ERROR_ACCOUNT_NOT_ACTIVE("error.account.not.active"),
    ERROR_ACCOUNT_CURRENT_EMAIL_NOT_EQUALS("error.account.current.email.not.equals"),
    ERROR_ACCOUNT_WITH_THE_EMAIL_ALREADY_EXISTS("error.account.with.the.email.already.exists"),

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
    ERROR_MORE_THAN_ONE_USER_HAVE_ACTIVE_STATUS("error.more.than.one.user.have.status.active"),
    ERROR_USER_HAVE_ACTIVE_CONTENT("error.user.have.active.content"),
    ERROR_USER_RESET_PASSWORD("error.user.reset.password"),
    ERROR_USER_CHECK_PASSWORD("error.user.check.password"),
    ERROR_USER_CHANGE_PASSWORD("error.user.change.password"),

    ERROR_COMMUNITY_NOT_FOUND("error.community.not.found"),
    ERROR_COMMUNITY_NOT_ACTIVE("error.community.not.active"),
    ERROR_COMMUNITY_HAVE_ACTIVE_CONTENT("error.community.have.active.content"),
    ERROR_COMMUNITY_NOT_HAVE_OWNER("error.community.not.have.owner"),
    ERROR_COMMUNITY_NOT_HAVE_OWNER_AND_EMPLOYEE("error.community.not.have.owner.and.employee"),
    ERROR_COMMUNITY_CREATE("error.community.create"),

    ERROR_SOLE_OWNER_OF_THE_USER_COMMUNITY_ACTIVE("error.sole.owner.of.the.user.community.active"),

    ERROR_RELATION_ALREADY_EXISTS("error.relation.already.exists"),

    ERR_SYSTEM_CREATE("err.system.create.exception"),
    ERR_SYSTEM_DB("err.system.bd"),
    ERR_SYSTEM_SOLR("err.system.solr"),
    ERR_SYSTEM_REDIS("err.system.redis"),
    ERR_SYSTEM_SEND_MAIL("err.system.send.mail"),

    ERROR_RATING_CALCULATION("error.rating.calculation");

    public String KEY;

    ErrorEnum(String KEY) {
        this.KEY = KEY;
    }
}
