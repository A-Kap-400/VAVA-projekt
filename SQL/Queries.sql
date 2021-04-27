
--Kniha--Neprihlaseny------------------------------------------------------------------------------------------

--Zobraz->
SELECT books.id, books.typ, books.name, books.author, books.borrow_until
FROM library.books AS books
         LEFT JOIN library.users AS users ON users.id = books.user_id
WHERE books.deleted_at is NULL
  AND users.deleted_at is NULL
  AND books.typ ILIKE Zaner
  AND books.name ILIKE Nazov
  AND books.author ILIKE Autor
  AND books.borrow_until is NULL;



--Kniha--------------------------------------------------------------------------------------------------------

--Pozicat->
UPDATE library.books
SET user_id      = ID_zakaznik,
    borrow_until = DatumVrateniaKnihy
WHERE id = ID_Kniha
  AND deleted_at is NULL;

--Vratit->
UPDATE library.books
SET user_id      = NULL,
    borrow_until = NULL
WHERE id = ID_Kniha
  AND deleted_at is NULL;

--Pridat->
INSERT INTO library.books
    (name, author, typ, description)
VALUES (Nazov, Autor, Zaner, Popis);

--Odstran->
UPDATE library.books
SET deleted_at = current_timestamp
WHERE id = ID_Kniha
  AND deleted_at is NULL;

--Zobraz->
SELECT books.id, books.typ, books.name, books.author, books.borrow_until, users.user_name
FROM library.books AS books
         LEFT JOIN library.users AS users ON users.id = books.user_id
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
INSERT INTO library.users
    (user_name, address, psc, city, phone_number)
VALUES (MenoPriezvisko, Adresa, psc_zak, Mesto, TelCislo);

--Odstranit->
UPDATE library.users
SET deleted_at = current_timestamp
WHERE id = ID_Zakaznik
  and deleted_at is NULL;

--Upravit--Vyhladat
SELECT user_name, address, city, psc, phone_number
FROM library.users
WHERE id = ID_Zakaznik
  AND deleted_at is NULL;

--Upravit--upravit udaje ->
UPDATE library.users
SET user_name    = MenoPriezvisko,
    address      = Adresa,
    city         = Mesto,
    psc          = psc_zak,
    phone_number = TelCislo
WHERE id = ID_Zakaznik
  AND deleted_at is NULL;

-- Zobrazit ->
SELECT DISTINCT users.id, user_name, address, psc, city, phone_number
FROM library.users
         RIGHT JOIN library.books b on users.id = b.user_id
WHERE b.deleted_at is NULL
  AND users.deleted_at is NULL
  AND (user_name = Meno_ID_zakaznik OR users.id = Meno_ID_zakaznik)
  AND b.borrow_until < current_timestamp
ORDER BY users.id ASC, b.borrow_until ASC;

--Administrativa------------------------------------------------------------------------------------------------------

--Pridat->
INSERT INTO library.administrators
    (admin_name, password_hash, password_salt, root)
VALUES (PouzMeno, heslo_hash, heslo_sol, Administrator);

--Odstranit->
UPDATE library.administrators
SET deleted_at = current_timestamp
WHERE (id = Meno_ID_Zamestnanec OR admin_name = Meno_ID_Zamestnanec)
  AND deleted_at is NULL;

--Zmenit heslo->
UPDATE library.administrators
SET password_hash = heslo_hash,
    password_salt = heslo_sol
WHERE (id = Meno_ID_Zamestnanec OR admin_name = Meno_ID_Zamestnanec)
  AND deleted_at is NULL;

--Zobraz->
SELECT id, admin_name, root, created_at, updated_at
FROM library.administrators
WHERE deleted_at is NULL
  AND (id = Meno_ID_Zamestnanec OR admin_name = Meno_ID_Zamestnanec);
