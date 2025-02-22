
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

ALTER TABLE career_board_schema.user_post
ADD COLUMN post_uuid UUID UNIQUE;
