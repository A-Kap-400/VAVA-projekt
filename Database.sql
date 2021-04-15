CREATE SCHEMA IF NOT EXISTS library;

CREATE TABLE IF NOT EXISTS library.Administrators
(
    id            SERIAL      NOT NULL PRIMARY KEY,
    admin_name    VARCHAR(30) NOT NULL,
    password_hash VARCHAR(64) NOT NULL,
    password_salt VARCHAR(32) NOT NULL,
    root          BOOLEAN     NOT NULL,
    created_at    timestamp   NOT NULL DEFAULT current_timestamp,
    updated_at    timestamp   NOT NULL DEFAULT current_timestamp,
    deleted_at    timestamp            DEFAULT NULL

);

CREATE TABLE IF NOT EXISTS library.Users
(
    id           SERIAL       NOT NULL PRIMARY KEY,
    user_name    VARCHAR(50)  NOT NULL,
    address      VARCHAR(250) NOT NULL,
    psc          VARCHAR(5) NOT NULL,
    city         VARCHAR(250) NOT NULL,
    phone_number VARCHAR(13)  NOT NULL,
    created_at   timestamp    NOT NULL DEFAULT current_timestamp,
    updated_at   timestamp    NOT NULL DEFAULT current_timestamp,
    deleted_at   timestamp             DEFAULT NULL

);

CREATE TABLE IF NOT EXISTS library.Books
(
    id           SERIAL       NOT NULL PRIMARY KEY,
    name         VARCHAR(50)  NOT NULL,
    author       VARCHAR(50)  NOT NULL,
    typ          VARCHAR(50)  NOT NULL,
    description  VARCHAR(500) NOT NULL,
    borrow_until timestamp             DEFAULT NULL,
    user_id      BIGINT                DEFAULT NULL,
    created_at   timestamp    NOT NULL DEFAULT current_timestamp,
    updated_at   timestamp    NOT NULL DEFAULT current_timestamp,
    deleted_at   timestamp             DEFAULT NULL,

    CONSTRAINT Book_user
        FOREIGN KEY (user_id)
            REFERENCES library.Users (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE

);

INSERT INTO library.Administrators
(admin_name,password_hash,password_salt,root)
VALUES
('Admin','ba27cae1149da2ad94db7fc40e482a51c06780fca11e5179ec06dbd2b8260da6','iSlyrYJsIq7DIrxZKQ3g5ancJyBU8FoI', TRUE), -- heslo: Admin
('PatrikVelcicky','3d03fbde14b5a75187ea3962d0519ce42b0d4786aee2616ab3b853bfc1087f9c', 'dceT84MwRekTQUMIgF6qcModYsPCuYQv', FALSE),-- heslo: heslo
('AkosKapel', '2cf2e95e987731fb1149faf4be65962f899749c289d3415c81e54ead4db6aae5', 'mOHqXO5561UHScp5s3fp7IPcNXc7ojGJ', FALSE);-- heslo: heslo

INSERT INTO library.Users
(user_name, address, psc, city, phone_number)
VALUES
('Patrik Velcicky', 'Svitavska 903/6', '96501', 'Ziar nad Hronom', '+421918070678'),
('Akos Kapel', 'Svitavska 903/10', '96501', 'Ziar nad Hronom', '+421918071679');

INSERT INTO library.Books
(name,author,typ,description)
VALUES
('The Lord of the Rings', 'J. R. R. Tolkien', 'Novel', 'The Lord of the Rings is an epic high fantasy novel by the English author and scholar J. R. R. Tolkien. Set in Middle-earth, the world at some distant time in the past, the story began as a sequel to Tolkien''s 1937 children''s book The Hobbit, but eventually developed into a much larger work.'),
('The Lord of the Rings', 'J. R. R. Tolkien', 'Novel', 'The Lord of the Rings is an epic high fantasy novel by the English author and scholar J. R. R. Tolkien. Set in Middle-earth, the world at some distant time in the past, the story began as a sequel to Tolkien''s 1937 children''s book The Hobbit, but eventually developed into a much larger work.'),
('The Hobbit, or There and Back Again', 'J. R. R. Tolkien', 'Novel', 'The Hobbit, or There and Back Again is a children''s fantasy novel by English author J. R. R. Tolkien. It was published on 21 September 1937 to wide critical acclaim, being nominated for the Carnegie Medal and awarded a prize from the New York Herald Tribune for best juvenile fiction.'),
('The Hobbit, or There and Back Again', 'J. R. R. Tolkien', 'Novel', 'The Hobbit, or There and Back Again is a children''s fantasy novel by English author J. R. R. Tolkien. It was published on 21 September 1937 to wide critical acclaim, being nominated for the Carnegie Medal and awarded a prize from the New York Herald Tribune for best juvenile fiction.');