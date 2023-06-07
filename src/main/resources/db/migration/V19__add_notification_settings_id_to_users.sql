ALTER TABLE users
  ADD COLUMN notification_settings_id BIGINT REFERENCES notification_settings(id);

