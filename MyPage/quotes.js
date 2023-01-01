const quotes = [
    {
        quote: "Good!",
        author: "Lhy",
    },

    {
        quote: "Bed",
        author: "Kim",
    },

    {
        quote: "Nice!",
        author: "Lin",
    },

    {
        quote: "Boom!",
        author: "Bap",
    },

    {
        quote: "Cool!",
        author: "June",
    },

    {
        quote: "Oh!",
        author: "Chan",
    },

    {
        quote: "OMG!",
        author: "Chu",
    },

    {
        quote: "Gone!",
        author: "Park",
    },

    {
        quote: "Away!",
        author: "Ryu",
    },

    {
        quote: "Bamm!",
        author: "Song",
    }
];

const quote = document.querySelector("#quote span:first-child");
const author = document.querySelector("#quote span:last-child");
const todaysQuote = quotes[Math.floor(Math.random() * quotes.length)];

quote.innerText = todaysQuote.quote;
author.innerText = todaysQuote.author;