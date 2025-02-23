-- Create interview type table
CREATE TABLE interview_type (
    type_id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Add type_id column to user_interview table (without NOT NULL initially)
ALTER TABLE user_interview
ADD COLUMN type_id BIGINT,
ADD CONSTRAINT fk_interview_type
    FOREIGN KEY (type_id)
    REFERENCES interview_type(type_id);
