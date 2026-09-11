ALTER TABLE `fever_episodes`
    ADD COLUMN `severity` ENUM('CAUTION', 'EMERGENCY') NOT NULL COMMENT '발열 심각도' AFTER `status`;