const data = [
  {
    id: 1,
    title: "The Lord of the Rings",
    publicationDate: "1954-07-29",
    author: "J. R. R. Tolkien",
    genres: [
      "fantasy",
      "high-fantasy",
      "adventure",
      "fiction",
      "novels",
      "literature",
    ],
    hasMovieAdaptation: true,
    pages: 1216,
    translations: {
      spanish: "El señor de los anillos",
      chinese: "魔戒",
      french: "Le Seigneur des anneaux",
    },
    reviews: {
      goodreads: {
        rating: 4.52,
        ratingsCount: 630994,
        reviewsCount: 13417,
      },
      librarything: {
        rating: 4.53,
        ratingsCount: 47166,
        reviewsCount: 452,
      },
    },
  },
  {
    id: 2,
    title: "The Cyberiad",
    publicationDate: "1965-01-01",
    author: "Stanislaw Lem",
    genres: [
      "science fiction",
      "humor",
      "speculative fiction",
      "short stories",
      "fantasy",
    ],
    hasMovieAdaptation: false,
    pages: 295,
    translations: {},
    reviews: {
      goodreads: {
        rating: 4.16,
        ratingsCount: 11663,
        reviewsCount: 812,
      },
      librarything: {
        rating: 4.13,
        ratingsCount: 2434,
        reviewsCount: 0,
      },
    },
  },
  {
    id: 3,
    title: "Dune",
    publicationDate: "1965-01-01",
    author: "Frank Herbert",
    genres: ["science fiction", "novel", "adventure"],
    hasMovieAdaptation: true,
    pages: 658,
    translations: {
      spanish: "",
    },
    reviews: {
      goodreads: {
        rating: 4.25,
        ratingsCount: 1142893,
        reviewsCount: 49701,
      },
    },
  },
  {
    id: 4,
    title: "Harry Potter and the Philosopher's Stone",
    publicationDate: "1997-06-26",
    author: "J. K. Rowling",
    genres: ["fantasy", "adventure"],
    hasMovieAdaptation: true,
    pages: 223,
    translations: {
      spanish: "Harry Potter y la piedra filosofal",
      korean: "해리 포터와 마법사의 돌",
      bengali: "হ্যারি পটার এন্ড দ্য ফিলোসফার্স স্টোন",
      portuguese: "Harry Potter e a Pedra Filosofal",
    },
    reviews: {
      goodreads: {
        rating: 4.47,
        ratingsCount: 8910059,
        reviewsCount: 140625,
      },
      librarything: {
        rating: 4.29,
        ratingsCount: 120941,
        reviewsCount: 1960,
      },
    },
  },
  {
    id: 5,
    title: "A Game of Thrones",
    publicationDate: "1996-08-01",
    author: "George R. R. Martin",
    genres: ["fantasy", "high-fantasy", "novel", "fantasy fiction"],
    hasMovieAdaptation: true,
    pages: 835,
    translations: {
      korean: "왕좌의 게임",
      polish: "Gra o tron",
      portuguese: "A Guerra dos Tronos",
      spanish: "Juego de tronos",
    },
    reviews: {
      goodreads: {
        rating: 4.44,
        ratingsCount: 2295233,
        reviewsCount: 59058,
      },
      librarything: {
        rating: 4.36,
        ratingsCount: 38358,
        reviewsCount: 1095,
      },
    },
  },
];

// function getBooks() {
//   return data;
// }

// function getBook(id) {
//   return data.find((d) => d.id === id);
// }

// //Destructuring

// const book = getBook(1);

// // const title = book.title;

// const { title, author, pages, publicationDate, genres } = book;

// console.log(title, author, pages, publicationDate, genres);

// // const primaryGenre  = genres[0];

// // const secondaryGenre  = genres[1];

// /*
//  *https://www.freecodecamp.org/news/javascript-rest-vs-spread-operators/
//  */
// //Rest operator
// const [primaryGenre, secondaryGenre, ...otherGeneres] = genres;

// console.log(primaryGenre, secondaryGenre, otherGeneres);

// // Spread operator
// const newGenres = ["epic fatansy", ...genres];

// console.log(newGenres);

// // spread book otherwise book key will override the value
// const updatedBook = {
//   ...book,
//   moviePublicationDate: "2001-12-19",
//   pages: "2010",
// };

// updatedBook;

// //Template literals : https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Template_literals
// const summary = `HI my name is ${book.title} ${2 + 4}`;

// summary;

// // Multi-line strings
// console.log(`string text line 1
//   string text line 2`);

// //optional chaining

// const adventurer = {
//   name: "Alice",
//   cat: {
//     name: "Dinah",
//   },
// };

// const dogName = adventurer.dog?.name;
// console.log(dogName);
// // Expected output: undefined

// // console.log(adventurer.someNonExistentMethod?.());
// // // Expected output: undefined

// // const conditionalVal = adventurer.dog?name ? adventurer.dog.name : ;

// // conditionalVal;

// // Array reduce

// var list = [6, 1, 2, 3, 4];

// const red = list.reduce((prev, ele) => prev + ele, 0);

// red;

// //slice copy the array

// list.slice().sort();

// const newBook = {
//   id: "1",
//   name: "hi",
// };
// const bookAfterAdd = [...data, newBook];
// bookAfterAdd;

// // delete book object

// const bookAfterDelete = bookAfterAdd.filter((book) => book.id !== 3);

// // update book object

// const bookAfterUPdate = bookAfterDelete.map((book) =>
//   book.id == 1 ? {} : book
// );

//Promise

async function getTodos() {
  const res = await fetch("https://jsonplaceholder.typicode.com/todos/1");
  const data = await res.json();
  console.log(data);
  // it will wait tille api complete then it will execute below.
  console.log(2343);
}
getTodos();
console.log(190);
