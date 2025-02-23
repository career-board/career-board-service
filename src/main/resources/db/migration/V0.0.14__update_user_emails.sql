-- Update existing users with more realistic email addresses
UPDATE user_account 
SET email = 
    CASE username
        WHEN 'user' THEN 'supun.kavinda@example.com'
        WHEN 'admin' THEN 'alice.smith@example.com'
        WHEN 'bob_jones' THEN 'bob.jones@example.com'
        WHEN 'charlie_brown' THEN 'charlie.brown@example.com'
        WHEN 'emma_davis' THEN 'emma.davis@example.com'
        WHEN 'lucas_miller' THEN 'lucas.miller@example.com'
        WHEN 'lily_johnson' THEN 'lily.johnson@example.com'
        WHEN 'noah_williams' THEN 'noah.williams@example.com'
        WHEN 'mia_lee' THEN 'mia.lee@example.com'
        WHEN 'oliver_martin' THEN 'oliver.martin@example.com'
    END
WHERE username IN (
    'user', 'admin', 'bob_jones', 'charlie_brown', 'emma_davis',
    'lucas_miller', 'lily_johnson', 'noah_williams', 'mia_lee', 'oliver_martin'
);
