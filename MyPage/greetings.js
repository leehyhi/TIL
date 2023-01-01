const loginForm = document.querySelector("#login-form");
const loginInput = document.querySelector("#login-form input");
const greeting = document.querySelector("#greeting");

const HIDDEN_CLASSNAME = "hidden";
const USERNAME_KEY = "username";

function onLoginSubmit(event) {
    event.preventDefault(); //브라우저가 새로고침이 되지않게 하는 이벤트
    loginForm.classList.add(HIDDEN_CLASSNAME); //hidden이라는 class name 을 더해줘서 form을 숨김
    const username = loginInput.value; 
    localStorage.setItem(USERNAME_KEY, username); //유저가 이름을 제출할 때 해당 제출 명령을 저장함
    paintGreetings(username);
}

function paintGreetings(username) {
    greeting.innerText = "hello " + username;
    greeting.classList.remove(HIDDEN_CLASSNAME);
}
 /*submit을 하게되면 브라우저의 기본 동작을
막게 됨, 브라우저가 submit 할 때 원래 하는 동작을 안하게 됨, 대신, 그 기본 동작을 멈추고 우리가 원하는 대로 할
수 있게 됨 */
const savedUsername = localStorage.getItem(USERNAME_KEY);

if(savedUsername  === null) {
    // Show the form, 즉 local storage에 유저정보가 없을 때
    loginForm.classList.remove(HIDDEN_CLASSNAME);
    loginForm.addEventListener("submit", onLoginSubmit);
} else {
    // Show the greeting
    paintGreetings(savedUsername);
}
