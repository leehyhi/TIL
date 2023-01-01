const clock = document.querySelector("h2#clock");

function getClock() {
    const date = new Date(); //호출 하는 당시의 현재 날짜랑 시간을 알려줌
    const hours = String(date.getHours()).padStart(2, "0");
    const minutes = String(date.getMinutes()).padStart(2, "0");
    const seconds = String(date.getSeconds()).padStart(2, "0");
    clock.innerText = hours + ":" + minutes + ":" + seconds;
}

getClock();
setInterval(getClock, 1000); //function을 계속해서 호출하는건 setInterval()