CREATE TABLE IF NOT EXISTS APP_USER (
                        id          BIGINT PRIMARY KEY AUTO_INCREMENT,
                        UUID        VARCHAR(36) NOT NULL,
                        LOGIN       VARCHAR(255) NOT NULL,
                        EMAIL       VARCHAR(255) NOT NULL,
                        USER_NAME   VARCHAR(255) NOT NULL,
                        PASSWORD    VARCHAR(255) NOT NULL,
                        ROLE        ENUM('USER', 'ADMIN'),
                        is_lock      BOOLEAN DEFAULT TRUE,
                        is_enable   BOOLEAN DEFAULT FALSE
);