DROP TABLE IF EXISTS notification_settings;

CREATE TABLE notification_settings (
  id BIGSERIAL PRIMARY KEY,
  users_id BIGINT REFERENCES users(id) NOT NULL,
  daily_notifications BOOLEAN NOT NULL,
  text_notifications BOOLEAN NOT NULL,
  email_notifications BOOLEAN NOT NULL
);