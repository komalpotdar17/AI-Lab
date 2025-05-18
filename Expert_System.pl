% ---------- Book Facts ----------
% Format: book(Name, Author, Genre, Language, Year, Publisher, Length, AgeGroup).


% Additional book facts: name, author, genre, language, year, publisher, length, age_group
book('The Secret Garden', 'Frances Hodgson Burnett', fiction, english, 1911, harper, medium, kids).
book('Anne of Green Gables', 'L.M. Montgomery', fiction, english, 1908, lothrop, medium, kids).
book('Little Women', 'Louisa May Alcott', fiction, english, 1868, roberts, long, teens).
book('The Call of the Wild', 'Jack London', adventure, english, 1903, macmillan, short, adults).
book('Treasure Island', 'Robert Louis Stevenson', adventure, english, 1883, cassell, medium, kids).
book('Robinson Crusoe', 'Daniel Defoe', adventure, english, 1719, william, long, adults).
book('Black Beauty', 'Anna Sewell', fiction, english, 1877, jarrold, medium, kids).
book('Gulliver\'s Travels', 'Jonathan Swift', adventure, english, 1726, benj, medium, adults).
book('The Scarlet Letter', 'Nathaniel Hawthorne', fiction, english, 1850, ticknor, medium, adults).
book('Of Mice and Men', 'John Steinbeck', fiction, english, 1937, covici, short, adults).
book('A Tale of Two Cities', 'Charles Dickens', historical, english, 1859, chapman, medium, adults).
book('David Copperfield', 'Charles Dickens', fiction, english, 1850, bradbury, long, adults).
book('Oliver Twist', 'Charles Dickens', fiction, english, 1837, bentley, medium, adults).
book('Great Expectations', 'Charles Dickens', fiction, english, 1861, chapman, long, adults).
book('Hard Times', 'Charles Dickens', fiction, english, 1854, bradbury, medium, adults).
book('A Christmas Carol', 'Charles Dickens', fiction, english, 1843, chapman, short, adults).
book('Bleak House', 'Charles Dickens', fiction, english, 1853, bradbury, long, adults).
book('The Hound of the Baskervilles', 'Arthur Conan Doyle', mystery, english, 1902, george, medium, adults).
book('The Adventures of Sherlock Holmes', 'Arthur Conan Doyle', mystery, english, 1892, george, medium, adults).
book('The Three Musketeers', 'Alexandre Dumas', adventure, french, 1844, baudry, long, adults).
book('The Count of Monte Cristo', 'Alexandre Dumas', adventure, french, 1844, baudry, long, adults).
book('The Man in the Iron Mask', 'Alexandre Dumas', adventure, french, 1850, baudry, long, adults).
book('The Phantom of the Opera', 'Gaston Leroux', horror, french, 1910, pierre, medium, adults).
book('Journey to the Center of the Earth', 'Jules Verne', science_fiction, french, 1864, pierre, medium, teens).
book('Twenty Thousand Leagues Under the Sea', 'Jules Verne', science_fiction, french, 1870, pierre, long, adults).
book('Around the World in Eighty Days', 'Jules Verne', adventure, french, 1873, hetzel, medium, adults).
book('The War of the Worlds', 'H.G. Wells', science_fiction, english, 1898, heinemann, medium, adults).
book('The Invisible Man', 'H.G. Wells', science_fiction, english, 1897, cockerell, short, adults).
book('The Island of Doctor Moreau', 'H.G. Wells', science_fiction, english, 1896, heinemann, medium, adults).
book('Brave New World Revisited', 'Aldous Huxley', dystopian, english, 1958, chatto, short, adults).
book('A Room with a View', 'E.M. Forster', romance, english, 1908, edward, medium, adults).
book('Howard\'s End', 'E.M. Forster', fiction, english, 1910, edward, medium, adults).
book('The Remains of the Day', 'Kazuo Ishiguro', fiction, english, 1989, faber, medium, adults).
book('White Teeth', 'Zadie Smith', fiction, english, 2000, penguin, medium, adults).

% ---------- Recommendation System ----------

recommend_books :-
    write('Welcome to the Book Recommendation System!'), nl,
    write('Please answer the following questions to get a book recommendation.'), nl,

    ask_previous_book(PreviousBook),
    ( PreviousBook \= 'none' ->
        recommend_similar_book(PreviousBook)
    ;
        ask_author(Author),
        ask_genre(Genre),
        ask_language(Language),
        ask_year(Year),
        ask_publisher(Publisher),
        ask_length(Length),
        ask_age_group(AgeGroup),

        nl, write('Here are the recommended books based on your preferences:'), nl,
        find_books(Author, Genre, Language, Year, Publisher, Length, AgeGroup)
    ).

