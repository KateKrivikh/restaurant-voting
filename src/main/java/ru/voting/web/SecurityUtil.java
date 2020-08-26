package ru.voting.web;

import static ru.voting.model.AbstractBaseEntity.START_SEQ;

public class SecurityUtil {
    public static int authUserId() {
        return START_SEQ;
    }
}