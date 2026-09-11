ALTER TABLE `daily_record`
    ADD COLUMN `temperature`     DOUBLE       NULL COMMENT 'TEMPERATURE 타입일 때 체온값(℃)',
    ADD COLUMN `medication_name` VARCHAR(100) NULL COMMENT 'MEDICATION 타입일 때 약 이름',
    ADD COLUMN `dosage`          DOUBLE       NULL COMMENT 'MEDICATION 타입일 때 복용량(mL)';
