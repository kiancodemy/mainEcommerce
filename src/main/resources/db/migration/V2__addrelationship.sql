ALTER TABLE refresh_token
    DROP CONSTRAINT IF EXISTS uc_refreshtoken_user;
ALTER TABLE refresh_token
    ALTER COLUMN user_id SET NOT NULL;