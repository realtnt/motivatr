DROP TABLE IF EXISTS challenges;

CREATE TABLE challenges (
  id BIGSERIAL PRIMARY KEY,
  author_id BIGINT REFERENCES users(id) NOT NULL,
  title TEXT NOT NULL,
  description TEXT NOT NULL,
  image_url TEXT,
  video_url TEXT,
  published_on TIMESTAMP,

  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL
);

