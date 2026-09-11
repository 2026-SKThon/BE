ALTER TABLE `daily_record`
    ADD COLUMN `response_status`  VARCHAR(10) NULL,
    ADD COLUMN `breathing_status` VARCHAR(10) NULL,
    ADD COLUMN `hydration_status` VARCHAR(10) NULL;
