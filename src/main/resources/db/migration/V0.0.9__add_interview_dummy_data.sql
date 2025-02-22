-- Insert dummy interview data
INSERT INTO user_interview (user_id, description, content, created_at, status, moderator_comment)
VALUES
(1, 'Software Engineer Interview at Google', 
'I recently had a technical interview at Google for a Software Engineer position. Here are the key topics covered:

1. Data Structures & Algorithms
- Binary Tree traversal
- Dynamic Programming
- Graph algorithms

2. System Design
- Scalability considerations
- Load balancing
- Caching strategies

3. Behavioral Questions
- Past project experiences
- Team collaboration
- Conflict resolution

Tips for preparation and what to expect in each round.', 
'2025-02-23 00:00:00', 'PUBLISHED', 'Great detailed experience sharing!'),

(2, 'Product Manager Interview Experience',
'Sharing my interview experience for a Product Manager role at Amazon. The interview process consisted of multiple rounds focusing on:

1. Product Sense
2. Analytical Thinking
3. Leadership Principles
4. Technical Understanding

Key learnings and preparation strategies included.',
'2025-02-23 00:00:00', 'DRAFT', NULL),

(3, 'Data Scientist Interview Questions',
'Just completed a series of interviews for a Data Scientist position. Here are the common questions and topics covered:

- Machine Learning concepts
- Statistical analysis
- Python coding challenges
- Real-world case studies

Including detailed explanations and sample solutions.',
'2025-02-23 00:00:00', 'PUBLISHED', 'Very informative content for aspiring data scientists'),

(4, 'Frontend Developer Technical Interview',
'Comprehensive breakdown of my frontend developer interview:

- JavaScript fundamentals
- React concepts
- CSS challenges
- System design questions

Including code samples and preparation resources.',
'2025-02-23 00:00:00', 'PUBLISHED', 'Excellent technical details'),

(5, 'DevOps Engineer Interview Process',
'Detailed walkthrough of the DevOps interview process covering:

- CI/CD pipelines
- Cloud platforms (AWS)
- Container orchestration
- Infrastructure as Code

Real scenarios and practical tips included.',
'2025-02-23 00:00:00', 'DRAFT', NULL);

-- Insert dummy interview images
INSERT INTO interview_image (interview_id, image_name)
VALUES
(1, 'google-office.jpg'),
(1, 'whiteboard-solution.jpg'),
(2, 'amazon-principles.jpg'),
(3, 'data-visualization.png'),
(4, 'frontend-mockup.png'),
(5, 'architecture-diagram.png');
