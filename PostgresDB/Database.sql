DROP SCHEMA IF EXISTS library CASCADE;

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
    psc          VARCHAR(5)   NOT NULL,
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

    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES library.Users (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);


INSERT INTO library.Administrators (admin_name, password_hash, password_salt, root)
VALUES ('Admin', 'c608b436bf663de7d6c62c13411adf8afdf9bbdf13cf682d781bd7c7da6cc578', 'HvjnxoqoyRVH6uKhpT2yyHqKPKY2VeSc',
        TRUE),                                      -- heslo: Admin
       ('PatrikVelcicky', 'c47a6c22ad4fc87cee495678d1e2639d72e44835e47c33760140bed9803e1935',
        'eZ906QesJWDNOPQJB7jBKq9zpIzGoXQg', FALSE), -- heslo: heslo
       ('AkosKappel', '8a0b87198b284d6da964d93ffe6808d27b75851c7d3d3e0a030ba46a1262a437',
        'q79AUZ6tpyZC1tOv971JwD0VavhWVk2n', FALSE); -- heslo: heslo


INSERT INTO library.Users (user_name, address, psc, city, phone_number)
VALUES ('Patrik Velcicky', 'Svitavska 903/6', '96501', 'Ziar nad Hronom', '+421918070678'),
       ('Akos Kappel', 'Svitavska 903/10', '96501', 'Ziar nad Hronom', '+421918071679'),
       ('James Bond', 'Baker street', '007', 'London', '0918273465'),
       ('Tony Stark', 'Wold Street', '12345', 'New York', '0987654321');


INSERT INTO library.Books (name, author, typ, description)
VALUES ('The Lord of the Rings', 'J. R. R. Tolkien', 'Novel',
        'The Lord of the Rings is an epic high fantasy novel by the English author and scholar J. R. R. Tolkien. Set in Middle-earth, the world at some distant time in the past, the story began as a sequel to Tolkien''s 1937 children''s book The Hobbit, but eventually developed into a much larger work.'),
       ('The Lord of the Rings', 'J. R. R. Tolkien', 'Novel',
        'The Lord of the Rings is an epic high fantasy novel by the English author and scholar J. R. R. Tolkien. Set in Middle-earth, the world at some distant time in the past, the story began as a sequel to Tolkien''s 1937 children''s book The Hobbit, but eventually developed into a much larger work.'),
       ('The Hobbit, or There and Back Again', 'J. R. R. Tolkien', 'Novel',
        'The Hobbit, or There and Back Again is a children''s fantasy novel by English author J. R. R. Tolkien. It was published on 21 September 1937 to wide critical acclaim, being nominated for the Carnegie Medal and awarded a prize from the New York Herald Tribune for best juvenile fiction.'),
       ('The Hobbit, or There and Back Again', 'J. R. R. Tolkien', 'Novel',
        'The Hobbit, or There and Back Again is a children''s fantasy novel by English author J. R. R. Tolkien. It was published on 21 September 1937 to wide critical acclaim, being nominated for the Carnegie Medal and awarded a prize from the New York Herald Tribune for best juvenile fiction.');



--Kniha--Neprihlaseny------------------------------------------------------------------------------------------

--Zobraz->
SELECT books.id, books.typ, books.name, books.author, books.borrow_until
FROM library.Books AS books
         LEFT JOIN library.Users AS users ON users.id = books.user_id
WHERE books.deleted_at is NULL
  AND users.deleted_at is NULL
  AND books.typ ILIKE Zaner
  AND books.name ILIKE Nazov
  AND books.author ILIKE Autor
  AND books.borrow_until is NULL;



--Kniha--------------------------------------------------------------------------------------------------------

--Pozicat->
UPDATE library.Books
SET user_id      = ID_zakaznik,
    borrow_until = DatumVrateniaKnihy
WHERE id = ID_Kniha
  AND deleted_at is NULL;

--Vratit->
UPDATE library.Books
SET user_id      = NULL,
    borrow_until = NULL
WHERE id = ID_Kniha
  AND deleted_at is NULL;

--Pridat->
INSERT INTO library.Books
    (name, author, typ, description)
VALUES (Nazov, Autor, Zaner, Popis);

--Odstran->
UPDATE library.Books
SET deleted_at = current_timestamp
WHERE id = ID_Kniha
  AND deleted_at is NULL;

--Zobraz->
SELECT books.id, books.typ, books.name, books.author, books.borrow_until, users.user_name
FROM library.Books AS books
         LEFT JOIN library.Users AS users ON users.id = books.user_id
WHERE books.deleted_at is NULL
  AND users.deleted_at is NULL
  AND books.id = ID_Kniha
  AND books.typ ILIKE Zaner
  AND books.name ILIKE Nazov
  AND books.author ILIKE Autor
  AND (users.user_name = Meno_ID_zakaznik OR users.id = Meno_ID_zakaznik)
  AND books.borrow_until < current_timestamp
  AND books.borrow_until is NULL;



--Zakaznik-------------------------------------------------------------------------------------------------------

--Pridat->
INSERT INTO library.Users
    (user_name, address, psc, city, phone_number)
VALUES (MenoPriezvisko, Adresa, psc_zak, Mesto, TelCislo);

--Odstranit->
UPDATE library.Users
SET deleted_at = current_timestamp
WHERE id = ID_Zakaznik
  and deleted_at is NULL;

--Upravit--Vyhladat
SELECT user_name, address, city, psc, phone_number
FROM library.Users
WHERE id = ID_Zakaznik
  AND deleted_at is NULL;

--Upravit--upravit udaje ->
UPDATE library.Users
SET user_name    = MenoPriezvisko,
    address      = Adresa,
    city         = Mesto,
    psc          = psc_zak,
    phone_number = TelCislo
WHERE id = ID_Zakaznik
  AND deleted_at is NULL;

-- Zobrazit ->
SELECT DISTINCT users.id, user_name, address, psc, city, phone_number
FROM library.Users
         RIGHT JOIN library.Books b on users.id = b.user_id
WHERE b.deleted_at is NULL
  AND users.deleted_at is NULL
  AND (user_name = Meno_ID_zakaznik OR users.id = Meno_ID_zakaznik)
  AND b.borrow_until < current_timestamp
ORDER BY users.id ASC, b.borrow_until ASC;

--Administrativa------------------------------------------------------------------------------------------------------

--Pridat->
INSERT INTO library.Administrators
    (admin_name, password_hash, password_salt, root)
VALUES (PouzMeno, heslo_hash, heslo_sol, Administrator);

--Odstranit->
UPDATE library.Administrators
SET deleted_at = current_timestamp
WHERE (id = Meno_ID_Zamestnanec OR admin_name = Meno_ID_Zamestnanec)
  AND deleted_at is NULL;

--Zmenit heslo->
UPDATE library.Administrators
SET password_hash = heslo_hash,
    password_salt = heslo_sol
WHERE (id = Meno_ID_Zamestnanec OR admin_name = Meno_ID_Zamestnanec)
  AND deleted_at is NULL;

--Zobraz->
SELECT id, admin_name, root, created_at, updated_at
FROM library.Administrators
WHERE deleted_at is NULL
  AND (id = Meno_ID_Zamestnanec OR admin_name = Meno_ID_Zamestnanec);
