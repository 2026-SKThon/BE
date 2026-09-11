-- 1. 병원 테이블 생성
CREATE TABLE hospitals (
                           id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name                VARCHAR(100) NOT NULL,
                           category            VARCHAR(30),
                           has_emergency_room  BOOLEAN      NOT NULL,
                           phone               VARCHAR(30),
                           emergency_phone     VARCHAR(30),
                           address             VARCHAR(255),
                           latitude            DOUBLE,
                           longitude           DOUBLE,
                           note                TEXT,
                           description         TEXT,
                           directions          VARCHAR(255),
                           created_at          DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
                           updated_at          DATETIME(6)  DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2. 요일별 진료시간 테이블 생성
CREATE TABLE hospital_schedules (
                                    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    hospital_id BIGINT      NOT NULL,
                                    day_of_week VARCHAR(10) NOT NULL,
                                    start_time  VARCHAR(4),
                                    end_time    VARCHAR(4),
                                    created_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
                                    updated_at  DATETIME(6) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6),

                                    CONSTRAINT fk_hospital_schedules_hospital
                                        FOREIGN KEY (hospital_id) REFERENCES hospitals (id)
                                            ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. 조회 성능 향상을 위한 인덱스 생성
CREATE INDEX idx_hospital_schedules_hospital_id ON hospital_schedules (hospital_id);
CREATE INDEX idx_hospital_schedules_day_of_week ON hospital_schedules (day_of_week);
CREATE INDEX idx_hospitals_location ON hospitals (latitude, longitude);