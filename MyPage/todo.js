const toDoForm = document.getElementById("todo-form");
const toDoInput = toDoForm.querySelector("#todo-form input");
const toDoList = document.getElementById("todo-list");

const TODOS_KEY = "todos";

let toDos = [];

function saveToDos() {
    localStorage.setItem(TODOS_KEY, JSON.stringify(toDos)); //JSON.stringify() === 자바스크립트의 object나 array등을 string 변환
}

function deleteToDo(event) { //이 함수는 toDo를 삭제함
    const li = event.target.parentElement;
    li.remove();
    toDos = toDos.filter((toDo) => toDo.id !== parseInt(li.id));
    saveToDos();
}

function paintTodDo(newTodo) {
    const li = document.createElement("li"); //li를 생성
    li.id = newTodo.id;
    const span = document.createElement("span"); //span 생성
    span.innerText = newTodo.text; //텍스트를 span 내부에 넣음
    //span 안에 넣은 새로운 텍스트는 사용자가 form에서 우리에게 준 newTodo값
    const button = document.createElement("button"); //button 생성
    button.innerText = "❌"; //button의 텍스트를 변경
    button.addEventListener("click", deleteToDo);
    li.appendChild(span); //span을 li 내부에 집어넣음
    li.appendChild(button);
    toDoList.appendChild(li);
    //뭔가가 클릭되었다는 것을 어떻게 확인할 수 있나? === eventListener
}

function handleToDoSubmit(event) {
    event.preventDefault();
    const newTodo = toDoInput.value;
    toDoInput.value = ""; //우리는 input을 비우고
    const newTodoObj = {
        text:newTodo,
        id: Date.now(),
    };
    toDos.push(newTodoObj); //그 텍스트(newTodo)를 toDos array에 푸쉬하고
    paintTodDo(newTodoObj); //그 다음에는, 화면에 toDo를 그려주고
    saveToDos(); //toDo들을 저장
}

toDoForm.addEventListener("submit", handleToDoSubmit); //사용자가 form을 submit하면

const savedToDos = localStorage.getItem(TODOS_KEY);

if (savedToDos !== null) {
    const parsedToDos = JSON.parse(savedToDos); //localStorage에서 갖고 온 string을 JavaScript object로 변환
    toDos = parsedToDos;
    parsedToDos.forEach(paintTodDo);
    //자바스크립트는 array에 있는 각각의 item에 대해 function을 실행할 수 있게 해줌
    //forEach는 각 item에 대해 function을 실행하게 해줌

}