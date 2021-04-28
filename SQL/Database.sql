DROP SCHEMA IF EXISTS library CASCADE;

CREATE SCHEMA IF NOT EXISTS library;

CREATE TABLE IF NOT EXISTS library.administrators
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


CREATE TABLE IF NOT EXISTS library.users
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


CREATE TABLE IF NOT EXISTS library.books
(
    id           SERIAL        NOT NULL PRIMARY KEY,
    name         VARCHAR(50)   NOT NULL,
    author       VARCHAR(50)   NOT NULL,
    typ          VARCHAR(50)   NOT NULL,
    description  VARCHAR(1000) NOT NULL,
    borrow_until timestamp              DEFAULT NULL,
    user_id      BIGINT                 DEFAULT NULL,
    created_at   timestamp     NOT NULL DEFAULT current_timestamp,
    updated_at   timestamp     NOT NULL DEFAULT current_timestamp,
    deleted_at   timestamp              DEFAULT NULL,

    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES library.users (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);


INSERT INTO library.administrators (admin_name, password_hash, password_salt, root)
VALUES ('Admin', 'c608b436bf663de7d6c62c13411adf8afdf9bbdf13cf682d781bd7c7da6cc578', 'HvjnxoqoyRVH6uKhpT2yyHqKPKY2VeSc',
        TRUE),                                      -- heslo: Admin
       ('PatrikVelcicky', 'c47a6c22ad4fc87cee495678d1e2639d72e44835e47c33760140bed9803e1935',
        'eZ906QesJWDNOPQJB7jBKq9zpIzGoXQg', FALSE), -- heslo: heslo
       ('AkosKappel', '8a0b87198b284d6da964d93ffe6808d27b75851c7d3d3e0a030ba46a1262a437',
        'q79AUZ6tpyZC1tOv971JwD0VavhWVk2n', FALSE); -- heslo: heslo


INSERT INTO library.users (user_name, address, psc, city, phone_number)
VALUES ('Patrik Velcicky', 'Svitavska 903/6', '96501', 'Ziar nad Hronom', '+421918070678'),
       ('Akos Kappel', 'Svitavska 903/10', '96501', 'Ziar nad Hronom', '+421918071679'),
       ('James Bond', 'Baker street', '00007', 'London', '0918273465'),
       ('Tony Stark', 'Wold Street', '12345', 'New York', '0987654321'),
       ('Bruce Banner', '', '69420', 'Los Angeles', '0908765123'),
       ('Steve Rogers', '', '99999', 'Bratislava', '+421945287534'),
       ('Nick Fury', '', '25025', 'Paris', '+421985462537'),
       ('Scott Lang', '', '75421', 'Rome', '+421942571598'),
       ('Peter Parker', '', '23579', 'Berlin', '+421953276618'),
       ('Natasha Romanoff', '', '06660', 'Moskva', '+421977514236'),
       ('Clint Barton', '', '98765', 'Nitra', '+421911245755'),
       ('Wanda Maximoff', '', '11111', 'Trnava', '+421923665875');


INSERT INTO library.books (name, author, typ, description)
VALUES ('Anna Karenina', 'Leo Tolstoy', 'Novel',
        'Any fan of stories that involve juicy subjects like adultery, gambling, marriage plots, and, well, Russian feudalism, would instantly place Anna Karenina at the peak of their “greatest novels” list. And that’s exactly the ranking that publications like Time magazine have given the novel since it was published in its entirety in 1878. Written by Russian novelist Leo Tolstoy, the eight-part towering work of fiction tells the story of two major characters: a tragic, disenchanted housewife, the titular Anna, who runs off with her young lover, and a lovestruck landowner named Konstantin Levin, who struggles in faith and philosophy.'),
       ('1984', 'George Orwell', 'Novel',
        'Orwell’s imagination of what a future society might look like at its worst has some shocking similarities to modern times. In this dystopian tale, mindless obedience rules, and as the main character finds himself straying, the regime crushes in. Although written in 1949, Orwell makes indirect references to “fake news,” “facetime,” “social media,” and more. Big Brother is watching!'),
       ('Don Quixote', 'Miguel de Cervantes', 'Novel',
        'Miguel de Cervantes’s Don Quixote, perhaps the most influential and well-known work of Spanish literature, was first published in full in 1615. The novel, which is very regularly regarded as one of the best literary works of all time, tells the story of a man who takes the name “Don Quixote de la Mancha” and sets off in a fit of obsession over romantic novels about chivalry to revive the custom and become a hero himself. The character of Don Quixote has become an idol and somewhat of an archetypal character, influencing many major works of art, music, and literature since the novel’s publication.'),
       ('Station Eleven', 'Emily St. John Mandel', 'Fiction',
        'An audacious, darkly glittering novel set in the eerie days of civilization’s collapse, Station Eleven tells the spellbinding story of a Hollywood star, his would-be savior, and a nomadic group of actors roaming the scattered outposts of the Great Lakes region, risking everything for art and humanity.'),
       ('Stratené mesto', 'Philip Reeve', 'Fiction',
        'Prímerie je len prchavou ilúziou. Tom Nathsworthy s dcérou Wren opustili vzdušné. Prišli na neuveriteľné tajomstvo, ktoré sa pred zrakom ľudstva ukrývalo celé roky. Londýn je nažive! V troskách kedysi veľkolepého mesta totiž tepe nenápadný, no úžasný život. Napriek tomu, že sa tu cítia relatívne v bezpečí, vojnový front ich dobieha. Mobilné mestá opäť vyrazili do boja. A Londýnčania sa domnievajú, že vlastnia kľúč k ukončeniu vojny, ktorá požiera celý svet. Stihnú ho však použiť?'),
       ('To Kill a Mockingbird', 'Harper Lee', 'Novel',
        'Harper Lee, believed to be one of the most influential authors to have ever existed, famously published only a single novel (up until its controversial sequel was published in 2015 just before her death). Lee’s To Kill a Mockingbird was published in 1960 and became an immediate classic of literature. The novel examines racism in the American South through the innocent wide eyes of a clever young girl named Jean Louise (“Scout”) Finch. Its iconic characters, most notably the sympathetic and just lawyer and father Atticus Finch, served as role models and changed perspectives in the United States at a time when tensions regarding race were high.'),
       ('One Hundred Years of Solitude', 'Gabriel Garcia Marquez', 'Fiction',
        'One of the 20th century’s enduring works, One Hundred Years of Solitude is a widely beloved and acclaimed novel known throughout the world, and the ultimate achievement in a Nobel Prize–winning career. The novel tells the story of the rise and fall of the mythical town of Macondo through the history of the Buendía family. It is a rich and brilliant chronicle of life and death, and the tragicomedy of humankind. In the noble, ridiculous, beautiful, and tawdry story of the Buendía family, one sees all of humanity, just as in the history, myths, growth, and decay of Macondo, one sees all of Latin America.'),
       ('Harry Potter and the Philosopher’s Stone', 'J.K. Rowling', 'Fantasy',
        'Harry Potter has never even heard of Hogwarts when the letters start dropping on the doormat at number four, Privet Drive. Addressed in green ink on yellowish parchment with a purple seal, they are swiftly confiscated by his grisly aunt and uncle. Then, on Harry’s eleventh birthday, a great beetle-eyed giant of a man called Rubeus Hagrid bursts in with some astonishing news: Harry Potter is a wizard, and he has a place at Hogwarts School of Witchcraft and Wizardry. An incredible adventure is about to begin!'),
       ('Harry Potter and the Chamber of Secrets', 'J.K. Rowling', 'Fantasy',
        'Harry Potter’s summer has included the worst birthday ever, doomy warnings from a house-elf called Dobby, and rescue from the Dursleys by his friend Ron Weasley in a magical flying car! Back at Hogwarts School of Witchcraft And Wizardry for his second year, Harry hears strange whispers echo through empty corridors – and then the attacks start. Students are found as though turned to stone … Dobby’s sinister predictions seem to be coming true.'),
       ('Harry Potter and the Prisoner of Azkaban', 'J.K. Rowling', 'Fantasy',
        'When the Knight Bus crashes through the darkness and screeches to a halt in front of him, it’s the start of another far from ordinary year at Hogwarts for Harry Potter. Sirius Black, escaped mass-murderer and follower of Lord Voldemort, is on the run – and they say he is coming after Harry. In his first ever Divination class, Professor Trelawney sees an omen of death in Harry’s tea leaves... But perhaps most terrifying of all are the Dementors patrolling the school grounds, with their soul-sucking kiss.'),
       ('Harry Potter and the Goblet of Fire', 'J.K. Rowling', 'Fantasy',
        'The Triwizard Tournament is to be held at Hogwarts. Only wizards who are over seventeen are allowed to enter – but that doesn’t stop Harry dreaming that he will win the competition. Then at Hallowe’en, when the Goblet of Fire makes its selection, Harry is amazed to find his name is one of those that the magical cup picks out. He will face death-defying tasks, dragons and Dark wizards, but with the help of his best friends, Ron and Hermione, he might just make it through – alive!'),
       ('Harry Potter and the Order of the Phoenix', 'J.K. Rowling', 'Fantasy',
        'Dark times have come to Hogwarts. After the Dementors’ attack on his cousin Dudley, Harry Potter knows that Voldemort will stop at nothing to find him. There are many who deny the Dark Lord’s return, but Harry is not alone: a secret Order gathers at Grimmauld Place to fight against the Dark forces. Harry must allow Professor Snape to teach him how to protect himself from Voldemort’s savage assaults on his mind. But they are growing stronger by the day and Harry is running out of time.'),
       ('Harry Potter and the Half-Blood Prince', 'J.K. Rowling', 'Fantasy',
        'When Dumbledore arrives at Privet Drive one summer night to collect Harry Potter, his wand hand is blackened and shrivelled, but he does not reveal why. Secrets and suspicion are spreading through the wizarding world, and Hogwarts itself is not safe. Harry is convinced that Malfoy bears the Dark Mark: there is a Death Eater amongst them. Harry will need powerful magic and true friends as he explores Voldemort’s darkest secrets, and Dumbledore prepares him to face his destiny.'),
       ('Harry Potter and the Deathly Hallows', 'J.K. Rowling', 'Fantasy',
        'As he climbs into the sidecar of Hagrid’s motorbike and takes to the skies, leaving Privet Drive for the last time, Harry Potter knows that Lord Voldemort and the Death Eaters are not far behind. The protective charm that has kept Harry safe until now is broken, but he cannot keep hiding. The Dark Lord is breathing fear into everything Harry loves, and to stop him Harry will have to find and destroy the remaining Horcruxes. The final battle must begin – Harry must stand and face his enemy.'),
       ('Hamlet', 'William Shakespeare', 'Fiction',
        'The Tragedy of Hamlet, Prince of Denmark, or more simply Hamlet, is a tragedy by William Shakespeare, believed to have been written between 1599 and 1601. The play, set in Denmark, recounts how Prince Hamlet exacts revenge on his uncle Claudius, who has murdered Hamlet''s father, the King, and then taken the throne and married Gertrude, Hamlet''s mother. The play vividly charts the course of real and feigned madness—from overwhelming grief to seething rage—and explores themes of treachery, revenge, incest, and moral corruption.'),
       ('Romeo and Juliet', 'William Shakespeare', 'Fiction',
        'This lyrical tragedy of two star-crossed lovers and their feuding families is one of the world''s most famous love stories.'),
       ('The Lord of the Rings', 'J. R. R. Tolkien', 'Novel',
        'The Lord of the Rings is an epic high fantasy novel by the English author and scholar J. R. R. Tolkien. Set in Middle-earth, the world at some distant time in the past, the story began as a sequel to Tolkien''s 1937 children''s book The Hobbit, but eventually developed into a much larger work.'),
       ('The Lord of the Rings', 'J. R. R. Tolkien', 'Novel',
        'The Lord of the Rings is an epic high fantasy novel by the English author and scholar J. R. R. Tolkien. Set in Middle-earth, the world at some distant time in the past, the story began as a sequel to Tolkien''s 1937 children''s book The Hobbit, but eventually developed into a much larger work.'),
       ('The Hobbit, or There and Back Again', 'J. R. R. Tolkien', 'Novel',
        'The Hobbit, or There and Back Again is a children''s fantasy novel by English author J. R. R. Tolkien. It was published on 21 September 1937 to wide critical acclaim, being nominated for the Carnegie Medal and awarded a prize from the New York Herald Tribune for best juvenile fiction.'),
       ('The Hobbit, or There and Back Again', 'J. R. R. Tolkien', 'Novel',
        'The Hobbit, or There and Back Again is a children''s fantasy novel by English author J. R. R. Tolkien. It was published on 21 September 1937 to wide critical acclaim, being nominated for the Carnegie Medal and awarded a prize from the New York Herald Tribune for best juvenile fiction.');


CREATE OR REPLACE FUNCTION trigger_set_timestamp()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER set_timestamp
    BEFORE UPDATE
    ON library.administrators
    FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();


CREATE TRIGGER set_timestamp
    BEFORE UPDATE
    ON library.users
    FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();


CREATE TRIGGER set_timestamp
    BEFORE UPDATE
    ON library.books
    FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

