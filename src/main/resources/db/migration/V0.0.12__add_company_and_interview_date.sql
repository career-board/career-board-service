-- Add company and interview_date columns
ALTER TABLE user_interview
ADD COLUMN company VARCHAR(255) NOT NULL DEFAULT 'Unknown Company',
ADD COLUMN interview_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- Add timestamp columns if they don't exist
DO $$ 
BEGIN 
    -- Add created_at if it doesn't exist
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns 
                  WHERE table_name = 'user_interview' AND column_name = 'created_at') THEN
        ALTER TABLE user_interview 
        ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
    END IF;

    -- Add updated_at if it doesn't exist
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns 
                  WHERE table_name = 'user_interview' AND column_name = 'updated_at') THEN
        ALTER TABLE user_interview 
        ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Remove the default constraints after adding the columns
ALTER TABLE user_interview
ALTER COLUMN company DROP DEFAULT,
ALTER COLUMN interview_date DROP DEFAULT;