ask_previous_book(PreviousBook) :-
    write('Do you want recommendations based on a previously read book? (Type the book name in quotes or type "none") '), nl,
    read(UserInput),
    process_input(UserInput, PreviousBook).

process_input('any', any) :- !.
process_input(UserInput, UserInput).

recommend_similar_book(PreviousBook) :-
    book(PreviousBook, Author, Genre, Language, Year, Publisher, Length, AgeGroup),
    find_similar_book(PreviousBook, Author, Genre, Language, Year, Publisher, Length, AgeGroup).

find_similar_book(PreviousBook, Author, Genre, Language, Year, Publisher, Length, AgeGroup) :-
    findall(
        [Name, SimilarityScore],
        ( book(Name, A, G, L, Y, P, Len, AG),
          Name \= PreviousBook,
          similarity_score([Author, Genre, Language, Year, Publisher, Length, AgeGroup], [A, G, L, Y, P, Len, AG], SimilarityScore)
        ),
        SimilarBooks),
    sort_similar_books(SimilarBooks, SortedBooks),
    SortedBooks = [[BestMatch, _] | _],
    write('Based on your previously read book, we recommend: '), write(BestMatch), nl.

find_books(Author, Genre, Language, Year, Publisher, Length, AgeGroup) :-
    findall(
        [Name, MatchScore],
        ( book(Name, A, G, L, Y, P, Len, AG),
          similarity_score([Author, Genre, Language, Year, Publisher, Length, AgeGroup], [A, G, L, Y, P, Len, AG], MatchScore)
        ),
        MatchedBooks),
    sort_similar_books(MatchedBooks, SortedBooks),
    ( SortedBooks = [[BestMatch, _] | _] ->
        write('We recommend: '), write(BestMatch), nl
    ;
        write('No books match your preferences.'), nl
    ).

% ---------- Sort for Similarity Score (Descending) ----------

sort_similar_books(Unsorted, Sorted) :-
    predsort(compare_scores_desc, Unsorted, Sorted).

compare_scores_desc(Order, [_, S1], [_, S2]) :-
    compare(OrderRev, S2, S1),
    reverse_order(OrderRev, Order).

reverse_order(<, >).
reverse_order(>, <).
reverse_order(=, =).

% ---------- Similarity Comparison ----------

similarity_score([A1, G1, L1, Y1, P1, Len1, AG1], [A2, G2, L2, Y2, P2, Len2, AG2], Score) :-
    compare_attribute(A1, A2, AuthorScore),
    compare_attribute(G1, G2, GenreScore),
    compare_attribute(L1, L2, LanguageScore),
    compare_year(Y1, Y2, YearScore),
    compare_attribute(P1, P2, PublisherScore),
    compare_attribute(Len1, Len2, LengthScore),
    compare_attribute(AG1, AG2, AgeGroupScore),
    Score is AuthorScore + GenreScore + LanguageScore + YearScore + PublisherScore + LengthScore + AgeGroupScore.

compare_attribute(any, _, 1) :- !.
compare_attribute(_, any, 1) :- !.
compare_attribute(X, X, 1) :- !.
compare_attribute(_, _, 0).

compare_year(any, _, 1) :- !.
compare_year(_, any, 1) :- !.
compare_year(Y1, Y2, 1) :- Y1 = Y2, !.
compare_year(Y1, Y2, 0.5) :- abs(Y1 - Y2) =< 5, !.
compare_year(_, _, 0).

% ---------- Ask User Preferences ----------

ask_author(Author) :-
    write('Preferred author? (Type name in quotes or "any") '), nl,
    read(UserInput),
    process_input(UserInput, Author).

ask_genre(Genre) :-
    write('Preferred genre? (e.g., fiction, fantasy, romance, or "any") '), nl,
    read(UserInput),
    process_input(UserInput, Genre).

ask_language(Language) :-
    write('Preferred language? (e.g., english, spanish, or "any") '), nl,
    read(UserInput),
    process_input(UserInput, Language).

ask_year(Year) :-
    write('Preferred publication year? (e.g., 1984 or "any") '), nl,
    read(UserInput),
    process_input(UserInput, Year).

ask_publisher(Publisher) :-
    write('Preferred publisher? (e.g., penguin, harper, or "any") '), nl,
    read(UserInput),
    process_input(UserInput, Publisher).

ask_length(Length) :-
    write('Preferred book length? (short, medium, long, or "any") '), nl,
    read(UserInput),
    process_input(UserInput, Length).

ask_age_group(AgeGroup) :-
    write('Age group? (kids, teens, adults, or "any") '), nl,
    read(UserInput),
    process_input(UserInput, AgeGroup).

% ---------- Auto-start in SWI-Prolog ----------

:- initialization(recommend_books).
