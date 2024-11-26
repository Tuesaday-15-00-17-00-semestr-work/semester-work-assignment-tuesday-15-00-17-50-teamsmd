CREATE TABLE IF NOT EXISTS Users (
  user_id int primary key,
  username text not null,
  pass text not null,
  role_id int not null,
  email text not null
);