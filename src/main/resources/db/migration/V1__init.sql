SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `daily_record`;
DROP TABLE IF EXISTS `notification_settings`;
DROP TABLE IF EXISTS `medication_records`;
DROP TABLE IF EXISTS `condition_checks`;
DROP TABLE IF EXISTS `temperature_logs`;
DROP TABLE IF EXISTS `fever_episodes`;
DROP TABLE IF EXISTS `devices`;
DROP TABLE IF EXISTS `guardian_children`;
DROP TABLE IF EXISTS `children`;
DROP TABLE IF EXISTS `users`;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `users` (
    `id`            BIGINT          NOT NULL AUTO_INCREMENT,
    `name`          VARCHAR(50)     NOT NULL            COMMENT '이름',
    `email`         VARCHAR(100)    NOT NULL            COMMENT '이메일',
    `password_hash` VARCHAR(255)    NOT NULL,
    `phone`         VARCHAR(20)     NULL,
    `created_at`    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_users_email` (`email`)
);

CREATE TABLE `children` (
    `id`                  BIGINT          NOT NULL AUTO_INCREMENT,
    `name`                VARCHAR(50)     NOT NULL,
    `birth_date`          DATE            NOT NULL    COMMENT '생년월일',
    `weight`              DOUBLE          NULL        COMMENT '최근 체중(kg)',
    `weight_recorded_at`  DATE            NULL        COMMENT '체중 측정시점',
    `allergy_status`      ENUM('NONE', 'YES', 'UNKNOWN') NOT NULL DEFAULT 'UNKNOWN' COMMENT '알러지 여부',
    `allergy_detail`      TEXT            NULL        COMMENT '알러지 상세',
    `regular_medication`  TEXT            NULL        COMMENT '평소 복용하는 약',
    `created_at`          DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`          DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE `guardian_children` (
    `id`         BIGINT      NOT NULL AUTO_INCREMENT,
    `user_id`    BIGINT      NOT NULL,
    `child_id`   BIGINT      NOT NULL,
    `role`       ENUM('MAIN', 'SUB') NOT NULL DEFAULT 'MAIN' COMMENT '관리보호자/공동보호자',
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_guardian_children` (`user_id`, `child_id`),
    FOREIGN KEY (`user_id`)  REFERENCES `users`(`id`),
    FOREIGN KEY (`child_id`) REFERENCES `children`(`id`)
);

CREATE TABLE `devices` (
    `id`                BIGINT          NOT NULL AUTO_INCREMENT,
    `child_id`          BIGINT          NOT NULL,
    `device_code`       VARCHAR(50)     NOT NULL    COMMENT '예: A21',
    `device_name`       VARCHAR(100)    NOT NULL    COMMENT '예: 온이 센서 A21',
    `is_connected`      TINYINT(1)      NOT NULL DEFAULT 0,
    `last_connected_at` DATETIME        NULL,
    `battery_level`     INT             NULL        COMMENT '배터리 %',
    `created_at`        DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`        DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`child_id`) REFERENCES `children`(`id`)
);

CREATE TABLE `fever_episodes` (
    `id`         BIGINT      NOT NULL AUTO_INCREMENT,
    `child_id`   BIGINT      NOT NULL,
    `status`     ENUM('ONGOING', 'ENDED') NOT NULL DEFAULT 'ONGOING',
    `started_at` DATETIME    NOT NULL,
    `ended_at`   DATETIME    NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`child_id`) REFERENCES `children`(`id`)
);

CREATE TABLE `temperature_logs` (
    `id`               BIGINT      NOT NULL AUTO_INCREMENT,
    `child_id`         BIGINT      NOT NULL,
    `device_id`        BIGINT      NULL        COMMENT 'NULL이면 수동 입력',
    `fever_episode_id` BIGINT      NULL        COMMENT '발열 에피소드',
    `temperature`      DOUBLE      NOT NULL    COMMENT '체온(℃)',
    `source`           ENUM('DEVICE', 'MANUAL') NOT NULL DEFAULT 'DEVICE' COMMENT '측정 방법',
    `measurement_site` ENUM('EAR', 'ARMPIT', 'FOREHEAD', 'OTHER') NULL COMMENT '측정 부위',
    `note`             TEXT        NULL        COMMENT '메모',
    `measured_at`      DATETIME    NOT NULL    COMMENT '측정 시점',
    `created_at`       DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`child_id`)         REFERENCES `children`(`id`),
    FOREIGN KEY (`device_id`)        REFERENCES `devices`(`id`),
    FOREIGN KEY (`fever_episode_id`) REFERENCES `fever_episodes`(`id`)
);

