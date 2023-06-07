ALTER TABLE users_data
  RENAME COLUMN points TO highest_streak;
  
ALTER TABLE users_data
  RENAME COLUMN level TO total_completed;

ALTER TABLE users_data
  ALTER COLUMN highest_streak TYPE INTEGER;
  