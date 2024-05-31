
package com.study.testcode;

public interface HelloTest {
    Long TEST_USER_ID = 1L;
    String TEST_USER_MESSAGE = "Hello Test";

    Hello HELLO = Hello.builder()
            .id(TEST_USER_ID)
            .message(TEST_USER_MESSAGE)
            .build();

    Long UPDATE_TEST_USER_ID = 2L;
    String UPDATE_TEST_USER_MESSAGE = "Hi";

    Hello UPDATE_HELLO = Hello.builder()
            .id(UPDATE_TEST_USER_ID)
            .message(UPDATE_TEST_USER_MESSAGE)
            .build();
}
