INSERT INTO application_user
VALUES (1, '$2a$10$XXjjnAFlw6kAZuKaZSIqde3AUn3eYkD6Ic1DPs.ModJDzKPnpeEsK', 'jon.snow'),
       (2, '$2a$10$XXjjnAFlw6kAZuKaZSIqde3AUn3eYkD6Ic1DPs.ModJDzKPnpeEsK', 'daenerys.targaryen'),
       (3, '$2a$10$XXjjnAFlw6kAZuKaZSIqde3AUn3eYkD6Ic1DPs.ModJDzKPnpeEsK', 'tyrion.lannister'),
       (4, '$2a$10$XXjjnAFlw6kAZuKaZSIqde3AUn3eYkD6Ic1DPs.ModJDzKPnpeEsK', 'khal.drogo');

INSERT INTO application_role
VALUES (1,'ROLE_ADMIN'), (2,'ROLE_USER');

INSERT INTO user_roles
VALUES (1,1), (2,2), (3,2), (4,2);
