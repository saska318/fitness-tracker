INSERT INTO app_user (id, email, password) VALUES
    (1, 'demo@fit.com', '$2a$10$Dow1aFzG7y9w7k6QJr9m6u7lO6Cw8v7w1Qm6FQp6Y9l9Ew8uKfY3K'),
    (2, 'saska@fit.com', '$2a$10$Dow1aFzG7y9w7k6QJr9m6u7lO6Cw8v7w1Qm6FQp6Y9l9Ew8uKfY3K');
INSERT INTO workout (id, user_id, type, duration_minutes, intensity, feeling, workout_date) VALUES
-- WEEK 1
(1,1,'CARDIO',30,6,7,'2025-03-02T10:00:00'),
(2,1,'STRENGTH',45,8,8,'2025-03-04T18:00:00'),

-- WEEK 2
(3,1,'CARDIO',25,5,6,'2025-03-10T09:00:00'),
(4,1,'FLEXIBILITY',40,4,9,'2025-03-12T20:00:00'),

-- WEEK 3
(5,1,'STRENGTH',50,9,7,'2025-03-18T17:30:00'),
(6,1,'CARDIO',35,7,8,'2025-03-20T07:30:00'),

-- WEEK 4
(7,1,'FLEXIBILITY',30,3,10,'2025-03-25T19:00:00'),

-- drugi korisnik (provera da filtrira po useru)
(8,2,'CARDIO',60,8,8,'2025-03-05T10:00:00');