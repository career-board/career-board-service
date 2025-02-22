-- Drop existing tables
DROP TABLE IF EXISTS post_image;
DROP TABLE IF EXISTS user_post;

-- Create interview table
CREATE TABLE user_interview (
    interview_id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    description VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    moderator_comment TEXT,
    FOREIGN KEY (user_id) REFERENCES user_account(user_id)
);

-- Create interview image table
CREATE TABLE interview_image (
    image_id SERIAL PRIMARY KEY,
    interview_id BIGINT NOT NULL,
    image_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (interview_id) REFERENCES user_interview(interview_id)
);
