% Book Database
book('To Kill a Mockingbird', 'Harper Lee', fiction, english, 1960, 281, medium, 'young_adult').
book('1984', 'George Orwell', dystopian, english, 1949, 328, medium, 'adult').
book('The Great Gatsby', 'F. Scott Fitzgerald', fiction, english, 1925, 180, short, 'adult').
book('Moby Dick', 'Herman Melville', adventure, english, 1851, 635, long, 'adult').
book('Pride and Prejudice', 'Jane Austen', romance, english, 1813, 279, medium, 'young_adult').
book('War and Peace', 'Leo Tolstoy', historical, russian, 1869, 1225, long, 'adult').
book('The Catcher in the Rye', 'J.D. Salinger', fiction, english, 1951, 214, medium, 'young_adult').
book('Harry Potter and the Sorcerer\'s Stone', 'J.K. Rowling', fantasy, english, 1997, 309, medium, 'children').
book('The Hobbit', 'J.R.R. Tolkien', fantasy, english, 1937, 310, medium, 'children').
book('Don Quixote', 'Miguel de Cervantes', adventure, spanish, 1605, 863, long, 'adult').

% Query Rules
recommend_by_genre(Genre, Book) :-
    book(Book, _, Genre, _, _, _, _, _).

recommend_for_age(AgeGroup, Book) :-
    book(Book, _, _, _, _, _, _, Ag
