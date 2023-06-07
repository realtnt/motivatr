ALTER TABLE users
  ADD COLUMN users_data_id BIGINT REFERENCES users_data(id);

