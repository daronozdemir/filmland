CREATE TABLE Customer (
  id INTEGER NOT NULL AUTO_INCREMENT;,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL.
  PRIMARY KEY (id)
);

--CREATE TABLE Subscription (
--  subscription_id INTEGER PRIMARY KEY,
--  subscription_type VARCHAR(50) NOT NULL,
--  price DECIMAL(10,2) NOT NULL,
--  max_movies_per_month INTEGER NOT NULL
--);
--
--CREATE TABLE User_Subscription (
--  user_subscription_id INTEGER PRIMARY KEY,
--  user_id INTEGER NOT NULL,
--  subscription_id INTEGER NOT NULL,
--  start_date DATE NOT NULL,
--  end_date DATE NOT NULL,
--  FOREIGN KEY (user_id) REFERENCES User(user_id),
--  FOREIGN KEY (subscription_id) REFERENCES Subscription(subscription_id)
--);