ALTER TABLE auth_password ADD COLUMN failed_attempts INT NOT NULL DEFAULT 0;
ALTER TABLE auth_password ADD COLUMN locked_until DATETIME NULL;
