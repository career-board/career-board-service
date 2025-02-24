-- Rename columns
ALTER TABLE user_interview 
RENAME COLUMN content TO details;

ALTER TABLE user_interview 
RENAME COLUMN moderator_comment TO editorial;

