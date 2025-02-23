-- Add email column to user_account table
ALTER TABLE user_account
ADD COLUMN email VARCHAR(255) NULL;

-- Update existing records with generated email addresses
UPDATE user_account
SET email = LOWER(username) || '@example.com'
WHERE email IS NULL;

-- Add unique constraint and not null constraint after data is populated
ALTER TABLE user_account
ALTER COLUMN email SET NOT NULL,
ADD CONSTRAINT user_email_unique UNIQUE (email);