CREATE TABLE `condition_checks` (
    `id`                BIGINT      NOT NULL AUTO_INCREMENT,
    `child_id`          BIGINT      NOT NULL,
    `user_id`           BIGINT      NOT NULL    COMMENT '기록한 보호자',
    `fever_episode_id`  BIGINT      NULL,
    `response_status`   ENUM('NORMAL', 'LETHARGIC', 'UNKNOWN') NOT NULL DEFAULT 'UNKNOWN' COMMENT '반응',
    `breathing_status`  ENUM('NORMAL', 'DIFFICULT', 'UNKNOWN') NOT NULL DEFAULT 'UNKNOWN' COMMENT '호흡',
    `hydration_status`  ENUM('NORMAL', 'REDUCED', 'UNKNOWN')   NOT NULL DEFAULT 'UNKNOWN' COMMENT '수분 섭취/소변',
    `note`              TEXT        NULL        COMMENT '동반 증상 메모',
    `created_at`        DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`child_id`)         REFERENCES `children`(`id`),
    FOREIGN KEY (`user_id`)          REFERENCES `users`(`id`),
    FOREIGN KEY (`fever_episode_id`) REFERENCES `fever_episodes`(`id`)
);

CREATE TABLE `medication_records` (
    `id`                  BIGINT      NOT NULL AUTO_INCREMENT,
    `child_id`            BIGINT      NOT NULL    COMMENT '아이 ID',
    `user_id`             BIGINT      NOT NULL    COMMENT '기록한 보호자',
    `fever_episode_id`    BIGINT      NULL        COMMENT '발열 에피소드',
    `medication_name`     VARCHAR(100) NOT NULL   COMMENT '예: 챔프 시럽',
    `medication_type`     ENUM('CHAMP', 'BRUFEN', 'OTHER') NOT NULL DEFAULT 'OTHER' COMMENT '약 타입',
    `dosage`              DOUBLE      NOT NULL    COMMENT '복용량',
    `dosage_unit`         VARCHAR(10) NOT NULL DEFAULT 'mL' COMMENT '단위',
    `temperature_at_time` DOUBLE      NULL        COMMENT '복용 시점 체온',
    `taken_at`            DATETIME    NOT NULL    COMMENT '복용 시점',
    `note`                TEXT        NULL        COMMENT '추가 메모',
    `created_at`          DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`child_id`)         REFERENCES `children`(`id`),
    FOREIGN KEY (`user_id`)          REFERENCES `users`(`id`),
    FOREIGN KEY (`fever_episode_id`) REFERENCES `fever_episodes`(`id`)
);

CREATE TABLE `notification_settings` (
    `id`                      BIGINT      NOT NULL AUTO_INCREMENT,
    `user_id`                 BIGINT      NOT NULL,
    `child_id`                BIGINT      NOT NULL,
    `temperature_alert`       TINYINT(1)  NOT NULL DEFAULT 1 COMMENT '체온 변화 알림',
    `device_connection_alert` TINYINT(1)  NOT NULL DEFAULT 1 COMMENT '기기 연결 알림',
    `shared_record_alert`     TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '공유 기록 알림',
    `updated_at`              DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_notification_settings` (`user_id`, `child_id`),
    FOREIGN KEY (`user_id`)  REFERENCES `users`(`id`),
    FOREIGN KEY (`child_id`) REFERENCES `children`(`id`)
);

CREATE TABLE `daily_record` (
    `id`           BIGINT      NOT NULL AUTO_INCREMENT,
    `child_id`     BIGINT      NOT NULL,
    `record_type`  ENUM('TEMPERATURE', 'MEDICATION', 'CONDITION') NOT NULL COMMENT '기록 유형',
    `recorded_at`  DATETIME    NOT NULL COMMENT '기록 시점',
    `created_at`   DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_daily_record_child_recorded` (`child_id`, `recorded_at`),
    FOREIGN KEY (`child_id`) REFERENCES `children`(`id`)
);
