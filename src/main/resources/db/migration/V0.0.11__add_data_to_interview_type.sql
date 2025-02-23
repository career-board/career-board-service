
-- Insert default interview types
INSERT INTO interview_type (name) VALUES
    ('ASSESSMENT'),
    ('SCREENING'),
    ('FIRST_ROUND'),
    ('SECOND_ROUND'),
    ('THIRD_ROUND');

-- Update existing interviews to have a default type (using ASSESSMENT as default)
UPDATE user_interview 
SET type_id = (SELECT type_id FROM interview_type WHERE name = 'ASSESSMENT');

-- Now that all records have a type_id, add the NOT NULL constraint
ALTER TABLE user_interview
ALTER COLUMN type_id SET NOT NULL;
