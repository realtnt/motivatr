DROP TABLE IF EXISTS users_data;

CREATE TABLE users_data (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT REFERENCES users(id) NOT NULL,
  points BIGINT,
  streak INTEGER,
  level INTEGER,
  last_challenge_completed BIGINT REFERENCES challenges(id),

  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL
);

