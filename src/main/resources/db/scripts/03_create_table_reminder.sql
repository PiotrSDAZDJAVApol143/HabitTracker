CREATE TABLE IF NOT EXISTS REMINDER (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    MESSAGE             VARCHAR(255) NOT NULL,
    REMINDER_TIME       DATETIME,
    habit_id            BIGINT,
    goal_id             BIGINT,
    FOREIGN KEY (habit_id) REFERENCES HABIT(id)
);
