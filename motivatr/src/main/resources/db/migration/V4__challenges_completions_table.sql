DROP TABLE IF EXISTS challenges_completions;

CREATE TABLE challenges_completions (
  user_id BIGINT REFERENCES users(id) NOT NULL,
  challenge_id BIGINT REFERENCES challenges(id) NOT NULL,
  completed_at TIMESTAMP NOT NULL
);

