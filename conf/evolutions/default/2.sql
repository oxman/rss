# --- Add column login date

# --- !Ups

ALTER TABLE user ADD date_login timestamp;
UPDATE user SET date_login=date;

# --- !Downs

ALTER TABLE user DROP date_login;