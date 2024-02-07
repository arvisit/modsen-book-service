INSERT INTO books(id, title, isbn, genre_id, description, author) VALUES
    ('f4b27fe4-0ea1-423d-add6-daa2ee63802f', 'Spring Microservices in Action', '978-1-617-29695-6', (SELECT id FROM genres WHERE name = 'Computer Science'), 'Guide to Spring Cloud for microservices development', 'Illary Huaylupo Sanchez'),
    ('c79eb50b-2e71-4e98-87ab-2074c7441713', 'Java Persistence with Hibernate', '978-1-617-29045-9', (SELECT id FROM genres WHERE name = 'Computer Science'), 'Guide to Java Persistence and ORM', 'Christian Bauer'),
    ('0319cc17-a6e0-4bfd-a9ef-2ab5cca8abca', 'The Linux Command Line', '978-5-4461-1430-6', (SELECT id FROM genres WHERE name = 'Computer Science'), 'Guide to Linux command line', 'William Shotts');
