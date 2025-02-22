CREATE EXTENSION IF NOT EXISTS "uuid-ossp" SCHEMA career_board_schema;


ALTER TABLE career_board_schema.user_post
ADD COLUMN post_uuid UUID UNIQUE;


